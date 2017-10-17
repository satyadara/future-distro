<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <table class="table table-bordered">
        <thead>
        <td>ID_ITEM</td>
        <td>ID_EMP</td>
        <td>Nama</td>
        <td>Harga</td>
        <td>Warna</td>
        <td>Ukuran</td>
        <td>Jenis</td>
        <td>Status</td>
        </thead>
        <c:forEach items="${items}" var="item">
        <tr>
            <td>${item.getId_item()}</td>
            <td>${item.getId_emp()}</td>
            <td>${item.getName_item()}</td>
            <td>${item.getPrice()}</td>
            <td>${item.getColor()}</td>
            <td>${item.getSize()}</td>
            <td>${item.getType()}</td>
            <td>${item.getStatus()}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>