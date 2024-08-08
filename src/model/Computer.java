package model;//**Typ Produktu: Komputer**
//
//**Specyficzna Obsługa**: Implementacja funkcji umożliwiającej konfigurację specyfikacji laptopa/komputera przed dodaniem
// do koszyka, takiej jak wybór procesora, ilości RAM, itp.

public class Computer extends Product {
    private Processor processor;
    private RAM ram;
    private int storage;

    public Computer(int id, String name, double price, int availableQuantity) {
        super(id, name, price, availableQuantity);
    }

    public void configureComputer( Processor processor, RAM ram, int storage) {
        this.processor = processor;
        this.ram = ram;
        this.storage = storage;
        System.out.println("Skonfigurowano komputer: Procesor: " + processor + ", RAM: " + ram + "GB, Dysk: " + storage + "GB");
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Procesor: " + processor + ", RAM: " + ram + "GB, Dysk: " + storage + "GB");
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
