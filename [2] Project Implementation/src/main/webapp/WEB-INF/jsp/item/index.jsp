<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manager - Stok</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/creation.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>Stok Barang(${count})</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <a href="${pageContext.request.contextPath}/item/create">
                <button class="btn btn-default" style="float: right;">+Tambah Stok</button>
            </a>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-bordered table-responsive">
                <tr>
                    <th>ID</th>
                    <th>Tipe Barang</th>
                    <th>Ukuran</th>
                    <th>Nama Barang</th>
                    <th>Warna</th>
                    <th>Harga</th>
                    <th>Kuantitas</th>
                    <th>Pilihan</th>
                </tr>

                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.getId_item()}</td>
                        <td>${item.getType()}</td>
                        <td>${item.getSize()}</td>
                        <td>${item.getName_item()}</td>
                        <td>${item.getColor()}</td>
                        <td>${item.getPrice()}</td>
                        <td>${item.getStock()}</td>
                        <td>
                            <a href="/item/${item.getId_item()}/edit">
                                Edit
                            </a>
                            |
                            <a href="/item/${item.getId_item()}/delete">
                                Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</div>
</body>
</html>