<?php

if($_SERVER['REQUEST_METHOD']=='GET'){
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php"; 
    $w = mysqli_query($conn, "SELECT *from tb_anggota where level_anggota =2");

    $data = array();
    while ($row = mysqli_fetch_object($w)) {  

     

       $data[] = $row;
    }   

    echo json_encode($data);

}


?>