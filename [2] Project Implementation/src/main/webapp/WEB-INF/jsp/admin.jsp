<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
    <title>Admin Page</title>
</head>
<body>

<nav class="navbar navbar-default">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
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
                <h1>Hi, Raditia.</h1>
                <h4>Welcome to POS dashboard.</h4>
                <br>
                <br>
                <div class="col-md-3 text-center">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h2>Rp. 76.127.009</h2>
                            <p>Net Sales</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 text-center">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h2>Rp. 76.127.009</h2>
                            <p>Gross Sales</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 text-center">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h2>332</h2>
                            <p>Number of transaction</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 text-center">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h2>3</h2>
                            <p>Items running of stock</p>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 text-center">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            Hehehehe
                        </div>
                    </div>
                </div>

                <div class="col-md-3 text-center">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            Hehehehe
                        </div>
                    </div>
                </div>

                <div class="col-md-3 text-center">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            Hehehehe
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <a href="/">Go to home page</a>
                    <form:form name="logout"  action="/logout" method="POST">
                        <input type="submit" class="btn btn-danger" value="sign out" />
                    </form:form>
                </div>

            </div>
        </div>
    </div>


</body>
</html>