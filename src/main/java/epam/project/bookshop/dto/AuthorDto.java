package epam.project.bookshop.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class AuthorDto extends GenericDto{

    private String fio;

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(fio, authorDto.fio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fio);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorDto.class.getSimpleName() + "[", "]")
                .add("fio='" + fio + "'")
                .toString();
    }
}
