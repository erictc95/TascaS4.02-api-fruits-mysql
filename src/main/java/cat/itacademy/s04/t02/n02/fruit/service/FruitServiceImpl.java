package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.dto.CreateFruitRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.FruitResponse;
import cat.itacademy.s04.t02.n02.fruit.dto.UpdateFruitRequest;
import cat.itacademy.s04.t02.n02.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;

    public FruitServiceImpl(FruitRepository fruitRepository, ProviderRepository providerRepository) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public FruitResponse createFruit(CreateFruitRequest request) {
        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ProviderNotFoundException(request.providerId()));

        Fruit fruit = new Fruit(request.name(), request.weightInKilos(), provider);

        Fruit savedFruit = fruitRepository.save(fruit);

        return new FruitResponse(
                savedFruit.getId(),
                savedFruit.getName(),
                savedFruit.getWeightInKilos(),
                savedFruit.getProvider().getId(),
                savedFruit.getProvider().getName()
        );
    }

    @Override
    public FruitResponse getFruitById(Long id) {
        Fruit fruit = fruitRepository.findById(id).orElseThrow(() -> new FruitNotFoundException(id));

        return new FruitResponse(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos(),
                fruit.getProvider().getId(),
                fruit.getProvider().getName()
        );
    }

    @Override
    public List<FruitResponse> getAllFruits() {
        return fruitRepository.findAll()
                .stream()
                .map(fruit -> new FruitResponse(
                                fruit.getId(),
                                fruit.getName(),
                                fruit.getWeightInKilos(),
                                fruit.getProvider().getId(),
                                fruit.getProvider().getName()
                        )
                )
                .toList();
    }

    @Override
    public FruitResponse updateFruit(Long id, UpdateFruitRequest request) {
        Fruit updatedFruit = fruitRepository.findById(id).orElseThrow(() -> new FruitNotFoundException(id));
        Provider provider = providerRepository.findById(request.providerId()).orElseThrow(() -> new ProviderNotFoundException(request.providerId()));

        updatedFruit.setName(request.name());
        updatedFruit.setWeightInKilos(request.weightInKilos());
        updatedFruit.setProvider(provider);

        Fruit savedFruit = fruitRepository.save(updatedFruit);

        return new FruitResponse(
                savedFruit.getId(),
                savedFruit.getName(),
                savedFruit.getWeightInKilos(),
                savedFruit.getProvider().getId(),
                savedFruit.getProvider().getName()
        );
    }

    @Override
    public void deleteFruit(Long id) {
        Fruit fruit = fruitRepository.findById(id).orElseThrow(() -> new FruitNotFoundException(id));
        fruitRepository.delete(fruit);
    }

    public List<FruitResponse> getFruitsByProviderId(Long providerId) {
        providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException(providerId));

        return fruitRepository.findByProviderId(providerId)
                .stream()
                .map(fruit -> new FruitResponse(
                        fruit.getId(),
                        fruit.getName(),
                        fruit.getWeightInKilos(),
                        fruit.getProvider().getId(),
                        fruit.getProvider().getName()
                ))
                .toList();
    }
}
