package service;

import model.Customer;
import model.Order;
import model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    public List<Product> cart;
    List<Order> orders;
    private OrdersProcessor orderProcessor;

    public Cart() {
        this.cart = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.orderProcessor = new OrdersProcessor();
    }

    //dodawanie produktu do koszyka
    public void addProductToCart(Product product, int quantity){
        for (int i = 0; i < quantity; i++) {
            cart.add(product);
        }
        System.out.println("Dodano produkt do koszyka " + product.getName());
        System.out.println("Kwota: " + product.getPrice());
    }

    //usuwanie produktu z koszyka
    public boolean removeProductFromCart(int productId) {
        Optional<Product> productToRomove = cart.stream()
                .filter(product -> product.getId() == productId)
                .findFirst();
        if (productToRomove.isPresent()){
            cart.remove(productToRomove.get());
            System.out.println("Usunięto produkt z koszyka: " + productToRomove.get());
            return true;

        }else {
            System.out.println("Produkt o ID: " + productId + " nie został znaleziony w koszyku");
            return false;
        }
    }

    //przeglądanie  wszystkich produktów
    public void viewProductsInCart() {
        if (cart.isEmpty()){
            System.out.println("Brak produktów w koszyku.");
        }else {
            System.out.println("Lista produktów w koszyku:");
            cart.forEach(product -> {
                        product.displayDetails();
                BigDecimal price = product.getPrice();
                System.out.println("Kwota: " + price);
                System.out.println("--------------------------------------------------------");
                    });

            Order tempOrder = new Order(null, new ArrayList<>(cart));
            BigDecimal totalAmount = tempOrder.getTotalAmount();
            System.out.println("Łączna kwota po zastosowaniu zniżki: " + totalAmount);
        }
    }

    public BigDecimal calculateTotalAmount() {
        BigDecimal totalAmount = cart.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalAmount.compareTo(BigDecimal.valueOf(5000)) > 0) {
            totalAmount = totalAmount.multiply(BigDecimal.valueOf(0.90)); // 10% zniżki
        } else if (totalAmount.compareTo(BigDecimal.valueOf(3000)) > 0) {
            totalAmount = totalAmount.multiply(BigDecimal.valueOf(0.95)); // 5% zniżki
        }

        return totalAmount;
    }

    //składanie zamówienia
    public void placeOrder(Customer customer){
        BigDecimal totalAmount = calculateTotalAmount();
        if (cart.isEmpty()){
            System.out.println("Koszyk jest pusty, brak możliwości złożenia zamówienia");
            return;
        }

        for (Product product : cart) {
            product.setAvailableQuantity(product.getAvailableQuantity() - 1);
        }

            Order order = new Order(customer, new ArrayList<>(cart));
            orders.add(order);
            order.displayOrderDetails();
            cart.clear();

            System.out.println("Zamówienie zostało złożonę i zapisane");
            System.out.println("Godzina złożenia zamówienia: " + order.getOrderTime());
            orderProcessor.processOrder(order);

    }

    public void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("Brak złożonych zamówień.");
        } else {
            System.out.println("Lista złożonych zamówień:");
            for (Order order : orders) {
                System.out.println("Zamówienie złożone dnia: " + order.getOrderTime());
                order.displayOrderDetails();
                System.out.println("--------------------------------------------------------");
            }

        }
    }
}
