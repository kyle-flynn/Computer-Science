package edu.cis.gvsu.test;

import edu.cus.gvsu.NumberBase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Example (but nowhere near complete) unit tests for the NumberBase.conversions method
 *
 * @author Zachary Kurmas
 */
// Created  8/26/13 at 11:48 AM
// (C) Zachary Kurmas 2013
//
// Modified by Jared Moore 2016

public class NumberBaseTest_Sample {

    @Test
    public void decimal_to_binary() throws Throwable {
        System.out.println(NumberBase.convert("0", 10, 2));
        Assert.assertEquals("0", NumberBase.convert("0", 10, 2));
        Assert.assertEquals("1", NumberBase.convert("1", 10, 2));
        Assert.assertEquals("1010", NumberBase.convert("10", 10, 2));
        Assert.assertEquals("1000011110001", NumberBase.convert("4337", 10, 2));
    }

    @Test
    public void binary_to_decimal() throws Throwable {
        Assert.assertEquals("10", NumberBase.convert("1010", 2, 10));
    }

    @Test
    public void decimal_to_hex() throws Throwable {
        Assert.assertEquals("64", NumberBase.convert("100", 10, 16));
        Assert.assertEquals("dead", NumberBase.convert("57005", 10, 16));
    }

    @Test
    public void hex_to_binary() throws Throwable {
        Assert.assertEquals("1010", NumberBase.convert("a", 16, 2));
    }

    @Test
    public void randoms_to_hex() throws Throwable {
        Assert.assertEquals("8bdf", NumberBase.convert("t82", 35, 16));
    }

    // Remember:  When looking for an exception, you can do only *one* test
    // per method.
    @Test(expected = IllegalArgumentException.class)
    public void input_is_valid1() {
        NumberBase.convert("14d", 10, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void input_is_valid2() {
        NumberBase.convert("3", 2, 6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void input_is_valid3() {
        NumberBase.convert("z", 30, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void input_is_valid4() {
        NumberBase.convert("X", 10, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void input_is_valid5() {
        NumberBase.convert("$", 10, 2);
    }

    // Be sure to test other values and base pairs!
}