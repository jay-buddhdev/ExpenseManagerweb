<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.*" %>
<%@page import="Project.DatabaseConstants"%>
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
        <title>Income Category</title>
    </head>
    <%
        // response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        // response.setHeader("Pragma","no-cache");
        // response.setHeader("Expires","0");
    %>
    <body>
        <%@ include file="header.jsp" %>
        <section class="header1 cid-sv2HXHjGWJ mbr-fullscreen" id="header1-w" >



            <div class="mbr-overlay" style="opacity: 0.1; background-color: rgb(250, 250, 250);"></div>

            <div class="container">
                <div class="row">
                    <div class="col-12 col-lg-5">
                        <h1 class="mbr-section-title mbr-fonts-style mb-3 display-1"><strong>Add Income</strong></h1>
                        <div class="container">
                            <div class="row justify-content">
                                <%ResultSet resultset = null;%>
                                <%Connection connection = null;%>
                                <%Statement stmt = null;%>

                                <form action="AssessIncome" method="POST"> 
                                    <label for="inc_cat">
                                        <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-4 ">
                                            Select Your Income Category
                                        </h4>
                                    </label> <br />
                                    <%
                                        ArrayList<String> categories = new ArrayList<String>();
                                        try {
                                            Class.forName(DatabaseConstants.driverName);
                                            connection = DriverManager.getConnection(DatabaseConstants.connectionString, DatabaseConstants.UserName, DatabaseConstants.Password);
                                            stmt = connection.createStatement();

                                            String sql1 = "Select inc_cat from income", sql2 = "select oinc_name from o_income";
                                            resultset = stmt.executeQuery(sql1);
                                            while (resultset.next()) {
                                                categories.add(resultset.getString("inc_cat"));
                                            }
                                            resultset.close();
                                            resultset = stmt.executeQuery(sql2);
                                            while (resultset.next()) {
                                                categories.add(resultset.getString("oinc_name"));
                                            }
                                    %>
                                    <select id="inc_cat" name="income" style="margin-bottom: 20px">
                                        <% for (int i = 0; i < categories.size(); i++) {%>
                                        <option><%=categories.get(i)%></option>
                                        <% } %>
                                    </select>
                                    <%
                                        } catch (Exception e) {
                                            System.out.println(e.toString());
                                        }

                                    %>

                                    <br><label for="inc_cat">
                                        <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-4 ">
                                            Enter your income below
                                        </h4>
                                    </label><br>


                                    <input type="text" id="income1" name="income1" placeholder="Enter Your Total Income.." class="myText" required="" maxlength="10" onkeypress="return isNumber(event)">

                                    <input class="form btn-primary" type="submit" value="Proceed" >
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
