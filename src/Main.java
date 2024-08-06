import enums.*;

import enums.*;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        ProductManager productManager = new ProductManager();
        Cart cart = new Cart();
        Customer customer1 = new Customer("Jarek Talarek", "talarek@op.pl", "ul. Przykładowa 10, 00-001 Warszawa");

        Computer computer = new Computer(1, "Laptop", 3000.00, 10);
        computer.configureComputer(Processor.INTEL_I7, RAM.RAM_16GB, 512);
        computer.displayDetails();

        Smartphone smartphone = new Smartphone(2, "Smartphone", 1200.00, 20);
        smartphone.configureSmartphone(Color.BLACK, BatteryCapacity.BATTERY_4000MAH, Accessories.CASE);
        smartphone.displayDetails();

        Electronics electronics = new Electronics(3, "Odkurzacz", 300, 10);
        electronics.displayDetails();
        Computer computer1 = new Computer(1, "Laptop", 3000.00, 10);
        computer1.configureComputer(Processor.INTEL_I7, RAM.RAM_16GB, 512);


        Smartphone smartphone1 = new Smartphone(2, "Smartphone", 1200.00, 20);
        smartphone1.configureSmartphone(Color.BLACK, BatteryCapacity.BATTERY_4000MAH, Accessories.CASE);


        Electronics odkurzacz = new Electronics(3, "Odkurzacz", 300, 10);

        System.out.println("--------------------------------------------");

        productManager.addProduct(computer);
        productManager.addProduct(smartphone);
        productManager.addProduct(odkurzacz);
        System.out.println("--------------------------------------------");

        productManager.viewProducts();
        System.out.println("--------------------------------------------");

        productManager.removeProduct(1);
        System.out.println("--------------------------------------------");

        Smartphone updatedSmarthpone = new Smartphone(2, "Iphone", 3000, 8);
        updatedSmarthpone.configureSmartphone(Color.BLACK, BatteryCapacity.BATTERY_4000MAH, Accessories.CHARGER);
        productManager.updateProduct(2, updatedSmarthpone);

        System.out.println("--------------------------------------------");

        productManager.viewProducts();

        System.out.println("--------------------------------------------");

        Optional<Product> productById = productManager.findProductById(3);
        System.out.println(productById.get());

        System.out.println("--------------------------------------------");

        cart.addProductToCart(computer1);
        cart.addProductToCart(smartphone1);

        System.out.println("--------------------------------------------");

        cart.viewProductsInCart();

        System.out.println("--------------------------------------------");

        cart.placeOrder(customer1);

        System.out.println("--------------------------------------------");

        cart.viewOrders();

        System.out.println("--------------------------------------------");


    }
}