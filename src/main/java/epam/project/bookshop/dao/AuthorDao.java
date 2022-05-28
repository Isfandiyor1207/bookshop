package epam.project.bookshop.dao;

import epam.project.bookshop.entity.Author;

import java.util.Optional;

public interface AuthorDao extends BaseDao<Author> {

    Optional<Author> findByFirstName(String firstName);

    Optional<Author> findByLastName(String lastName);

    Optional<Author> findByFio(String firstName, String lastName);
}
