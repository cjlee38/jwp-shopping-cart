package woowacourse.shoppingcart.domain.order;

import java.util.List;

public class Order {

    private final Long id;
    private final List<OrderDetail> orderDetails;

    public Order(List<OrderDetail> orderDetails) {
        this(null, orderDetails);
    }

    public Order(Long id, List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
