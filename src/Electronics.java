////**Dla pozostałych produktów, które będą sprzedawane w sklepie typem produktu będzie po prostu:
//// Electronics i dla produktów o tym typie nie ma żadnej dodatkowej obsługi!!!**

public class Electronics extends Product{

    public Electronics(int id, String name, double price, int availableQuantity) {
        super(id, name, price, availableQuantity);
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
    }
}
