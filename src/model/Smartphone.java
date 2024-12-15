package model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Smartphone extends Product {
    private Color color;
    private BatteryCapacity batteryCapacity;
    private Set<Accessories> accessories;

    public Smartphone(int id, String name, BigDecimal price, int availableQuantity) {
        super(id, name, price, availableQuantity);
        this.accessories = new HashSet<>();
    }

    public void addAccessory(Accessories accessory) {
        if (accessories.contains(accessory)) {
            System.out.println("Akcesorium " + accessory + " już jest dodane do smartfona.");
        } else {
            accessories.add(accessory);
            System.out.println("Akcesorium " + accessory + " zostało dodane do smartfona.");
        }
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        boolean hasPrevious = false;

        if (color != null) {
            System.out.print("Kolor: " + color);
            hasPrevious = true;
        }

        if (batteryCapacity != null) {
            if (hasPrevious) {
                System.out.print(", ");
            }
            System.out.print("Bateria: " + batteryCapacity);
            hasPrevious = true;
        }

        if (accessories != null && !accessories.isEmpty()) {
            if (hasPrevious) {
                System.out.print(", ");
            }
            System.out.print("Akcesoria: " + accessories);
            hasPrevious = true;
        }

        if (hasPrevious) {
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Smartphone{" +
                super.toString() +
                ", color=" + color +
                ", batteryCapacity=" + batteryCapacity +
                ", accessories=" + accessories +
                '}';
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BatteryCapacity getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(BatteryCapacity batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public Set<Accessories> getAccessories() {
        return accessories;
    }

    public void setAccessories(Set<Accessories> accessories) {
        this.accessories = accessories;
    }
}
