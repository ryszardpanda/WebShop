package model;//**Typ Produktu: Komputer**
//
//**Specyficzna Obsługa**: Implementacja funkcji umożliwiającej konfigurację specyfikacji laptopa/komputera przed dodaniem
// do koszyka, takiej jak wybór procesora, ilości RAM, itp.

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
        boolean hasPrevious = false;
        if (processor != null) {
            System.out.print("Procesor: " + processor);
            hasPrevious = true;
        }

        if (ram != null) {
            if (hasPrevious) {
                System.out.print(", ");
            }
            System.out.print("RAM: " + ram + "GB");
            hasPrevious = true;
        }

        if (storage != 0) {
            if (hasPrevious) {
                System.out.print(", ");
            }
            System.out.print("Dysk: " + storage + "GB");
            hasPrevious = true;
        }

        if (hasPrevious) {
            System.out.println();
        }
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
}
