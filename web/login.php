<?php
ob_start();
session_start();
include "api/+connection.php";
?>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>Aplikasi Tabungan Mahasiswa | Login</title>
	<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
	<link rel="icon" href="assets/img/icon.ico" type="image/x-icon" />

	<!-- Fonts and icons -->
	<script src="assets/js/plugin/webfont/webfont.min.js"></script>
	<script>
		WebFont.load({
			google: {
				"families": ["Open+Sans:300,400,600,700"]
			},
			custom: {
				"families": ["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands"],
				urls: ['assets/css/fonts.css']
			},
			active: function() {
				sessionStorage.fonts = true;
			}
		});
	</script>

	<!-- CSS Files -->
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/azzara.min.css">
</head>

<body class="login">
	<div class="wrapper wrapper-login">
		<div class="container container-login animated fadeIn">
			<center> <img width="100" src="assets/img/icon.png" alt=""> </center>
			<br>
			<h3 class="text-center">Login Admin Ke Aplikasi Tabungan Mahasiswa</h3>
			<form action="" method="post">

				<div class="login-form">
					<div class="form-group form-floating-label">
						<input id="username" name="username" type="text" class="form-control input-border-bottom" required>
						<label for="username" class="placeholder">Username</label>
					</div>
					<div class="form-group form-floating-label">
						<input id="password" name="password" type="password" class="form-control input-border-bottom" required>
						<label for="password" class="placeholder">Password</label>
						<div class="show-password">
							<i class="flaticon-interface"></i>
						</div>
					</div>
					<div class="form-action mb-3">
						<input name="login" type="submit" value="Login" class=" btn btn-primary btn-rounded btn-login">
					</div>
				</div>
			</form>

			<?php
			if (isset($_POST['login'])) {
				$username = mysqli_real_escape_string($conn, $_POST['username']);
				$password = md5(mysqli_real_escape_string($conn, $_POST['password']));


				$record = mysqli_num_rows(mysqli_query($conn, "SELECT*FROM tb_anggota WHERE username = '$username' AND password = '$password'"));

				if ($record == 1) {
					$result = mysqli_query($conn, "SELECT*FROM tb_anggota WHERE username = '$username' AND password = '$password'");
					while ($row = mysqli_fetch_array($result)) {
						$_SESSION['username'] = $row['username'];
						$_SESSION['nama_anggota'] = $row['nama_anggota'];
					}

					header("location:/AplikasiTabunganMahasiswa/?page=dashboard");
					// echo "<script>
					// alert('Succes to Login');
					// window.location.href='https://romakhan.xyz/';
					// </script>";
				} else {
					echo "<script>
                                    alert('Failed to Login');
                                    window.location.href='login.php?status=failed';
                                    </script>";
					// header('location:login.php?status=failed');
				}
			}
			?>
		</div>

	</div>
	<script src="assets/js/core/jquery.3.2.1.min.js"></script>
	<script src="assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<script src="assets/js/ready.js"></script>
</body>

</html>