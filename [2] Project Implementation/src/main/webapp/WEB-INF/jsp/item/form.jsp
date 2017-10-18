<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manager - Stok Barang</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/creation.css">
</head>
<body>
<div class="container">
    <div class="row">
        <h1>Tambah Stok Barang</h1>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <form class="form" method="post">
                        <div class="form-group col-md-4">
                            <label>Tipe Barang</label>
                            <select class="form-control" name="type">
                                <option>Pakaian</option>
                                <option>Celana</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <!-- Kosongan  -->
                        </div>
                        <div class="form-group col-md-4">
                            <label>Ukuran</label>
                            <select class="form-control" name="size">
                                <option>S</option>
                                <option>M</option>
                                <option>L</option>
                                <option>XL</option>
                            </select>
                        </div>
                        <div class=" form-group col-md-12">
                            <label>Nama Barang</label>
                            <input type="text" name="name_item" class="form-control">
                        </div>
                        <div class="form-group col-md-4">
                            <label>Warna</label>
                            <select class="form-control" name="color">
                                <option>Merah</option>
                                <option>Kuning</option>
                                <option>Hijau</option>
                            </select>
                        </div>
                        <div class="form-group col-md-4">
                            <label>Harga</label>
                            <input type="number" name="price" class="form-control">
                        </div>
                        <div class="form-group col-md-4">
                            <label>Kuantitas</label>
                            <input type="number" name="stock" class="form-control">
                        </div>
                        <div class="col-md-12">
                            <button class="btn btn-default" style="float: right;">Tambah Stok</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>