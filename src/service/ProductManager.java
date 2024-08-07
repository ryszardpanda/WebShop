package service;

import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {

    List<Product> products;

    public ProductManager() {
        this.products = new ArrayList<>();
    }

    //dodawanie produktu
    public void addProduct(Product product){
        products.add(product);
        System.out.println("Dodano produkt " + product.getName());
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
            products.forEach(Product::displayDetails);
        }
    }

    //Znajdowanie produktu po ID
    public Optional<Product> findProductById(int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst();
    }
}
