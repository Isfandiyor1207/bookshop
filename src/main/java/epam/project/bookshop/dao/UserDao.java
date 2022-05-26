package epam.project.bookshop.dao;

import epam.project.bookshop.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User>{

    Optional<User> findByUsername(String username);

}
