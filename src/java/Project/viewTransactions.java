package Project;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class viewTransactions extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String[] year_month = request.getParameter("trasac_month").split("-");
        java.time.LocalDate inp = java.time.LocalDate.of(Integer.parseInt(year_month[0]), Integer.parseInt(year_month[1]), 1);

        ResultSet rs = null, rs1 = null;
        Connection con = null, con1 = null;
        PreparedStatement ps = null, ps1 = null;

        String name = null;
        HttpSession session = request.getSession(false);
        name = (String) session.getAttribute("unm");

        request.setAttribute("includeFooter", "NO");
        RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
        rd.include(request, response);
        try {
            Class.forName(DatabaseConstants.driverName);
            con = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);
            con1 = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);

            String sql = "select * from trasaction where uid = ? and MONTH(tdatetime) = ? and YEAR(tdatetime) = ? and tot_exp > 0";

            try {
                ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setInt(2, inp.getMonthValue());
                ps.setInt(3, inp.getYear());
                rs = ps.executeQuery();

                // out.println("<html>");
                // out.println("<body>");
                out.print("<div class=\"container\">");

                out.println("<table class=\"table table-hover\">");
                out.println("<tr>");
                out.println("<th>Transaction Date</td>");
                out.println("<th>Income Category</td>");
                out.println("<th>Income</td>");
                out.println("<th>Balance</td>");
                out.println("</tr>");

                int count = 0;

                while (rs.next()) {
                    ++count;

                    out.println("<tr>");
                    out.print("<td>" + rs.getTime("tdatetime") + "</td>");
                    out.print("<td>" + rs.getString("t_cat") + "</td>");
                    out.print("<td>" + rs.getString("tot_exp") + "</td>");
                    out.print("<td>" + rs.getString("tot_inc") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");

                out.print("<hr style = background-color:#000000; border-width:0; color:#000000; height:2px; lineheight:0; display: inline-block; text-align: left; width:100%;' />");

                String sql1 = "select * from trasaction where uid = ? and MONTH(tdatetime) = ? and YEAR(tdatetime) = ? and tot_exp < 0";
                ps1 = con1.prepareStatement(sql1);
                ps1.setString(1, name);
                ps1.setInt(2, inp.getMonthValue());
                ps1.setInt(3, inp.getYear());
                rs1 = ps1.executeQuery();

                out.println("<table class=\"table table-hover\">");
                out.println("<tr>");
                out.println("<td><b>Transaction Date</b></td>");
                out.println("<td><b>Expense Category</b></td>");
                out.println("<td><b>Expense</b></td>");
                out.println("<td><b>Balance</b></td>");
                out.println("</tr>");

                while (rs1.next()) {

                    out.println("<tr>");

                    out.print("<td>" + rs1.getTime("tdatetime") + "</td>");
                    out.print("<td>" + rs1.getString("t_cat") + "</td>");
                    out.print("<td>" + (-Double.parseDouble(rs1.getString("tot_exp"))) + "</td>");
                    out.print("<td>" + rs1.getString("tot_inc") + "</td>");

                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</div>");
                // out.print("</body></html>");
            } catch (SQLException sq) {
                System.out.println("Exception 1 Occured:" + sq.toString());
            }

        } catch (Exception e) {
            System.out.println("Exception 2 Occured:" + e.toString());
        }
        request.getRequestDispatcher("footer.jsp").include(request, response);
    }
}
