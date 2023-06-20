<?php

$w = mysqli_query($conn, "SELECT *from tb_anggota where level_anggota =2");

$data = array();
while ($row = mysqli_fetch_object($w)) {
    $data[] = $row;
}

?>
<div class="page-inner">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <!-- <h4 class="card-title">Basic</h4> -->
                    <div class="row">
                        <div class="col-4" style="margin-top: 20px;">
                            <h4 class="page-title">Data Transaksi</h4>
                        </div>
                        <div class="col-8">
                            <form action="" method="get">
                                <input type="hidden" name="page" value="transaksi">
                                <div class="row  ">
                                    <div class="col-3">
                                        <div class="form-group">
                                            <label for="exampleFormControlSelect1">Pilih Anggota</label>
                                            <select required name="id_anggota" class="form-control" id="exampleFormControlSelect1">
                                                <option value="0">Semua</option>
                                                <?php
                                                foreach ($data as  $value) { ?>
                                                    <option <?php if (isset($_GET['id_anggota']))   if ($_GET['id_anggota'] == $value->id_anggota) {
                                                                echo "selected";
                                                            }   ?> value="<?php echo $value->id_anggota; ?>"><?php echo $value->nama_anggota; ?></option>
                                                <?php     }
                                                ?>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <div class="form-group">
                                            <label for="exampleFormControlSelect1">Tanggal Dari</label>
                                            <input value="<?php if (isset($_GET['tanggal_dari'])) echo $_GET['tanggal_dari'];   ?>" required type="date" name="tanggal_dari" class="form-control" id="">
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <div class="form-group">
                                            <label for="exampleFormControlSelect1">Tanggal Hingga</label>
                                            <input value="<?php if (isset($_GET['tanggal_hingga'])) echo $_GET['tanggal_dari'];   ?>" required type="date" name="tanggal_hingga" class="form-control" id="">
                                        </div>
                                    </div>
                                    <div class="mt-10 col-1" style="margin-top: 10px;">
                                        <label for="exampleFormControlSelect1"> </label>
                                        <input value="Filter" type="submit" name="filter" class="btn-sm btn btn-secondary" id="">
                                    </div>
                                </div>
                            </form>


                        </div>
                    </div>

                    <div class="row" id="btn-input" style="display: none;">
                        <div class="col-7">
                            <div class="row justify-content-end">
                                <button class="text-right btn-sm btn btn-secondary" data-toggle="modal" data-target="#exampleModal">
                                    <span class="btn-label">
                                        <i class="fa fa-plus"></i>
                                    </span>
                                    Tambah Data
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table id="basic-datatables" class="display table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Tanggal</th>
                                    <th>Nama Mahasiswa</th>
                                    <th>Jumlah Kas</th>
                                    <th>Jumlah Tabungan</th>
                                    <th>Jumlah Transaksi</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php

                                $printTitle = "Print Data Transaksi ";

                                $pos = 1;
                                $q1 = "SELECT tb_transaksi.*, tb_anggota.nama_anggota
                                FROM tb_transaksi
                                LEFT JOIN   tb_anggota ON tb_anggota.id_anggota = tb_transaksi.id_anggota  ";

                                if (isset($_GET['id_anggota'])) {
                                    $Tanggal1 = mysqli_real_escape_string($conn, $_GET['tanggal_dari']);
                                    $Tanggal2 = mysqli_real_escape_string($conn, $_GET['tanggal_hingga']);
                                    $id = mysqli_real_escape_string($conn, $_GET['id_anggota']);

                                    $printMessage= "Dari Tanggal ".$Tanggal1." Hingga ".$Tanggal2 ;

                                    $q1 .= "   where (tb_transaksi.tanggal_transaksi BETWEEN '$Tanggal1' AND '$Tanggal2')  ";
                                    if ($id != 0) {
                                        $data = mysqli_fetch_assoc(mysqli_query($conn,"SELECT*FROM tb_anggota WHERE id_anggota = '$id'   "));        
                                        $printTitle .= $data['nama_anggota'];
                                        $q1 .= " AND tb_transaksi.id_anggota = '$id'";
                                    }
                                }

                                $q1 .= " GROUP BY tb_transaksi.id_transaksi   order by tb_transaksi.tanggal_transaksi desc ";
                                $totalKas = 0;
                                $totalTabungan = 0;
                                $w = mysqli_query($conn,  $q1);

                                while ($row = mysqli_fetch_object($w)) {
                                    $totalKas = $row->jumlah_kas + $totalKas;
                                    $totalTabungan = $row->jumlah_tabungan + $totalTabungan;
                                ?>
                                    <tr>
                                        <td> <?php echo $pos++; ?> </td>
                                        <td><?php echo $row->tanggal_transaksi; ?></td>
                                        <td><?php echo $row->nama_anggota; ?></td>
                                        <td><?php echo rupiah($row->jumlah_kas); ?></td>
                                        <td><?php echo rupiah($row->jumlah_tabungan); ?></td>
                                        <td><?php echo rupiah($row->jumlah_transaksi); ?></td>
                                        <td>
                                            <p class="demo">

                                                <button data-toggle="modal" data-target="#exampleModalEdit<?php echo $row->id_transaksi; ?>" type="button" class="btn-sm btn btn-icon btn-round btn-info">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <a href="?page=transaksi&act=del&id=<?php echo $row->id_transaksi; ?>" onclick="return confirm('Apakah Anda Benar Benar Ingin Menghapus?')">
                                                    <button class="btn-sm btn btn-icon btn-round btn-danger"><i class="fa fa-trash"></i></button>
                                                </a>

                                            </p>
                                        </td>
                                    </tr>
                                <?php } ?>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th rowspan="1" colspan="1"> </th>
                                    <th rowspan="1" colspan="1"> </th>
                                    <th rowspan="1" colspan="1"> Total</th>
                                    <th rowspan="1" colspan="1"><?php echo rupiah($totalKas); ?></th>
                                    <th rowspan="1" colspan="1"><?php echo rupiah($totalTabungan); ?></th>
                                    <th rowspan="1" colspan="1"><?php echo rupiah($totalKas + $totalTabungan); ?></th>
                                    <th rowspan="1" colspan="1"> </th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<?php



