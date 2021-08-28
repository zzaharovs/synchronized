package ru.netology;

import ru.netology.car.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarShop {

    private static CarShop instance = null;

    private final Lock lock;
    private final Condition condition;
    private final List<Car> carsOnSale;
    private final int TIME_TO_RECEIPT_CAR;
    private final int LIMIT_SOLD_CARS;
    private int soldCars;
    private boolean endSales;

    private CarShop() {
        this.lock = new ReentrantLock(true);
        this.condition = lock.newCondition();
        this.carsOnSale = new ArrayList<>();
        this.TIME_TO_RECEIPT_CAR = 500;
        this.LIMIT_SOLD_CARS = 10;
        this.soldCars = 0;
        endSales = false;
    }

    public static synchronized CarShop get() {
        if (instance == null) {
            instance = new CarShop();
        }
        return instance;
    }


    public void sellCar() {

        try {
            lock.lock();
            while (carsOnSale.isEmpty()) {
                try {
                    if (endSales) {
                        break;
                    }
                    System.out.println("Машин нет");
                    condition.await();
                } catch (InterruptedException ignored) {
                }
            }

            if (endSales) {
                System.out.println("Продажи закрыты. " + Thread.currentThread().getName() + " отправился домой");
                condition.signalAll();
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
        } finally {
            lock.unlock();
        }

    }

    public void addCarFromFactory(Car car) {


        try {
            lock.lock();

            if (endSales) {
                System.out.println("Продажи закрыты. Автомобиль не принят");
                condition.signalAll();
                Thread.currentThread().interrupt();
                return;
            }
            try {
                System.out.println("Поступила новая машина. Оформляю документы");
                Thread.sleep(TIME_TO_RECEIPT_CAR);
                System.out.println("Оформление машины " + car + " завершено");
                carsOnSale.add(car);
                condition.signal();
            } catch (InterruptedException ignored) {
            }
        } finally {
            lock.unlock();
        }
    }

}
