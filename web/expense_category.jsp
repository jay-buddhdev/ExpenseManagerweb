

<%@page import="java.util.ArrayList"%>
<%@page import="Project.DatabaseConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
            #header1-w
            {
               background-image: url("assets/images/income_back.png");
            }
        </style>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script>
            function isNumber(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }
        </script>
        <title>Expense Category</title>
    </head>

    <%
//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Expires", "0");
    %>
    <body>
        <%@ include file="header.jsp" %>

        <section class="header1 cid-sv2HXHjGWJ mbr-fullscreen" id="header1-w">



            <div class="mbr-overlay" style="opacity: 0.1; background-color: rgb(250, 250, 250);"></div>

            <div class="container">
                <div class="row">
                    <div class="col-12 col-lg-5">
                        <h1 class="mbr-section-title mbr-fonts-style mb-3 display-1"><strong>Add Expense</strong></h1>
                        <div class="container">
                            <div class="row justify-content">
                                <%@ page import="java.sql.*" %>
                                <%ResultSet resultset = null;%>
                                <%Connection connection = null;%>
                                <%Statement stmt = null;%>

                                <form action="AssessExpense" method="POST"> 
                                    <label for="exp_cat">
                                        <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-4">
                                            Select Your expense Category
                                        </h4>
                                    </label><br>
                                    <%
                                        ArrayList<String> categories = new ArrayList<String>();
                                        try {
                                            Class.forName(DatabaseConstants.driverName);
                                            connection = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);
                                            stmt = connection.createStatement();

                                            String sql1 = "Select exp_cat from expense", sql2 = "select oexp_name from o_expense";
                                            resultset = stmt.executeQuery(sql1);
                                            while (resultset.next()) {
                                                categories.add(resultset.getString("exp_cat"));
                                            }
                                            resultset.close();
                                            resultset = stmt.executeQuery(sql2);
                                            while (resultset.next()) {
                                                categories.add(resultset.getString("oexp_name"));
                                            }
                                    %>
                                    <select id="exp_cat" name="expense" style="margin-bottom: 20px">
                                        <% for (int i = 0; i < categories.size(); i++) {%>
                                        <option><%=categories.get(i)%></option>
                                        <% } %>
                                    </select>
                                    <%
                                        } catch (Exception e) {
                                            System.out.println(e.toString());
                                        }
                                    %>

                                    <br><label for="exp_cat">
                                        <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-41">
                                            Enter Your expense
                                        </h4>
                                    </label><br>


                                    <input type="text" id="expense1" name="expense1" placeholder="Enter Your Total Expense.." class="myText" required="" maxlength="10" onkeypress="return isNumber(event)">

                                    <input type="submit" value="Proceed" class="form btn-primary">
                                </form>
                            </div>
                            <p class="mbr-text mbr-fonts-style display-7"></p>

                        </div>
                    </div>
                </div>
        </section>


    </div>
    <%@include file="footer.jsp" %>
</body>
</html>