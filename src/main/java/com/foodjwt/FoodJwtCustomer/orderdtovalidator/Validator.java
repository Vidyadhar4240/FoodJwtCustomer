package com.foodjwt.FoodJwtCustomer.orderdtovalidator;

import com.foodjwt.FoodJwtCustomer.dto.OrderDTO;

public class Validator {
    public static boolean isValid(OrderDTO orderDTO) {
        if (orderDTO == null || orderDTO.getMenuIds() == null || orderDTO.getMenuIds().isEmpty()) {
            return false;
        }

        boolean flag = orderDTO.getMenuIds().stream().anyMatch(item -> item <= 0) ;

        if (flag) {
            System.out.println("Ids cannot be less than 0");
            return false;
        }
        return true;
    }
}