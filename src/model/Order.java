package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderId;
    private Customer customer;
    private List<Product> orders;
    private double totalAmount;
    private LocalDateTime orderTime;

    public Order(Customer customer, List<Product> orders) {
        this.orderId = UUID.randomUUID().toString();
        this.customer = customer;
        this.orders = orders;
        this.totalAmount = calculateTotalAmount();
        this.orderTime = LocalDateTime.now();
    }

    private double calculateTotalAmount(){
        return orders.stream()
                .mapToDouble(Product::getPrice)
                .sum();
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

    public double getTotalAmount() {
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
