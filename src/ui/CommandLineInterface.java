package ui;

import model.Customer;
import model.Order;
import model.Product;
import service.Cart;
import service.OrdersProcessor;
import service.ProductManager;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

public class CommandLineInterface {
    private final ProductManager productManager;
    private final Cart cart;
    private final OrdersProcessor ordersProcessor;
    private final Scanner scanner;


    public CommandLineInterface(ProductManager productManager, Cart cart, OrdersProcessor ordersProcessor) {
        this.productManager = productManager;
        this.cart = cart;
        this.ordersProcessor = ordersProcessor;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nWitaj w sklepie internetowym!");
            System.out.println("Wybierz opcję:");
            System.out.println("1. Przeglądaj produkty");
            System.out.println("2. Dodaj produkt do koszyka");
            System.out.println("3. Pokaż produkty w koszyku");
            System.out.println("4. Złóż zamówienie");
            System.out.println("5. Pokaż historię zamówień");
            System.out.println("6. Usuń produkt");
            System.out.println("7. Wyjdź");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Dostępne produkty:");
                    productManager.viewProducts();
                    break;
                case "2":
                    addProductToCart();
                    break;
                case "3":
                    System.out.println("Produkty w koszyku:");
                    cart.viewProductsInCart();
                    break;
                case "4":
                    placeOrder();
                    break;
                case "5":
                    System.out.println("Historia zamówień:");
                    cart.viewOrders();
                    break;
                case "6":
                    removeProductFromCart();
                    break;
                case "7":
                    exit = true;
                    System.out.println("Do zobaczenia!");
                    break;


                default:
                    System.out.println("Nieprawidłowa opcja, spróbuj ponownie.");
            }
        }
    }

//    private void viewProducts() {
//        System.out.println("\nDostępne produkty:");
//        productManager.viewProducts();
//    }

    public void removeProductFromCart(){
        System.out.println("Podaj ID produktu, który chcesz usunąć z koszyka:");
        int productId = Integer.parseInt(scanner.nextLine());
        Optional<Product> product = productManager.findProductById(productId);
        if (product.isPresent()){
            productManager.removeProduct(productId);
            System.out.println("produkt: " + product.get() + " został usunięty");
        }else {
            System.out.println("Produkt o podanym ID nie istnieje.");
        }
    }

    private void addProductToCart() {
        System.out.println("Podaj ID produktu, który chcesz dodać do koszyka:");
        int productId = Integer.parseInt(scanner.nextLine());

        Optional<Product> product = productManager.findProductById(productId);
        if (product.isPresent()) {
            cart.addProductToCart(product.get());
            System.out.println("Produkt został dodany do koszyka.");
        } else {
            System.out.println("Produkt o podanym ID nie istnieje.");
        }
    }

//    private void viewCart() {
//        System.out.println("\nProdukty w koszyku:");
//        cart.viewProductsInCart();
//    }

    private void placeOrder() {
        if (cart.cart.isEmpty()) {
            System.out.println("Koszyk jest pusty, nie można złożyć zamówienia.");
            return;
        }

        System.out.println("Podaj dane klienta:");
        System.out.print("Imię i nazwisko: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Adres: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(name, email, address);
        cart.placeOrder(customer);


    }

}
