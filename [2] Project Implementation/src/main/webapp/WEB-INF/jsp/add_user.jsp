<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Add User</title>
</head>
<body>
    <div class="container">
        <div class="col-md-6 col-md-offset-3">
            <form:form name="add_user" action="/add_user" method="post">
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
                            <td>Role</td>
                            <td><input type="text" name="role" /></td>
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
</body>
</html>