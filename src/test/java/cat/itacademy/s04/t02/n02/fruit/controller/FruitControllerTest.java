package cat.itacademy.s04.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.dto.CreateFruitRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.FruitResponse;
import cat.itacademy.s04.t02.n02.fruit.service.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FruitController.class)
public class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FruitService fruitService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnFruitById() throws Exception {
        FruitResponse response = new FruitResponse(1L, "Apple", 10, 1L, "Fresh Fruits");

        when(fruitService.getFruitById(1L)).thenReturn(response);

        mockMvc.perform(get("/fruits/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(10));
    }

    @Test
    void shouldReturnAllFruits() throws Exception {

        List<FruitResponse> fruits = List.of(
                new FruitResponse(1L, "Apple", 10, 1L, "Fresh Fruits"),
                new FruitResponse(2L, "Banana", 8, 1L, "Fresh Fruits"));

        when(fruitService.getAllFruits()).thenReturn(fruits);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldCreateFruit() throws Exception {
        CreateFruitRequest request = new CreateFruitRequest("Banana", 8, 1L);

        FruitResponse response = new FruitResponse(1L, "Banana", 8, 1L, "Fresh Fruits");

        when(fruitService.createFruit(request)).thenReturn(response);

        mockMvc.perform(post("/fruits")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value("Banana"));
    }
}
