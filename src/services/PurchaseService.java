package services;

import dao.DaoFactory;
import dao.ProductDao;
import dao.PurchaseDao;
import entities.Product;
import entities.Purchase;

import java.util.Date;
import java.util.List;

public class PurchaseService {

    private final PurchaseDao purchaseDao = DaoFactory.createPurchaseDao();
    private final ProductDao productDao = DaoFactory.createProductDao();

    public List<Purchase> findAll() {
        return purchaseDao.findAll();
    }

    public Purchase findById(Integer id) {
        return purchaseDao.findById(id);
    }

    public void insert(Purchase p) {
        if (p.getDate() == null) {
            p.setDate(new Date());
        }
        purchaseDao.insert(p);
    }

    public void update(Purchase p) {
        purchaseDao.update(p);
    }

    public boolean deleteById(Integer id) {
        Purchase purchase = purchaseDao.findById(id);
        if (purchase != null) {
            for (Product p : purchase.getListProducts()) {
                returnProductToStock(p, p.getQuantity());
            }
            purchaseDao.deleteById(id);
            return true;
        }
        return false;
    }

    public void addProductToPurchase(Purchase p, Product stockProduct, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (quantity > stockProduct.getQuantity()) {
            throw new IllegalArgumentException("Invalid quantity! Only " + stockProduct.getQuantity() + " units available.");
        }

        Product existingProduct = p.getProductById(stockProduct.getId());

        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
        } else {
            Product purchasedProduct = new Product(
                    stockProduct.getId(),
                    stockProduct.getName(),
                    quantity,
                    stockProduct.getPrice()
            );
            p.addProduct(purchasedProduct);
        }

        stockProduct.setQuantity(stockProduct.getQuantity() - quantity);
        productDao.update(stockProduct);

        if (p.getId() != null) {
            purchaseDao.update(p);
        }
    }

    public void removeProductFromPurchase(Purchase p, Product productInPurchase, int quantity) {
        if (quantity <= 0 || (productInPurchase != null && quantity > productInPurchase.getQuantity())) {
            throw new IllegalArgumentException("Invalid quantity.");
        }

        p.removeProduct(productInPurchase, quantity);
        returnProductToStock(productInPurchase, quantity);

        if (p.getId() != null) {
            purchaseDao.update(p);
        }
    }

    private void returnProductToStock(Product p, int quantity) {
        Product stockProduct = productDao.findById(p.getId());
        if (stockProduct != null) {
            stockProduct.setQuantity(stockProduct.getQuantity() + quantity);
            productDao.update(stockProduct);
        } else {
            Product newProduct = new Product(p.getId(), p.getName(), quantity, p.getPrice());
            productDao.insert(newProduct);
        }
    }
}
