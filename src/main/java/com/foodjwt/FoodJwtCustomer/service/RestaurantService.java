package com.foodjwt.FoodJwtCustomer.service;

import com.foodjwt.FoodJwtCustomer.domain.Restaurant;
import com.foodjwt.FoodJwtCustomer.repository.MenuRepository;
import com.foodjwt.FoodJwtCustomer.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired private RestaurantRepository restaurantRepo;

    @Autowired private static MenuRepository menuRepo;

    public List<Restaurant> listOfRestaurants() {
        return restaurantRepo.findAll();
    }
}