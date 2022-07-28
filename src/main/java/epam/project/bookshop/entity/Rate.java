package epam.project.bookshop.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class Rate extends BaseDomain{

    private Long userId;

    private Long bookId;

    private Long rate;

    public Rate(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
        Rate rate1 = (Rate) o;
        return Objects.equals(userId, rate1.userId) && Objects.equals(bookId, rate1.bookId) && Objects.equals(rate, rate1.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, bookId, rate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Rate.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("bookId=" + bookId)
                .add("rate=" + rate)
                .toString();
    }
}
