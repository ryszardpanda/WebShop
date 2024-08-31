package service;

import model.Order;
import model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrdersProcessor implements Runnable {
    private final Order order;

    public OrdersProcessor(Order order) {
        this.order = order;
    }


//    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//    public void processOrderWithDelay(Order order) {
//        scheduler.schedule(() -> {
//            System.out.println("Zamówienie jest weryfikowane...");
//        }, 1, TimeUnit.SECONDS);
//
//        scheduler.schedule(() -> {
//            System.out.println("Zamówienie zostało potwierdzone.");
//        }, 3, TimeUnit.SECONDS);
//
//        scheduler.schedule(() -> {
//            System.out.println("Pomyślnie złożono zamówienie o ID: " + order.getOrderId());
//            generateInvoice(order);
//        }, 5, TimeUnit.SECONDS);
//    }
//
//    public void shutdown() {
//        scheduler.shutdown();
//        try {
//            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
//                scheduler.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            scheduler.shutdownNow();
//            Thread.currentThread().interrupt();
//        }
//    }

    private void generateInvoice(Order order) {
        String invoiceFileName = "invoice_" + order.getOrderId() + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(invoiceFileName))) {
            writer.println("Faktura dla zamówienia o ID: " + order.getOrderId());
            writer.println("Godzina złożenia zamówienia: " + order.getOrderTime());
            writer.println("===================================");
            writer.println("Szczegóły klienta:");
            writer.println("Imie: " + order.getCustomer().getName());
            writer.println("Adres: " + order.getCustomer().getAdress());
            writer.println("Email: " + order.getCustomer().getEmail());
            writer.println("===================================");
            writer.println("Produkty:");
            List<Product> products = order.getProducts();
            for (Product product : products) {
                writer.println("- " + product.getName() + ": " + product.getPrice() + " PLN");
            }
            writer.println("===================================");
            writer.println("Łączna kwota ze zniżkami: " + order.getTotalAmount() + " PLN");
            writer.println("===================================");
            writer.println("Dziękujemy za zakupy!");

            System.out.println("Faktura została zapisana");
        } catch (IOException e) {
            System.out.println("Błąd podczas generowania faktury: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        generateInvoice(order);
    }
}
