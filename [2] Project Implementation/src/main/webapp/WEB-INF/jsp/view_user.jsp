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

<nav class="navbar navbar-default">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                data-target="#bs-example-navbar-collapse-1">
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
    <div class="row">
        <div class="col-md-12">
            <h1>Data Pengguna</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <a href="/add_user">
                <button type="button" class="btn btn-default" style="float: right">Tambah Kasir</button>
            </a>
        </div>
    </div>

    <br>

    <div class="row">
        <div class="table-responsive col-md-12">

            <table id="usertable" class="table table-bordered table-striped">

                <thead>

                <!--<th><input type="checkbox" id="checkall" /></th>-->
                <th>Nama Lengkap</th>
                <th>Username</th>
                <th>Alamat</th>
                <th>No KTP</th>
                <th>No Telp</th>
                <th>Jenis Kelamin</th>
                <th>Role</th>
                <th>Edit</th>

                <th>Delete</th>
                </thead>

                <tbody>

                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td>${user.getNamaLengkap()}</td>
                        <td>${user.getUsername()}</td>
                        <td>${user.getAlamat()}</td>
                        <td>${user.getKtp()}</td>
                        <td>${user.getTelp()}</td>
                        <td>${user.getJenisKelamin()}</td>
                        <td>${user.getRole()}</td>
                        <td><a href="edit_user/${user.getUsername()}">edit</a></td>
                        <td><a href="delete_user/${user.getUsername()}">delete</a></td>
                    </tr>
                </c:forEach>

                </tbody>

            </table>
        </div>
    </div>
</div>
</body>
</html>