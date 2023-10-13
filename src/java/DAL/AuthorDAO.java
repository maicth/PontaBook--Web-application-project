/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Author;
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
public class AuthorDAO {
    private Connection con;
    public HashMap<Integer, Author> authorHm;

    public String status = "";

    public AuthorDAO() {
        try {
            con = new DBContext().getConnection();

        } catch (Exception e) {
            status = "Error " + e.getMessage();
        }
    }

    public HashMap<Integer, Author> getAuthorHm() {
        return authorHm;
    }

    public void setAuthorHm(HashMap<Integer, Author> authorHm) {
        this.authorHm = authorHm;
    }


   
    public void loadAuthor() {
        authorHm = new HashMap<Integer, Author>();
        String sql = "SELECT * FROM Author_HE150645";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int authorId = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);

                authorHm.put(authorId, new Author(authorId, name, email));
            }
        } catch (Exception e) {
            status = "Error load Author " + e.getMessage();
        }
    }
    
    public void insert(Author a){
        String sql = "INSERT INTO Author_HE150645 VALUES ( ? , ? )";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, a.getName());
            ps.setString(2, a.getEmail());
            
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(Author a){
        String sql = "UPDATE Author_HE150645 SET name = ? , email = ? WHERE id = ? ";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, a.getName());
            ps.setString(2, a.getEmail());

            ps.setInt(5, a.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int aid){
        String sql = "DELETE Author_HE150645 WHERE id = ? ";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,aid);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
