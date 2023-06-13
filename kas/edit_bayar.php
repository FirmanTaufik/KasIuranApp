<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php";
    $id_transaksi = mysqli_real_escape_string($conn, $_POST['id_transaksi']);
    $jumlah_kas = mysqli_real_escape_string($conn, $_POST['jumlah_kas']); 
    $jumlah_tabungan = mysqli_real_escape_string($conn, $_POST['jumlah_tabungan']); 
    $jumlah_transaksi = mysqli_real_escape_string($conn, $_POST['jumlah_transaksi']); 

    $q =  "UPDATE tb_transaksi
	SET  
		jumlah_kas='$jumlah_kas',
		jumlah_tabungan='$jumlah_tabungan',
		jumlah_transaksi='$jumlah_transaksi'
	WHERE id_transaksi='$id_transaksi'";

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
