package cat.itacademy.s04.t02.n02.fruit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fruits")
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int weightInKilos;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    public Fruit() {
    }

    public Fruit(String name, int weightInKilos, Provider provider) {
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeightInKilos() {
        return weightInKilos;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeightInKilos(int weightInKilos) {
        this.weightInKilos = weightInKilos;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
