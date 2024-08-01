import enums.*;

public class Main {
    public static void main(String[] args) {

        Computer computer = new Computer(1, "Laptop", 3000.00, 10);
        computer.configureComputer(Processor.INTEL_I7, RAM.RAM_16GB, 512);
        computer.displayDetails();

        Smartphone smartphone = new Smartphone(2, "Smartphone", 1200.00, 20);
        smartphone.configureSmartphone(Color.BLACK, BatteryCapacity.BATTERY_4000MAH, Accessories.CASE);
        smartphone.displayDetails();

        Electronics electronics = new Electronics(3, "Odkurzacz", 300, 10);
        electronics.displayDetails();
    }

}