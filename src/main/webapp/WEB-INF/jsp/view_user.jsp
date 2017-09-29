<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>View User</title>
</head>
<body>
<div class="container">
    <div class="col-md-8 col-md-offset-2">
        <table border="1" width="90%">
            <tr>
                <th>ID</th>
                <th>Nama Lengkap</th>
                <th>Username</th>
                <th>Password</th>
                <th>Alamat</th>
                <th>No KTP</th>
                <th>No HP</th>
                <th>Jenis Kelamin</th>
                <th>Role</th>

            </tr>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.getId()}</td>
                    <td>${user.getName()}</td>
                    <td>${user.getEmail()}</td>
                    <td><a href="edit_user/${user.getId()}">edit</a> </td>
                    <td><a href="delete_user/${user.getId()}">delete</a></td>
                </tr>
            </c:forEach>
        </table>
        <a href="add_user">Add user</a>
    </div>
</div>
</body>
</html>