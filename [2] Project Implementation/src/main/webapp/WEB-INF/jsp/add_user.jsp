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
        <form class="form-horizontal" action="/add_user" method="post">
            <fieldset>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="namaLengkap">Nama Lengkap</label>
                    <div class="col-md-4">
                        <input id="namaLengkap" type="text" name="namaLengkap" placeholder="Nama Lengkap">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="username">Username</label>
                    <div class="col-md-4">
                        <input id="username" type="text" name="username" placeholder="Username">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="password">Password</label>
                    <div class="col-md-4">
                        <input id="password" type="password" name="password" placeholder="Password">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="alamat">Alamat</label>
                    <div class="col-md-4">
                        <input id="alamat" type="text" name="alamat" placeholder="Alamat">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="ktp">No KTP</label>
                    <div class="col-md-4">
                        <input id="ktp" type="text" name="ktp" placeholder="No KTP">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="telp">No Telp</label>
                    <div class="col-md-4">
                        <input id="telp" type="text" name="telp" placeholder="No Telp">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="jeniskelamin">Jenis Kelamin</label>
                    <div class="col-md-4">
                        <select id="jeniskelamin" name="jenisKelamin" class="form-control">
                            <option value="L">Laki-laki</option>
                            <option value="P">Perempuan</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="role">Role</label>
                    <div class="col-md-4">
                        <select id="role" name="role" class="form-control">
                            <option value="ROLE_ADMIN">Admin</option>
                            <option value="ROLE_USER">Kasir</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="submit"></label>
                    <div class="col-md-4">
                        <button id="submit" name="submit" class="btn btn-default">Tambah</button>
                    </div>
                </div>

    </fieldset>
    </form>
    </div>
</div>
</body>
</html>