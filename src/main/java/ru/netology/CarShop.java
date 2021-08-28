package ru.netology;

import ru.netology.car.Car;

import java.util.ArrayList;
import java.util.List;

public class CarShop {

    private static CarShop instance = null;

    private final List<Car> carsOnSale;
    private final int TIME_TO_RECEIPT_CAR;
    private final int LIMIT_SOLD_CARS;
    private int soldCars;
    boolean endSales;

    private CarShop() {
        this.carsOnSale = new ArrayList<>();
        this.TIME_TO_RECEIPT_CAR = 450;
        this.LIMIT_SOLD_CARS = 10;
        this.soldCars = 0;
        endSales = false;
    }

    public static CarShop get() {
        if (instance == null) {
            instance = new CarShop();
        }
        return instance;
    }


    public synchronized void sellCar() {

        while (carsOnSale.isEmpty()) {
            try {
                if (endSales) {
                    break;
                }
                System.out.println("Машин нет");
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        if (endSales) {
            System.out.println("Продажи закрыты. " + Thread.currentThread().getName() + " отправился домой");
            notifyAll();
            Thread.currentThread().interrupt();
            return;
        }


        System.out.println("Автомобиль " + carsOnSale.get(0) + " продан " + Thread.currentThread().getName());
        carsOnSale.remove(0);
        soldCars++;

        if (soldCars == LIMIT_SOLD_CARS) {
            endSales = !endSales;
        }

        System.out.println("Количество проданных машин = " + soldCars);

    }

    public synchronized void addCarFromFactory(Car car) {


        if (endSales) {
            System.out.println("Продажи закрыты. Автомобиль не принят");
            notifyAll();
            Thread.currentThread().interrupt();
            return;
        }


        try {
            System.out.println("Поступила новая машина. Оформляю документы");
            Thread.sleep(TIME_TO_RECEIPT_CAR);
            System.out.println("Оформление машины " + car + " завершено");
            carsOnSale.add(car);
            notify();
        } catch (InterruptedException ignored) {
        }

    }


}
