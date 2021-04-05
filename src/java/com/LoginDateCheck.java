/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "LoginDateCheck", urlPatterns = {"/LoginDateCheck"})
public class LoginDateCheck extends HttpServlet {

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
            out.println("<title>Login-Date-Check</title>");            
            out.println("</head>");
            out.println("<body>");
             String name=request.getParameter("uname");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment","root","root");
            PreparedStatement ps=con.prepareStatement("select * from employee where emp_name='"+ name +"'");
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                Date resgister_date=rs.getDate("register_dates");
                SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
                Date today=new Date();
                String todaysDate=f.format(today);
                String nydsDate=f.format(resgister_date);
                long diffrent =(today.getTime()-resgister_date.getTime())/86400000;
                //out.print( diffrent+"Yes"+todaysDate+"yes  ");
                
                if(diffrent>=14)
                {
                    out.println("<h2><font color='red'>Your Password Expired Change Your Password</font></h2>");
                    RequestDispatcher rd=request.getRequestDispatcher("ChangePasswordForm.html");
                    rd.include(request, response);
                }
                else{
                        RequestDispatcher rd=request.getRequestDispatcher("Login");
                        rd.forward(request, response);
                }
                
                
            }else{
                    out.println("<h3><font color='red'>Invalid Username or Password - Relogin with Correct Username Password</font></h3> ");
                    RequestDispatcher rd=request.getRequestDispatcher("index.html");
                            rd.include(request, response);
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
