package by.verbitsky.servletdemo.model.service.impl;

/**
 * The enum Order status contains order status values
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see by.verbitsky.servletdemo.entity.Order
 */
enum OrderStatus {
    /**
     * Paid status.
     */
    PAID (1),
    /**
     * new order status.
     */
    ADDED(0);

    OrderStatus (int orderStausId) {
        this.orderStatusId = orderStausId;
    }

    /**
     * The Order status id.
     */
    int orderStatusId;

    public int getOrderStatusId() {
        return orderStatusId;
    }
}
