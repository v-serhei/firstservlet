package by.verbitsky.servletdemo.entity;

import by.verbitsky.servletdemo.entity.ext.Song;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


/**
 * Class User. Describes user order
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see AudioContent
 * @see Song
 */

public class Order {
    /**
     * contains list of selected songs
     */
    private Set<Song> orderList;
    /**
     * current order id
     */
    private long orderId;
    /**
     * order owner id
     */
    private long userId;
    /**
     * contains order price value
     */
    private BigDecimal orderPrice;
    /**
     * contains order creation date
     */
    private LocalDate orderDate;
    /**
     * field provides order status:
     * 0 - created
     * 1 - paid
     */
    private int orderStatus;

    public Order() {
        orderList = new HashSet<>();
        orderPrice = BigDecimal.ZERO;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isOrderPaid() {
        return orderStatus > 0;
    }

    public int getOrderStatusId() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void addSong(Song song) {
        orderList.add(song);
    }

    public void removeSongById(long songId) {
        orderList.removeIf(song -> song.getId() == songId);
        recalculateOrderPrice();
    }

    public void addAllSongs(Collection<Song> songs) {
        orderList.addAll(songs);
    }

    public Set<Song> getOrderList() {
        return Collections.unmodifiableSet(orderList);
    }

    private void recalculateOrderPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (Song song : orderList) {
            total = total.add(song.getPrice());
        }
        setOrderPrice(total);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false;
        if (o == null) return false;

        Order order = (Order) o;
        if (orderId != order.orderId) return false;
        return orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ orderId >>> 32);
        result = 31 * result + orderStatus;
        return (result);
    }
}
