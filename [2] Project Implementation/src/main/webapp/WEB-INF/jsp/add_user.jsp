<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Add User</title>
</head>
<body>

<nav class="navbar navbar-default">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li><a href="/">Home <span class="sr-only">(current)</span></a></li>
            <li><a href="#">Stok</a></li>
            <li><a href="#">Diskon</a></li>
            <li><a href="/view_user">Kasir</a></li>
        </ul>
    </div>
</nav>

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
                        <input type="password" name="password">
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