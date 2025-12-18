package dao;

import db.DB;

public class DaoFactory {
    public static ProductDao createProductDao() {
        return new ProductDaoJDBC(DB.getConnection());
    }

    public static PurchaseDao createPurchaseDao() {
        return new PurchaseDaoJDBC(DB.getConnection());
    }
}