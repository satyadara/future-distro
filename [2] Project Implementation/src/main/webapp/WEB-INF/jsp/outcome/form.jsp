<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manager - Tambah Diskon</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/creation.css">
</head>
</head>
<body>
<div class="container">
    <div class="row">
        <h1>Stok Barang</h1>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <form class="form" method="post">
                        <input type="text" name="id_disc" value="${discount.getId_disc()}" hidden>
                        <input type="text" name="id_emp" value="${discount.getId_emp()}" hidden>
                        <div class="form-group col-md-12">
                            <label>Nama Diskon</label>
                            <input type="text" name="name" class="form-control" value="${discount.getName()}">
                        </div>
                        <div class="form-group col-md-4">
                            <label>Presentase</label>
                            <input type="number" name="percentage" class="form-control"
                                   value="${ discount.getPercentage()}">
                        </div>
                        <div class="form-group col-md-4">
                            <label>Tanggal Mulai</label>
                            <input type="date" name="start_date" class="form-control"
                                   value="${discount.getStart_date()}">
                        </div>
                        <div class="form-group col-md-4">
                            <label>Tanggal Selesai</label>
                            <input type="date" name="end_date" class="form-control" value="${discount.getEnd_date()}">
                        </div>
                        <div class="form-group col-md-12">
                            <label>Deskripsi</label>
                            <input type="text" name="desc" class="form-control" value="${discount.getDesc()}">
                        </div>
                        <div class="form-group col-md-4">
                            <label>Status</label>
                            <select class="form-control" name="status">
                                <c:choose>
                                    <c:when test="${discount.getStatus() == 'Aktif'}">
                                        <option selected>Aktif</option>
                                        <option>Tidak Aktif</option>
                                        <option>Menunggu</option>
                                    </c:when>
                                    <c:when test="${discount.getStatus() == 'Tidak Aktif'}">
                                        <option>Aktif</option>
                                        <option selected>Tidak Aktif</option>
                                        <option>Menunggu</option>
                                    </c:when>
                                    <c:when test="${discount.getStatus() == 'Menunggu'}">
                                        <option>Aktif</option>
                                        <option>Tidak Aktif</option>
                                        <option selected>Menunggu</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option>Aktif</option>
                                        <option>Tidak Aktif</option>
                                        <option>Menunggu</option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                        <div class="col-md-12">
                            <button type="submit" class="btn btn-default" style="float: right;">Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
