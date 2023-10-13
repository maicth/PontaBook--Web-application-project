/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Book;
import Models.Item;
import Models.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class OrderDAO {
    private Connection con;
    public String stat;

    public OrderDAO() {
        try {
            con = new DBContext().getConnection();

        } catch (Exception e) {
            stat = "Error " + e.getMessage();
        }
    }
    
    public ArrayList<Order> orders;

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
    
    public void getAllOrders(){
        orders = new ArrayList<>();
        AccountDAO adao = new AccountDAO();
        String sql = "SELECT * FROM Order_HE150645";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Order od = new Order();
                
                adao.loadAddress();
                
                
                od.setId(rs.getInt(1));
                od.setUserId(rs.getInt(2));
                od.setOrderDate(rs.getDate(3));
                od.setShippedDate(rs.getDate(4));
                od.setStatus(rs.getInt(5));
//                od.setAddressId(rs.getInt(6));
                
                od.setAdd(adao.getAddressById(rs.getInt(6)));
                od.setOdetails(getOrderDetailsByOrder(rs.getInt(1)));
                
                orders.add(od);
            }
            
            
        }catch(SQLException e){
            stat = "error at load orders " + e.getMessage();
        }
    }
    
    public ArrayList<Order> getAllOrders(int userId){
        ArrayList<Order> olist = new ArrayList<>();
        AccountDAO adao = new AccountDAO();
        
        String sql = "SELECT * FROM Order_HE150645 WHERE customerId = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Order od = new Order();
                
                
                adao.loadAddress();
                od.setId(rs.getInt(1));
                od.setUserId(rs.getInt(2));
                od.setOrderDate(rs.getDate(3));
                od.setShippedDate(rs.getDate(4));
                od.setStatus(rs.getInt(5));
//                od.setAddressId(rs.getInt(6));
                
                od.setAdd(adao.getAddressById(rs.getInt(6)));
                od.setOdetails(getOrderDetailsByOrder(rs.getInt(1)));
                
                olist.add(od);
            }
            
            
        }catch(SQLException e){
            stat = "error at load orders " + e.getMessage();
        }
        
        return olist;
    }
    
    
    public ArrayList<Order> getOrderByUser(int userId){
        
        ArrayList<Order> olist = new ArrayList<>();
        for (Order order : orders) {
            if(order.getUserId() == userId){
                olist.add(order);
            }
        }
        
        return olist;
    }
    
    public Order getOrder(int orderId){
        getAllOrders();
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }

        return null;
    }
    
    public ArrayList<Item> getOrderDetailsByOrder(int orderId){
        
        ArrayList<Item> list = new ArrayList<>();
        String sql = "SELECT * FROM Orderdetail_HE150645 WHERE orderId = ? ";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                BookDAO bdao = new BookDAO();
                bdao.loadBook();
                Item i = new Item();
                i.setB(bdao.search(rs.getInt(2)));
                i.setQuantity(rs.getInt(3));
                i.setDiscount(rs.getFloat(4));
                
                list.add(i);
            }
            
        }catch(SQLException e){
            stat = "error at load order details " + e.getMessage();
        }
        
        return list;
    }
    
    public void insert(int userId, ArrayList<Item> items, int addressId){
        String sql = "INSERT INTO Order_HE150645 VALUES ( ? , ? , ? , ? , ? )";
        BookDAO bdao = new BookDAO();
        bdao.loadBook();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new Date(date.getTime());
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setDate(2, sqlDate);
            ps.setNull(3, java.sql.Types.DATE);
            ps.setInt(4, 1);
            ps.setInt(5, addressId);
            
            ps.execute();
            
            String sql_get_id = "SELECT @@Identity as orderId";
            PreparedStatement ps_id = con.prepareStatement(sql_get_id);
            ResultSet rs = ps_id.executeQuery();
            int orderId = 0;
            if(rs.next()){
                orderId = rs.getInt("orderId");
            }
            
            if(orderId != 0){
                for (Item item : items) {
                    String sql_insert_item = "INSERT INTO Orderdetail_HE150645 VALUES ( ? , ? , ? , ? )";
                    PreparedStatement ps_insert_item = con.prepareStatement(sql_insert_item);
                    ps_insert_item.setInt(1, orderId);
                    ps_insert_item.setInt(2, item.getB().getId());
                    ps_insert_item.setInt(3, item.getQuantity());
                    ps_insert_item.setFloat(4, item.getDiscount());
                    
                    ps_insert_item.execute();
                    
                    Book b = bdao.search(item.getB().getId());                  
                    
                    String sql_decrease = "UPDATE Book_HE150645 SET quantity = ? WHERE id = ? ";
                    PreparedStatement ps_decrease = con.prepareStatement(sql_decrease);
                    ps_decrease.setInt(1, b.getQuantity() - item.getQuantity());
                    ps_decrease.setInt(2, b.getId());
                    
                    ps_decrease.execute();
                }
                
                
                
                String sql_delete_cart = "DELETE Cart_HE150645 WHERE customerId = ? ";
                PreparedStatement ps_delete_cart = con.prepareStatement(sql_delete_cart);
                ps_delete_cart.setInt(1, userId);
                ps_delete_cart.execute();
                
            }
            else {
                con.rollback();
            }
            
            con.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void update(int oid, int ostatus, Order o){
        String sql = "UPDATE Order_HE150645 SET [status] = ? WHERE id = ? ";
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ostatus);
            ps.setInt(2, oid);
            ps.execute();
            
            if(ostatus == 5){              
                for (Item item : o.getOdetails()) {
                    String sql_increase = "UPDATE Book_HE150645 SET quantity = ? WHERE id = ? ";
                    PreparedStatement ps_increase = con.prepareStatement(sql_increase);
                    ps_increase.setInt(1, item.getB().getQuantity() + item.getQuantity());
                    ps_increase.setInt(2, item.getB().getId());

                    ps_increase.execute();
                }
            }
            
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        finally{
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
