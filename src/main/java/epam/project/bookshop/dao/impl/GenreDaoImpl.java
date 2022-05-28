package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.GenreDao;
import epam.project.bookshop.entity.Genre;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenreDaoImpl implements GenreDao {

    static GenreDaoImpl instance;
    static ReentrantLock lock = new ReentrantLock();
    static AtomicBoolean isCreated = new AtomicBoolean();

    public static GenreDaoImpl getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new GenreDaoImpl();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean save(Genre genre) {
        return false;
    }

    @Override
    public boolean updated(Genre genre) {
        return false;
    }

    @Override
    public boolean deleteById(Genre genre) {
        return false;
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Genre> findAll() {
        return null;
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return Optional.empty();
    }
}
