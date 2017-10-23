<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
    <title>View User</title>
</head>
<body>

<div class="container">
    <div class="row">

        <div class="col-md-12">
            <h4>Data Pengguna</h4>
            <div class="table-responsive">

                <table id="usertable" class="table table-bordered table-striped">

                    <thead>

                    <!--<th><input type="checkbox" id="checkall" /></th>-->
                    <th>Username</th>
                    <th>Password</th>
                    <th>Edit</th>

                    <th>Delete</th>
                    </thead>

                    <tbody>

                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <td>${user.getUsername()}</td>
                            <td>${user.getPassword()}</td>
                            <td><a href="edit_user/${user.getUsername()}">edit</a> </td>
                            <td><a href="delete_user/${user.getUsername()}">delete</a></td>
                        </tr>
                    </c:forEach>

                    </tbody>

                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>