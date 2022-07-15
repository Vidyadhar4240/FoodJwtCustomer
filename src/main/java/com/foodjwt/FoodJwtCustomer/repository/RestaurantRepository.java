package com.foodjwt.FoodJwtCustomer.repository;

import com.foodjwt.FoodJwtCustomer.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}