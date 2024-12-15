package ui;

import model.*;
import service.Cart;
import service.ProductManager;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class CommandLineInterface {
    private final ProductManager productManager;
    private final Cart cart;
    private final Scanner scanner;


    public CommandLineInterface(ProductManager productManager, Cart cart) {
        this.productManager = productManager;
        this.cart = cart;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        productManager.loadProductsFromCSV("products.csv");
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
            System.out.println("7. Usuń produkt z koszyka");
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
                        running = false;
                        System.out.println("Do zobaczenia!");
                            try {
                                productManager.saveProductsToCSV("products.csv");
                                System.out.println("Produkty zostały zapisane do pliku products.csv");
                            } catch (Exception e) {
                                System.out.println("Wystąpił błąd podczas zapisywania produktów: " + e.getMessage());
                            }

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
        int productId;
        while (true){
            try {
                System.out.println("Podaj ID produktu, który chcesz usunąć z koszyka:");
                productId = Integer.parseInt(scanner.nextLine());
                break;
            }catch (NumberFormatException e){
                System.out.println("Błąd: Wprowadzono niepoprawny format ilości. Wprowadź liczbę całkowitą.");
            }
        }
        Optional<Product> product = productManager.findProductById(productId);
        if (product.isPresent()){
            cart.removeProductFromCart(productId);
        }else {
            System.out.println("Produkt o podanym ID nie istnieje.");
        }
    }

    private void addProductToCart() {
        try {
            int productId;
            while (true){
                try {
                    System.out.println("Podaj ID produktu, który chcesz dodać do koszyka:");
                    productId = Integer.parseInt(scanner.nextLine());
                    break;
                }catch (NumberFormatException e) {
                    System.out.println("Błąd: Wprowadzono niepoprawny format ilości. Wprowadź liczbę całkowitą.");
                }
            }

            Optional<Product> product = productManager.findProductById(productId);
            if (product.isPresent()) {
                Product selectedProduct = product.get();

                int quantity;
                while (true) {
                    try {
                        System.out.println("Podaj ilość sztuk:");
                        quantity = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: Wprowadzono niepoprawny format ilości. Wprowadź liczbę całkowitą.");
                    }
                }

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

    private void configureProduct() {
        while (true) {
            System.out.println("Wybierz typ produktu do skonfigurowania:");
            System.out.println("1. Komputer");
            System.out.println("2. Smartphone");

            String productChoice = scanner.nextLine();

            switch (productChoice) {
                case "1":
                    configureComputer();
                    return;
                case "2":
                    configureSmartphone();
                    return;
                default:
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private void configureComputer() {
        boolean validId = false;
        Computer computer = null;

        while (!validId) {
            try {
                System.out.println("Podaj ID komputera do konfiguracji:");
                int productId = Integer.parseInt(scanner.nextLine());

                Optional<Product> product = productManager.findProductById(productId);
                if (product.isPresent() && product.get() instanceof Computer) {
                    computer = (Computer) product.get();
                    validId = true;
                } else {
                    System.out.println("Produkt o podanym ID nie istnieje lub nie jest komputerem. Spróbuj ponownie.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Błąd: Wprowadzono niepoprawny format liczby. Spróbuj ponownie.");
            }
        }

        // Konfigurowanie procesora
        Processor chosenProcessor = null;
        while (chosenProcessor == null) {
            System.out.println("Wybierz procesor:");
            for (Processor processor : Processor.values()) {
                System.out.println(processor.ordinal() + 1 + ". " + processor);
            }
            try {
                int processorChoice = Integer.parseInt(scanner.nextLine());
                chosenProcessor = Processor.values()[processorChoice - 1];
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Błędny wybór. Spróbuj ponownie.");
            }
        }

        // Konfigurowanie RAM
        RAM chosenRam = null;
        while (chosenRam == null) {
            System.out.println("Wybierz ilość RAM:");
            for (RAM ram : RAM.values()) {
                System.out.println(ram.ordinal() + 1 + ". " + ram);
            }
            try {
                int ramChoice = Integer.parseInt(scanner.nextLine());
                chosenRam = RAM.values()[ramChoice - 1];
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Błędny wybór. Spróbuj ponownie.");
            }
        }

        // Konfigurowanie dysku
        int storage = -1;
        while (storage < 0) {
            System.out.println("Podaj rozmiar dysku (w GB):");
            try {
                storage = Integer.parseInt(scanner.nextLine());
                if (storage < 0) {
                    System.out.println("Rozmiar dysku musi być dodatni. Spróbuj ponownie.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Błąd: Wprowadzono niepoprawny format liczby. Spróbuj ponownie.");
            }
        }

        // Zatwierdzenie konfiguracji
        computer.configureComputer(chosenProcessor, chosenRam, storage);
        System.out.println("Komputer skonfigurowany: " + computer);

        System.out.println("Czy chcesz dodać produkt do koszyka? (tak/nie)");

        String addToCartChoice = "";
        while (!addToCartChoice.equalsIgnoreCase("tak") && !addToCartChoice.equalsIgnoreCase("nie")) {
            addToCartChoice = scanner.nextLine();
            if (addToCartChoice.equalsIgnoreCase("tak")) {
                int quantity = -1;
                while (quantity < 1) {
                    System.out.println("Podaj ilość sztuk:");
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity < 1) {
                            System.out.println("Ilość musi być dodatnia. Spróbuj ponownie.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: Wprowadzono niepoprawny format liczby. Spróbuj ponownie.");
                    }
                }
                cart.addProductToCart(computer, quantity);
                System.out.println("Skonfigurowany komputer został dodany do koszyka.");
            } else if (addToCartChoice.equalsIgnoreCase("nie")) {
                System.out.println("Konfiguracja została porzucona.");
            } else {
                System.out.println("Nieprawidłowy wybór. Wpisz 'tak' lub 'nie'.");
            }
        }
        }
    //pomogl chat
    private void configureSmartphone() {
        Smartphone smartphone = null;
        boolean validId = false;

        while (!validId) {
            try {
                System.out.println("Podaj ID smartfona do konfiguracji:");
                int productId = Integer.parseInt(scanner.nextLine());

                Optional<Product> product = productManager.findProductById(productId);
                if (product.isPresent() && product.get() instanceof Smartphone) {
                    smartphone = (Smartphone) product.get();
                    validId = true;
                } else {
                    System.out.println("Produkt o podanym ID nie istnieje lub nie jest smartfonem. Spróbuj ponownie.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Błąd: Wprowadzono niepoprawny format liczby. Spróbuj ponownie.");
            }
        }

        Color chosenColor = null;
        while (chosenColor == null) {
            System.out.println("Wybierz kolor smartfona:");
            for (Color color : Color.values()) {
                System.out.println(color.ordinal() + 1 + ". " + color);
            }
            try {
                int colorChoice = Integer.parseInt(scanner.nextLine());
                chosenColor = Color.values()[colorChoice - 1];
                smartphone.setColor(chosenColor);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Błędny wybór koloru. Spróbuj ponownie.");
            }
        }

        BatteryCapacity chosenBattery = null;
        while (chosenBattery == null) {
            System.out.println("Wybierz pojemność baterii:");
            for (BatteryCapacity batteryCapacity : BatteryCapacity.values()) {
                System.out.println(batteryCapacity.ordinal() + 1 + ". " + batteryCapacity);
            }
            try {
                int batteryChoice = Integer.parseInt(scanner.nextLine());
                chosenBattery = BatteryCapacity.values()[batteryChoice - 1];
                smartphone.setBatteryCapacity(chosenBattery);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Błędny wybór pojemności baterii. Spróbuj ponownie.");
            }
        }

        System.out.println("Wybierz akcesoria (wprowadź numery rozdzielone przecinkami, lub zostaw puste, aby pominąć):");
        for (Accessories accessory : Accessories.values()) {
            System.out.println(accessory.ordinal() + 1 + ". " + accessory);
        }

        String accessoriesInput = scanner.nextLine();
        if (!accessoriesInput.isEmpty()) {
            String[] accessoriesChoices = accessoriesInput.split(",");
            for (String choice : accessoriesChoices) {
                try {
                    int accessoryIndex = Integer.parseInt(choice.trim()) - 1;
                    smartphone.addAccessory(Accessories.values()[accessoryIndex]);
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Błędny wybór akcesoriów: " + choice.trim() + ". Pomijanie tego wyboru.");
                }
            }
        }

        System.out.println("Smartfon skonfigurowany: " + smartphone);

        String addToCartChoice = "";
        while (!addToCartChoice.equalsIgnoreCase("tak") && !addToCartChoice.equalsIgnoreCase("nie")) {
            System.out.println("Czy chcesz dodać produkt do koszyka? (tak/nie)");
            addToCartChoice = scanner.nextLine();

            if (addToCartChoice.equalsIgnoreCase("tak")) {
                int quantity = -1;
                while (quantity < 1) {
                    System.out.println("Podaj ilość sztuk:");
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity < 1) {
                            System.out.println("Ilość musi być dodatnia. Spróbuj ponownie.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: Wprowadzono niepoprawny format liczby. Spróbuj ponownie.");
                    }
                }
                cart.addProductToCart(smartphone, quantity);
                System.out.println("Skonfigurowany smartfon został dodany do koszyka.");
            } else if (addToCartChoice.equalsIgnoreCase("nie")) {
                System.out.println("Konfiguracja została porzucona.");
            } else {
                System.out.println("Nieprawidłowy wybór. Wpisz 'tak' lub 'nie'.");
            }
        }
    }


    public void checkDiscount (){
        System.out.println("Zyskaj 10% zniżki przy zakupach za minimum 5000zł");
        System.out.println("Zyskaj 5% zniżki przy zakupach za minimum 3000zł");
    }


    public void adminMode() {

        String correctLogin = "admin1";
        String correctPassword = "admin1";

        String login;
        String password;

        do {
            System.out.println("Podaj login:");
            login = scanner.nextLine();
            System.out.println("Podaj haslo:");
            password = scanner.nextLine();

            if (!login.equals(correctLogin) || !password.equals(correctPassword)) {
                System.out.println("Dane są niepoprawne, spróbuj ponownie");
            }
        } while (!login.equals(correctLogin) || !password.equals(correctPassword));


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
                        int productId;
                        while (true) {
                            try {
                                System.out.println("Podaj ID produktu:");
                                productId = Integer.parseInt(scanner.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Błąd: Wprowadzono niepoprawny format ID. Wprowadź liczbę całkowitą.");
                            }
                        }

                        BigDecimal productPrice;
                        while (true) {
                            try {
                                System.out.println("Podaj cenę produktu:");
                                productPrice = new BigDecimal(scanner.nextLine());
                                break; //
                            } catch (NumberFormatException e) {
                                System.out.println("Błąd: Wprowadzono niepoprawny format ceny. Wprowadź poprawną wartość liczbową.");
                            }
                        }

                        int productQuantity;
                        while (true) {
                            try {
                                System.out.println("Podaj ilość dostępnych sztuk:");
                                productQuantity = Integer.parseInt(scanner.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Błąd: Wprowadzono niepoprawny format ilości. Wprowadź liczbę całkowitą.");
                            }
                        }

                        System.out.println("Wybierz typ produktu:");
                        System.out.println("1. Computer");
                        System.out.println("2. Smartphone");
                        System.out.println("3. Inny Produkt - Elektronika");

                        String typeChoice = scanner.nextLine();
                        Product newProduct;

                        switch (typeChoice) {
                            case "1":
                                newProduct = new Computer(productId, productName, productPrice, productQuantity);
                                break;
                            case "2":
                                newProduct = new Smartphone(productId, productName, productPrice, productQuantity);
                                break;
                            case "3":
                                newProduct = new Electronics(productId, productName, productPrice, productQuantity);
                                break;
                            default:
                                System.out.println("Nieprawidłowy wybór typu produktu. Dodanie produktu przerwane.");
                                return;
                        }
                        productManager.addProduct(newProduct);
                        System.out.println("Produkt został pomyślnie dodany.");

                    } catch (NumberFormatException e) {
                        System.out.println("Błąd: Wprowadzono niepoprawny format liczby.");
                    } catch (Exception e) {
                        System.out.println("Wystąpił błąd podczas dodawania produktu: " + e.getMessage());
                    }
                    break;

                case "2":

                    try {
                        int productIdToRemove;
                        while (true) {
                            try {
                                System.out.println("Podaj ID produktu do usunięcia:");
                                productIdToRemove = Integer.parseInt(scanner.nextLine());
                                productManager.removeProduct(productIdToRemove);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Błąd: Wprowadzono niepoprawny format ID. Wprowadź liczbę całkowitą.");
                            }
                            break;
                        }
                    }catch (Exception e) {
                        System.out.println("Wystąpił błąd podczas aktualizacji produktu: " + e.getMessage());
                    }

                case "3":

                    try {

                        int productIdToUpdate;
                        while (true) {
                            try {
                                System.out.println("Podaj ID produktu, który chesz zaktualizować:");
                                productIdToUpdate = Integer.parseInt(scanner.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Błąd: Wprowadzono niepoprawny format ID. Wprowadź liczbę całkowitą.");
                            }
                        }

                        System.out.println("Podaj zaktualizowaną nazwę:");
                        String productNameToUpdate = scanner.nextLine();


                        BigDecimal productPriceToUpdate;
                        while (true) {
                            try {
                                System.out.println("Podaj zaktualizowaną cenę:");
                                productPriceToUpdate = new BigDecimal(scanner.nextLine());
                                break; //
                            } catch (NumberFormatException e) {
                                System.out.println("Błąd: Wprowadzono niepoprawny format ceny. Wprowadź poprawną wartość liczbową.");
                            }
                        }

                        int productQuantityToUpdate;
                        while (true) {
                            try {
                                System.out.println("Podaj zaktualizowaną ilość sztuk:");
                                productQuantityToUpdate = Integer.parseInt(scanner.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Błąd: Wprowadzono niepoprawny format ilości. Wprowadź liczbę całkowitą.");
                            }
                        }

                        Product updatedProduct = new Product(productIdToUpdate, productNameToUpdate, productPriceToUpdate, productQuantityToUpdate);
                        productManager.updateProduct(productIdToUpdate, updatedProduct);
                        System.out.println("Produkt został pomyślnie zaktualizowany.");
                    } catch (Exception e) {
                        System.out.println("Wystąpił błąd podczas aktualizacji produktu: " + e.getMessage());
                    }
            }
        }
    }
}
