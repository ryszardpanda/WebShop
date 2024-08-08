package model;////**Wymagania Biznesowe**
////**Task 1**: Stworzenie klasy model.Product reprezentującej produkt z polami takimi jak id, nazwa, cena, ilość dostępna. - DONE
//W sklepie internetowym sprzedawane będą produkty będące elektroniką:
//
//**Typ Produktu: Komputer**
//
//**Specyficzna Obsługa**: Implementacja funkcji umożliwiającej konfigurację specyfikacji laptopa/komputera przed dodaniem
// do koszyka, takiej jak wybór procesora, ilości RAM, itp. - DONE
//
//**Typ Produktu: Smartfon**
//
//Specyficzna Obsługa: Dodanie funkcji umożliwiającej wybór koloru, pojemności baterii,
// oraz dodatkowych akcesoriów przy zakupie smartfona. - DONE
//
//**Dla pozostałych produktów, które będą sprzedawane w sklepie typem produktu będzie po prostu:
// model.Electronics i dla produktów o tym typie nie ma żadnej dodatkowej obsługi!!!** - DONE
//
//**CEL:**
//Dostosowanie obsługi różnych typów produktów pozwala na lepsze spełnienie oczekiwań klientów.
// Funkcje specyficzne dla każdego typu produktu mogą zwiększyć atrakcyjność sklepu i zapewnić klientom bardziej
// spersonalizowane doświadczenie zakupowe.

//DONE

public class Product {
    private int id;
    private String name;
    private double price;
    private int availableQuantity;

    public Product(int id, String name, double price, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void displayDetails() {
        System.out.println("Produkt: " + name + ", Cena: " + price + " ID " + id);
    }

    @Override
    public String toString() {
        return "Produkt" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
