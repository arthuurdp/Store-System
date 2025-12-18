package entities;

public class Product {
    private Integer id;
    private String name;
    private Integer quantity;
    private Double price;

    public Product(Integer id, String name, Integer quantity, Double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.id);
        sb.append(", Name: ").append(this.name);
        sb.append(", Quantity: ").append(this.quantity);
        sb.append(", Price: R$ ").append(String.format("%.2f", this.price));
        return sb.toString();
    }
}
