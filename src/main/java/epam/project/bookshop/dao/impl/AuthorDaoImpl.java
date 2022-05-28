package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.AuthorDao;
import epam.project.bookshop.entity.Author;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDaoImpl implements AuthorDao {

    private static AuthorDaoImpl instance;
    private static ReentrantLock lock=new ReentrantLock();
    private static AtomicBoolean isCreated=new AtomicBoolean();

    public static AuthorDaoImpl getInstance() {
        if (!isCreated.get()){
            lock.lock();
            try{
                if (instance == null){
                    instance = new AuthorDaoImpl();
                    isCreated.set(true);
                }
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public Optional<Author> findByFirstName(String firstName) {
        return Optional.empty();
    }

    @Override
    public Optional<Author> findByLastName(String lastName) {
        return Optional.empty();
    }

    @Override
    public Optional<Author> findByFio(String firstName, String lastName) {
        return Optional.empty();
    }

    @Override
    public boolean save(Author author) {
        return false;
    }

    @Override
    public boolean updated(Author author) {
        return false;
    }

    @Override
    public boolean deleteById(Author author) {
        return false;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() {
        return null;
    }
}
