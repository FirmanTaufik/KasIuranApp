<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    include "+connection.php";
    header('Content-Type: application/json; charset=utf-8');
    $Tanggal1 = mysqli_real_escape_string($conn, $_GET['Tanggal1']);
    $Tanggal2 = mysqli_real_escape_string($conn, $_GET['Tanggal2']);


    $q1 = "SELECT tb_transaksi.*, tb_anggota.nama_anggota
    FROM tb_transaksi
    LEFT JOIN   tb_anggota ON tb_anggota.id_anggota = tb_transaksi.id_anggota
    where (tb_transaksi.tanggal_transaksi BETWEEN '$Tanggal1' AND '$Tanggal2')  
    GROUP BY tb_transaksi.id_transaksi";

    if (isset($_GET['id'])) {

        $id = mysqli_real_escape_string($conn, $_GET['id']);
        $q1 = "SELECT tb_transaksi.*, tb_anggota.nama_anggota
    FROM tb_transaksi
    LEFT JOIN   tb_anggota ON tb_anggota.id_anggota = tb_transaksi.id_anggota
    where (tb_transaksi.tanggal_transaksi BETWEEN '$Tanggal1' AND '$Tanggal2') AND tb_transaksi.id_anggota = '$id' 
    GROUP BY tb_transaksi.id_transaksi";
    }

    $q2  = "SELECT tb_penarikan.*, tb_anggota.nama_anggota
    FROM tb_penarikan
    LEFT JOIN   tb_anggota ON tb_anggota.id_anggota = tb_penarikan.id_anggota
    where (tb_penarikan.tanggal_penarikan BETWEEN '$Tanggal1' AND '$Tanggal2')  
    GROUP BY tb_penarikan.id_penarikan";

    if (isset($_GET['id'])) {

        $id = mysqli_real_escape_string($conn, $_GET['id']);
        $q2  = "SELECT tb_penarikan.*, tb_anggota.nama_anggota
    FROM tb_penarikan
    LEFT JOIN   tb_anggota ON tb_anggota.id_anggota = tb_penarikan.id_anggota
    where (tb_penarikan.tanggal_penarikan BETWEEN '$Tanggal1' AND '$Tanggal2') AND tb_penarikan.id_anggota = '$id' 
    GROUP BY tb_penarikan.id_penarikan";
    }

    $q3  = "SELECT *	FROM tb_pengeluaran   where (tanggal_pengeluaran BETWEEN '$Tanggal1' AND '$Tanggal2')  ";



    $result =   array(
        'Transaksi' => FunctionName($q1),
        'Penarikan' =>  FunctionName($q2),
        'Pengeluaran' =>  FunctionName($q3),
    );

    echo json_encode(array($result));
}

function FunctionName($q)
{
    include "+connection.php";
    $DataAnggota = array();
    $id = mysqli_query($conn, $q);
    while ($row = mysqli_fetch_object($id)) {
        $DataAnggota[] = $row;
    }
    return $DataAnggota;
}
