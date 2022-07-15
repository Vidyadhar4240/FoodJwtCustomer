package com.foodjwt.FoodJwtCustomer.dto;

import java.util.List;

public class OrderDTO {

    private List <Integer> menuIds;

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }
}