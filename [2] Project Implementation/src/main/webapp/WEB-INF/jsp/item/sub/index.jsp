<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manager - Stok</title>
    <%--bootstrap.min.css--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/creation.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>Tipe Barang( ${count} )</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <a href="${pageContext.request.contextPath}/item/${content}/create">
                <button class="btn btn-default" style="float: right;">+Tambah Tipe</button>
            </a>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-6">
            <table class="table table-striped table-bordered table-responsive" id="mTable">
                <tr>
                    <th>ID Tipe</th>
                    <th>Nama Tipe</th>
                    <th>Pilihan</th>
                </tr>

                <c:forEach items="${datas}" var="data">
                    <tr>
                        <td>${data.getId()}</td>
                        <td>${data.getName()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/item/${content}/${data.getId()}/edit">
                                Edit
                            </a>
                            |
                            <a href="${pageContext.request.contextPath}/item/${content}/${data.getId()}/delete">
                                Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<%--bootstrap.js--%>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>