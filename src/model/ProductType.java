package model;

public enum ProductType {
    COMPUTER("Computer"),
    SMARTPHONE("Smartphone"),
    ELECTRONICS("Electronics");

    private final String typeName;

    ProductType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static ProductType fromString(String typeName) {
        for (ProductType type : ProductType.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        return ELECTRONICS;
    }
}
