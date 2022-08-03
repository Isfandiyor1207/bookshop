package epam.project.bookshop.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class Book extends BaseDomain {

    private String name;

    private String isbn;

    private String publisher;

    private int publishingYear;

    private Long price;

    private Long numberOfBooks;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(Long numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return publishingYear == book.publishingYear && Objects.equals(name, book.name) && Objects.equals(isbn, book.isbn) && Objects.equals(publisher, book.publisher) && Objects.equals(price, book.price) && Objects.equals(numberOfBooks, book.numberOfBooks) && Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, isbn, publisher, publishingYear, price, numberOfBooks, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("isbn='" + isbn + "'")
                .add("publisher='" + publisher + "'")
                .add("publishingYear=" + publishingYear)
                .add("price=" + price)
                .add("numberOfBooks=" + numberOfBooks)
                .add("description='" + description + "'")
                .toString();
    }
}