$q1 = "SELECT tb_transaksi.*, tb_anggota.nama_anggota
FROM tb_transaksi
LEFT JOIN   tb_anggota ON tb_anggota.id_anggota = tb_transaksi.id_anggota 
GROUP BY tb_transaksi.id_transaksi";
$w = mysqli_query($conn, $q1);

while ($row = mysqli_fetch_object($w)) {

?>
    <div class="modal fade" id="exampleModalEdit<?php echo $row->id_transaksi; ?>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form action="" method="post">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Edit Data</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input name="id_transaksi" type="hidden" value="<?php echo $row->id_transaksi; ?>">
                        <div class="form-group">
                            <label for="email2">Tanggal transaksi</label>
                            <input placeholder="Tanggal transaksi" type="date" value="<?php echo $row->tanggal_transaksi; ?>" class="form-control" name="tanggal_transaksi" id="">
                        </div>

                        <div class="form-group">
                            <label for="exampleFormControlSelect1">Pilih Anggota</label>
                            <select name="id_anggota" class="form-control" id="exampleFormControlSelect1">
                                <?php
                                foreach ($data as  $value) { ?>
                                    <option <?php if ($value->id_anggota == $row->id_anggota) {
                                                echo "selected";
                                            } ?> value="<?php echo $value->id_anggota; ?>"><?php echo $value->nama_anggota; ?></option>
                                <?php     }
                                ?>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="email2">Jumlah Kas</label>
                            <input value="<?php echo $row->jumlah_kas; ?>" required placeholder="Jumlah Kas" type="number" class="form-control" name="jumlah_kas" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Jumlah Tabungan</label>
                            <input value="<?php echo $row->jumlah_tabungan; ?>" required placeholder="Jumlah Tabungan" type="number" class="form-control" name="jumlah_tabungan" id="">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <input type="submit" class="btn btn-secondary" value="Simpan" name="edit">
                    </div>
                </div>
            </form>
        </div>
    </div>
<?php } ?>


