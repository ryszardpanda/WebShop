package service;

import model.Order;
import model.Product;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OrdersProcessor implements Runnable {
    private final Order order;

    public OrdersProcessor(Order order) {
        this.order = order;
    }

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
