package com.example.sfff.controller;

import com.example.sfff.domain.Order;
import com.example.sfff.domain.OrderProduct;
import com.example.sfff.domain.Product;
import com.example.sfff.repos.OrderProductRepo;
import com.example.sfff.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderProductRepo orderProductRepo;

    @GetMapping
    public String order(Map<String, Object> model){
        List<Order> orders = orderRepo.findAll();

        model.put("orders",orders);

        return "orderList";
    }

    @GetMapping("/delete")
    @Transactional
    public String delete(
            @RequestParam Long orderId
    ){
        Optional<Order> findOrder = orderRepo.findById(orderId);
        Order order = findOrder.get();
        List<OrderProduct> products = orderProductRepo.findByOrderId(orderId);
        orderProductRepo.deleteAllByOrderId(orderId);
        System.out.println(products);
        orderRepo.delete(order);
        return "redirect:/admin/order";
    }

}
