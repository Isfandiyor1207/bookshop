package epam.project.bookshop.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class Order extends BaseDomain {

    private Long bookId;

    private Long userId;

    private Long orderQuantity;

    private Double orderPrice;

    private boolean delivered;

    public Order() {
    }

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

    public Long getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Long orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return delivered == order.delivered && Objects.equals(bookId, order.bookId) && Objects.equals(userId, order.userId) && Objects.equals(orderQuantity, order.orderQuantity) && Objects.equals(orderPrice, order.orderPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bookId, userId, orderQuantity, orderPrice, delivered);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("bookId=" + bookId)
                .add("userId=" + userId)
                .add("orderQuantity=" + orderQuantity)
                .add("orderPrice=" + orderPrice)
                .add("delivered=" + delivered)
                .toString();
    }
}
