<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Manager - Tambah Diskon</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
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
						<form class="form" >
							<div class="form-group col-md-12">
								<label>Nama Diskon</label>
								<input type="text" name="namaDiskon" class="form-control">
							</div>
							<div class="form-group col-md-4">
								<label>Presentase</label>
								<input type="number" name="presentase" class="form-control">
							</div>
							<div class="form-group col-md-4">
								<label>Tanggal Mulai</label>
								<input type="text" name="tgl_mulai" class="form-control">
							</div>
							<div class="form-group col-md-4">
								<label>Tanggal Selesai</label>
								<input type="text" name="tgl_selesai" class="form-control">
							</div>
							<div class="form-group col-md-12">
								<label>Deskripsi</label>
								<input type="text" name="deskripsi" class="form-control">
							</div>
							<div class="form-group col-md-4">
								<label>Status</label>
								<select class="form-control" name="warna">
									<option>Running</option>
									<option>Stopped</option>
									<option>Delayed</option>
								</select>
							</div>
							<div class="col-md-12">
								<button class="btn btn-default" style="float: right;">Tambah Diskon</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
