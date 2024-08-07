package model;//**Typ Produktu: Smartfon**
//
//Specyficzna Obsługa: Dodanie funkcji umożliwiającej wybór koloru, pojemności baterii,
// oraz dodatkowych akcesoriów przy zakupie smartfona.
//

public class Smartphone extends Product {
    private Color color;
    private BatteryCapacity batteryCapacity;
    private Accessories accessories;

    public Smartphone(int id, String name, double price, int availableQuantity) {
        super(id, name, price, availableQuantity);
    }

    public void configureSmartphone( Color color, BatteryCapacity batteryCapacity, Accessories accessories){
        this.color = color;
        this.batteryCapacity = batteryCapacity;
        this.accessories = accessories;
        System.out.println("Skonfigurowano smartfon: Kolor: " + color + ", Bateria: " + batteryCapacity + ", Akcesoria: " + accessories);
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Kolor: " + color + ", Bateria: " + batteryCapacity + ", Akcesoria: " + accessories);
    }

    @Override
    public String toString() {
        return "model.Smartphone{" +
                "color=" + color +
                ", batteryCapacity=" + batteryCapacity +
                ", accessories=" + accessories +
                '}';
    }
}
