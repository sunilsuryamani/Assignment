/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "ChangePassword", urlPatterns = {"/ChangePassword"})
public class ChangePassword extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try{
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Change-Password</title>");            
            out.println("</head>");
            out.println("<body>");
            String password=request.getParameter("upass");
            String email=request.getParameter("email");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment","root","root");
            PreparedStatement ps=con.prepareStatement("select password from employee where password='"+ password +"' ");
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
                out.println("<h3><font color='red'>This Password are use already other employee</font></h3>");
                RequestDispatcher rd=request.getRequestDispatcher("ChangePasswordForm.html");
                rd.include(request, response);
            }
            else{
                //out.println("no");
                
                PreparedStatement ps1=con.prepareStatement("UPDATE employee SET password=?,register_dates=Now() WHERE email_id=?");
                ps1.setString(1, password);
                ps1.setString(2,email);
                int n=ps1.executeUpdate();
                if(n==1)
                {
                    out.println("<h3><font color='green'>Your Password have been changed</font></h3> ");
                    out.println("<h3><font color='blue'>Employee Must Be Change The Password After 14 Days</font></h3> ");
                    RequestDispatcher rd=request.getRequestDispatcher("index.html");
                    rd.include(request, response);
                }else{
                    out.println("<h3><font color='red'>Email Incorrect</font></h3>");
                    RequestDispatcher rd=request.getRequestDispatcher("ChangePasswordForm.html");
                    rd.include(request, response);
                }
            }
            out.println("</body>");
            out.println("</html>");
        }catch(Exception ex){
            out.println(ex.getMessage());
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
        processRequest(request, response);
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
