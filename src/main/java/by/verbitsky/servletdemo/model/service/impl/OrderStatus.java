package by.verbitsky.servletdemo.model.service.impl;

enum OrderStatus {
    PAID (1),
    ADDED(0);

    OrderStatus (int orderStausId) {
        this.orderStatusId = orderStausId;
    }
    int orderStatusId;

    public int getOrderStatusId() {
        return orderStatusId;
    }
}
