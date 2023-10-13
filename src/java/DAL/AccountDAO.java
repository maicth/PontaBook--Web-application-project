/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Address;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author DELL
 */
public class AccountDAO {
    private Connection con;
    public ArrayList<User> accounts;
    public ArrayList<Address> adds;
    public String stat = "";

    public AccountDAO() {
        try {
            con = new DBContext().getConnection();

        } catch (Exception e) {
            stat = "Error " + e.getMessage();
        }
    }

    public ArrayList<User> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<User> accounts) {
        this.accounts = accounts;
    }


    public void loadAccount() {

        accounts = new ArrayList<User>();
        String sql = "SELECT * FROM User_HE150645";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String username = rs.getString(2);
                String pass = rs.getString(3);
                String name = rs.getString(4);
                String email = rs.getString(5);
                boolean gender = rs.getBoolean(6);
                Date dob = rs.getDate(7);
                boolean stat = rs.getBoolean(8);
                byte role = rs.getByte(9);

                accounts.add(new User(id, username, pass, name, email, gender, dob, stat, role));
            }
        } catch (Exception e) {
            stat = "Load Account " + e.getMessage();
        }
    }
    
    public ArrayList<Address> loadAddress(int userId){
        ArrayList<Address> adds = new ArrayList<>();
        String sql = "SELECT * FROM Address_HE150645 WHERE userId = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Address ad = new Address();
                ad.setId(rs.getInt(1));
                ad.setAdd(rs.getString(3));
                ad.setName(rs.getString(4));
                ad.setPhone(rs.getString(5));
                
                adds.add(ad);
            }          
            
        }catch(SQLException e){
            stat = "Error at load address " + e.getMessage();
        }
        return adds;
    }
    
    public void loadAddress(){
        adds = new ArrayList<>();
        String sql = "SELECT * FROM Address_HE150645";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Address ad = new Address();
                ad.setId(rs.getInt(1));
                ad.setUserId(rs.getInt(2));
                ad.setAdd(rs.getString(3));
                ad.setName(rs.getString(4));
                ad.setPhone(rs.getString(5));
                
                adds.add(ad);
            }          
            
        }catch(SQLException e){
            stat = "Error at load address " + e.getMessage();
        }
    }
    
    public Address getAddressById(int id){
        for (Address add : adds) {
            if(add.getId() == id){
                return add;
            }
        }
        return null;
    }
    
    public void insert(User u){
        String sql = "INSERT INTO User_HE150645 (username, password, email, role) VALUES (?, ?, ?, ?)";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getEmail());
            ps.setInt(4, 1);
            ps.executeUpdate();
            
            
        } catch (SQLException ex) {
            stat = "Error at insert User " + ex.getMessage();
        }
    }
    
    public void update(User u){
        String sql = "UPDATE User_HE150645 SET name = ? , email = ? , dob  = ? , gender =  ? , password = ? WHERE id = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            if(u.getDob() == null){
                ps.setNull(3, java.sql.Types.DATE);
            } else {
                ps.setDate(3, u.getDob());
            }
            ps.setBoolean(4, u.isGender());
            ps.setString(5, u.getPassword());
            
            ps.setInt(6, u.getId());
            ps.executeUpdate();
        } catch(SQLException e){
            stat = "error at update user " + e.getMessage();
        }
    }
    
    public void setStatus(int userId, boolean newstat){
        String sql = "UPDATE User_HE150645 SET [status] = ? WHERE id = ? ";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setBoolean(1, newstat);
            ps.setInt(2, userId);
            ps.executeUpdate();
            
        } catch(SQLException e){
            stat = "error at change status " + e.getMessage();
        }
    }
    
        
    public void setRole(int userId, int nrole){
        String sql = "UPDATE User_HE150645 SET role = ? WHERE id = ? ";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, nrole);
            ps.setInt(2, userId);
            ps.executeUpdate();
            
        } catch(SQLException e){
            stat = "error at change role " + e.getMessage();
        }
    }
    
    public User getUser(int userId){
        for (User account : accounts) {
            if(account.getId() == userId){
                return account;
            }
        }
        
        return null;
    }
    
    public void insertA(Address a){
        String sql = "INSERT INTO Address_HE150645 VALUES(?, ?, ?, ?)";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a.getUserId());
            ps.setString(2, a.getAdd());
            ps.setString(3, a.getName());
            ps.setString(4, a.getPhone());
            
            ps.executeUpdate();
            
        } catch(SQLException e){
            stat = "error at insert address " + e.getMessage();
        }
    }
    
    public void updateA(Address a) {
        String sql = "UPDATE Address_HE150645 SET [address] = ?, receiver = ?, phone = ? WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, a.getAdd());
            ps.setString(2, a.getName());
            ps.setString(3, a.getPhone());
            ps.setInt(4, a.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            stat = "error at update address " + e.getMessage();
        }
    }
    
    public void deleteA(int addressId) {
        String sql = "DELETE Address_HE150645 WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, addressId);
            ps.executeUpdate();
        } catch (SQLException e) {
            stat = "error at update address " + e.getMessage();
        }
    }
    
    
}
