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

import java.util.ArrayList;
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
    public String category(@AuthenticationPrincipal User user,@RequestParam int category,Map<String, Object> model){
        for (Category categoryEnum: Category.values()){
            if (category==categoryEnum.ordinal()) {
                Iterable<Product> products = productRepo.findByCategory(categoryEnum);
                model.put("products",products);
                model.put("whatIsIt",categoryEnum.getDisplayValue());
                model.put("user",user);
            }
        }
        model.put("categories",Category.values());
        return "main";
    }


    @GetMapping("search")
    public String search(@AuthenticationPrincipal User user,@RequestParam String search, Map<String, Object> model) {
        List<Product> products=new ArrayList<>();
        List<Product> productsByTitle=new ArrayList<>();
        List<Product> productsByDescription=new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            productsByTitle = productRepo.findByTitleContaining(search);
            productsByDescription = productRepo.findByDescriptionContaining(search);
            products.addAll(productsByTitle);
            products.addAll(productsByDescription);
        } else {
            products = productRepo.findAll();
        }

        model.put("products", products);
        model.put("user",user);
        model.put("whatIsIt","Search results");
        model.put("categories",Category.values());
        return "main";
    }


    @PostMapping
    public String addToCart(
            @AuthenticationPrincipal User user,
            @RequestParam Product product,
            @RequestParam int quantity){
        Cart cart = cartRepo.findByUserId(user.getId());
        List<CartProduct> cartProducts = cartProductRepo.findByCartId(cart.getId());
            CartProduct cartProduct = getCartProduct(cartProducts,product.getId());
            if(cartProduct!=null){

                    addQuantityToProductInCart(cartProduct,quantity);

            }
            else {
                addProductToCart(product,cart,quantity);
            }
        return "redirect:/";
    }



    public void addProductToCart(Product product, Cart cart, int quantity){
        CartProduct cp = new CartProduct();
        cp.setCart(cart);
        cp.setProduct(product);
        cp.setQuantity(quantity);
        cartProductRepo.save(cp);
    }

    public void addQuantityToProductInCart(CartProduct cartProduct, int quantity){
        if (cartProduct.getQuantity()-quantity>1){
            cartProduct.setQuantity(cartProduct.getQuantity()+quantity);
            cartProductRepo.save(cartProduct);
        }

    }

    public CartProduct getCartProduct(List<CartProduct> cartProducts, Long id) {
        CartProduct cartProduct = null;
        for (CartProduct cartProduct1 : cartProducts) {
            if (cartProduct1.getProduct().getId() == id) {
                cartProduct = cartProduct1;
                break;
            }
        }
        return cartProduct;
    }


}
