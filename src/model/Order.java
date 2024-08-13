package model;

import service.Cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderId;
    private Customer customer;
    private List<Product> orders;
    private BigDecimal totalAmount;
    private LocalDateTime orderTime;
    private Cart cart;

    public Order(Customer customer, List<Product> orders) {
        this.orderId = UUID.randomUUID().toString();
        this.customer = customer;
        this.orders = orders;
        this.totalAmount = calculateTotalAmount();
        this.orderTime = LocalDateTime.now();
    }

    private BigDecimal calculateTotalAmount(){
        BigDecimal totalAmount = orders.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalAmount.compareTo(BigDecimal.valueOf(5000)) > 0) {
            System.out.println("Zastosowano zniżkę 10% na całkowitą kwotę powyżej 5000 zł.");
            totalAmount = totalAmount.multiply(BigDecimal.valueOf(0.90)); // 10% discount
        } else if (totalAmount.compareTo(BigDecimal.valueOf(3000)) > 0) {
            System.out.println("Zastosowano zniżkę 5% na całkowitą kwotę powyżej 3000 zł.");
            totalAmount = totalAmount.multiply(BigDecimal.valueOf(0.95)); // 5% discount
        }

        return totalAmount;
    }

    public List<Product> getProducts() {
        return orders;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getOrders() {
        return orders;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void displayOrderDetails() {
        System.out.println("Zamówienie ID: " + orderId);
        System.out.println("Klient: " + customer.getName());
        System.out.println("Szczegóły zamówienia:");
        orders.forEach(Product::displayDetails);
        System.out.println("Łączna kwota zamówienia: " + totalAmount);
    }
}
