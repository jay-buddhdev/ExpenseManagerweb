package Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IncomeCategoryServlet extends HttpServlet {

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
            cat_name = request.getParameter("own_inc_cat_name");

            try {
                Class.forName(DatabaseConstants.driverName);
                connection = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);

                String sql = "insert into o_income(oinc_name, uid) values(?,?)";
                try {
                    psmt = connection.prepareStatement(sql);
                    psmt.setString(1, cat_name);
                    psmt.setInt(2, Integer.parseInt(name));
                    psmt.execute();

                    RequestDispatcher rd = request.getRequestDispatcher("/own_income_category.jsp");
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
