package com.example.sfff.controller;

import com.example.sfff.domain.Cart;
import com.example.sfff.domain.Role;
import com.example.sfff.domain.User;
import com.example.sfff.repos.CartRepo;
import com.example.sfff.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;


@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb != null){
            model.put("userExists","User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        createCartForUser(user);
        return "redirect:/login";
    }
    public void createCartForUser(User user){
        Cart newCart = new Cart();
        newCart.setUser(user);
        cartRepo.save(newCart);

    }
}
