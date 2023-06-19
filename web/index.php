<?php

include "api/+connection.php";
?>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>Aplikasi Tabungan Mahasiswa</title>
	<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
	<link rel="icon" href="assets/img/icon.png" type="image/x-icon" />

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

	<!-- CSS Just for demo purpose, don't include it in your project -->
	<link rel="stylesheet" href="assets/css/demo.css">
</head>

<body>
	<div class="wrapper">
		<!--
				Tip 1: You can change the background color of the main header using: data-background-color="blue | purple | light-blue | green | orange | red"
		-->
		<div class="main-header" style="background-color: #E91E63;">
			<!-- Logo Header -->
			<div class="logo-header">

				<a href="index.html" class="logo">
					<img width="40" src="assets/img/icon.png" alt="navbar brand" class="navbar-brand">
				</a>
				<button class="navbar-toggler sidenav-toggler ml-auto" type="button" data-toggle="collapse" data-target="collapse" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon">
						<i class="fa fa-bars" style="color:white"></i>
					</span>
				</button>
				<button class="topbar-toggler more"><i class="fa fa-ellipsis-v" style="color:white"></i></button>
				<div class="navbar-minimize">
					<button class="btn btn-minimize btn-rounded">
						<i class="fa fa-bars" style="color:white"></i>
					</button>
				</div>
			</div>
			<!-- End Logo Header -->

			<!-- Navbar Header -->
			<nav class="navbar navbar-header navbar-expand-lg">

				<div class="container-fluid">
					<div class="collapse" id="search-nav">
						<form class="navbar-left navbar-form nav-search mr-md-3">
							<div class="input-group">
								<div class="input-group-prepend">
									<button type="submit" class="btn btn-search pr-1">
										<i class="fa fa-search search-icon"></i>
									</button>
								</div>
								<input type="text" placeholder="Search ..." class="form-control">
							</div>
						</form>
					</div>
					<ul class="navbar-nav topbar-nav ml-md-auto align-items-center">
						<li class="nav-item toggle-nav-search hidden-caret">
							<a class="nav-link" data-toggle="collapse" href="#search-nav" role="button" aria-expanded="false" aria-controls="search-nav">
								<i class="fa fa-search"></i>
							</a>
						</li>
						<li class="nav-item dropdown hidden-caret">
							<a class="nav-link dropdown-toggle" href="#" id="messageDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<i class="fas fa-sign-in-alt" style="color:white"></i>
							</a>
					</ul>
				</div>
			</nav>
			<!-- End Navbar -->
		</div>

		<!-- Sidebar -->
		<div class="sidebar">

			<div class="sidebar-background"></div>
			<div class="sidebar-wrapper scrollbar-inner">
				<div class="sidebar-content">
					<div class="user">
						<div class="avatar-sm float-left mr-2">
							<img src="assets/img/user.png" alt="..." class="avatar-img rounded-circle">
						</div>
						<div class="info">
							<a>
								<span>
									Hizrian
									<span class="user-level">Administrator</span>
								</span>
							</a>

						</div>
					</div>
					<ul class="nav">
						<li class="nav-item <?php if (isset($_GET['page']) && $_GET['page'] == 'dashboard') {
												echo "active";
											} ?>">
							<a href="?page=dashboard">
								<i class="fas fa-home"></i>
								<p>Dashboard</p>
							</a>
						</li>
						<li class="nav-section">
							<span class="sidebar-mini-icon">
								<i class="fa fa-ellipsis-h"></i>
							</span>
							<h4 class="text-section">Menu</h4>
						</li>
						<li class="nav-item <?php if (isset($_GET['page']) && $_GET['page'] == 'anggota') {
												echo "active";
											} ?>">
							<a href="?page=anggota">
								<i class="fas fa-users"></i>
								<p>Data Anggota</p>
							</a>
						</li>
						<li class="nav-item <?php if (isset($_GET['page']) && $_GET['page'] == 'penarikan') {
												echo "active";
											} ?>">
							<a href="?page=penarikan">
								<i class="fas fa-money-bill-wave"></i>
								<p>Data Penarikan</p>
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- End Sidebar -->

		<div class="main-panel">
			<div class="content">
				<!-- <div class="page-inner">
					<div class="page-header">
						<h4 class="page-title">Starter Template</h4>
					</div>
					<div class="page-category">Starter Description</div>
				</div> -->

				<?php
				if (!isset($_GET['page'])) include "pages/dashboard.php";
				else {
					switch ($_GET['page']) {
						case '':
						case 'dashboard':
							include "pages/dashboard.php";
							break;
						case 'anggota':
							include "pages/anggota.php";
							break;
						case 'penarikan':
							include "pages/penarikan.php";
							break;
					}
				}

				?>
			</div>

		</div>

	</div>
	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery.3.2.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>

	<!-- jQuery UI -->
	<script src="assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
	<script src="assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>

	<!-- jQuery Scrollbar -->
	<script src="assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>

	<!-- Moment JS -->
	<script src="assets/js/plugin/moment/moment.min.js"></script>

	<!-- Chart JS -->
	<script src="assets/js/plugin/chart.js/chart.min.js"></script>

	<!-- jQuery Sparkline -->
	<script src="assets/js/plugin/jquery.sparkline/jquery.sparkline.min.js"></script>

	<!-- Chart Circle -->
	<script src="assets/js/plugin/chart-circle/circles.min.js"></script>

	<!-- Datatables -->
	<script src="assets/js/plugin/datatables/datatables.min.js"></script>

	<!-- Bootstrap Notify -->
	<script src="assets/js/plugin/bootstrap-notify/bootstrap-notify.min.js"></script>

	<!-- Bootstrap Toggle -->
	<script src="assets/js/plugin/bootstrap-toggle/bootstrap-toggle.min.js"></script>

	<!-- jQuery Vector Maps -->
	<script src="assets/js/plugin/jqvmap/jquery.vmap.min.js"></script>
	<script src="assets/js/plugin/jqvmap/maps/jquery.vmap.world.js"></script>

	<!-- Google Maps Plugin -->
	<script src="assets/js/plugin/gmaps/gmaps.js"></script>

	<!-- Sweet Alert -->
	<script src="assets/js/plugin/sweetalert/sweetalert.min.js"></script>

	<!-- Azzara JS -->
	<script src="assets/js/ready.min.js"></script>


	<script src="https://cdn.datatables.net/buttons/2.2.2/js/dataTables.buttons.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
	<script src="https://cdn.datatables.net/buttons/2.2.2/js/buttons.print.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
	<script src="https://cdn.datatables.net/buttons/2.2.2/js/buttons.html5.min.js"></script>
	<script>
		$(window).on('load', function() {

		});
		$(document).ready(function() {
			$('#basic-datatables').DataTable({
				'lengthChange': true,
				dom: 'Blfrtip',
				buttons: [{
						extend: 'pdf',
						title: '',
						messageTop: ''
					},
					{
						extend: 'csv',
						title: '',
						messageTop: ''
					},
					{
						extend: 'excel',
						title: '',
						messageTop: ''
					},
					{
						extend: 'print',
						title: '',
						messageTop: ''
					},
				],
				"lengthMenu": [
					[10, 15, 25, 35, 50, 100, -1],
					[10, 15, 25, 35, 50, 100, "All"]
				]
			});

			$('.buttons-pdf,.buttons-csv,.buttons-excel, .buttons-print').each(function() {
				$(this).removeClass('btn-default').addClass('btn-sm btn btn-secondary')
			})

			$(".dt-buttons").addClass('row').addClass('mb-2');

			$('.buttons-pdf,.buttons-csv,.buttons-excel, .buttons-print').each(function() {
				$(this).addClass('col-1 ml-2')
			})
			$('#multi-filter-select').DataTable({
				"pageLength": 5,
				initComplete: function() {
					this.api().columns().every(function() {
						var column = this;
						var select = $('<select class="form-control"><option value=""></option></select>')
							.appendTo($(column.footer()).empty())
							.on('change', function() {
								var val = $.fn.dataTable.util.escapeRegex(
									$(this).val()
								);

								column
									.search(val ? '^' + val + '$' : '', true, false)
									.draw();
							});

						column.data().unique().sort().each(function(d, j) {
							select.append('<option value="' + d + '">' + d + '</option>')
						});
					});
				}
			});

			// Add Row
			$('#add-row').DataTable({
				"pageLength": 5,
			});

			var action = '<td> <div class="form-button-action"> <button type="button" data-toggle="tooltip" title="" class="btn btn-link btn-primary btn-lg" data-original-title="Edit Task"> <i class="fa fa-edit"></i> </button> <button type="button" data-toggle="tooltip" title="" class="btn btn-link btn-danger" data-original-title="Remove"> <i class="fa fa-times"></i> </button> </div> </td>';

			$('#addRowButton').click(function() {
				$('#add-row').dataTable().fnAddData([
					$("#addName").val(),
					$("#addPosition").val(),
					$("#addOffice").val(),
					action
				]);
				$('#addRowModal').modal('hide');

			});
		});
	</script>
</body>

</html>