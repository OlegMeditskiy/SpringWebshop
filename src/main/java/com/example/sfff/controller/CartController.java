package com.example.sfff.controller;

import com.example.sfff.domain.Cart;
import com.example.sfff.domain.CartProduct;
import com.example.sfff.domain.Product;
import com.example.sfff.domain.User;
import com.example.sfff.repos.CartProductRepo;
import com.example.sfff.repos.CartRepo;
import com.example.sfff.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
public class CartController {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CartProductRepo cartProductRepo;

    @GetMapping("/cart")
    public String main(@AuthenticationPrincipal User user,Map<String, Object> model) {
        Cart cart = cartRepo.findByUserId(user.getId());
        Set<CartProduct> products = cartRepo.findByUserId(user.getId()).getCartProducts();
        if (!products.isEmpty()){
            int summ = 0;
            for (CartProduct product: products){
                summ += product.getProduct().getPrice()*product.getNumbers();
            }

            model.put("summ",summ);
            model.put("products", products);
        }
        else {
            int summ = 0;
            model.put("summ",summ);
        }

        return "cart";
    }

    @GetMapping("/cart/{cartProductId}")
    public String delete(
            @PathVariable Long cartProductId
    ){
        Optional<CartProduct> cartProduct = cartProductRepo.findById(cartProductId);
        CartProduct cp = cartProduct.get();
        if (cp.getNumbers()>1)
        {
            cp.setNumbers(cp.getNumbers()-1);
            cartProductRepo.save(cp);
        }
        else{
            cartProductRepo.delete(cp);
        }

        return "redirect:/cart";
    }


}
