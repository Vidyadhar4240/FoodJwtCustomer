package com.foodjwt.FoodJwtCustomer.controller;

import com.foodjwt.FoodJwtCustomer.domain.*;
import com.foodjwt.FoodJwtCustomer.dto.*;
import com.foodjwt.FoodJwtCustomer.orderdtovalidator.Validator;
import com.foodjwt.FoodJwtCustomer.repository.MenuRepository;
import com.foodjwt.FoodJwtCustomer.repository.OrderRepository;
import com.foodjwt.FoodJwtCustomer.repository.RestaurantRepository;
import com.foodjwt.FoodJwtCustomer.repository.UserRepository;
import com.foodjwt.FoodJwtCustomer.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/from_restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MenuRepository menuRepo;
    @Autowired
    private OrderRepository orderRepo;

    @Resource(name = "restaurantService")
    private RestaurantService restaurantService;

    @GetMapping("/list_restaurants")
    public ResponseEntity<List<Restaurant>> listRestaurants(@AuthenticationPrincipal User user) {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        System.out.println(restaurants.toString());
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/menu_list/{id}")
    public ResponseEntity<List<Menu>> listMenu(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long restaurant_id) {
        List<Menu> menu = menuRepo.findAllByRestaurantId(restaurant_id);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/restaurant_name/{id}")
    public ResponseEntity<RestaurantNameDTO> getRestaurantName(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long restaurant_id) {
        Restaurant restaurant = restaurantRepo.findById(restaurant_id).get();
        RestaurantNameDTO restaurantNameDTO = new RestaurantNameDTO();
        restaurantNameDTO.setRestaurantName(restaurant.getName());
        return ResponseEntity.ok(restaurantNameDTO);
    }


    @PostMapping("/update")
    public ResponseEntity<?> editProfile(@RequestBody User editRequest, @AuthenticationPrincipal User user) {
        user.setFirstName(editRequest.getFirstName());
        user.setLastName(editRequest.getLastName());
        user.setPassword(editRequest.getPassword());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/edit")
    public ResponseEntity<?> showEditProductPage(Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepo.findByUsername(email);
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/getName")
    public ResponseEntity<UserNameDTO> getName(Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepo.findByUsername(email);
        String userName = user.get().getFirstName() + " " + user.get().getLastName();
        UserNameDTO userNameDTO = new UserNameDTO();
        userNameDTO.setUserName(userName);
        return ResponseEntity.ok(userNameDTO);
    }

    @PostMapping("/save_order")
    public ResponseEntity<ResponseDTO> saveOrder(@AuthenticationPrincipal User user, @RequestBody OrderDTO orderDTO) {
        ResponseDTO responseDTO = new ResponseDTO();;
        try {
            if (!Validator.isValid(orderDTO)) {
                responseDTO.setStatus("Invalid order");
                return ResponseEntity.ok(responseDTO);
            } else {
                responseDTO.setStatus("Order is valid");
            }
            long userId = user.getId();
            if (orderDTO != null) {
                Order order = new Order();
                order.setUserId((int) userId);
                List<OrderItem> orderItems = new ArrayList<>();
                OrderItem orderItem = null;
                double totalAmount = 0;
                List<Integer> arrayMenuIds = orderDTO.getMenuIds();
                Long restaurant_id = null;
                if (arrayMenuIds != null && arrayMenuIds.size() != 0) {
                    for (Integer menuId : arrayMenuIds) {
                        restaurant_id = menuRepo.findById(menuId).get().getRestaurant().getId();
                        Optional<Menu> menu = menuRepo.findById(menuId);
                        orderItem = new OrderItem();
                        orderItem.setMenuId(menu.get().getId());
                        totalAmount = getTotal(totalAmount, (double) menu.get().getPrice());
                        order.setUserId((int) userId);
                        order.setTotalAmount(totalAmount);
                        orderItem.setOrder(order);
                        orderItems.add(orderItem);
                    }
                }
                order.setRestaurantIdOrderTable(restaurant_id);
                String restaurant_name = restaurantRepo.findById(restaurant_id).get().getName();
                order.setRestaurantName(restaurant_name);
                order.setOrderItems(orderItems);
                Date date = new Date();
                order.setOrderDate(date);
                orderRepo.save(order);
                responseDTO.setStatus("Success");
                responseDTO.setMessage("Order placed successfully");
            }
        } catch (Exception e) {
            assert responseDTO != null;
            responseDTO.setStatus("Error");
            responseDTO.setMessage("Error while placing order");
            e.printStackTrace();
        }
        return ResponseEntity.ok(responseDTO);
    }
    private static double getTotal(double value1, double value2) {
        BigDecimal total = BigDecimal.valueOf(value1).add(BigDecimal.valueOf(value2));
        return total.doubleValue();
    }

    @GetMapping("/order_history")
    public ResponseEntity<List<OrderDetailsDTO>> getMyOrders(@AuthenticationPrincipal User user) {
        int user_Id = Math.toIntExact(user.getId());
        List<OrderDetailsDTO> order = orderRepo.findAllByUserId(user_Id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/get_menu_items/{id}")
    public ResponseEntity<UserMenuListOrderDTO> getMyMenuItems(@AuthenticationPrincipal User user, @PathVariable(name = "id") int order_id) {
        List<String> menu_items = new ArrayList<>();
        List<Integer> menu = menuRepo.findAllByOrderId(order_id);
        for (int k = 0; k<menu.size(); k++) {
            menu_items.add(menuRepo.findById(menu.get(k)).get().getName());
        }
        UserMenuListOrderDTO userMenuListOrderDTO = new UserMenuListOrderDTO();
        userMenuListOrderDTO.setMenuNames(menu_items);
        System.out.println(userMenuListOrderDTO.getMenuNames());
        return ResponseEntity.ok(userMenuListOrderDTO);
    }
}