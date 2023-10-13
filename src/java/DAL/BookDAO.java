/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Book;
import Models.BookCategory;
import Models.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class BookDAO {
    private Connection con;
    public ArrayList<Book> books;
    public ArrayList<Book> booksByAuthor;
    public ArrayList<Book> booksByPub;
    public ArrayList<Book> booksByName;
    public String stat = "";
    private AuthorDAO adao = new AuthorDAO();
    private PublisherDAO pdao = new PublisherDAO();
    public BookDAO() {
        try {
            con = new DBContext().getConnection();

        } catch (Exception e) {
            stat = "Error " + e.getMessage();
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void loadBook() {
        books = new ArrayList<>();
        String sql = "SELECT * FROM Book_HE150645";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                int publisherId = rs.getInt(3);
                String img = rs.getString(4);
                int price = Math.round(rs.getFloat(5));
                Date publishDate = rs.getDate(6);
                String size = rs.getString(8);
                int quantity = rs.getInt(9);
                float discount = rs.getFloat(10);
                String description = rs.getString(7);
                boolean bstatus = rs.getBoolean(11);
                int authorId = rs.getInt(12);
                books.add(new Book(id, title, publisherId, img, price, publishDate, size, quantity, discount, description, bstatus, authorId));
            }

        } catch (Exception e) {
            stat = "Error load db" + e.getMessage();
        }
    }

    public void insert(Book b){
        String sql = "INSERT INTO Book_HE150645 VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, b.getTitle());
            ps.setInt(2, b.getPublisherId());
            ps.setString(3, b.getImage());
            ps.setFloat(4, b.getPrice());
            ps.setDate(5, b.getPublishDate());
            ps.setString(6, b.getDescription());
            ps.setString(7, b.getSize());
            ps.setInt(8, b.getQuantity());
            ps.setFloat(9, b.getDiscount());
            ps.setBoolean(10, b.isStatus());
            ps.setInt(11, b.getAuthorId());
            ps.execute();
            
            String sql_get_id = "SELECT @@Identity as bid";
            PreparedStatement ps_id = con.prepareStatement(sql_get_id);
            ResultSet rs = ps_id.executeQuery();
            if(rs.next()){
                b.setId(rs.getInt("bid"));
            }
            
            for (Category c : b.getCates()) {
                String sql_insert_cate = "INSERT INTO BookCategory_HE150645 VALUES(?, ?)";
                PreparedStatement ps_insert = con.prepareStatement(sql_insert_cate);
                ps_insert.setInt(1, b.getId());
                ps_insert.setInt(2, c.getId());
                
                ps_insert.executeUpdate();
            }
            
            con.commit();
        } catch (SQLException e){
            stat = "Error at Insert Book " + e.getMessage();
            try {
                con.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }      
    }
    
    public void update(Book b){      
        String sql = "UPDATE Book_HE150645 "
                + "SET title = ?, publisherId = ?, image = ?, price = ?, publishDate = ?, "
                + "size = ?, quantity = ?, discount = ?, description = ?, status = ?, authorId = ?"
                + " WHERE id = ?";
        
        try {       
            
            con.setAutoCommit(false);
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, b.getTitle());
            ps.setInt(2, b.getPublisherId());
            ps.setString(3, b.getImage());
            ps.setFloat(4, b.getPrice());
            ps.setDate(5, b.getPublishDate()) ;
            ps.setString(6, b.getSize());
            ps.setInt(7, b.getQuantity());
            ps.setFloat(8, b.getDiscount());
            ps.setString(9, b.getDescription());
            ps.setBoolean(10, b.isStatus());
            ps.setInt(11, b.getAuthorId());
            ps.setInt(12, b.getId());
            
            ps.execute();
            
            String sql_delete = "DELETE BookCategory_HE150645 WHERE bookId = ?";
            PreparedStatement ps_delete = con.prepareStatement(sql_delete);
            ps_delete.setInt(1, b.getId());
            ps_delete.executeUpdate();
            
            for (Category c : b.getCates()) {
                String sql_insert = "INSERT INTO BookCategory_HE150645 VALUES (?, ?)";
                PreparedStatement ps_insert = con.prepareStatement(sql_insert);
                ps_insert.setInt(1, b.getId());
                ps_insert.setInt(2, c.getId());
                
                ps_insert.executeUpdate();
            }
            
            con.commit();
        } catch (SQLException e){
            try {
                stat = "Error at Update Book " + e.getMessage();
                con.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        finally{
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void delete(int id){
        if (checkBook(id)){
            try {
                
                con.setAutoCommit(false);
                
                String sql_delete = "DELETE BookCategory_HE150645 WHERE bookId = ?";
                
                PreparedStatement ps_delete = con.prepareStatement(sql_delete);
                ps_delete.setInt(1, id);
                ps_delete.executeUpdate();
                
                String sql = "DELETE Book_HE150645 WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();
                
                con.commit();
            } catch (SQLException e){
                stat = "Error at Delete Book " + e.getMessage();
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            finally{
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    public boolean checkBook(int id){
        for (Book b : books) {
            if (b.getId() == id){
                return true;
            }
        }       
        return false;
    }
    
    public Book search(int id){
        for (Book b : books) {
            if(b.getId() == id){
                return b;
            }
        }
        
        return null;
    }
    
    public void searchByAuthor(int authId){
        adao.loadAuthor();
        booksByAuthor = new ArrayList<>();
        for (Book b : books) {
            if(b.getAuthorId() == authId && b.isStatus()){
                booksByAuthor.add(b);
            }
        }
    }
    
    public void searchByPub(int pubId){
        pdao.loadPublisher();
        booksByPub = new ArrayList<>();
        for (Book b : books) {
            if(b.getPublisherId() == pubId && b.isStatus()){
                booksByPub.add(b);
            }
        }
    }
    
    public void searchByName(String q){
        booksByName = new ArrayList<>();
        for (Book b : books) {
            if(b.getTitle().toLowerCase().contains(q.toLowerCase()) && b.isStatus()){
                booksByName.add(b);
            }
        }
    }
    
    public ArrayList<Book> sortByPriceAsc(ArrayList<Book> books){
         Collections.sort(books, new Comparator<Book>() {

        @Override
        public int compare(Book o1, Book o2) {
            // compare two instance of `Score` and return `int` as result.
            return Float.compare(o1.getPrice() * (1 - o1.getDiscount()), o2.getPrice() * (1 - o2.getDiscount()));
        }
    });
        return books;
    }
    
        public ArrayList<Book> sortByPriceDesc(ArrayList<Book> books){
            books = sortByPriceAsc(books);
            Collections.reverse(books);
            return books;
    }
    
    public ArrayList<Book> getActiveBooks(ArrayList<Book> books){
        
        ArrayList<Book> abooks = new ArrayList<>();
        for (Book b : books) {
            if(b.isStatus()){
                abooks.add(b);
            }
        }
        
        return abooks;
    }
}
