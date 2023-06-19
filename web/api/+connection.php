<?php
 
$host ="localhost";
$user = "root";
$pass = "";
$database ="db_kas"; 

$conn = mysqli_connect($host, $user, $pass, $database);

if(!$conn) {
    echo "tidak connect";
}

?>