package com.foodjwt.FoodJwtCustomer.repository;

import com.foodjwt.FoodJwtCustomer.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}