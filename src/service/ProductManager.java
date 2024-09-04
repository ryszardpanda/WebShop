package service;

import model.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {

   public List<Product> products;

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
        }
            System.out.println("Produkt o Id " + productId + " nie został znaleziony");
        return false;
    }

    //przeglądanie  wszystkich produktów
    public void viewProducts() {
        if (products.isEmpty()){
            System.out.println("Brak produktów w sklepie.");
            return;
        }
            System.out.println("Lista produktów:");
            products.forEach(product -> {
                product.displayDetails();
                System.out.println("--------------------------------------------------------");
            });
    }

    //Znajdowanie produktu po ID
    public Optional<Product> findProductById(int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst();
    }

    public void saveProductsToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("ID,Nazwa,Cena,Ilość,Typ\n"); // Column headers

            for (Product product : products) {
                writer.append(String.valueOf(product.getId())).append(",");
                writer.append(product.getName()).append(",");
                writer.append(product.getPrice().toString()).append(","); // BigDecimal to String
                writer.append(String.valueOf(product.getAvailableQuantity())).append(",");
                writer.append(getProductType(product)).append("\n");
            }

            System.out.println("Produkty zapisane do pliku CSV.");
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania produktów do pliku CSV: " + e.getMessage());
        }
    }

    public void loadProductsFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header row

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                BigDecimal price = new BigDecimal(fields[2]);
                int quantity = Integer.parseInt(fields[3]);
                String typeName = fields[4].trim();

                ProductType type = ProductType.fromString(typeName);

                Product product;
                switch (type) {
                    case COMPUTER:
                        product = new Computer(id, name, price, quantity);
                        break;
                    case SMARTPHONE:
                        product = new Smartphone(id, name, price, quantity);
                        break;
                    default:
                        product = new Electronics(id, name, price, quantity);
                        break;
                }

                products.add(product);
            }
            System.out.println("Produkty zostały załadowane z pliku CSV.");
        } catch (IOException e) {
            System.out.println("Błąd podczas wczytywania pliku CSV: " + e.getMessage());
        }
    }

    private String getProductType(Product product) {
        if (product instanceof Computer) {
            return ProductType.COMPUTER.getTypeName();
        } else if (product instanceof Smartphone) {
            return ProductType.SMARTPHONE.getTypeName();
        } else {
            return ProductType.ELECTRONICS.getTypeName();
        }
    }
}
