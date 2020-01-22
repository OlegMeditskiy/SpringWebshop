package com.example.sfff;
import com.example.sfff.controller.CartController;
import com.example.sfff.controller.MainController;
import com.example.sfff.domain.CartProduct;
import com.example.sfff.domain.Product;
import com.example.sfff.domain.User;
import com.example.sfff.repos.CartProductRepo;
import com.example.sfff.repos.ProductRepo;
import com.example.sfff.repos.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("u")
@TestPropertySource("/application-test.properties")
public class CartControllerTest {

    @Autowired
    private CartController cartController;

    @Autowired
    private MainController mainController;
    @Autowired
    private CartProductRepo cartProductRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = {"/create-user-before.sql","/create-productList-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-productList-after.sql","/create-user-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void cartPageTest() throws Exception{
        this.mockMvc.perform(get("/cart"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Cart")));
    }

    @Test
    @Sql(value = {"/create-user-before.sql","/create-productList-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-productList-after.sql","/create-user-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteQuantityTest(){
        User u = userRepo.findByUsername("u");

        mainController.addToCart(u,1,2);


        mainController.addToCart(u,2,4);


        List <CartProduct> cartProducts = cartProductRepo.findAll();
        cartController.deleteQuantity(cartProducts.get(0).getId());
        cartController.deleteQuantity(cartProducts.get(1).getId());
        cartController.deleteQuantity(cartProducts.get(1).getId());

        List <CartProduct> cartProductsNew = cartProductRepo.findAll();

        assertThat(cartProductsNew.get(0).getQuantity()).isEqualTo(1);
        assertThat(cartProductsNew.get(1).getQuantity()).isEqualTo(2);
    }
    @Test
    @Sql(value = {"/create-user-before.sql","/create-productList-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-productList-after.sql","/create-user-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addQuantityTest(){
        User u = userRepo.findByUsername("u");


        mainController.addToCart(u,1,2);


        mainController.addToCart(u,2,4);


        List <CartProduct> cartProducts = cartProductRepo.findAll();
        cartController.addQuantity(cartProducts.get(0).getId());
        cartController.addQuantity(cartProducts.get(1).getId());
        cartController.addQuantity(cartProducts.get(1).getId());

        List <CartProduct> cartProductsNew = cartProductRepo.findAll();

        assertThat(cartProductsNew.get(0).getQuantity()).isEqualTo(3);
        assertThat(cartProductsNew.get(1).getQuantity()).isEqualTo(6);
    }
    @Test
    @Sql(value = {"/create-user-before.sql","/create-productList-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-productList-after.sql","/create-user-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest(){
        User u = userRepo.findByUsername("u");
        Product product = productRepo.findById(1);

        mainController.addToCart(u,1,2);


        mainController.addToCart(u,2,4);

        List <CartProduct> cartProducts = cartProductRepo.findAll();
        cartController.delete(cartProducts.get(0).getId());
        cartController.delete(cartProducts.get(1).getId());

        List <CartProduct> cartProductsNew = cartProductRepo.findAll();
        assertThat(cartProductsNew.size()).isEqualTo(0);

    }
}
