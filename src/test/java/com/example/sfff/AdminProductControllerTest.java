package com.example.sfff;

import com.example.sfff.controller.AdminProductController;
import com.example.sfff.domain.Category;
import com.example.sfff.domain.Product;
import com.example.sfff.repos.ProductRepo;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@WithUserDetails("u")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql","/create-productList-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-productList-after.sql","/create-user-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminProductControllerTest {

    @Autowired
    private AdminProductController adminProductController;

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void addProductTest() throws Exception{
        Category category = Category.chocolate;
        String title = "Chocolate";
        String description="Chocolate product";
        int price = 5;
        Map<String, Object> model = new HashMap<>();
        File file1 = new File("C:/Users/olegm/IdeaProjects/sfff/src/main/resources/static/images/products/test.jpg");
        FileInputStream input = new FileInputStream(file1);
        MultipartFile file = new MockMultipartFile("file",file1.getName(),"image/jpg", IOUtils.toByteArray(input));
        adminProductController.add(category,title,description,price,model,file);
        List<Product> productsNew = productRepo.findAll();
        assertThat(productsNew.get(productsNew.size()-1).getTitle()).isEqualTo("Chocolate");
        assertThat(productsNew.get(productsNew.size()-1).getDescription()).isEqualTo("Chocolate product");
        assertThat(productsNew.get(productsNew.size()-1).getPrice()).isEqualTo(5);
    }

}
