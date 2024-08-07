package service;

import model.Order;
import model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OrdersProcessor {

    public void processOrder(Order order) {
        System.out.println("Przetwarzanie zamówienia ID: " + order.getOrderId());
        generateInvoice(order);
    }
    public void generateInvoice(Order order){
        String invoiceFileName = "invoice_" + order.getOrderId() + ".txt";

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(invoiceFileName));
            writer.println("Faktura dla zamówienia o ID: " + order.getOrderId());
            writer.println("Adres: " + order.getCustomer().getAdress());
            writer.println("Email: " + order.getCustomer().getEmail());
            writer.println("===================================");
            writer.println("Produkty:");
            List<Product> products = order.getProducts();
            for (Product product : products) {
                writer.println("- " + product.getName() + ": " + product.getPrice() + " PLN");
            }
            writer.println("===================================");
            writer.println("Łączna kwota: " + order.getTotalAmount() + " PLN");
            writer.println("===================================");
            writer.println("Dziękujemy za zakupy!");

            System.out.println("Faktura została pomyślnie wygenerowana");
            writer.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas generowania faktury: " + e.getMessage());
        }


    }
}
