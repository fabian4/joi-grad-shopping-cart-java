package com.thoughtworks.codepairing.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ShoppingCartTest {

    public static final int PRICE = 100;
    public static final String PRODUCT = "Product";

    Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("test");
    }

    @Test
    public void shouldCalculatePriceWithNoDiscount() {
        List<Product> products = asList(new Product(PRICE, "", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(100.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsWithNoDiscount() {
        List<Product> products = asList(new Product(PRICE, "", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(20, order.getLoyaltyPoints());
    }

    @Test
    public void shouldCalculatePriceFor10PercentDiscount() {
        List<Product> products = asList(new Product(PRICE, "DIS_10_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(90.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsFor10PercentDiscount() {
        List<Product> products = asList(new Product(PRICE, "DIS_10_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(10, order.getLoyaltyPoints());
    }

    @Test
    public void shouldCalculatePriceFor15PercentDiscount() {
        List<Product> products = asList(new Product(PRICE, "DIS_15_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(85.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsFor15PercentDiscount() {
        List<Product> products = asList(new Product(PRICE, "DIS_15_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(6, order.getLoyaltyPoints());
    }

    @Test
    public void shouldCalculatePriceFor20PercentDiscount() {
        List<Product> products = asList(new Product(PRICE, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(80.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointFor20PercentDiscount() {
        List<Product> products = asList(new Product(PRICE, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(5, order.getLoyaltyPoints());
    }

    @Test
    public void shouldCalculatePriceWithoutFreeExtra() {
        List<Product> products = asList(
                new Product(PRICE, "DIS_20_AAA", PRODUCT),
                new Product(PRICE, "DIS_20_BBB", PRODUCT),
                new Product(PRICE, "DIS_20_CCC", PRODUCT)
        );
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(80.0 * 3, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculatePriceWithFreeExtra() {
        List<Product> products = asList(
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT),
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT),
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT)
        );
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(100.0 * 2, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculatePriceWithFreeExtra_A() {
        List<Product> products = asList(
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT),
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT),
                new Product(PRICE, "DIS_20_AAA", PRODUCT)
        );
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(100.0 * 2 + 80.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculatePriceWithFreeExtra_B() {
        List<Product> products = asList(
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT),
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT),
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT),
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT),
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT),
                new Product(PRICE, "BULK_BUY_2_GET_1_AAA", PRODUCT)
        );
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(100.0 * 4, order.getTotalPrice(), 0.0);
    }
}
