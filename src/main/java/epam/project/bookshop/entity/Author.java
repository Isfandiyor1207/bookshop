package epam.project.bookshop.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class Author extends BaseDomain {

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
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return Objects.equals(fio, author.fio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fio);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Author.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("fio='" + fio + "'")
                .toString();
    }
}
