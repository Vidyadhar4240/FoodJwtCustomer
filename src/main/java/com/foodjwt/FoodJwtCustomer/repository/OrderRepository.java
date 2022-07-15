package com.foodjwt.FoodJwtCustomer.repository;

import com.foodjwt.FoodJwtCustomer.domain.Order;
import com.foodjwt.FoodJwtCustomer.dto.OrderDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public static final String FIND_ORDER = "SELECT o.order_id as orderId, o.total_amount as totalAmount, o.order_date as orderDate, o.restaurant_name as restaurantName FROM orders o WHERE o.user_id = ? ORDER BY o.order_date DESC";
    @Query(value = FIND_ORDER, nativeQuery = true)
    public List<OrderDetailsDTO> findAllByUserId(int userId);

    public static final String FIND_ORDER_ITEMS_ARRAY = "SELECT o.* FROM orders o WHERE o.user_id = ?";
    @Query(value = FIND_ORDER_ITEMS_ARRAY, nativeQuery = true)
    public List<List<Integer>> findArrayAllByUserId(int user_id);
}
