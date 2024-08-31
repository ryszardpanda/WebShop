package service;

import model.Customer;
import model.Order;
import model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cart {
    public List<Product> cart;
    List<Order> orders;
    private final String cartFilePath = "/Users/macbookpro/Desktop/java_projects/WebShop/cart.csv";

    public Cart() {
        this.cart = new ArrayList<>();
        this.orders = new ArrayList<>();
        loadCartFromCSV();
    }

    // dodawanie produktu do koszyka
    public void addProductToCart(Product product, int quantity) {
        for (int i = 0; i < quantity; i++) {
            cart.add(product);
        }
        System.out.println("Dodano produkt do koszyka " + product.getName());
        System.out.println("Kwota: " + product.getPrice());
        saveCartToCSV(); // Zapisz zmiany w koszyku
    }

    // usuwanie produktu z koszyka
    public boolean removeProductFromCart(int productId) {
        Optional<Product> productToRomove = cart.stream()
                .filter(product -> product.getId() == productId)
                .findFirst();
        if (productToRomove.isPresent()) {
            cart.remove(productToRomove.get());
            System.out.println("Usunięto produkt z koszyka: " + productToRomove.get());
            saveCartToCSV(); // Zapisz zmiany w koszyku
            return true;
        } else {
            System.out.println("Produkt o ID: " + productId + " nie został znaleziony w koszyku");
            return false;
        }
    }

    // przeglądanie wszystkich produktów
    public void viewProductsInCart() {
        if (cart.isEmpty()) {
            System.out.println("Brak produktów w koszyku.");
        } else {
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

    // Zapisanie koszyka do pliku CSV
    private void saveCartToCSV() {
        try (FileWriter writer = new FileWriter(cartFilePath)) {
            for (Product product : cart) {
                writer.append(String.valueOf(product.getId())).append(",");
                writer.append(product.getName()).append(",");
                writer.append(product.getPrice().toString()).append(",");
                writer.append(String.valueOf(product.getAvailableQuantity())).append("\n");
            }
            System.out.println("Koszyk został zapisany do pliku CSV.");
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania koszyka do pliku CSV: " + e.getMessage());
        }
    }

    // Wczytanie koszyka z pliku CSV
    private void loadCartFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(cartFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                BigDecimal price = new BigDecimal(fields[2]);
                int quantity = Integer.parseInt(fields[3]);

                // Dla prostoty przyjmujemy, że wszystkie produkty są ogólnego typu "Product".
                Product product = new Product(id, name, price, quantity);
                cart.add(product);
            }
            System.out.println("Koszyk został wczytany z pliku CSV.");
        } catch (IOException e) {
            System.out.println("Błąd podczas wczytywania koszyka z pliku CSV: " + e.getMessage());
        }
    }

    // składanie zamówienia
    public void placeOrder(Customer customer) {
        if (cart.isEmpty()) {
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
        saveCartToCSV(); // Koszyk zostaje wyczyszczony, więc zapisujemy pusty stan
        System.out.println("Zamówienie zostało złożone.");
        System.out.println("Godzina złożenia zamówienia: " + order.getOrderTime());


        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new OrdersProcessor(order));
        executorService.shutdown();
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
