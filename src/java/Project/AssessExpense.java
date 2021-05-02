package Project;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AssessExpense extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String name = null;
        double bal = 0;
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.html");
            return;
        }
        name = (String) session.getAttribute("unm");

        String cat = request.getParameter("expense");
        if (cat == null) {
            String from = request.getHeader("Referer");
            response.sendRedirect(from);
            return;
        }
        double exp = Double.parseDouble(request.getParameter("expense1"));

        Connection connection = null, connection1 = null;
        PreparedStatement ps = null, ps1 = null;
        ResultSet rs = null;

        try {
            Class.forName(DatabaseConstants.driverName);
            connection1 = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);
            String query = "select tot_inc from trasaction where uid = ? order by tid desc limit 1";

            try {
                ps1 = connection1.prepareStatement(query);
                ps1.setString(1, name);
                rs = ps1.executeQuery();

                String bal1 = null;

                while (rs.next()) {
                    bal1 = rs.getString("tot_inc");
                    bal = Double.parseDouble(bal1);
                    bal -= exp;
                }

            } catch (SQLException sq) {
                System.out.println("Exception 1:" + sq.toString());
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println("Exception 2:" + e.toString());
        }
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat df;
        df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currTime = df.format(date);

        try {
            Class.forName(DatabaseConstants.driverName);
            connection = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);

            String sql = "insert into trasaction(tdatetime, t_cat, tot_inc, tot_exp, uid) values (?,?,?,?,?)";

            ps = connection.prepareStatement(sql);
            try {
                ps.setString(1, currTime);
                ps.setString(2, cat);
                ps.setDouble(3, bal);
                ps.setDouble(4, -exp);
                ps.setInt(5, Integer.parseInt(name));
//                ps.setString(1, currTime);
//                ps.setString(1, name);
//                ps.setString(2, String.valueOf(exp));
//                ps.setString(3, String.valueOf(bal));
//                ps.setString(4, s);
//                ps.setString(5, ym);
//                ps.setString(6, cat);
//                ps.setTimestamp(7, sqlTime);
                ps.executeUpdate();
                out.println("<html><head></head><body onload=\"alert('Expense Successfully Asessed')\"></body></html>");
            } catch (SQLException sq) {
                System.out.println("Exception 1 Occured:" + sq.toString());
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception 2 Occured:" + e.toString());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
        rd.include(request, response);
    }
}
