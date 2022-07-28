package epam.project.bookshop.mapper;

import epam.project.bookshop.dto.GenreDto;
import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static epam.project.bookshop.command.ParameterName.GENRE_NAME;
import static epam.project.bookshop.command.ParameterName.ID;

public class GenreMapper implements BaseMapper<GenreDto> {

    private static final GenreMapper instance = new GenreMapper();

    private GenreMapper(){}

    public static GenreMapper getInstance() {
        return instance;
    }

    @Override
    public GenreDto resultSetToDto(ResultSet resultSet) throws DaoException {
        GenreDto genre = new GenreDto();
        try {
            genre.setId(resultSet.getLong(ID));
            genre.setName(resultSet.getString(GENRE_NAME));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return genre;
    }
}
