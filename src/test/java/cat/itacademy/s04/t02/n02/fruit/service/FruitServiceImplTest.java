package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.dto.CreateFruitRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.UpdateFruitRequest;
import cat.itacademy.s04.t02.n02.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FruitServiceImplTest {

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;

    @Test
    void shouldCreateFruitSuccessfully() {
        CreateFruitRequest request = new CreateFruitRequest("Apple", 10, 1L);

        Fruit savedFruit = new Fruit("Apple", 10, new Provider("Fresh Fruits", "Spain"));

        when(fruitRepository.save(any(Fruit.class))).thenReturn(savedFruit);

        Provider provider = new Provider("Fresh Fruits", "Spain");

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        var response = fruitService.createFruit(request);

        assertEquals("Apple", response.name());
        assertEquals(10, response.weightInKilos());

        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void shouldReturnFruitById() {
        Fruit fruit = new Fruit("Banana", 15, new Provider("Fresh Fruits", "Spain"));

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));

        var response = fruitService.getFruitById(1L);

        assertEquals("Banana", response.name());
        assertEquals(15, response.weightInKilos());

        verify(fruitRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenFruitNotFound() {
        when(fruitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.getFruitById(1L));
    }

    @Test
    void shouldReturnAllFrits() {
        List<Fruit> fruits = List.of(
                new Fruit("Apple", 10, new Provider("Fresh Fruits", "Spain")),
                new Fruit("Banana", 15, new Provider("Fresh Fruits", "Spain"))
        );

        when(fruitRepository.findAll()).thenReturn(fruits);

        var response = fruitService.getAllFruits();

        assertEquals(2, response.size());

        assertEquals("Apple", response.get(0).name());
        assertEquals("Banana", response.get(1).name());

        verify(fruitRepository).findAll();
    }

    @Test
    void shouldUpdateFruit() {
        Fruit fruit = new Fruit("Apple", 10, new Provider("Fresh Fruits", "Spain"));

        UpdateFruitRequest request = new UpdateFruitRequest("Green Apple", 15,1L);

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));

        when(fruitRepository.save(any(Fruit.class))).thenReturn(fruit);

        Provider provider = new Provider("Fresh Fruits", "Spain");

        when(providerRepository.findById(1L))
                .thenReturn(Optional.of(provider));

        var response = fruitService.updateFruit(1L, request);

        assertEquals("Green Apple", response.name());
        assertEquals(15, response.weightInKilos());

        verify(fruitRepository).findById(1L);
        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void shouldDeleteFruit() {
        Fruit fruit = new Fruit("Orange", 5, new Provider("Fresh Fruits", "Spain"));

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));

        fruitService.deleteFruit(1L);

        verify(fruitRepository).findById(1L);
        verify(fruitRepository).delete(fruit);
    }

}
