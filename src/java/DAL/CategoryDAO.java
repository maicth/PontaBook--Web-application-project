/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.BookCategory;
import Models.Category;
import Models.Publisher;
import java.sql.Connection;
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
public class CategoryDAO {
    private Connection con;
    public ArrayList<Category> menu;
    public ArrayList<BookCategory> bookCate;

    public String stat = "";

    public CategoryDAO() {
        try {
            con = new DBContext().getConnection();

        } catch (Exception e) {
            stat = "Error " + e.getMessage();
        }
    }

    public ArrayList<Category> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Category> menu) {
        this.menu = menu;
    }



    public void loadCategory() {
        menu = new ArrayList<Category>();
        String sql = "SELECT * FROM Category_HE150645";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int pid = rs.getInt(3);

                menu.add(new Category(id, name, pid));
            }
        } catch (Exception e) {
            stat = "Load Category " + e.getMessage();
        }
    }
    
    public void loadBookCate(){
        bookCate = new ArrayList<>();
        
        String sql = "SELECT * FROM BookCategory_HE150645";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int bookId = rs.getInt(1);
                int cateId = rs.getInt(2);
                
                bookCate.add(new BookCategory(bookId, cateId));
            }
        } catch (Exception e){
            stat = "Error at load BookCate " + e.getMessage();
        }
    }
    
    
        
    public ArrayList<Integer> getBookByCate(int id){
        ArrayList<Integer> listBooks = new ArrayList<>();
        
        for (BookCategory bc : bookCate) {
            if(bc.getCategoryId() == id){
                listBooks.add(bc.getBookId());
            }
        }
        
        return listBooks;
    }
    
    public ArrayList<Category> getCateByBook(int id) {
        ArrayList<Category> cates = new ArrayList<>();

        for (BookCategory bc : bookCate) {
            if (bc.getBookId()== id) {
                cates.add(getCategoryById(bc.getCategoryId()));
            }
        }

        return cates;
    }
    
    public Category getCategoryById(int id){
        for (Category c : menu) {
            if(c.getId() == id){
                return c;
            }
        }
        
        return null;
    }
    
    public void insert(Category c) {
        String sql = "INSERT INTO Category_HE150645 VALUES ( ? , ? )";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setNull(2, java.sql.Types.INTEGER);

            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Category c) {
        String sql = "UPDATE Category_HE150645 SET name = ? WHERE id = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setInt(2, c.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int cid) {
        String sql = "DELETE Category_HE150645 WHERE id = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
