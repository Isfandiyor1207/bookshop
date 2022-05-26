package epam.project.bookshop.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book extends BaseDomain{

    String name;

    String isbn;

    String publisher;

    String publishingYear;

    String genreId;

    Long authorId;

    Long attachmentId;
}
