package ru.netology;

import ru.netology.car.CarBrand;
import ru.netology.car.Car;

import java.util.Random;

public class CarFactory extends Thread {

    private final int TIME_TO_CONSTRUCT_MS = 500;


    @Override
    public void run() {

        while (!isInterrupted()) {

            try {
                Thread.sleep(TIME_TO_CONSTRUCT_MS);
                System.out.println("Новый автомобиль выпущен");
                CarShop.get().addCarFromFactory(new Car(randomBrand()));

            } catch (InterruptedException ignored) {
            }
        }


    }

    private CarBrand randomBrand() {

        CarBrand carBrand;
        Random rand = new Random();
        int value = rand.nextInt(5);

        switch (value) {
            case 0:
                carBrand = CarBrand.AUDI;
                break;
            case 1:
                carBrand = CarBrand.BMW;
                break;
            case 2:
                carBrand = CarBrand.MAZDA;
                break;
            case 3:
                carBrand = CarBrand.TOYOTA;
                break;
            case 4:
                carBrand = CarBrand.FERRARI;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        return carBrand;
    }

}
