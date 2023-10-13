/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;
import java.sql.Date;


/**
 *
 * @author DELL
 */
public class Book {
    private int id;
    private String title;
    private int publisherId;
    private String image;
    private int price;
    private Date publishDate;
    private String size;
    private int quantity;
    private float discount;
    private String description;
    private boolean status;
    private int authorId;
    private ArrayList<Category> cates = new ArrayList<>();
    public Book() {
    }

    public Book(int id, String title, int publisherId, String image, int price, Date publishDate, String size, int quantity, float discount, String description, boolean status, int authorId) {
        this.id = id;
        this.title = title;
        this.publisherId = publisherId;
        this.image = image;
        this.price = price;
        this.publishDate = publishDate;
        this.size = size;
        this.quantity = quantity;
        this.discount = discount;
        this.description = description;
        this.status = status;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public ArrayList<Category> getCates() {
        return cates;
    }

    public void setCates(ArrayList<Category> cates) {
        this.cates = cates;
    }
    
    
    
}