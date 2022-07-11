package epam.project.bookshop.mapper;

import epam.project.bookshop.dto.AuthorDto;
import epam.project.bookshop.entity.Author;
import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static epam.project.bookshop.command.ParameterName.*;

public class AuthorMapper implements BaseMapper<Author>{
    private static final Logger logger= LogManager.getLogger();
    private static final AuthorMapper instance = new AuthorMapper();

    private AuthorMapper(){}

    public static AuthorMapper getInstance() {
        return instance;
    }

    @Override
    public Author resultSetToEntity(ResultSet resultSet) throws DaoException {
        try {
            Author author = new Author();
            author.setId(resultSet.getLong(ID));
            author.setFio(resultSet.getString(AUTHOR_FIO));
            return author;
        } catch (SQLException e) {
            logger.info(e);
            throw new DaoException(e);
        }
    }

    public AuthorDto resultSetToDto(ResultSet resultSet) throws DaoException{
        try {
            AuthorDto authorDto=new AuthorDto();
            authorDto.setId(resultSet.getLong(ID));
            authorDto.setFio(resultSet.getString(AUTHOR_FIO));
            return authorDto;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
