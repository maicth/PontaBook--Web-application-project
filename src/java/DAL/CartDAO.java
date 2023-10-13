/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Item;
import Models.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class CartDAO {
    private Connection con;
    public String stat;
    
    
    public CartDAO() {
        try {
            con = new DBContext().getConnection();

        } catch (Exception e) {
            stat = "Error " + e.getMessage();
        }
    }
    
    public ArrayList<Cart> loadCart(int uid){
        ArrayList<Cart> carts = new ArrayList<>();
        String sql = "SELECT * FROM Cart_HE150645 WHERE customerId = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int userId = rs.getInt(1);
                int bookId = rs.getInt(2);
                int quantity = rs.getInt(3);
                
                carts.add(new Cart(userId, bookId, quantity));
                
            }
            
        } catch(SQLException e){
            stat = "Error at load Cart " + e.getMessage();
        }
        
        return carts;
    }
    
    public ArrayList<Item> getItems(int userId){
        ArrayList<Item> items = new ArrayList<>();
        BookDAO bdao = new BookDAO();
        bdao.loadBook();
        for (Cart c : loadCart(userId)) {              
            Item item = new Item();
            item.setB(bdao.search(c.getBookId()));
            item.setQuantity(c.getQuantity());
            item.setDiscount(item.getB().getDiscount());
            items.add(item);
        }
        
        return items;
    }

    public void insert(Cart c){
        
        String sql = "INSERT INTO Cart_HE150645 VALUES(?, ?, ? ) ";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, c.getUserId());
            ps.setInt(2, c.getBookId());
            ps.setInt(3, c.getQuantity());
            
            ps.execute();
        }catch(SQLException e){
            stat = "Error at insert cart " + e.getMessage();
        }
        
    }
    
    public void update(Cart c){
        String sql = "UPDATE Cart_HE150645 SET quantity = ? WHERE customerId = ? AND bookId = ? ";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, c.getQuantity());
            ps.setInt(2, c.getUserId());
            ps.setInt(3, c.getBookId());

            ps.execute();
        } catch (SQLException e) {
            stat = "Error at update cart " + e.getMessage();
        }
    }
    
    public void delete(int userId, int bookId){
        String sql = "DELETE Cart_HE150645 WHERE customerId = ? AND bookId = ? ";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);

            ps.execute();
        } catch (SQLException e) {
            stat = "Error at update cart " + e.getMessage();
        }
    }

}
