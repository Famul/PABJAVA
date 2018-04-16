/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.imsi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import pl.imsi.beans.Product;

/**
 *
 * @author 187684
 */
public class ProductDao {
    private List<Product> products;
    
    public ProductDao() {
        products = new ArrayList<>();
        init();
    }

    private void init() {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/SQLServer");
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select productid, productname from " +
                    "northwind.dbo.products");            
            while (rs.next()) {
                products.add(new Product(Long.parseLong(rs.getString("productid")), 
                        rs.getString("productname")));
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> findAll() {
        return this.products;
    }
}
