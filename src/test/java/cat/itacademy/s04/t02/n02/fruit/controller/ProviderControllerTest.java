package cat.itacademy.s04.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.dto.CreateProviderRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponse;
import cat.itacademy.s04.t02.n02.fruit.dto.UpdateProviderRequest;
import cat.itacademy.s04.t02.n02.fruit.service.ProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProviderController.class)
public class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProviderService providerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnProviderById() throws Exception {

        ProviderResponse response =
                new ProviderResponse(1L, "Fresh Fruits", "Spain");

        when(providerService.getProviderById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/providers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fresh Fruits"))
                .andExpect(jsonPath("$.country").value("Spain"));
    }

    @Test
    void shouldReturnAllProviders() throws Exception {

        List<ProviderResponse> providers = List.of(
                new ProviderResponse(1L, "Fresh Fruits", "Spain"),
                new ProviderResponse(2L, "Tropical Fruits", "Brazil")
        );

        when(providerService.getAllProviders())
                .thenReturn(providers);

        mockMvc.perform(get("/providers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldCreateProvider() throws Exception {

        CreateProviderRequest request =
                new CreateProviderRequest("Fresh Fruits", "Spain");

        ProviderResponse response =
                new ProviderResponse(1L, "Fresh Fruits", "Spain");

        when(providerService.createProvider(request))
                .thenReturn(response);

        mockMvc.perform(post("/providers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Fresh Fruits"));
    }

    @Test
    void shouldUpdateProvider() throws Exception {

        UpdateProviderRequest request =
                new UpdateProviderRequest("Fresh Fruits Updated", "Spain");

        ProviderResponse response =
                new ProviderResponse(1L, "Fresh Fruits Updated", "Spain");

        when(providerService.updateProvider(1L, request))
                .thenReturn(response);

        mockMvc.perform(put("/providers/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name")
                        .value("Fresh Fruits Updated"));
    }

    @Test
    void shouldDeleteProvider() throws Exception {

        mockMvc.perform(delete("/providers/1"))
                .andExpect(status().isNoContent());
                verify(providerService).deleteProvider(1L);
    }
}
