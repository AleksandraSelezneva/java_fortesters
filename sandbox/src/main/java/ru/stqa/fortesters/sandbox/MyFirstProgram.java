package ru.stqa.fortesters.sandbox;

public class MyFirstProgram {

    public static void main(String[] args) {
        hello("world");
        hello("user");
        hello("Alexei");
        double len = 5;
        System.out.println("Площадь квадрата со стороной " + len + "=" + area(len));
        double c = 4;
        double d = 6;
        System.out.println("Площадь прямоугольника со сторонами " + c + " и " + d + " = " + area(c,d));
    }

    public static void hello(String somebody) {
        System.out.println("Hello," + somebody + "!");
    }

    public static double area (double l) {
    return l * l;
}
    public static double area (double a, double b) {
        return a * b;
    }
}