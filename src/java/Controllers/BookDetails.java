/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.AuthorDAO;
import DAL.BookDAO;
import DAL.CategoryDAO;
import DAL.PublisherDAO;
import Models.Book;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class BookDetails extends HttpServlet {
    BookDAO bdao;
    PublisherDAO pdao;
    AuthorDAO adao;
    CategoryDAO cdao;
    public void init(){
        bdao = new BookDAO();
        pdao = new PublisherDAO();
        adao = new AuthorDAO();
        cdao = new CategoryDAO();
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
        init();
        String id = request.getParameter("bid");
        
        bdao.loadBook();
        pdao.loadPublisher();
        adao.loadAuthor();
        cdao.loadBookCate();
        cdao.loadCategory();
        if(id.length() != 0){
            Book b = bdao.search(Integer.parseInt(id));
            request.setAttribute("book", b);
            request.setAttribute("PublisherDAO", pdao);
            request.setAttribute("AuthorDAO", adao);
            request.setAttribute("CDAO", cdao.getCateByBook(b.getId()));
            request.getRequestDispatcher("Views/BookDetails.jsp").forward(request, response);
        }
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
