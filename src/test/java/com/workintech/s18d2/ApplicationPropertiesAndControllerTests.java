package com.workintech.s18d2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.s18d2.controller.FruitController;
import com.workintech.s18d2.entity.Fruit;
import com.workintech.s18d2.entity.FruitType;
import com.workintech.s18d2.services.FruitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(value = {ApplicationPropertiesAndControllerTests.class, FruitController.class})
@ExtendWith(ResultAnalyzer2.class)
class ApplicationPropertiesAndControllerTests {

    @Autowired
    private Environment env;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FruitService fruitService;

    private Fruit sampleFruit;

    @BeforeEach
    void setUp() {
        sampleFruit = new Fruit();
        sampleFruit.setId(1L);
        sampleFruit.setName("Apple");
        sampleFruit.setPrice(20.0);
        sampleFruit.setFruitType(FruitType.SWEET);
    }
    @Test
    @DisplayName("application properties istenilenler eklendi mi?")
    void serverPortIsSetTo8585() {

        String serverPort = env.getProperty("server.port");
        assertThat(serverPort).isEqualTo("8080");


        String datasourceUrl = env.getProperty("spring.datasource.url");
        assertNotNull(datasourceUrl);

        String datasourceUsername = env.getProperty("spring.datasource.username");
        assertNotNull(datasourceUsername);

        String datasourcePassword = env.getProperty("spring.datasource.password");
        assertNotNull(datasourcePassword);

        String hibernateDdlAuto = env.getProperty("spring.jpa.hibernate.ddl-auto");
        assertNotNull(hibernateDdlAuto);


    }

    @Test
    void testGetFruits() throws Exception {
        given(fruitService.getAllAsc()).willReturn(List.of(sampleFruit));
        mockMvc.perform(get("/fruit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("Apple")));
    }

    @Test
    void testGetFruitsDesc() throws Exception {
        given(fruitService.getAllDesc()).willReturn(List.of(sampleFruit));
        mockMvc.perform(get("/fruit/desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("Apple")));
    }

    @Test
    void testGetFruitsByName() throws Exception {
        given(fruitService.findByNameContaining("Apple")).willReturn(List.of(sampleFruit));
        mockMvc.perform(get("/fruit/name/{name}", "Apple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("Apple")));
    }


    @Test
    void testSaveFruit() throws Exception {
        given(fruitService.saveOrUpdate(any())).willReturn(sampleFruit);
        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleFruit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Apple")));
    }

    @Test
    void testGetByIdSuccess() throws Exception {
        given(fruitService.getById(1L)).willReturn(sampleFruit);
        mockMvc.perform(get("/fruit/{id}", 1L))
                .andExpect(status().isOk());
    }



    @Test
    void testDeleteFruitSuccess() throws Exception {
        when(fruitService.deleteById(anyLong())).thenReturn(sampleFruit);

        mockMvc.perform(delete("/fruit/{id}", 1L))
                .andExpect(status().isOk());
    }

}


