package ru.netology;

public class Main {

    public static void main(String[] args) {


        System.out.println("Начинаем продажи автомобилей");

        new CarFactory().start();

        for(int i = 0; i < 4; i++) {
            new Customer("Покупатель " + (i+1)).start();
        }

    }

}
