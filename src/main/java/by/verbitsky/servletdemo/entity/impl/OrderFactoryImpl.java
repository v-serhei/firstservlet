package by.verbitsky.servletdemo.entity.impl;

import by.verbitsky.servletdemo.entity.*;
import by.verbitsky.servletdemo.entity.ext.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OrderFactoryImpl implements OrderFactory<Order> {
    private static final int DEFAULT_ORDER_STATUS_VALUE = 0;
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ORDER_PRICE = "order_summ";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COLUMN_ORDER_STATUS = "order_status";

    private static final ContentFactory<AudioContent> contentFactory = new AudioContentFactory<>();

    @Override
    public Optional<Order> createOrder(ResultSet resultSet) throws SQLException {
        Order result = new Order();
        result.setOrderId(resultSet.getLong(COLUMN_ORDER_ID));
        result.setUserId(resultSet.getLong(COLUMN_USER_ID));
        result.setOrderPrice(resultSet.getBigDecimal(COLUMN_ORDER_PRICE));
        result.setOrderDate(resultSet.getDate(COLUMN_ORDER_DATE).toLocalDate());
        result.setOrderStatus(resultSet.getInt(COLUMN_ORDER_STATUS));
        Optional<AudioContent> song = contentFactory.createSingleContent(resultSet, ContentType.SONG);
        song.ifPresent(audioContent -> result.addSong((Song) audioContent));
        while (resultSet.next()){
            Optional<AudioContent> nextSong = contentFactory.createSingleContent(resultSet, ContentType.SONG);
            nextSong.ifPresent(audioContent -> result.addSong((Song) audioContent));
        }
        return Optional.of(result);
    }

    @Override
    public List<Order> createOrders(ResultSet resultSet) throws SQLException {
        List<Order> result = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Order> currentOrder = createOrder(resultSet);
            currentOrder.ifPresent(result::add);
        }
        return result;
    }

    @Override
    public Order createOrder(User user, Set<Song> songs) {
        Order result = new Order();
        //user id
        result.setUserId(user.getUserId());
        //add all songs for order description
        result.addAllSongs(songs);
        //order sum price
        for (Song song : songs) {
            result.setOrderPrice(result.getOrderPrice().add(song.getPrice()));
        }
        //order date
        result.setOrderDate(LocalDate.now());
        //order status
        result.setOrderStatus(DEFAULT_ORDER_STATUS_VALUE);
        return result;
    }
}
