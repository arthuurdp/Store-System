package entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Purchase {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Integer id;
    private Date date;
    private final List<Product> listProducts = new ArrayList<>();

    public Purchase(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Product> getListProducts() {
        return listProducts;
    }

    public void addProduct(Product p) {
        listProducts.add(p);
    }

    public void removeProduct(Product p, int quantity) {
        Product prod = getProductById(p.getId());
        if (prod != null) {
            int newQuantity = p.getQuantity() - quantity;
            if (newQuantity <= 0) {
                listProducts.remove(p);
            } else {
                p.setQuantity(newQuantity);
            }
        }
    }

    public Product getProductById(Integer id) {
        for (Product p : listProducts) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public double total() {
        double sum = 0.0;
        for (Product p : listProducts) {
            sum += p.getPrice() * p.getQuantity();
        }
        return sum;
    }

    public double calculateIcms() {
        return total() * 1.17;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n===================================================\n");
        sb.append(String.format("                PURCHASE DETAILS #%d\n", id));
        if (date != null) {
            sb.append(String.format("Date: %s\n", sdf.format(date)));
        }
        sb.append("===================================================\n");

        if (listProducts.isEmpty()) {
            sb.append("   (No products in this purchase)\n");
        } else {
            sb.append(String.format("%-25s | %-10s | %-5s\n", "Product Name", "Qty", "Price"));
            sb.append("---------------------------------------------------\n");
            for (Product p : listProducts) {
                sb.append(String.format("%-25s | %-10d | R$ %.2f\n",
                                       p.getName(), p.getQuantity(), p.getPrice()));
            }
        }
        sb.append("---------------------------------------------------\n");
        sb.append(String.format("Subtotal          : R$ %10.2f\n", total()));
        sb.append(String.format("Total (inc. ICMS) : R$ %10.2f\n", calculateIcms()));
        sb.append("===================================================\n");

        return sb.toString();
    }
}
