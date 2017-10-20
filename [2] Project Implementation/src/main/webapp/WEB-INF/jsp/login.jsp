<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>LOGIN</title>
</head>
<body>

    <div class="row">
        <div class="col-md-6 col-lg-offset-6">
            <h1 class="col-md-10 col-md-offset-1">Welcome</h1>
            <div class="col-md-10 col-md-offset-1">
                <form:form name="submitForm"  action="${login}" method="POST">
                    <div align="center">
                        <table>
                            <tr>
                                <td>User Name</td>
                                <td><input type="text" name="username" /></td>
                            </tr>
                            <tr>
                                <td>Password</td>
                                <td><input type="password" name="password" /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input type="submit" value="Submit" /></td>
                            </tr>
                        </table>
                        <div style="color: red">${error}</div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>