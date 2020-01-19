package com.example.sfff.controller;

import com.example.sfff.domain.*;
import com.example.sfff.repos.CartProductRepo;
import com.example.sfff.repos.CartRepo;
import com.example.sfff.repos.OrderProductRepo;
import com.example.sfff.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CartProductRepo cartProductRepo;

    @Autowired
    OrderProductRepo orderProductRepo;

    @GetMapping
    public String order(@AuthenticationPrincipal User user,Map<String, Object> model){
        List<Order> orders = orderRepo.findByUserId(user.getId());
        System.out.println(orders);
        model.put("orders",orders);
        model.put("user",user);
        return "order";
    }

    @PostMapping
    @Transactional
    public String checkout(
            @AuthenticationPrincipal User user,
            @RequestParam String address

    ){
        Cart cart = cartRepo.findByUserId(user.getId());
        Order order = createNewOrder(address,user);
        addProductFromCartToOrder(cart,order);
        cartProductRepo.deleteAllByCartId(cart.getId());
        return "redirect:/cart";
    }

    public Order createNewOrder(String address,User user){
        Order order = new Order();
        order.setUserAddress(address);
        order.setUser(user);
        orderRepo.save(order);
        return order;
    }

    public void addProductFromCartToOrder(Cart cart, Order order){
        List<CartProduct> products = cartProductRepo.findByCartOrderById(cart);
        for (CartProduct product: products){
            OrderProduct addProductToOrder = new OrderProduct();
            addProductToOrder.setOrder(order);
            addProductToOrder.setQuantity(product.getNumbers());
            addProductToOrder.setProduct(product.getProduct());
            orderProductRepo.save(addProductToOrder);
        }
    }
}
