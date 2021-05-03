package Project;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    
    

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            Class.forName(DatabaseConstants.driverName);
            con = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);

            int count = 0;

            String a = request.getParameter("email_id");
            String b = request.getParameter("password");
            String password=SHA.getSHA512(b);

            String query = " select uid,email,password from users where email=? and password=?";

            try {
                ps = con.prepareStatement(query);
                ps.setString(1, a);
                ps.setString(2, password);
                rs = ps.executeQuery();

                String id = null;
                while (rs.next()) {
                    ++count;
                    id = rs.getString("uid");
                }
                if (count == 0) {
                    out.println("<html><head></head><body onload=\"alert('Invalid Username or Password')\"></body></html>");
                    RequestDispatcher rd = request.getRequestDispatcher("login.html");
                    rd.include(request, response);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("unm", id);

                    response.sendRedirect("home.jsp");
                    return;
//                    RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//                    rd.forward(request, response);
                }
            } catch (SQLException ex) {
                System.out.println("Exception 1:" + ex);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception 2:" + e);
        }
    }
}
