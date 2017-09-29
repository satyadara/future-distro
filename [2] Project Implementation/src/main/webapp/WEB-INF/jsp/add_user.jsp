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
                        Nama Lengkap
                    </td>
                    <td>
                        <input type="text" name="namaLengkap">
                    </td>
                </tr>
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
                        Alamat
                    </td>
                    <td>
                        <input type="text" name="alamat">
                    </td>
                </tr>
                <tr>
                    <td>
                        No KTP
                    </td>
                    <td>
                        <input type="number" name="noktp">
                    </td>
                </tr>
                <tr>
                    <td>
                        No HP
                    </td>
                    <td>
                        <input type="number" name="nohp">
                    </td>
                </tr>
                <tr>
                    <td>
                        Jenis Kelamin
                    </td>
                    <td>
                        <input type="radio" name="jenisKelamin" value="L"> Laki-laki
                        <input type="radio" name="jenisKelamin" value="P"> Perempuan
                    </td>
                </tr>
                <tr>
                    <td>
                        ROLE
                    </td>
                    <td>
                        <<input type="radio" name="role" value="admin"> Admin
                        <input type="radio" name="role" value="cashier"> Cashier
                    </td>
                </tr>
            </table>
            <input type="submit" value="Add">
        </form>
    </div>
</div>
</body>
</html>