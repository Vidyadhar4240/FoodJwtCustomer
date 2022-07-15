package com.foodjwt.FoodJwtCustomer.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    private static final long serialVersionUID = 546438l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Menu.class)
    @JoinColumn(name = "fk_restaurant_id", referencedColumnName = "restaurant_id")
    private Set<Menu> menuSet;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name="veg")
    private boolean veg;

    @Column(name="nonveg")
    private boolean nonVeg;

    @Column(length = 512)
    private String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isVeg() {
        return veg;
    }

    public void setVeg(boolean veg) {
        this.veg = veg;
    }

    public boolean isNonVeg() {
        return nonVeg;
    }

    public void setNonVeg(boolean nonVeg) {
        this.nonVeg = nonVeg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}