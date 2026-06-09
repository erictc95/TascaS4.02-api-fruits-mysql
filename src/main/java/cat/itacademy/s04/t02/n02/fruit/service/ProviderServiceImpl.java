package cat.itacademy.s04.t02.n02.fruit.service;

import cat.itacademy.s04.t02.n02.fruit.dto.CreateProviderRequest;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponse;
import cat.itacademy.s04.t02.n02.fruit.dto.UpdateProviderRequest;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService{

    private final ProviderRepository providerRepository;
    private final FruitRepository fruitRepository;


    public ProviderServiceImpl(ProviderRepository providerRepository, FruitRepository fruitRepository) {
        this.providerRepository = providerRepository;
        this.fruitRepository = fruitRepository;
    }

    @Override
    public ProviderResponse createProvider(CreateProviderRequest request) {
        Provider provider = new Provider(request.name(), request.country());
        if(providerRepository.existsByName(request.name())) {
            throw new IllegalArgumentException(
                    "Provider with name " + request.name() + " already exists"
            );
        }
        Provider savedProvider = providerRepository.save(provider);
        return new ProviderResponse(savedProvider.getId(), savedProvider.getName(), savedProvider.getCountry());
    }

    @Override
    public ProviderResponse getProviderById(Long id) {
        Provider provider = providerRepository.findById(id).orElseThrow(() -> new ProviderNotFoundException(id));

        return new ProviderResponse(
                provider.getId(),
                provider.getName(),
                provider.getCountry()
        );
    }

    @Override
    public List<ProviderResponse> getAllProviders() {
        return providerRepository.findAll()
                .stream()
                .map(provider -> new ProviderResponse(
                        provider.getId(),
                        provider.getName(),
                        provider.getCountry()
                )
                )
                .toList();
    }

    @Override
    public ProviderResponse updateProvider(Long id, UpdateProviderRequest request) {
        Provider provider = providerRepository.findById(id).orElseThrow(() -> new ProviderNotFoundException(id));

        if (providerRepository.existsByName(request.name())
                && !provider.getName().equals(request.name())) {

            throw new IllegalArgumentException(
                    "Provider with name " + request.name() + " already exists"
            );
        }

        provider.setName(request.name());
        provider.setCountry(request.country());

        Provider savedProvider = providerRepository.save(provider);

        return new ProviderResponse(
                savedProvider.getId(),
                savedProvider.getName(),
                savedProvider.getCountry()
        );
    }

    @Override
    public void deleteProvider(Long id) {
        Provider provider = providerRepository.findById(id).orElseThrow(() -> new ProviderNotFoundException(id));

        if (fruitRepository.existsByProviderId(id)) {
            throw new IllegalArgumentException(
                    "Cannot delete provider, bacause it has associated fruits"
            );
        }
        providerRepository.delete(provider);
    }
}
