<div class="page-inner">
    <div class="page-header">
        <h4 class="page-title">Selamat Datang Kembali Admin</h4>
    </div>
    <div class="row">
        <div class="col-sm-6 col-md-3">
            <div class="card card-stats card-round">
                <div class="card-body ">
                    <div class="row align-items-center">
                        <div class="col-icon">
                            <div class="icon-big text-center icon-primary bubble-shadow-small">
                                <i class="fas fa-users"></i>
                            </div>
                        </div>
                        <div class="col col-stats ml-3 ml-sm-0">
                            <div class="numbers">
                                <p class="card-category">Anngota</p>
                                <h4 class="card-title"><?php echo data()->jumlah_anggota; ?></h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="card card-stats card-round">
                <div class="card-body">
                    <div class="row align-items-center">
                        <div class="col-icon">
                            <div class="icon-big text-center icon-info bubble-shadow-small">
                                <i class="far fa-newspaper"></i>
                            </div>
                        </div>
                        <div class="col col-stats ml-3 ml-sm-0">
                            <div class="numbers">
                                <p class="card-category">Total Kas</p>
                                <h4 class="card-title"><?php echo data()->totalKas; ?></h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="card card-stats card-round">
                <div class="card-body">
                    <div class="row align-items-center">
                        <div class="col-icon">
                            <div class="icon-big text-center icon-success bubble-shadow-small">
                                <i class="far fa-chart-bar"></i>
                            </div>
                        </div>
                        <div class="col col-stats ml-3 ml-sm-0">
                            <div class="numbers">
                                <p class="card-category">Total Tabungan</p>
                                <h4 class="card-title"><?php echo data()->totalTabungan; ?></h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="card card-stats card-round">
                <div class="card-body">
                    <div class="row align-items-center">
                        <div class="col-icon">
                            <div class="icon-big text-center icon-secondary bubble-shadow-small">
                                <i class="far fa-check-circle"></i>
                            </div>
                        </div>
                        <div class="col col-stats ml-3 ml-sm-0">
                            <div class="numbers">
                                <p class="card-category">Total Pengeluaran</p>
                                <h4 class="card-title"><?php echo data()->totalPengeluaran; ?></h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<?php

function data()
{
    include "api/+connection.php";
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

    $q4 = mysqli_num_rows(mysqli_query($conn, "SELECT  *from tb_anggota where level_anggota =2 ")); 

    $object = new  stdClass();
    $object->totalKas = rupiah($jumlah_kas-  $jumlah_pengeluaran);
    $object->totalTabungan =rupiah($jumlah_tabungan- $jumlah_penarikan) ;
    $object->totalPengeluaran = rupiah($jumlah_pengeluaran) ;
    $object->jumlah_anggota = $q4;

    return $object;
}


?>