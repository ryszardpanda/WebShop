package service;

import model.Product;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.BufferedReader;

public class ProductManager {

    List<Product> products;

    public ProductManager() {
        this.products = new ArrayList<>();
    }

    //dodawanie produktu
    public void addProduct(Product product){
        products.add(product);
        System.out.println("Dodano produkt: " + product.getName());
    }

    //usuwanie produktu
    public boolean removeProduct(int productId) {
        Optional<Product> productToRomove = products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst();
        if (productToRomove.isPresent()){
            products.remove(productToRomove.get());
            System.out.println("Usunięto produkt: " + productToRomove.get());
            return true;

        }else {
            System.out.println("Produkt o ID: " + productId + " nie został znaleziony");
            return false;
        }
    }

    //aktualizacja produktu
    public boolean updateProduct(int productId, Product updatedProduct) {
        Optional<Product> productToUpdate = products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst();
        if (productToUpdate.isPresent()){
            int indexOfProductToUpdate = products.indexOf(productToUpdate.get());
            products.set(indexOfProductToUpdate, updatedProduct);

            System.out.println("Zaktualizowano produkt: " + updatedProduct);
            return true;
        }else
            System.out.println("Produkt o Id " + productId + " nie został znaleziony");
        return false;
    }

    //przeglądanie  wszystkich produktów
    public void viewProducts() {
        if (products.isEmpty()){
            System.out.println("Brak produktów w sklepie.");
        }else {
            System.out.println("Lista produktów:");
            products.forEach(product -> {
                product.displayDetails();
                System.out.println("--------------------------------------------------------");
            });
        }
    }

    //Znajdowanie produktu po ID
    public Optional<Product> findProductById(int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst();
    }


    //chat pomogl
    public void saveProductsToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("ID,Nazwa,Cena,Ilość\n"); // Nagłówki kolumn

            for (Product product : products) {
                writer.append(String.valueOf(product.getId())).append(",");
                writer.append(product.getName()).append(",");
                writer.append(product.getPrice().toString()).append(",");
                writer.append(String.valueOf(product.getAvailableQuantity())).append("\n");
            }

            System.out.println("Produkty zapisane do pliku CSV.");
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania produktów do pliku CSV: " + e.getMessage());
        }
    }

    //chat pomogl
    public void loadProductsFromCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Pomijamy pierwszą linię (nagłówki)
                    continue;
                }

                String[] fields = line.split(",");

                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                BigDecimal price = new BigDecimal(fields[2]);
                int quantity = Integer.parseInt(fields[3]);

                Product product = new Product(id, name, price, quantity);
                products.add(product);
            }

            System.out.println("Produkty załadowane z pliku CSV.");
        } catch (IOException e) {
            System.out.println("Błąd podczas odczytywania produktów z pliku CSV: " + e.getMessage());
        }
    }
}
