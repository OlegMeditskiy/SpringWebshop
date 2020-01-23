package com.example.sfff.controller;

import com.example.sfff.domain.Order;
import com.example.sfff.domain.OrderStatus;
import com.example.sfff.repos.OrderRepo;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminOrderTestController {

    @Autowired
    OrderRepo orderRepo;

    @PostMapping("updateOrder/{order}/{address}/{orderStatus}")
    public ResponseEntity<String> orderSave(
            @PathVariable String address,
            @PathVariable OrderStatus orderStatus,
            @PathVariable Order order
    ){
        order.setUserAddress(address);
        order.setOrderStatus(orderStatus);
        orderRepo.save(order);
        return ResponseEntity.accepted().body("Order was updated");
    }
    @ExceptionHandler(TypeMismatchException.class)
    public
    @ResponseBody
    String typeMismatchExpcetionHandler(Exception exception, HttpServletRequest request) {
        return "Wrong Order Status: OrderStatus must be (checking, processing, packing, sent, delivered, returned)";
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public
    @ResponseBody
    String missingParameterExceptionHandler(Exception exception, HttpServletRequest request) {
        return "missing param";
    }

    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    String generalExceptionHandler(Exception exception, HttpServletRequest request) {
        return "No order with this id";
    }

}