<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Tambah Data</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="email2">Tanggal Transaksi</label>
                        <input required placeholder="Tanggal Transaksi" type="date" class="form-control" name="tanggal_transaksi" id="">
                    </div>

                    <div class="form-group">
                        <label for="exampleFormControlSelect1">Pilih Anggota</label>
                        <select name="id_anggota" class="form-control" id="exampleFormControlSelect1">
                            <?php
                            foreach ($data as  $value) { ?>
                                <option value="<?php echo $value->id_anggota; ?>"><?php echo $value->nama_anggota; ?></option>
                            <?php     }
                            ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="email2">Jumlah Kas</label>
                        <input required placeholder="Jumlah Kas" type="number" class="form-control" name="jumlah_kas" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Jumlah Tabungan</label>
                        <input required placeholder="Jumlah Tabungan" type="number" class="form-control" name="jumlah_tabungan" id="">
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn btn-secondary" value="Simpan" name="save">
                </div>
            </div>
        </form>
    </div>
</div>

<?php

if (isset($_GET['act']) == 'del') {
    $id =  $_GET['id'];
    $q =  "DELETE FROM tb_transaksi WHERE id_transaksi ='$id'";
    $e = mysqli_query($conn, $q);
    if ($e) {

        echo "<script>
        alert('Succes di hapus');
        window.location.href='?page=transaksi';
        </script>";
    } else {
        alert('gagal di hapus');
        echo "<script>alert('Error');window.history.go(-1);</script>";
    }
}

if (isset($_POST['edit'])) {
    $id_transaksi = mysqli_real_escape_string($conn, $_POST['id_transaksi']);
    $tanggal_transaksi = mysqli_real_escape_string($conn, $_POST['tanggal_transaksi']);
    $id_anggota = mysqli_real_escape_string($conn, $_POST['id_anggota']);
    $jumlah_kas = mysqli_real_escape_string($conn, $_POST['jumlah_kas']);
    $jumlah_tabungan = mysqli_real_escape_string($conn, $_POST['jumlah_tabungan']);
    $jumlah_transaksi = $jumlah_kas + $jumlah_tabungan;

    $q =  "UPDATE tb_transaksi
	SET 
		id_anggota='$id_anggota',
		jumlah_kas='$jumlah_kas',
		jumlah_tabungan='$jumlah_tabungan',
		jumlah_transaksi='$jumlah_transaksi',
		tanggal_transaksi= '$tanggal_transaksi'
	WHERE id_transaksi='$id_transaksi'";

    $up = mysqli_query($conn, $q);

    if ($up) {
        echo "<script>
        alert('sukses menyimpan');
        window.location.href='?page=transaksi';
        </script>";
    } else {
        echo "<script>
            alert('gagal menyimpan');
            window.location.href='?page=transaksi';
            </script>";
    }
}
if (isset($_POST['save'])) {
    $tanggal_transaksi = mysqli_real_escape_string($conn, $_POST['tanggal_transaksi']);
    $id_anggota = mysqli_real_escape_string($conn, $_POST['id_anggota']);
    $jumlah_kas = mysqli_real_escape_string($conn, $_POST['jumlah_kas']);
    $jumlah_tabungan = mysqli_real_escape_string($conn, $_POST['jumlah_tabungan']);
    $jumlah_transaksi = $jumlah_kas + $jumlah_tabungan;
    $q =  "INSERT INTO tb_transaksi
	(id_transaksi, id_anggota, jumlah_kas, jumlah_tabungan, jumlah_transaksi, tanggal_transaksi)
	VALUES (NULL,  '$id_anggota', '$jumlah_kas', '$jumlah_tabungan', '$jumlah_transaksi', '$tanggal_transaksi')";

    $up = mysqli_query($conn, $q);

    if ($up) {
        echo "<script>
        alert('sukses menyimpan');
        window.location.href='?page=transaksi';
        </script>";
    } else {
        echo "<script>
            alert('gagal menyimpan');
            window.location.href='?page=transaksi';
            </script>";
    }
}
?>


<script>
    window.addEventListener('load', () => {
        var value = $("#btn-input").html();
        $(".dt-buttons").append(value);
        $("#btn-input").remove();
        //  $(".dt-buttons").addClass('row').addClass('mb-2');
        //  $('.buttons-pdf,.buttons-csv,.buttons-excel, .buttons-print').each(function() {
        // 		$(this).addClass('col-1 ml-2')
        // 	})


    });
</script>