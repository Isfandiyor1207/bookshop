package epam.project.bookshop.service;

public interface GenericService<T> {
    boolean add(T entity);

    boolean deleteById(Long id);

    void update(T entity);

}
