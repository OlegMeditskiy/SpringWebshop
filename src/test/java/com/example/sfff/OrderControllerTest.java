package com.example.sfff;
import com.example.sfff.controller.*;
import com.example.sfff.domain.*;
import com.example.sfff.repos.*;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithUserDetails("u")
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql","/create-productList-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-productList-after.sql","/create-user-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderControllerTest {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderController orderController;

    @Autowired
    private MainController mainController;
    @Autowired
    private OrderProductRepo orderProductRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void orderPageTest() throws Exception{
        this.mockMvc.perform(get("/order"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Order")));
    }

    @Test
    public void checkoutTest() throws Exception{
        User user = userRepo.findByUsername("u");

        mainController.addToCart(user,1,1);

        mainController.addToCart(user,2,1);

        mainController.addToCart(user,3,1);

        String address = "kvistbrogatan 22";
        orderController.checkout(user,address);
        Order order = orderRepo.findAll().get(0);
        assertThat(order.getUser().getId()).isEqualTo(1);
        assertThat(order.getOrderProducts().size()).isEqualTo(3);

    }

}
