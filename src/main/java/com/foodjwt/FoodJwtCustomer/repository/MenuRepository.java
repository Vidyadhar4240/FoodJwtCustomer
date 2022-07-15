package com.foodjwt.FoodJwtCustomer.repository;

import com.foodjwt.FoodJwtCustomer.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    public static final String FIND_MENU = "SELECT m.* FROM menu m, restaurant r WHERE m.restaurant_id = r.restaurant_id AND m.restaurant_id = ?";

    @Query(value = FIND_MENU, nativeQuery = true)
    public List<Menu> findAllByRestaurantId(Long restaurant_id);

    Optional<Menu> findById(Integer menuId);

    public static final String FIND_MENU_BY_ORDERID = "SELECT oi.menu_id FROM order_items oi WHERE oi.order_id = ?";
    @Query(value = FIND_MENU_BY_ORDERID, nativeQuery = true)
    List<Integer> findAllByOrderId(int order_id);
}