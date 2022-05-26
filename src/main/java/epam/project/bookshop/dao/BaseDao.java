package epam.project.bookshop.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    boolean save(T t);

    boolean updated(T t);

    boolean deleteById(T t);

    Optional<T> findById(Long id);

    List<T> findAll();

}
