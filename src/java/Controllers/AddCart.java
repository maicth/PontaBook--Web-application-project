/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.BookDAO;
import DAL.CartDAO;
import Models.Item;
import Models.User;
import Models.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class AddCart extends HttpServlet {
    BookDAO bdao;
    CartDAO ctdao;
    
    
    public void init(){
        bdao = new BookDAO();
        ctdao = new CartDAO();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Cart</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Cart at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        bdao.loadBook();
       
        User u = (User) request.getSession().getAttribute("account");
         
        if(u != null){                       
            if(request.getParameter("deleteId") != null){
                int bid = Integer.parseInt(request.getParameter("deleteId"));
                ctdao.delete(u.getId(), bid);
            }
            int s = ctdao.loadCart(u.getId()).size();
            if(s > 0){
                request.setAttribute("items", getItems(u.getId()));
            }   
            request.setAttribute("size", s);
            request.getRequestDispatcher("Views/Member/Cart.jsp").forward(request, response);
            
        }
        else {
            // chuyen den login
            request.getRequestDispatcher("Login").forward(request, response);
        }
    
    }
    
    public ArrayList<Item> getItems(int userId){
        ArrayList<Item> items = new ArrayList<>();
                
        for (Cart c : ctdao.loadCart(userId)) {              
            Item item = new Item();
            item.setB(bdao.search(c.getBookId()));
            item.setQuantity(c.getQuantity());

            items.add(item);
        }
        
        return items;
    }
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        int bookId = Integer.parseInt(request.getParameter("bid"));
        
        int userId = Integer.parseInt(request.getParameter("uid"));
        if(request.getParameter("quantity") != null){
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            addBookCart(bookId, userId, quantity);
        }
        
        if(request.getParameter("updateQuantity") != null){
            int uquantity = Integer.parseInt(request.getParameter("updateQuantity"));
            ctdao.update(new Cart(userId, bookId, uquantity));
        }
        response.sendRedirect("cart");
    }
    
    
    public void addBookCart(int bookId, int userId, int quantity){
        for (Cart cart : ctdao.loadCart(userId)) {
            if(cart.getBookId() == bookId){
                cart.setQuantity(cart.getQuantity() + quantity);
                
                // check max quantity
                ctdao.update(cart);
                return;
            }

        }
        
        Cart c = new Cart();
        c.setUserId(userId);
        c.setBookId(bookId);
        c.setQuantity(quantity);
        
        // insert cart to db
        ctdao.insert(c);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
