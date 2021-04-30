package Project;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ExpenseCategoryServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        Connection connection = null;
        PreparedStatement psmt = null;

        HttpSession session = request.getSession(false);

        String name = null;
        String cat_name = null;

        if (session != null) {
            name = (String) session.getAttribute("unm");
            cat_name = request.getParameter("own_exp_cat_name");

            try {
                Class.forName(DatabaseConstants.driverName);
                connection = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);

                String sql = "insert into o_expense(oexp_name, uid) values(?,?)";
                try {
                    psmt = connection.prepareStatement(sql);
                    psmt.setString(1, cat_name);
                    psmt.setInt(2, Integer.parseInt(name));
                    psmt.execute();

                    RequestDispatcher rd = request.getRequestDispatcher("/own_expense_category.jsp");
                    rd.include(request, response);
                    out.println("<html><head></head><body onload=\"alert('Your Item Added Successfully')\"></body></html>");
                } catch (SQLException se) {
                    System.out.println("Exception No 1:" + se);
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Exception No 2:" + e);
            }
        }
    }
}
