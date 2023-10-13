/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author DELL
 */
public class Item {
    private Book b;
    private int quantity;
    private float discount;
    public Item() {
    }

    public Item(Book b, int quantity) {
        this.b = b;
        this.quantity = quantity;
    }

    public Item(Book b, int quantity, float discount) {
        this.b = b;
        this.quantity = quantity;
        this.discount = discount;
    }

    
    public Book getB() {
        return b;
    }

    public void setB(Book b) {
        this.b = b;
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
    
    
}
