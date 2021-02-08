package ru.stqa.fortesters.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;


public class DistanceTests {

    @Test
    public void testDistance1(){
        Point p1 = new Point(1,2);
        Point p2 = new Point(3,4);
        Assert.assertEquals(p1.distance(p2),2.8284271247461903);
    }
    @Test
    public void testDistance2(){
        Point p1 = new Point(1,1);
        Point p2 = new Point(10,3);
        Assert.assertEquals(p1.distance(p2),9.219544457292887);
    }
    @Test
    public void testDistance3(){
        Point p1 = new Point(0,0);
        Point p2 = new Point(9,9);
        Assert.assertEquals(p1.distance(p2),12.727922061357855);
    }
}
