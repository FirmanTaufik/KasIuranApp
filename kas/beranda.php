<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    include "+connection.php";
    header('Content-Type: application/json; charset=utf-8');

    $q1 = mysqli_fetch_assoc(mysqli_query($conn, "SELECT  ifnull(SUM(jumlah_kas),0)   jumlah_kas, 
    ifnull(SUM(jumlah_tabungan),0)  jumlah_tabungan
    FROM tb_transaksi"));
    $jumlah_kas =  $q1['jumlah_kas'];
    $jumlah_tabungan =  $q1['jumlah_tabungan'];

    $q2 = mysqli_fetch_assoc(mysqli_query($conn, "SELECT ifnull(SUM(jumlah_pengeluaran),0) jumlah_pengeluaran
FROM tb_pengeluaran"));

    $jumlah_pengeluaran = $q2['jumlah_pengeluaran'];

    $q3 = mysqli_fetch_assoc(mysqli_query($conn, "SELECT ifnull(SUM(jumlah_penarikan),0) jumlah_penarikan
    FROM tb_penarikan"));
 $jumlah_penarikan = $q3['jumlah_penarikan'];


    $object = new  stdClass();
    $object->totalKas = $jumlah_kas-  $jumlah_pengeluaran;
    $object->totalTabungan = $jumlah_tabungan- $jumlah_penarikan;
    $object->totalPengeluaran = $jumlah_pengeluaran;
    echo json_encode($object);
}

 
