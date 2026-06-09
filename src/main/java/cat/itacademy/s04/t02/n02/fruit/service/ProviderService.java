package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponse;
import cat.itacademy.s04.t02.n02.fruit.dto.CreateProviderRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.UpdateProviderRequest;

import java.util.List;

public interface ProviderService {

    ProviderResponse createProvider(CreateProviderRequest request);

    ProviderResponse getProviderById(Long id);

    List<ProviderResponse> getAllProviders();

    ProviderResponse updateProvider(Long id, UpdateProviderRequest request);

    void deleteProvider(Long id);
}
