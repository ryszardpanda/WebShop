package model;

import java.math.BigDecimal;

public class Computer extends Product {
    private Processor processor;
    private RAM ram;
    private int storage;

    public Computer(int id, String name, BigDecimal price, int availableQuantity) {
        super(id, name, price, availableQuantity);
    }

    public void configureComputer( Processor processor, RAM ram, int storage) {
        this.processor = processor;
        this.ram = ram;
        this.storage = storage;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        checkConfiguration();
    }

    @Override
    public String toString() {
        return "Computer{" +
                "name='" + getName() + '\'' +
                ", processor=" + processor +
                ", ram=" + ram +
                ", storage=" + storage + "GB" +
                '}';
    }

    public void checkConfiguration() {
        boolean hasPrevious = false;

        hasPrevious = printWithComma(hasPrevious, processor != null, "Procesor: " + processor);
        hasPrevious = printWithComma(hasPrevious, ram != null, "RAM: " + ram + "GB");
        hasPrevious = printWithComma(hasPrevious, storage != 0, "Dysk: " + storage + "GB");

        if (hasPrevious) {
            System.out.println();
        }
    }

    private boolean printWithComma(boolean hasPrevious, boolean condition, String text) {
        if (condition) {
            if (hasPrevious) {
                System.out.print(", ");
            }
            System.out.print(text);
            return true;
        }
        return hasPrevious;
    }
}
