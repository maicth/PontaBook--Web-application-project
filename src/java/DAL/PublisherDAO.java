/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Publisher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class PublisherDAO {
    private Connection con;
    public HashMap<Integer, Publisher> publisherHm;

    public String status = "";

    public PublisherDAO() {
        try {
            con = new DBContext().getConnection();

        } catch (Exception e) {
            status = "Error " + e.getMessage();
        }
    }


    public HashMap<Integer, Publisher> getPublisherHm() {
        return publisherHm;
    }

    public void setPublisherHm(HashMap<Integer, Publisher> publisherHm) {
        this.publisherHm = publisherHm;
    }

    public void loadPublisher() {
        publisherHm = new HashMap<>();
        String sql = "SELECT * FROM Publisher_HE150645";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int publisherId = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String add = rs.getString(4);
                String phone = rs.getString(5);

                publisherHm.put(publisherId, new Publisher(publisherId, name, email, add, phone));
            }
        } catch (SQLException e) {
            status = "Error load Publisher " + e.getMessage();
        }
    }
    
    public void insert(Publisher p){
        String sql = "INSERT INTO Publisher_HE150645 VALUES ( ? , ? , ? , ? )";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getAddress());
            ps.setString(4, p.getPhone());
            
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(Publisher p){
        String sql = "UPDATE Publisher_HE150645 SET name = ? , email = ? , [address] = ? , phone = ?  WHERE id = ? ";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getAddress());
            ps.setString(4, p.getPhone());
            ps.setInt(5, p.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int pid){
        String sql = "DELETE Publisher_HE150645 WHERE id = ? ";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,pid);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
