<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php";
    $jumlah_pengeluaran = mysqli_real_escape_string($conn, $_POST['jumlah_pengeluaran']);
    $keperluan = mysqli_real_escape_string($conn, $_POST['keperluan']);

    $q =  "INSERT INTO tb_pengeluaran
	(id_pengeluaran, jumlah_pengeluaran, keperluan)
	VALUES (NULL, '$jumlah_pengeluaran', '$keperluan', NOW())";

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
