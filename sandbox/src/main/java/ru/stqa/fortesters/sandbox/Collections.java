package ru.stqa.fortesters.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

    public static void main(String[] args) {
        //создание массива (если в [] задан размер массива, то больше элеемнтов задать нельзя)
        String[] langs = {"Java", "C#", "Python", "PHP"};

        //создание списка (кол-во элементов не задается и может меняться)
        List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP");

//проведем итерацию по элементам списка с помощью счетчика
        // (для массива аттрибут length)
        // (для списка аттрибут size)
        for (String l : languages) {
            System.out.println("Я хочу выучить " + l);
        }
    }
}