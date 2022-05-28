package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.UserDao;
import epam.project.bookshop.entity.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDaoImpl implements UserDao {

    static UserDaoImpl instance;
    static ReentrantLock lock = new ReentrantLock();
    static AtomicBoolean isCreated = new AtomicBoolean();

    public static UserDaoImpl getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new UserDaoImpl();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean updated(User user) {
        return false;
    }

    @Override
    public boolean deleteById(User user) {
        return false;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
}
