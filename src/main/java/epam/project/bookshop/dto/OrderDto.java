package epam.project.bookshop.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class OrderDto extends GenericDto{

    private UserDto userDto;

    private Long userId;

    private BookDto bookDto;

    private Long bookId;

    private Long orderQuantity;

    private Double orderPrice;

    private boolean delivered;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BookDto getBookDto() {
        return bookDto;
    }

    public void setBookDto(BookDto bookDto) {
        this.bookDto = bookDto;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
        OrderDto orderDto = (OrderDto) o;
        return delivered == orderDto.delivered && Objects.equals(userDto, orderDto.userDto) && Objects.equals(userId, orderDto.userId) && Objects.equals(bookDto, orderDto.bookDto) && Objects.equals(bookId, orderDto.bookId) && Objects.equals(orderQuantity, orderDto.orderQuantity) && Objects.equals(orderPrice, orderDto.orderPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userDto, userId, bookDto, bookId, orderQuantity, orderPrice, delivered);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderDto.class.getSimpleName() + "[", "]")
                .add("userDto=" + userDto)
                .add("userId=" + userId)
                .add("bookDto=" + bookDto)
                .add("bookId=" + bookId)
                .add("orderQuantity=" + orderQuantity)
                .add("orderPrice=" + orderPrice)
                .add("delivered=" + delivered)
                .toString();
    }
}
