package ui;

import model.*;
import service.Cart;
import service.OrdersProcessor;
import service.ProductManager;

import java.math.BigDecimal;
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
            System.out.println("3. Konfiguruj produkt");
            System.out.println("4. Pokaż produkty w koszyku");
            System.out.println("5. Złóż zamówienie");
            System.out.println("6. Pokaż historię zamówień");
            System.out.println("7. Usuń produkt");
            System.out.println("8. Sprawdź aktualne promocje");
            System.out.println("9. Wyjdź");

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
                    configureProduct();
                    break;
                case "4":
                    System.out.println("Produkty w koszyku:");
                    cart.viewProductsInCart();
                    break;
                case "5":
                    placeOrder();
                    break;
                case "6":
                    System.out.println("Historia zamówień:");
                    cart.viewOrders();
                    break;
                case "7":
                    removeProductFromCart();
                    break;
                    case "8":
                        System.out.println("Promocje:");
                    checkDiscount();
                    break;
                case "9":
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
            cart.removeProductFromCart(productId);
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

    //pomogl chat :P
    private void configureProduct() {
        System.out.println("Wybierz typ produktu do skonfigurowania:");
        System.out.println("1. Komputer");
        System.out.println("2. Smartphone");

        String productChoice = scanner.nextLine();

        switch (productChoice) {
            case "1":
                configureComputer();
                break;
            case "2":
                configureSmartphone();
                break;
            default:
                System.out.println("Nieprawidłowy wybór.");
        }
    }

    //pomogl chat
    private void configureComputer() {
        System.out.println("Podaj ID komputera do konfiguracji:");
        int productId = Integer.parseInt(scanner.nextLine());

        Optional<Product> product = productManager.findProductById(productId);
        if (product.isPresent() && product.get() instanceof Computer) {
            Computer computer = (Computer) product.get();

            System.out.println("Wybierz procesor:");
            for (Processor processor : Processor.values()) {
                System.out.println(processor.ordinal() + 1 + ". " + processor);
            }
            int processorChoice = Integer.parseInt(scanner.nextLine());
            Processor chosenProcessor = Processor.values()[processorChoice - 1];

            System.out.println("Wybierz ilość RAM:");
            for (RAM ram : RAM.values()) {
                System.out.println(ram.ordinal() + 1 + ". " + ram);
            }
            int ramChoice = Integer.parseInt(scanner.nextLine());
            RAM chosenRam = RAM.values()[ramChoice - 1];

            System.out.println("Podaj rozmiar dysku (w GB):");
            int storage = Integer.parseInt(scanner.nextLine());

            computer.configureComputer(chosenProcessor, chosenRam, storage);
            System.out.println("Komputer skonfigurowany: " + computer);

            System.out.println("Czy chcesz dodać produkt do koszyka? (tak/nie)");
            String addToCartChoice = scanner.nextLine();
            if (addToCartChoice.equalsIgnoreCase("tak")) {
                cart.addProductToCart(computer);
                System.out.println("Skonfigurowany komputer został dodany do koszyka.");
            } else {
                System.out.println("Konfiguracja została porzucona.");
            }
        } else {
            System.out.println("Produkt o podanym ID nie istnieje lub nie jest komputerem.");
        }
    }

    //pomogl chat
    private void configureSmartphone() {
        System.out.println("Podaj ID smartfona do konfiguracji:");
        int productId = Integer.parseInt(scanner.nextLine());

        Optional<Product> product = productManager.findProductById(productId);
        if (product.isPresent() && product.get() instanceof Smartphone) {
            Smartphone smartphone = (Smartphone) product.get(); //rzutowanie product na smartphone

            System.out.println("Wybierz kolor smartfona:");
            for (Color color : Color.values()) {
                System.out.println(color.ordinal() + 1 + ". " + color);
            }
            int colorChoice = Integer.parseInt(scanner.nextLine());
            Color chosenColor = Color.values()[colorChoice - 1];
            smartphone.setColor(chosenColor);

            System.out.println("Wybierz pojemność baterii:");
            for (BatteryCapacity batteryCapacity : BatteryCapacity.values()) {
                System.out.println(batteryCapacity.ordinal() + 1 + ". " + batteryCapacity);
            }
            int batteryChoice = Integer.parseInt(scanner.nextLine());
            BatteryCapacity chosenBattery = BatteryCapacity.values()[batteryChoice - 1];
            smartphone.setBatteryCapacity(chosenBattery);

            System.out.println("Wybierz akcesoria (wprowadź numery rozdzielone przecinkami, lub zostaw puste, aby pominąć):");
            for (Accessories accessory : Accessories.values()) {
                System.out.println(accessory.ordinal() + 1 + ". " + accessory);
            }
            String accessoriesInput = scanner.nextLine();
            if (!accessoriesInput.isEmpty()) {
                String[] accessoriesChoices = accessoriesInput.split(",");
                for (String choice : accessoriesChoices) {
                    int accessoryIndex = Integer.parseInt(choice.trim()) - 1;
                    smartphone.addAccessory(Accessories.values()[accessoryIndex]);
                }
            }

            System.out.println("Smartfon skonfigurowany: " + smartphone);

            System.out.println("Czy chcesz dodać produkt do koszyka? (tak/nie)");
            String addToCartChoice = scanner.nextLine();
            if (addToCartChoice.equalsIgnoreCase("tak")) {
                cart.addProductToCart(smartphone);
                System.out.println("Skonfigurowany smartfon został dodany do koszyka.");
            } else {
                System.out.println("Konfiguracja została porzucona.");
            }
        } else {
            System.out.println("Produkt o podanym ID nie istnieje lub nie jest smartfonem.");
        }
    }

    public void checkDiscount (){
        System.out.println("Zyskaj 10% zniżki przy zakupach za minimum 5000zł");
        System.out.println("Zyskaj 5% zniżki przy zakupach za minimum 3000zł");
    }


}
