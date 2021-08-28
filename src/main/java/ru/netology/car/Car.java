package ru.netology.car;

public class Car {

    final private CarBrand carBrand;

    public Car(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    @Override
    public String toString() {
        return String.valueOf(carBrand);
    }
}
