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
                    <form class="form" method="POST">
                        <div class="form-group col-md-4">
                            <input type="hidden" name="id_item" value="${item.getId_item()}">
                            <input type="hidden" name="id_emp" value="${item.getId_emp()}">
                            <label>Tipe Barang</label>
                            <select class="form-control" name="type" value="${item.getType()}">
                                <%--<c:choose>--%>
                                <%--<c:when test="${item.getType() == 'Pakaian'}">--%>
                                <%--<option selected>Pakaian</option>--%>
                                <%--<option>Celana</option>--%>
                                <%--</c:when>--%>
                                <%--<c:when test="${item.getType() == 'Celana'}">--%>
                                <%--<option>Pakaian</option>--%>
                                <%--<option selected>Celana</option>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                <%--<option>Pakaian</option>--%>
                                <%--<option>Celana</option>--%>
                                <%--</c:otherwise>--%>
                                <%--</c:choose>--%>
                                <c:forEach items="${types}" var="type">
                                    <c:choose>
                                        <c:when test="${item.getType() == type.getIdItem_Type()}">
                                            <option value="${type.getIdItem_Type()}" selected>${type.getNameItem_Type()}
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${type.getIdItem_Type()}">${type.getNameItem_Type()}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label>Merek</label>
                            <select class="form-control" name="merk">
                                <c:forEach items="${merks}" var="merk">
                                    <c:choose>
                                        <c:when test="${item.getMerk() == merk.getIdItem_Merk()}">
                                            <option value="${merk.getIdItem_Merk()}"
                                                    selected>${merk.getNameItem_Merk()}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${merk.getIdItem_Merk()}">${merk.getNameItem_Merk()}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group col-md-4">
                            <label>Ukuran</label>
                            <select class="form-control" name="size">
                                <c:choose>
                                    <c:when test="${item.getSize() == 'S'}">
                                        <option selected>S</option>
                                        <option>M</option>
                                        <option>L</option>
                                        <option>XL</option>
                                    </c:when>
                                    <c:when test="${item.getSize() == 'M'}">
                                        <option>S</option>
                                        <option selected>M</option>
                                        <option>L</option>
                                        <option>XL</option>
                                    </c:when>
                                    <c:when test="${item.getSize() == 'L'}">
                                        <option>S</option>
                                        <option>M</option>
                                        <option selected>L</option>
                                        <option>XL</option>
                                    </c:when>
                                    <c:when test="${item.getSize() == 'XL'}">
                                        <option>S</option>
                                        <option>M</option>
                                        <option>L</option>
                                        <option selected>XL</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option>S</option>
                                        <option>M</option>
                                        <option>L</option>
                                        <option>XL</option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                        <div class=" form-group col-md-12">
                            <label>Nama Barang</label>
                            <input required type="text" name="name_item" class="form-control"
                                   value="${item.getName_item()}">
                        </div>
                        <div class="form-group col-md-4">
                            <label>Warna</label>
                            <select class="form-control" name="color">
                                <c:forEach items="${colors}" var="color">
                                    <c:choose>
                                        <c:when test="${item.getColor() == color.getIdItem_Color()}">
                                            <option value="${color.getIdItem_Color()}"
                                                    selected>${color.getNameItem_Color()}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${color.getIdItem_Color()}">${color.getNameItem_Color()}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group col-md-4">
                            <label>Harga</label>
                            <input type="number" name="price" class="form-control" value="${item.getPrice()}">
                        </div>
                        <div class="form-group col-md-4">
                            <label>Kuantitas</label>
                            <input type="number" name="stock" class="form-control" value="${item.getStock()}">
                        </div>
                        <div class="col-md-12">
                            <button type="submit" class="btn btn-default" style="float: right;">Simpan</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>