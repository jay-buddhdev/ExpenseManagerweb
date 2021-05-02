<%@page import="Project.DatabaseConstants"%>
<!DOCTYPE html>
<html> <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style type="text/css">
            #header1-w
            {
                background-image: url("assets/images/expense_back_category.png");
            }
        </style>
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
    </head>
    <%
        // response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        // response.setHeader("Pragma", "no-cache");
        // response.setHeader("Expires", "0");
    %>
    <body>
        <%@ include file="header.jsp" %>
        <section class="header1 cid-sv2HXHjGWJ mbr-fullscreen" id="header1-w">



            <div class="mbr-overlay" style="opacity: 0.1; background-color: rgb(250, 250, 250);"></div>

            <div class="container">
                <div class="row">
                    <div class="col-12 col-lg-5">
                        <h1 class="mbr-section-title mbr-fonts-style mb-3 display-1"><strong>Add Own Expense Category</strong></h1>
                        <div class="container">
                            <div class="row justify-content">
                                <form action="ExpenseCategoryServlet" style="margin-bottom: 20px">
                                    <label for="own_exp_cat_name">
                                        <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-4 ">
                                            Enter your expense category </h4></label>
                                    <input type="text" id="own_exp_cat_name" name="own_exp_cat_name" required="">
                                    <input type="submit" id="add_exp" class="form btn-primary" value="Add to My List">
                                </form>
                            </div>
                            <div class="row justify-content">

                                <%@ page language="java" import="java.sql.*" %>
                                <%ResultSet resultset = null;%>
                                <%Connection connection = null;%>
                                <%PreparedStatement ps = null;%>

                                <form action="AssessExpense" method="post">
                                    <%
                                        HttpSession ses = request.getSession(false);
                                        String name = null;
                                        if (ses != null) {
                                            name = (String) session.getAttribute("unm");
                                            Class.forName(DatabaseConstants.driverName);
                                            connection = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);

                                            String sql = "select oexp_name from o_expense where uid=?";
                                            ps = connection.prepareStatement(sql);
                                            ps.setString(1, name);
                                            resultset = ps.executeQuery();
                                    %>

                                    <label for="expense" >
                                        <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-4 ">
                                            Select Your Own Expense Category</h4></label><br>

                                    <select id="expense" name="expense" style="margin-bottom: 20px">
                                        <% while (resultset.next()) {%>
                                        <option><%= resultset.getString("oexp_name")%></option>
                                        <% }
                        }%>    
                                    </select><br>

                                    <input type="text" id="expense1" name="expense1" placeholder="Enter Your Total Expense.." class="myText" required="" maxlength="10" onkeypress="return isNumber(event)">

                                    <input type="submit" value="Proceed" class="form btn-primary">
                                    <input type="submit" value="Delete Selected Item" class="form btn-secondary" onclick="form.action = 'DeleteExpenseCategory';" formnovalidate="">
                                </form>
                            </div>
                            <p class="mbr-text mbr-fonts-style display-7"></p>

                        </div>
                    </div>
                </div>
        </section>
    </body>
    <%@include file="footer.jsp" %>
</html>
