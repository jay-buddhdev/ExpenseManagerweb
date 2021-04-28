

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Home</title>

    </head>
    <body>


        <%@ include file="header.jsp" %>
        <%@ page session = "false"  language="Java" import="java.util.*,java.io.*,javax.servlet.http.*,javax.servlet.*,java.sql.*"%>
        <%@page import="Project.DatabaseConstants" %>
        <%ResultSet rs = null;%>
        <%Connection con = null;%>
        <%PreparedStatement ps = null;%>
        <%

//            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//            response.setHeader("Pragma", "no-cache");
//            response.setHeader("Expires", "0");
            Class.forName(DatabaseConstants.driverName);
            con = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);

            HttpSession ses = request.getSession(false);
            if (ses == null) {
                response.sendRedirect("index.html");
                return;
            }

            String name = (String) ses.getAttribute("unm");
            String sql = "Select fname,lname from users where uid=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            String fnm = null;
            String lnm = null;

            while (rs.next()) {
                fnm = rs.getString("fname");
                lnm = rs.getString("lname");
            }
            String nm = fnm + " " + lnm;


        %>
        <section class="header10 cid-suUOAKZGXg" id="header10-n">
            <div class="align-center container-fluid">
                <div class="row justify-content-center">
                    <div class="col-md-12 col-lg-9">
                        <h1 class="mbr-section-title mbr-fonts-style mb-3 display-1"><strong>Expense Manager</strong></h1>
                        <p class="mbr-text mbr-fonts-style display-7">
                            <% out.println("Welcome,<b> " + nm + "</b>");%></p>
                        <div class="mbr-section-btn mt-3" style="width: 50%; margin: auto">
                            <!--<h3 class="mbr-section-subtitle align-center mbr-fonts-style mb-4 "><strong>Select Month and Year</strong></h3>-->
                            <form action="viewTransactions">
                                <input class="form-control" type="month" id="trasac_month" name="trasac_month" required>
                                <input class="btn btn-secondary display-4" type="Submit" value="View Transactions" style="margin: auto; margin-top: 10px">
                            </form>
                        </div>
                        <div class="image-wrap mt-4">
                            <img src="assets/images/intro.png" alt="Mobirise" title="">
                        </div>
                    </div>
                </div>
            </div>
        </section>

<!--        <section class="content1 cid-s48udlf8KU" id="content1-8">

            <div class="container">
                <div class="row justify-content-center">
                    <div class="">

                    </div>
                </div>
            </div>
        </section>-->

        <%
            boolean includeFooter = request.getAttribute("includeFooter") == null || !((String)request.getAttribute("includeFooter")).equals("NO");
            if(includeFooter){
        %>
        <%@include file="footer.jsp" %>
        <%
            }
        %>
    </body>
</html>
