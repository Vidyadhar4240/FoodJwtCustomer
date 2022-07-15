package com.foodjwt.FoodJwtCustomer.dto;

import java.util.Date;

public interface OrderDetailsDTO {
    int getOrderId();
    int getTotalAmount();
    Date getOrderDate();
    String getRestaurantName();
}
