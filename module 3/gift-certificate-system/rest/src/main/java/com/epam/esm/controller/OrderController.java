package com.epam.esm.controller;

import com.epam.esm.OrderService;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping("/{userId}")
    public List<Order> getOrderByUser(@PathVariable long userId, Pagination pagination) {
        return service.findByUserId(userId, pagination);
    }

    @GetMapping("/{userId}/order/{orderId}")
    public Order getOrderByUser(@PathVariable long userId, @PathVariable long orderId) {
        return service.findByUserId(userId, orderId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO, @PathVariable long userId) {
        Order order = service.createOrder(orderDTO, userId);
        ResponseEntity<Order> responseEntity = new ResponseEntity<>(order, HttpStatus.CREATED);
        return responseEntity;
    }
}
