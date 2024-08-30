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
        CommandLineInterface ui = new CommandLineInterface(productManager, cart);
        ui.start();
    }
}