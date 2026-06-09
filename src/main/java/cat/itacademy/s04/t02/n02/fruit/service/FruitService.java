package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.dto.CreateFruitRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.FruitResponse;
import cat.itacademy.s04.t02.n02.fruit.dto.UpdateFruitRequest;

import java.util.List;

public interface FruitService {

    FruitResponse createFruit(CreateFruitRequest request);

    FruitResponse getFruitById(Long id);

    List<FruitResponse> getAllFruits();

    FruitResponse updateFruit(Long id, UpdateFruitRequest request);

    void deleteFruit(Long id);

    List<FruitResponse> getFruitsByProviderId(Long providerId);
}
