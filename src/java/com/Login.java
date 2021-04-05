/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    

    static int attemptcount = 0;
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
            out.println("<title>Login-Page</title>");            
            out.println("</head>");
            out.println("<body>");
            //String name=request.getParameter("uname");
            //String pass=request.getParameter("upass");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment","root","root");
            PreparedStatement ps=con.prepareStatement("select * from employee where emp_name='"+ request.getParameter("uname") +"' ");
           // ps.setString(1, name);
          //  ps.setString(2, pass);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                String u=rs.getString("emp_name");
                String up=rs.getString("password");
                String status=rs.getString("status");
                
               if(status.equals("open"))
               {
                   if(u.equals(request.getParameter("uname"))&&up.equals(request.getParameter("upass")))
                   {
                        HttpSession session=request.getSession();
                        session.setAttribute("emp_name",u);
                        out.println("<h3><font color='green'>Welcom </font></h3>"+"<h3><font color='red'>" +u+"</font></h3>");
                        out.println("<h3><a href=Logout>Logout</a></h3>");
                        
                   }else{
                       out.println("<h3><font color='#a23e3e'>Invalid Username or Password - Relogin "
                               + "with Correct Username Password- No. of Attemps Remaining :</font></h3> "+"<h3><font color='#25670b'>" + (4 - attemptcount)+"</font></h3>");
                       attemptcount = attemptcount + 1;
                       RequestDispatcher rd=request.getRequestDispatcher("index.html");
                        rd.include(request, response);
                   }
                    //out.println("yes " +u);
               }else{
                   out.println("<h3><font color='red'>Your Account Locked Already : Contact Administrator </font></h3>");
                   RequestDispatcher rd=request.getRequestDispatcher("index.html");
                        rd.include(request, response);
               }
            }else{
                out.println("<h3><font color='red'>Invalid Username or Password - Relogin with Correct Username Password</font></h3> ");
                RequestDispatcher rd=request.getRequestDispatcher("index.html");
                        rd.include(request, response);
            }
            if(attemptcount==5)
            {
                out.println("<h3><font color='blue'>Your Account Has Been Locked Due to Five Invalid Attempts - Contact Administrator</font></h3>");
               
                PreparedStatement ps1=con.prepareStatement("update employee set status='locked' where emp_name='"+ request.getParameter("uname")+"'");
                ps1.executeUpdate();
                attemptcount = 0;
            }
           
            
//            while(rs.next())
//            {
//                String u=rs.getString("username");
//                String up=rs.getString("password");
//                if(up.equals("Aabcd@156"))
//                {
//                    out.println("yes");
//                   // break;
//                }
//                
//            }
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
