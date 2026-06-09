package cat.itacademy.s04.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.dto.CreateProviderRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponse;
import cat.itacademy.s04.t02.n02.fruit.dto.UpdateProviderRequest;
import cat.itacademy.s04.t02.n02.fruit.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;


    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProviderResponse createProvider(@Valid @RequestBody CreateProviderRequest request) {
        return providerService.createProvider(request);
    }

    @GetMapping
    public List<ProviderResponse> findAllProviders() {
        return providerService.getAllProviders();
    }

    @GetMapping("/{id}")
    public ProviderResponse getProviderById(@PathVariable Long id) {
        return providerService.getProviderById(id);
    }

    @PutMapping("/{id}")
    public ProviderResponse updateProvider(@PathVariable Long id, @Valid @RequestBody UpdateProviderRequest request) {
        return providerService.updateProvider(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
    }

}
