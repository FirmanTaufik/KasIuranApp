<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    include "+connection.php";
    header('Content-Type: application/json; charset=utf-8');
    $id_anggota = mysqli_real_escape_string($conn, $_GET['IdAnggota']);

    $q1 = "SELECT tb_anggota.*, t1.tabungan
    FROM tb_anggota
    LEFT JOIN (SELECT id_anggota , SUM(jumlah_tabungan) AS tabungan  from tb_transaksi WHERE  id_anggota = ' $id_anggota')
    AS t1 ON t1.id_anggota = tb_anggota.id_anggota
     WHERE tb_anggota.id_anggota = '$id_anggota'";

    $q2 = "SELECT *	FROM tb_transaksi WHERE id_anggota='$id_anggota'";
    $q3 = "SELECT *	FROM tb_penarikan WHERE id_anggota= '$id_anggota'";

    $result =   array(
        'DataAnggota' => FunctionName($q1),
        'DataTransaksi' =>  FunctionName($q2),
        'DataPenarikan' =>  FunctionName($q3),
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
    return $DataAnggota ;
}
