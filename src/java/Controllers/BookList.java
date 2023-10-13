/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Models.*;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class BookList extends HttpServlet {
    
    
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
        bdao.loadBook();
        pdao.loadPublisher();
        adao.loadAuthor();
        cdao.loadCategory();

        request.setAttribute("PublisherDAO", pdao);
        request.setAttribute("AuthorDAO", adao);
        request.setAttribute("CDAO", cdao);
        
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
        processRequest(request, response);

        ArrayList<Book> blist = bdao.books;
        String q = request.getParameter("q");
        if(q != null && q.trim().length() != 0){
            bdao.searchByName(q);
            blist = bdao.booksByName;
        }
                    
        if(!blist.isEmpty()){
            
            int size = bdao.books.size();
            int nrpp = 5;
            int cp = 0;

            ControlPaging controlPage = new ControlPaging(nrpp, cp, size);
            controlPage.calc();
            request.setAttribute("CP", controlPage);       
            request.setAttribute("Books", blist);
        }
        
        request.getRequestDispatcher("Views/Admin/BookList.jsp").forward(request, response);
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
        processRequest(request, response);
        ArrayList<Book> blist = bdao.books;
        String q = request.getParameter("q");
        if(q != null && q.trim().length() != 0){
            bdao.searchByName(q);
            blist = bdao.booksByName;
        }
        
        int size = blist.size();
        int nrpp = 5;

        int cp = Integer.parseInt(request.getParameter("cp"));
        int np = Integer.parseInt(request.getParameter("np"));

        if (request.getParameter("home") != null) {
            cp = 0;
        }

        if (request.getParameter("end") != null) {
            cp = np - 1;
        }

        if (request.getParameter("pre") != null) {
            cp = cp - 1;
        }

        if (request.getParameter("next") != null) {
            cp = cp + 1;
        }

        for (int i = 0; i < np; i++) {
            if (request.getParameter("btn" + i) != null) {
                cp = i;
            }
        }
        if(!blist.isEmpty()){
            ControlPaging controlPage = new ControlPaging(nrpp, cp, size);
            controlPage.calc();

            request.setAttribute("Books", blist);
            request.setAttribute("CP", controlPage);
        }
        request.getRequestDispatcher("Views/Admin/BookList.jsp").forward(request, response);
        
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
