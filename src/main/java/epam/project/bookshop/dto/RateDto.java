package epam.project.bookshop.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class RateDto extends GenericDto {

    private Long bookId;

    private Long userId;

    private Long rate;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RateDto rateDto = (RateDto) o;
        return Objects.equals(bookId, rateDto.bookId) && Objects.equals(userId, rateDto.userId) && Objects.equals(rate, rateDto.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bookId, userId, rate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RateDto.class.getSimpleName() + "[", "]")
                .add("bookId=" + bookId)
                .add("userId=" + userId)
                .add("rate=" + rate)
                .toString();
    }
}
