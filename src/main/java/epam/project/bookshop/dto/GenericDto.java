package epam.project.bookshop.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class GenericDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericDto that = (GenericDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GenericDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
