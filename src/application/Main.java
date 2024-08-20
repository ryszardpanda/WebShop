package application;

import model.*;
import service.Cart;
import service.OrdersProcessor;
import service.ProductManager;
import ui.CommandLineInterface;

import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        ProductManager productManager = new ProductManager();
        Cart cart = new Cart();
     //   Customer customer1 = new Customer("Jarek Talarek", "talarek@op.pl", "ul. Przykładowa 10, 00-001 Warszawa");
        OrdersProcessor ordersProcessor = new OrdersProcessor();


        Computer computer = new Computer(1, "Laptop", new BigDecimal(3000), 10);
        computer.configureComputer(Processor.INTEL_I7, RAM.RAM_16GB, 512);

        Smartphone smartphone = new Smartphone(2, "Nokia 3310", new BigDecimal(200), 20);
        smartphone.configureSmartphone(Color.BLACK, BatteryCapacity.BATTERY_4000MAH, Accessories.CASE);

        Electronics odkurzacz1 = new Electronics(3, "Odkurzacz", new BigDecimal(3000), 10);
      //  electronics.displayDetails();
        Computer computer1 = new Computer(1, "Laptop", new BigDecimal(3000), 10);
        computer1.configureComputer(Processor.INTEL_I7, RAM.RAM_16GB, 512);

        Smartphone smartphone1 = new Smartphone(5, "IPHONE 15", new BigDecimal(5000), 20);
        smartphone1.configureSmartphone(Color.BLACK, BatteryCapacity.BATTERY_4000MAH, Accessories.CASE);

        Electronics odkurzacz = new Electronics(4, "Odkurzacz", new BigDecimal(3000), 10);

        Smartphone smartphone2 = new Smartphone(6, "IPHONE 15", new BigDecimal(5000), 20);

//        System.out.println("--------------------------------------------");
//
        productManager.addProduct(computer);
        productManager.addProduct(smartphone);
        productManager.addProduct(odkurzacz1);
        productManager.addProduct(odkurzacz);
        productManager.addProduct(smartphone1);
        productManager.addProduct(smartphone2);


        System.out.println("Wybierz akcesoria (wprowadź numery rozdzielone przecinkami, lub zostaw puste, aby pominąć):");
        for (Accessories accessory : Accessories.values()) {
            System.out.println(accessory.ordinal() + 1 + ". " + accessory);
        }
//        System.out.println("--------------------------------------------");
//
//        productManager.viewProducts();
//        System.out.println("--------------------------------------------");

//        productManager.removeProduct(1);
//        System.out.println("--------------------------------------------");
//
//        Smartphone updatedSmarthpone = new Smartphone(2, "Iphone", 3000, 8);
//        updatedSmarthpone.configureSmartphone(Color.BLACK, BatteryCapacity.BATTERY_4000MAH, Accessories.CHARGER);
//        productManager.updateProduct(2, updatedSmarthpone);
//
//        System.out.println("--------------------------------------------");
//
//        productManager.viewProducts();
//
//        System.out.println("--------------------------------------------");
//
//        Optional<Product> productById = productManager.findProductById(3);
//        System.out.println(productById.get());
//
//        System.out.println("--------------------------------------------");
//
//        cart.addProductToCart(computer1);
//        cart.addProductToCart(smartphone1);
//
//        System.out.println("--------------------------------------------");
//
//        cart.viewProductsInCart();
//
//        System.out.println("--------------------------------------------");

//        cart.placeOrder(customer1);
//
//        System.out.println("--------------------------------------------");
//
//        cart.viewOrders();

//        System.out.println("--------------------------------------------");
//
//        cart.placeOrder(customer1);

        CommandLineInterface ui = new CommandLineInterface(productManager, cart, ordersProcessor);
        ui.start();



    }
}