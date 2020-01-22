package com.example.sfff.controller;

import com.example.sfff.domain.*;
import com.example.sfff.repos.OrderProductRepo;
import com.example.sfff.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{order}")
    public String orderEditForm(@PathVariable Order order, Model model) {
        model.addAttribute("order", order);
        model.addAttribute("orderStatuses",order.getCategoryExcept(order.getOrderStatus()));

        return "orderEdit";
    }

    @PostMapping
    public String orderSave(
            @RequestParam String address,
            @RequestParam OrderStatus orderStatus,
            @RequestParam("orderId") Order order
            ){
        order.setUserAddress(address);
        order.setOrderStatus(orderStatus);
        orderRepo.save(order);

        return "redirect:/admin/order";
    }

}
