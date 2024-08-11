package model;////**Dla pozostałych produktów, które będą sprzedawane w sklepie typem produktu będzie po prostu:
//// model.Electronics i dla produktów o tym typie nie ma żadnej dodatkowej obsługi!!!**

import java.math.BigDecimal;

public class Electronics extends Product {

    public Electronics(int id, String name, BigDecimal price, int availableQuantity) {
        super(id, name, price, availableQuantity);
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
    }
}
