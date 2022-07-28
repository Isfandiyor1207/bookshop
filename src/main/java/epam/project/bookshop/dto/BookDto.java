package epam.project.bookshop.dto;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class BookDto extends GenericDto{

    private String name;

    private String isbn;

    private String publisher;

    private int publishingYear;

    private Long price;

    private Long numberOfBooks;

    private Double averageRate;

    private Long numberOfVotedUser;

    private String description;

    private List<GenreDto> genreDtoList;

    private List<AttachmentDto> attachmentDtoList;

    private List<AuthorDto> authorDtoList;

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    public Long getNumberOfVotedUser() {
        return numberOfVotedUser;
    }

    public void setNumberOfVotedUser(Long numberOfVotedUser) {
        this.numberOfVotedUser = numberOfVotedUser;
    }

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

    public List<GenreDto> getGenreDtoList() {
        return genreDtoList;
    }

    public void setGenreDtoList(List<GenreDto> genreDtoList) {
        this.genreDtoList = genreDtoList;
    }

    public List<AttachmentDto> getAttachmentDtoList() {
        return attachmentDtoList;
    }

    public void setAttachmentDtoList(List<AttachmentDto> attachmentDtoList) {
        this.attachmentDtoList = attachmentDtoList;
    }

    public List<AuthorDto> getAuthorDtoList() {
        return authorDtoList;
    }

    public void setAuthorDtoList(List<AuthorDto> authorDtoList) {
        this.authorDtoList = authorDtoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookDto bookDto = (BookDto) o;
        return publishingYear == bookDto.publishingYear && Objects.equals(name, bookDto.name) && Objects.equals(isbn, bookDto.isbn) && Objects.equals(publisher, bookDto.publisher) && Objects.equals(price, bookDto.price) && Objects.equals(numberOfBooks, bookDto.numberOfBooks) && Objects.equals(averageRate, bookDto.averageRate) && Objects.equals(numberOfVotedUser, bookDto.numberOfVotedUser) && Objects.equals(description, bookDto.description) && Objects.equals(genreDtoList, bookDto.genreDtoList) && Objects.equals(attachmentDtoList, bookDto.attachmentDtoList) && Objects.equals(authorDtoList, bookDto.authorDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, isbn, publisher, publishingYear, price, numberOfBooks, averageRate, numberOfVotedUser, description, genreDtoList, attachmentDtoList, authorDtoList);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BookDto.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("isbn='" + isbn + "'")
                .add("publisher='" + publisher + "'")
                .add("publishingYear=" + publishingYear)
                .add("price=" + price)
                .add("numberOfBooks=" + numberOfBooks)
                .add("averageRate=" + averageRate)
                .add("numberOfVotedUser=" + numberOfVotedUser)
                .add("description='" + description + "'")
                .add("genreDtoList=" + genreDtoList)
                .add("attachmentDtoList=" + attachmentDtoList)
                .add("authorDtoList=" + authorDtoList)
                .toString();
    }
}
