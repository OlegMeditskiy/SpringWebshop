package com.example.sfff.controller;

import com.example.sfff.domain.*;
import com.example.sfff.repos.CartProductRepo;
import com.example.sfff.repos.CartRepo;
import com.example.sfff.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private ProductRepo productRepo;


    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartProductRepo cartProductRepo;



    @GetMapping
    public String main(@AuthenticationPrincipal User user,Map<String, Object> model) {
        Iterable<Product> products = productRepo.findAllByOrderByIdDesc();
        model.put("products", products);
        model.put("whatIsIt","All products");
        model.put("user",user);
        model.put("categories",Category.values());
        return "main";
    }

    @GetMapping("category")
    public String category(@AuthenticationPrincipal User user,@RequestParam String c,Map<String, Object> model){
        for (Category categoryEnum: Category.values()){
            if (c.equals(categoryEnum.getDisplayValue())) {
                Iterable<Product> products = productRepo.findByCategory(categoryEnum.getDisplayValue());
                model.put("products",products);
                model.put("whatIsIt",c);
                model.put("user",user);
            }
        }
        model.put("categories",Category.values());
        return "main";
    }


    @GetMapping("search")
    public String search(@AuthenticationPrincipal User user,@RequestParam String search, Map<String, Object> model) {
        Iterable<Product> products;

        if (search != null && !search.isEmpty()) {
            products = productRepo.findByTitleContaining(search);
        } else {
            products = productRepo.findAll();
        }

        model.put("products", products);
        model.put("user",user);
        model.put("categories",Category.values());
        return "main";
    }


    @PostMapping
    public String addToCart(
            @AuthenticationPrincipal User user,
            @RequestParam Integer id){
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
