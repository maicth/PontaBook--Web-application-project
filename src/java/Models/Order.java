/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class Order {
    private int id;
    private int userId;
    private Date orderDate;
    private Date shippedDate;
    private int addressId;
    private int status;
    private String nstatus;
    
    private ArrayList<Item> odetails;
    private Address add;
    
    public Order() {
    }

    public Order(int id, int userId, Date orderDate, int addressId, int status, Date shippedDate) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.addressId = addressId;
        this.shippedDate = shippedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public ArrayList<Item> getOdetails() {
        return odetails;
    }

    public void setOdetails(ArrayList<Item> odetails) {
        this.odetails = odetails;
    }

    public Address getAdd() {
        return add;
    }

    public void setAdd(Address add) {
        this.add = add;
    }

    public String getNstatus() {
        if(status == 1) 
            nstatus ="Đang xử lý";
        if (status == 2)
            nstatus = "Đang lấy hàng";
        if (status == 3)
            nstatus = "Đang giao hàng";
        if (status == 4)
            nstatus = "Giao hàng thành công";
        if (status == 5)
            nstatus = "Bị hủy";
        
        return nstatus;
    }
    
    public String getNstatus(int stat) {
        String ostatus = "";
        if(stat == 1) 
            ostatus ="Đang xử lý";
        if (stat == 2)
            ostatus = "Đang lấy hàng";
        if (stat == 3)
            ostatus = "Đang giao hàng";
        if (stat == 4)
            ostatus = "Giao hàng thành công";
        if (stat == 5)
            ostatus = "Bị hủy";
        
        return ostatus;
    }

    public void setNstatus(String nstatus) {
        this.nstatus = nstatus;
    }
    
    
}
