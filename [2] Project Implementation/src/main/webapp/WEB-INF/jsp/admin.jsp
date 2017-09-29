<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Admin Page</title>
</head>
<body>
    <h1>Halo ini admin page</h1>
    <a href="/">Go to home page</a>

    <form:form name="logout"  action="/logout" method="POST">
        <input type="submit" value="sign out" />
    </form:form>
</body>
</html>