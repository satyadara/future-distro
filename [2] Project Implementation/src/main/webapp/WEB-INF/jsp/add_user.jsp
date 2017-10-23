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
        <form action="/add_user" method="post">
            <table>
                <tr>
                    <td>
                        Username
                    </td>
                    <td>
                        <input type="text" name="username">
                    </td>
                </tr>
                <tr>
                    <td>
                        Password
                    </td>
                    <td>
                        <input type="text" name="password">
                    </td>
                </tr>
                <tr>
                    <td>
                        Role
                    </td>
                    <td>
                        <input type="text" name="role">
                    </td>
                </tr>
            </table>
            <input type="submit" value="Add">
        </form>
    </div>
</div>
</body>
</html>