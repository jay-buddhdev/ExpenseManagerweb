package Project;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AssessIncome extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        Connection connection = null, connection1 = null;
        PreparedStatement ps = null, ps1 = null;
        ResultSet rs = null;

//        Calendar c = Calendar.getInstance();
//        String year = String.valueOf(c.get(Calendar.YEAR));
//        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
//
//        String ym = year + "-0" + month;
//        DateFormat df = new SimpleDateFormat("yyyy/MM/dd ");
//        java.util.Date dateobj = new java.util.Date();
//        String s = df.format(dateobj);
        String cat = request.getParameter("income");
        if (cat == null) {
            String from = request.getHeader("Referer");
            response.sendRedirect(from);
            return;
        }
        double inc = Double.parseDouble(request.getParameter("income1"));

        String name = null;

        HttpSession ses = request.getSession(false);
        if (ses == null) {
            response.sendRedirect("login.html");
            return;
        }
        name = (String) ses.getAttribute("unm");

        double bal = 0 + inc;

        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currTime = df.format(date);

        try {
            Class.forName(DatabaseConstants.driverName);
            connection1 = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);
            String query = "select tot_inc from trasaction where uid = ? order by tid desc limit 1";

            try {

                ps1 = connection1.prepareStatement(query);
                ps1.setString(1, name);
                rs = ps1.executeQuery();

                while (rs.next()) {
                    //out.println("bal:"+rs.getDouble("tot_bal"));
                    bal = rs.getDouble("tot_inc") + inc;
                    //out.println("total bal:"+bal);
                }

                // System.out.println("Income:" + bal);
                connection = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);

                String sql = "insert into trasaction(tdatetime, t_cat, tot_inc, tot_exp, uid) values (?,?,?,?,?)";

                ps = connection.prepareStatement(sql);

                ps.setString(1, currTime);
                ps.setString(2, cat);
                ps.setDouble(3, bal);
                ps.setDouble(4, inc);
                ps.setInt(5, Integer.parseInt(name));
//                ps.setString(1, name);
//                ps.setDouble(2, inc);
//                ps.setDouble(3, bal);
//                ps.setString(4, s);
//                ps.setString(5, ym);
//                ps.setString(6, cat);
//                ps.setTimestamp(7, sqlTime);

                ps.executeUpdate();
                out.println("<html><head></head><body onload=\"alert('Income Successfully Asessed')\"></body></html>");
            } catch (SQLException sq) {
                System.out.println("Exception 1 Occured:" + sq.toString());
            }
        } catch (Exception e) {
            System.out.println("Exception 2 Occured:" + e.toString());
        }

        ses.setAttribute("inc", inc);
        ses.setAttribute("bal", bal);

        RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
        rd.include(request, response);
    }
}
