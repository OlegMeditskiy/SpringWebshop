package com.example.sfff;

import com.example.sfff.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
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

    @Test
    public void mainPageTest() throws Exception{
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(xpath("/html/body/div[1]/div/div[3]/div/a[1]/button").string("Admin panel"));
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

        this.mockMvc.perform(post("/").param("id","1"))
                .andDo(print())
                .andExpect(authenticated());
        this.mockMvc.perform(get("/cart"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='cart-product']").nodeCount(1));


    }

}
