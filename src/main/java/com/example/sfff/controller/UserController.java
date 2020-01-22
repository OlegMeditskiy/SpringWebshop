package com.example.sfff.controller;

import com.example.sfff.domain.Cart;
import com.example.sfff.domain.CartProduct;
import com.example.sfff.domain.Role;
import com.example.sfff.domain.User;
import com.example.sfff.repos.CartProductRepo;
import com.example.sfff.repos.CartRepo;
import com.example.sfff.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartProductRepo cartProductRepo;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());

        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }
    @GetMapping("/delete/{user}")
    @Transactional
    public String delete(@PathVariable User user) {
        System.out.println(user);
        Cart cart = cartRepo.findByUserId(user.getId());
        cartProductRepo.deleteAllByCartId(cart.getId());
        cartRepo.delete(cart);
        user.getRoles().clear();
        userRepo.delete(user);
        return "redirect:/admin/user";
    }


    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);

        return "redirect:/admin/user";
    }
}