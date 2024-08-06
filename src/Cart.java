import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    List<Product> cart;
    List<Order> orders;
    private OrdersProcessor orderProcessor;

    public Cart() {
        this.cart = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.orderProcessor = new OrdersProcessor();
    }

    //dodawanie produktu do koszyka
    public void addProductToCart(Product product){
        cart.add(product);
        System.out.println("Dodano produkt do koszyka " + product.getName());
    }

    //usuwanie produktu z koszyka
    public boolean removeProductFromCart(int productId) {
        Optional<Product> productToRomove = cart.stream()
                .filter(product -> product.getId() == productId)
                .findFirst();
        if (productToRomove.isPresent()){
            cart.remove(productToRomove.get());
            System.out.println("Usunięto produkt z koszyka: " + productToRomove.get());
            return true;

        }else {
            System.out.println("Produkt o ID: " + productId + " nie został znaleziony w koszyku");
            return false;
        }
    }

    //przeglądanie  wszystkich produktów
    public void viewProductsInCart() {
        if (cart.isEmpty()){
            System.out.println("Brak produktów w koszyku.");
        }else {
            System.out.println("Lista produktów w koszyku:");
            cart.forEach(Product::displayDetails);
        }
    }

    //składanie zamówienia
    public void placeOrder(Customer customer){
        if (cart.isEmpty()){
            System.out.println("Koszyk jest pusty, brak możliwości złożenia zamówienia");
        }else {
            Order order = new Order(customer, new ArrayList<>(cart));
            orders.add(order);
            order.displayOrderDetails();
            cart.clear();
            System.out.println("Zamówienie zostało złożonę i zapisane");
            orderProcessor.processOrder(order);
        }
    }

    public void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("Brak złożonych zamówień.");
        } else {
            System.out.println("Lista złożonych zamówień:");
            orders.forEach(Order::displayOrderDetails);
        }
    }
}
