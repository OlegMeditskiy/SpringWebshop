package com.example.sfff.repos;

//import com.example.sfff.domain.Cart;
import com.example.sfff.domain.Order;
import com.example.sfff.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
