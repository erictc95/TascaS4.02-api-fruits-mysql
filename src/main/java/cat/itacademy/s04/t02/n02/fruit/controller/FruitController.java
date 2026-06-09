package cat.itacademy.s04.t02.n02.fruit.controller;

import cat.itacademy.s04.t02.n02.fruit.dto.CreateFruitRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.FruitResponse;
import cat.itacademy.s04.t02.n02.fruit.dto.UpdateFruitRequest;
import cat.itacademy.s04.t02.n02.fruit.service.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FruitResponse createFruit(@Valid @RequestBody CreateFruitRequest request) {
        return fruitService.createFruit(request);
    }

    @GetMapping
    public List<FruitResponse> findAllFruits(@RequestParam(required = false) Long providerId) {
        if (providerId != null) {
            return fruitService.getFruitsByProviderId(providerId);
        }
        return fruitService.getAllFruits();
    }

    @GetMapping("/{id}")
    public FruitResponse getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id);
    }

    @PutMapping("/{id}")
    public FruitResponse updateFruit(@PathVariable Long id, @Valid @RequestBody UpdateFruitRequest request) {
        return fruitService.updateFruit(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFruit(@PathVariable Long id) {
        fruitService.deleteFruit(id);
    }

}
