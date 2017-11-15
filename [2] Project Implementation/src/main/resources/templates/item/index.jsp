<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manager - Stok</title>
    <%--bootstrap.min.css--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/creation.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>Stok Barang( ${count} )</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <a href="/item/create">
                <button class="btn btn-default" style="float: right;">+Tambah Stok</button>
            </a>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-bordered table-responsive" id="mTable">
                <tr>
                    <th>ID</th>
                    <th>Merek</th>
                    <th>Tipe</th>
                    <th>Ukuran</th>
                    <th>Nama</th>
                    <th>Warna</th>
                    <th>Harga</th>
                    <th>Kuantitas</th>
                    <th>Status</th>
                    <th>Pilihan</th>
                </tr>

                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.getId_item()}</td>
                        <td>${item.getMerk()}</td>
                        <td>${item.getType()}</td>
                        <td>${item.getSize()}</td>
                        <td>${item.getName_item()}</td>
                        <td>${item.getColor()}</td>
                        <td>${item.getPrice()}</td>
                        <td>${item.getStock()}</td>
                        <td>${item.getStatus()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/item/${item.getId_item()}/edit">
                                Edit
                            </a>
                            |
                            <a href="${pageContext.request.contextPath}/item/${item.getId_item()}/delete">
                                Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li>
                        <a href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <c:forEach begin="1" end="${pages}" var="page">
                        <c:choose>
                            <c:when test="${page == currentPage}">
                                <li class="active"><a href="/item/page/${page}">${page}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="/item/page/${page}">${page}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <li>
                        <a href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>
<%--bootstrap.js--%>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>