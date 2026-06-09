package cat.itacademy.s04.t02.n02.fruit.dto;

public record FruitResponse(

        Long id,
        String name,
        int weightInKilos,
        Long providerId,
        String providerName
) {
}
