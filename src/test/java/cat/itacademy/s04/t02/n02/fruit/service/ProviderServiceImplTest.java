package cat.itacademy.s04.t02.n02.fruit.service;


import cat.itacademy.s04.t02.n02.fruit.dto.CreateProviderRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.UpdateProviderRequest;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
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
public class ProviderServiceImplTest {

    @Mock
    private FruitRepository fruitRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderServiceImpl providerService;

    @Test
    void shouldCreateProviderSuccessfully() {
        CreateProviderRequest request = new CreateProviderRequest("Fresh Fruits", "Spain");

        Provider savedProvider = new Provider("Fresh Fruits", "Spain");

        when(providerRepository.save(any(Provider.class))).thenReturn(savedProvider);
        when(providerRepository.existsByName("Fresh Fruits")).thenReturn(false);

        var response = providerService.createProvider(request);

        assertEquals("Fresh Fruits", response.name());
        assertEquals("Spain", response.country());

        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void shouldReturnProviderById() {
        Provider provider = new Provider("Fresh Fruits", "Spain");

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        var response = providerService.getProviderById(1L);

        assertEquals("Fresh Fruits", response.name());
        assertEquals("Spain", response.country());

        verify(providerRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProviderNotFound() {
        when(providerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProviderNotFoundException.class, () -> providerService.getProviderById(1L));
    }

    @Test
    void shouldReturnAllProviders() {
        List<Provider> providers = List.of(
                new Provider("Fresh Fruits", "Spain"),
                new Provider("Green Fruits", "Germany")
        );

        when(providerRepository.findAll()).thenReturn(providers);

        var response = providerService.getAllProviders();

        assertEquals(2, response.size());

        assertEquals("Fresh Fruits", response.get(0).name());
        assertEquals("Green Fruits", response.get(1).name());

        verify(providerRepository).findAll();
    }

    @Test
    void shouldUpdateProvider() {
        Provider provider = new Provider("Fresh Fruits", "Spain");

        UpdateProviderRequest request = new UpdateProviderRequest("Green Fruits", "Germany");

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        var response = providerService.updateProvider(1L, request);

        assertEquals("Green Fruits", response.name());
        assertEquals("Germany", response.country());

        verify(providerRepository).findById(1L);
        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void shouldDeleteProvider() {
        Provider provider = new Provider("Fresh Fruits", "Spain");

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        when(fruitRepository.existsByProviderId(1L)).thenReturn(false);

        providerService.deleteProvider(1L);

        verify(providerRepository).findById(1L);
        verify(providerRepository).delete(provider);
    }

    @Test
    void shouldThrowExceptionWhenProviderAlreadyExists() {

        CreateProviderRequest request =
                new CreateProviderRequest("Fresh Fruits", "Spain");

        when(providerRepository.existsByName("Fresh Fruits"))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> providerService.createProvider(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenDeletingProviderWithAssociatedFruits() {

        Provider provider =
                new Provider("Fresh Fruits", "Spain");

        when(providerRepository.findById(1L))
                .thenReturn(Optional.of(provider));

        when(fruitRepository.existsByProviderId(1L))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> providerService.deleteProvider(1L)
        );
    }
}
