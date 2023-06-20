<?php

if($_SERVER['REQUEST_METHOD']=='GET'){
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php"; 
    $npm = mysqli_real_escape_string($conn, $_GET['npm']); 
    $tanggal_lahir = mysqli_real_escape_string($conn, $_GET['tanggal_lahir']);  

    if ($npm!=$tanggal_lahir) {
        $result["succes"] = "0";
        $result["message"] = "Username atau Password Salah" ;

        echo json_encode($result);
        mysqli_close($conn);
        return;
    }

    $r = mysqli_num_rows(mysqli_query($conn, "SELECT*FROM tb_anggota WHERE    npm ='$npm' "));

    if ($r==1) {
        $id = mysqli_fetch_assoc(mysqli_query($conn,"SELECT*FROM tb_anggota WHERE    npm ='$npm' "));        
        $result["succes"] = "1";
        $result["message"] =  'success';    
        $result["id_anggota"]   = $id['id_anggota'];
        $result["level_anggota"]   = $id['level_anggota'];
        echo json_encode($result);
        mysqli_close($conn);

    } else {
        $result["succes"] = "0";
        $result["message"] = "Username atau Password Salah" ;

        echo json_encode($result);
        mysqli_close($conn);
    }

}
