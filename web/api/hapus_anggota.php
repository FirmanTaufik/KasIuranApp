<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php";
    $id_anggota = mysqli_real_escape_string($conn, $_POST['IdAnggota']); 

    $q =  "DELETE FROM tb_anggota WHERE id_anggota ='$id_anggota'";

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
