package com.example.sfff.controller;

import com.example.sfff.domain.Cart;
import com.example.sfff.domain.CartProduct;
import com.example.sfff.domain.Product;
import com.example.sfff.domain.User;
import com.example.sfff.repos.CartProductRepo;
import com.example.sfff.repos.CartRepo;
import com.example.sfff.repos.ProductRepo;
import com.example.sfff.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Controller
public class MainController {
    @Autowired
    private ProductRepo productRepo;


    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartProductRepo cartProductRepo;




    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Product> products = productRepo.findAllByOrderByIdDesc();

        model.put("products", products);

        return "main";
    }

    @GetMapping("search")
    public String search(@RequestParam String search, Map<String, Object> model) {
        Iterable<Product> products;

        if (search != null && !search.isEmpty()) {
            products = productRepo.findByTitleContaining(search);
        } else {
            products = productRepo.findAll();
        }

        model.put("products", products);

        return "main";
    }


    @PostMapping
    public String addToCart(
            @AuthenticationPrincipal User user,
            @RequestParam Integer id,
            Map<String, Object> model ){
        Cart cart = cartRepo.findByUserId(user.getId());
        Product product = productRepo.findById(id);
        List<CartProduct> cartProduct = cartProductRepo.findByProductId(id);
        System.out.println(cartProduct);
                if (cartProduct.isEmpty()){
                    addProductToCart(product,cart);
                }
                else{
                    addQuantityToProductInCart(cartProduct.get(0));
                    cartProductRepo.save(cartProduct.get(0));
                }
        return "redirect:/";
    }



    public void addProductToCart(Product product, Cart cart){
        CartProduct cp = new CartProduct();
        cp.setCart(cart);
        cp.setProduct(product);
        cartProductRepo.save(cp);
    }

    public void addQuantityToProductInCart(CartProduct cartProduct){
        cartProduct.setNumbers(cartProduct.getNumbers()+1);
    }

}
