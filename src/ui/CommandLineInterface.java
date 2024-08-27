package ui;

import model.*;
import service.Cart;
import service.OrdersProcessor;
import service.ProductManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

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
        productManager.loadProductsFromCSV("products.csv");
       // boolean exit = false;
        boolean running = true;
        while (running) {
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
            System.out.println("--------------------------------------------------------");
            System.out.println("10. Tryb administratora (ADMIN ONLY)");

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
                    try {
                        cart.shutdown();
                        running = false;
                        System.out.println("Do zobaczenia!");
                        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                            try {
                                productManager.saveProductsToCSV("products.csv");
                                System.out.println("Produkty zostały zapisane do pliku products.csv");
                            } catch (Exception e) {
                                System.out.println("Wystąpił błąd podczas zapisywania produktów: " + e.getMessage());
                            }
                        }));
                    }catch (Exception e){
                        System.out.println("Wystąpił błąd podczas zamykania programu: " + e.getMessage());
                    }
                    break;
                    case "10":
                    System.out.println("Witaj w trybie administratora, podaj swoje dane");
                    adminMode();
                    break;

                default:
                    System.out.println("Nieprawidłowa opcja, spróbuj ponownie.");
            }
        }
    }

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
        try {
            System.out.println("Podaj ID produktu, który chcesz dodać do koszyka:");
            int productId = Integer.parseInt(scanner.nextLine());

            Optional<Product> product = productManager.findProductById(productId);
            if (product.isPresent()) {
                Product selectedProduct = product.get();

                System.out.println("Podaj ilość sztuk:");
                int quantity = Integer.parseInt(scanner.nextLine());

                if (quantity > selectedProduct.getAvailableQuantity()) {
                    System.out.println("Niewystarczająca ilość dostępnych produktów. Dostępna ilość: " + selectedProduct.getAvailableQuantity());
                } else {
                    cart.addProductToCart(selectedProduct, quantity);
                    System.out.println("Dodano " + quantity + " sztuk produktu do koszyka.");
                }
            } else {
                System.out.println("Produkt o podanym ID nie istnieje.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Błąd: Wprowadzono niepoprawny format liczby.");
        } catch (Exception e) {
            System.out.println("Wystąpił błąd: " + e.getMessage());
        }
    }

    private void placeOrder() {
        try {

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
        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas składania zamówienia: " + e.getMessage());
        }
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
        try {
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
                    System.out.println("Podaj ilość sztuk:");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    cart.addProductToCart(computer, quantity);
                    System.out.println("Skonfigurowany komputer został dodany do koszyka.");
                } else {
                    System.out.println("Konfiguracja została porzucona.");
                }
            } else {
                System.out.println("Produkt o podanym ID nie istnieje lub nie jest komputerem.");
            }
        }catch (NumberFormatException e) {
            System.out.println("Błąd: Wprowadzono niepoprawny format liczby.");
        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas konfiguracji komputera: " + e.getMessage());
        }
    }

    //pomogl chat
    private void configureSmartphone() {
        try {
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
                    System.out.println("Podaj ilość sztuk:");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    cart.addProductToCart(smartphone, quantity);
                    System.out.println("Skonfigurowany smartfon został dodany do koszyka.");
                } else {
                    System.out.println("Konfiguracja została porzucona.");
                }
            } else {
                System.out.println("Produkt o podanym ID nie istnieje lub nie jest smartfonem.");
            }
        }catch (NumberFormatException e) {
            System.out.println("Błąd: Wprowadzono niepoprawny format liczby.");
        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas konfiguracji komputera: " + e.getMessage());
        }
    }

    public void checkDiscount (){
        System.out.println("Zyskaj 10% zniżki przy zakupach za minimum 5000zł");
        System.out.println("Zyskaj 5% zniżki przy zakupach za minimum 3000zł");
    }


    public void adminMode() {
        String correctLogin = "admin1";
        String correctPassword = "admin1";

        System.out.println("Podaj login:");
        String login = scanner.nextLine();
        System.out.println("Podaj haslo:");
        String password = scanner.nextLine();

        if (login.equals(correctLogin) && password.equals(correctPassword)) {
            System.out.println("Dane są poprawne:");
            System.out.println("Wybierz operację, którą chcesz wykonać");
            System.out.println("1. Dodaj produkt");
            System.out.println("2. Usuń produkt");
            System.out.println("3. Modyfikuj produkt");

            String operationChoice = scanner.nextLine();
            switch (operationChoice) {
                case "1":
                    try {
                    System.out.println("Podaj nazwę produktu:");
                    String productName = scanner.nextLine();

                    System.out.println("Podaj ID produktu:");
                    int productId = Integer.parseInt(scanner.nextLine());

                    System.out.println("Podaj cenę produktu:");
                    BigDecimal productPrice = new BigDecimal(scanner.nextLine());

                    System.out.println("Podaj ilość dostępnych sztuk:");
                    int productQuantity = Integer.parseInt(scanner.nextLine());

                    Product newProduct = new Product(productId, productName, productPrice, productQuantity);

                    productManager.addProduct(newProduct);
                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: Wprowadzono niepoprawny format liczby.");
                    } catch (Exception e) {
                        System.out.println("Wystąpił błąd podczas dodawania produktu: " + e.getMessage());
                    }
                    break;
                case "2":
                    try {
                    System.out.println("Podaj ID produktu do usunięcia:");
                    int productIdToRemove = Integer.parseInt(scanner.nextLine());
                     productManager.removeProduct(productIdToRemove);
                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: Wprowadzono niepoprawny format liczby.");
                    } catch (Exception e) {
                        System.out.println("Wystąpił błąd podczas usuwania produktu: " + e.getMessage());
                    }
                    break;
                case "3":
                    try {
                    System.out.println("Podaj ID produktu, który chesz zaktualizować:");
                    int productIdToUpdate = Integer.parseInt(scanner.nextLine());

                    System.out.println("Podaj zaktualizowaną nazwę:");
                    String productNameToUpdate = scanner.nextLine();


                    System.out.println("Podaj zamtualizowaną cenę:");
                    BigDecimal productPriceToUpdate = new BigDecimal(scanner.nextLine());

                    System.out.println("Podaj zaktualizowaną ilość sztuk:");
                    int productQuantityToUpdate = Integer.parseInt(scanner.nextLine());

                    Product updatedProduct = new Product(productIdToUpdate, productNameToUpdate, productPriceToUpdate, productQuantityToUpdate);
                    productManager.updateProduct(productIdToUpdate, updatedProduct);
                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: Wprowadzono niepoprawny format liczby.");
                    } catch (Exception e) {
                        System.out.println("Wystąpił błąd podczas aktualizacji produktu: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Nieprawidłowy wybór.");
            }
        } else {
            System.out.println("Dane są niepoprawne");
        }
    }
}
