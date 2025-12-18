package dao;

import db.DB;
import entities.Product;
import entities.Purchase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDaoJDBC implements PurchaseDao {
    private Connection conn;

    public PurchaseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Purchase obj) {
        PreparedStatement st = null;
        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    "INSERT INTO purchases (total_price, total_with_icms) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setDouble(1, obj.total());
            st.setDouble(2, obj.calculateIcms());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int purchaseId = rs.getInt(1);
                    insertItems(purchaseId, obj.getListProducts());
                }
                conn.commit();
            } else {
                conn.rollback();
                throw new RuntimeException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.closeStatement(st);
        }
    }

    private void insertItems(int purchaseId, List<Product> products) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO purchase_items (purchase_id, product_id, quantity, unit_price) " +
                            "VALUES (?, ?, ?, ?)");

            for (Product p : products) {
                st.setInt(1, purchaseId);
                st.setInt(2, p.getId());
                st.setInt(3, p.getQuantity());
                st.setDouble(4, p.getPrice());
                st.addBatch();
            }
            st.executeUpdate();
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM purchases WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Purchase findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT p.*, pi.product_id, pi.quantity as item_quantity, pi.unit_price, prod.name as product_name " +
                            "FROM purchases p " +
                            "JOIN purchase_items pi ON p.id = pi.purchase_id " +
                            "JOIN products prod ON pi.product_id = prod.id " +
                            "WHERE p.id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            Purchase purchase = null;
            while (rs.next()) {
                if (purchase == null) {
                    purchase = new Purchase(rs.getInt("id"));
                }
                Product prod = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("item_quantity"),
                        rs.getDouble("unit_price")
                );
                purchase.addProduct(prod);
            }
            return purchase;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Purchase> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM purchases ORDER BY purchase_date DESC");
            rs = st.executeQuery();

            List<Purchase> list = new ArrayList<>();
            while (rs.next()) {
                Purchase p = new Purchase(rs.getInt("id"));
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
