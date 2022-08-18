package epam.project.bookshop.pool;

import epam.project.bookshop.service.impl.SendEmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import static epam.project.bookshop.command.ParameterName.*;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static int CAPACITY_OF_QUEUE;
    private static String DATABASE_URL;
    private static String DRIVER;
    private static String PASSWORD_DB;
    private static String USERNAME_DB;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> freeConnection = new LinkedBlockingQueue<>(CAPACITY_OF_QUEUE);
    private BlockingQueue<ProxyConnection> usedConnection = new LinkedBlockingQueue<>(CAPACITY_OF_QUEUE);

    static {
        try {
            init();
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.fatal("Driver not registered: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {
        Properties properties = new Properties();
        properties.put(PASSWORD, PASSWORD_DB);
        properties.put(USER, USERNAME_DB);
        for (int i = 0; i < CAPACITY_OF_QUEUE; i++) {
            try {
                ProxyConnection connection = new ProxyConnection(DriverManager.getConnection(DATABASE_URL, properties));
                freeConnection.add(connection);
            } catch (SQLException e) {
                logger.fatal("Connection with database is failed: " + e);
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private static void init(){
        try (InputStream input = SendEmailService.class.getClassLoader().getResourceAsStream(PROPERTY_CONFIG)) {
            Properties prop = new Properties();
            prop.load(input);

            DATABASE_URL = prop.getProperty("datasource.url");
            USERNAME_DB = prop.getProperty("datasource.username");
            PASSWORD_DB = prop.getProperty("datasource.password");
            DRIVER = prop.getProperty("datasource.driver-class-name");
            CAPACITY_OF_QUEUE = Integer.parseInt(prop.getProperty("connection.capacity"));

        }catch (IOException ex) {
            logger.error(ex);
        }
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            usedConnection.put(connection);
        } catch (InterruptedException e) {
            logger.fatal("Get Connection is failed: " + e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            if (connection instanceof ProxyConnection){
                usedConnection.remove(connection);
                freeConnection.put((ProxyConnection) connection);
            }
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < CAPACITY_OF_QUEUE; i++) {
            try {
                freeConnection.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error("Connection is not destroyed: " + e);
            }
        }
        deregisterDriver();
    }

    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Can't access database to deregister driver: ", e);
            }
        });
    }

}
