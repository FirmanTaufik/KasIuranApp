<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php";
    $id_pengeluaran = mysqli_real_escape_string($conn, $_POST['id_pengeluaran']);
    $jumlah_pengeluaran = mysqli_real_escape_string($conn, $_POST['jumlah_pengeluaran']);
    $keperluan = mysqli_real_escape_string($conn, $_POST['keperluan']);

    $q =  "UPDATE tb_pengeluaran
	SET 
		jumlah_pengeluaran='$jumlah_pengeluaran',
		keperluan='$keperluan'
	WHERE id_pengeluaran= '$id_pengeluaran'";

    $id = mysqli_query($conn, $q);

    if ($id) {
        $result["succes"] = "1";
        $result["message"] =  'success';
        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["succes"] = "0";
        $result["message"] = "failed";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
