package ru.netology;

public class Customer extends Thread {

    private final int TIME_TO_CHOICE = 400;

    public Customer(String threadName) {
        super(threadName);
    }

    @Override
    public void run() {

        while (!isInterrupted()) {
            try {
                Thread.sleep(TIME_TO_CHOICE);
                System.out.println(Thread.currentThread().getName() + " вошел в магазин");
                CarShop.get().sellCar();

            } catch (InterruptedException ignore) {
            }
        }
    }

}
