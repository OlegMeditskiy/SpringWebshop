package com.example.sfff.controller;

import com.example.sfff.domain.Cart;
import com.example.sfff.domain.Role;
import com.example.sfff.domain.User;
import com.example.sfff.repos.CartProductRepo;
import com.example.sfff.repos.CartRepo;
import com.example.sfff.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Collections;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class RegistrationRestController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartProductRepo cartProductRepo;

    @PostMapping("/addUser/{userName}/{userPassword}")
    public ResponseEntity<String> addUser(
            @PathVariable String userName,
            @PathVariable String userPassword
    ){
        User userFromDb = userRepo.findByUsername(userName);
        if(userFromDb != null){
            return ResponseEntity.accepted().body("User exists");
        }
        else {
            User user = new User();
            user.setUsername(userName);
            user.setPassword(userPassword);
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);
            createCartForUser(user);
            return ResponseEntity.accepted().body("User was created");
        }
    }

    @PostMapping("/addAdminRoleToUser/{user}")
    public ResponseEntity<String> addAdminRoleToUser(
            @PathVariable User user
    ){
        if(user.getRoles().contains(Role.ADMIN)){
            return ResponseEntity.accepted().body("User is already Admin");
        }
        else{
            user.getRoles().clear();
            user.getRoles().add(Role.USER);
            user.getRoles().add(Role.ADMIN);
            userRepo.save(user);
            return ResponseEntity.accepted().body("User got Admin role");
        }


    }
    @PostMapping("/deleteAdminRoleFromUser/{user}")
    public ResponseEntity<String> deleteAdminRoleFromUser(
            @PathVariable User user
    ){
        if(!user.getRoles().contains(Role.ADMIN)){
            return ResponseEntity.accepted().body("User is not Admin");
        }
        else {
            user.getRoles().clear();
            user.getRoles().add(Role.USER);
            userRepo.save(user);
            return ResponseEntity.accepted().body("User is not Admin anymore");
        }

    }

    @DeleteMapping("/deleteUser/{user}")
    @Transactional
    public ResponseEntity<String> deleteUser(
            @PathVariable User user
    ){
        try{
            System.out.println(user);
            Cart cart = cartRepo.findByUserId(user.getId());
            cartProductRepo.deleteAllByCartId(cart.getId());
            cartRepo.delete(cart);
            user.getRoles().clear();
            userRepo.delete(user);
            return ResponseEntity.accepted().body("User "+user.getId()+" was deleted");
        }
        catch (Exception ex){
            return ResponseEntity.accepted().body("Error");
        }

    }
    public void createCartForUser(User user){
        Cart newCart = new Cart();
        newCart.setUser(user);
        cartRepo.save(newCart);
    }

}
