package com.example.sfff;

import com.example.sfff.controller.MainController;
import com.example.sfff.domain.CartProduct;
import com.example.sfff.domain.Product;
import com.example.sfff.domain.User;
import com.example.sfff.repos.CartProductRepo;
import com.example.sfff.repos.CartRepo;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("u")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql","/create-productList-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-productList-after.sql","/create-user-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartProductRepo cartProductRepo;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void mainPageTest() throws Exception{
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Admin panel")));
    }

    @Test
    public void categoryPageTest() throws Exception{
        this.mockMvc.perform(get("/category?category=3"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Chocolate")));

        this.mockMvc.perform(get("/category?category=2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ice cream")));
    }

    @Test
    public void productListTest() throws Exception{
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='product']").nodeCount(4));
    }

    @Test
    public void searchTest() throws Exception{
        this.mockMvc.perform(get("/search").param("search","Protein"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='product']").nodeCount(2))
                .andExpect(xpath("//*[@id='product']/div[@data-id=4]").exists())
                .andExpect(xpath("//*[@id='product']/div[@data-id=3]").exists());
    }

    @Test
    public void addProductToCart() throws Exception {
        User u = userRepo.findByUsername("u");
        Product product = productRepo.findById((long)1).get();
        mainController.addToCart(u,product,2);

        List<CartProduct> products = cartProductRepo.findByCartId((long) 1);
        System.out.println(products);
        assertThat(products).size().isEqualTo(1);
        assertThat(products.get(0).getQuantity()).isEqualTo(2);
    }

}
