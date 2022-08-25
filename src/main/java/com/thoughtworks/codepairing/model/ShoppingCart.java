package com.thoughtworks.codepairing.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {
    private List<Product> products;
    private Customer customer;

    private static Map<String, Integer> pointRule = new HashMap<String, Integer>(){{
        put("DIS_10", 10);
        put("DIS_15", 15);
        put("DIS_20", 20);
        put("NORMAL", 5);
    }};

    public ShoppingCart(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;

    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Order checkout() {
        double totalPrice = 0;

        int loyaltyPointsEarned = 0;
        Map<String, Integer> map = new HashMap<>(products.size());

        for (Product product : products) {
            double discount = 0;

            if (map.containsKey(product.getProductCode())) {
                map.put(product.getProductCode(), map.get(product.getProductCode()) + 1);
            } else {
                map.put(product.getProductCode(), 1);
            }

            if (product.getProductCode().startsWith("DIS_10")) {
                discount = (product.getPrice() * 0.1);
            } else if (product.getProductCode().startsWith("DIS_15")) {
                discount = (product.getPrice() * 0.15);
            } else if (product.getProductCode().startsWith("DIS_20")) {
                discount = (product.getPrice() * 0.2);
            }

            loyaltyPointsEarned += getLoyaltyPoint(product);

            if (map.get(product.getProductCode()) == 3) {
                map.put(product.getProductCode(), 0);
                continue;
            }

            totalPrice += product.getPrice() - discount;
        }

        return new Order(totalPrice, loyaltyPointsEarned);
    }

    public int getLoyaltyPoint(Product product) {

        String key = pointRule.keySet().stream().filter(prefix ->
                product.getProductCode().startsWith(prefix)
        ).findFirst().orElse("NORMAL");

        return (int) (product.getPrice() / pointRule.get(key));
    }

    @Override
    public String toString() {
        return "Customer: " + customer.getName() + "\n" + "Bought:  \n" + products.stream().map(p -> "- " + p.getName() + ", " + p.getPrice()).collect(Collectors.joining("\n"));
    }
}
