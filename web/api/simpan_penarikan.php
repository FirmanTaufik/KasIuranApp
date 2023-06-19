<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php";
    $id_anggota = mysqli_real_escape_string($conn, $_POST['id_anggota']);
    $jumlah_penarikan = mysqli_real_escape_string($conn, $_POST['jumlah_penarikan']);

    $q =  "INSERT INTO tb_penarikan
	(id_penarikan, jumlah_penarikan, id_anggota, tanggal_penarikan)
	VALUES (NULL,  '$jumlah_penarikan','$id_anggota', NOW())";

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
