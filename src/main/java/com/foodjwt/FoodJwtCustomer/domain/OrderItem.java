package com.foodjwt.FoodJwtCustomer.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    private static final long serialVersionUID = 546436l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderitem_id")
    private int orderitemId;

    @Column(name = "menu_id")
    private int menuId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public int getOrderitemId() {
        return orderitemId;
    }

    public void setOrderitemId(int orderitemId) {
        this.orderitemId = orderitemId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}