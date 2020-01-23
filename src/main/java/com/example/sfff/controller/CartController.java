package com.example.sfff.controller;

import com.example.sfff.domain.Cart;
import com.example.sfff.domain.CartProduct;
import com.example.sfff.domain.Category;
import com.example.sfff.domain.User;
import com.example.sfff.repos.CartProductRepo;
import com.example.sfff.repos.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CartController {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CartProductRepo cartProductRepo;

    @GetMapping("/cart")
    public String main(@AuthenticationPrincipal User user,Map<String, Object> model) {
        Cart cart = user.getCart();
        List<CartProduct> products = cartProductRepo.findByCartOrderById(cart);
        int sum = 0;
        if (!products.isEmpty()){

            for (CartProduct product: products){
                sum += product.getProduct().getPrice()*product.getQuantity();
            }

            model.put("sum",sum);
            model.put("products", products);
        }
        else {
            sum = 0;
            model.put("sum",sum);
        }
        model.put("user",user);
        model.put("categories", Category.values());
        return "cart";
    }

    @GetMapping("/cart/deleteQuantity/{cartProduct}")
    public String deleteQuantity(
            @PathVariable CartProduct cartProduct
    ){
        if (cartProduct.getQuantity()>1)
        {
            cartProduct.setQuantity(cartProduct.getQuantity()-1);
            cartProductRepo.save(cartProduct);
        }
        else{
            cartProductRepo.delete(cartProduct);
        }

        return "redirect:/cart";
    }
    @GetMapping("/cart/addQuantity/{cartProduct}")
    public String addQuantity(
            @PathVariable CartProduct cartProduct
    ){
        cartProduct.setQuantity(cartProduct.getQuantity()+1);
        cartProductRepo.save(cartProduct);

        return "redirect:/cart";
    }
    @GetMapping("/cart/delete/{cartProduct}")
    public String delete(
            @PathVariable CartProduct cartProduct
    ){
        cartProductRepo.delete(cartProduct);
        return "redirect:/cart";

    }




}
