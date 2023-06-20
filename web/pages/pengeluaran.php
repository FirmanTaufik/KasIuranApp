<div class="page-inner">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <!-- <h4 class="card-title">Basic</h4> -->
                    <div class="row">
                        <div class="col-4" style="margin-top: 20px;">
                            <h4 class="page-title">Data Pengeluaran Kas</h4>
                        </div>
                        <div class="col-8">
                            <form action="" method="get">
                                <input type="hidden" name="page" value="pengeluaran">
                                <div class="row  ">
                                    <div class="col-5">
                                        <div class="form-group">
                                            <label for="exampleFormControlSelect1">Tanggal Dari</label>
                                            <input value="<?php if (isset($_GET['tanggal_dari'])) echo $_GET['tanggal_dari'];   ?>" required type="date" name="tanggal_dari" class="form-control" id="">
                                        </div>
                                    </div>
                                    <div class="col-5">
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
                                    <th>Jumlah Pengeluaran</th>
                                    <th>Keperluan</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php
                                $printTitle = "Print Data Pengeluaran Kas ";

                                $pos = 1;
                                $q1 = "SELECT  * FROM tb_pengeluaran  ";

                                if (isset($_GET['tanggal_dari'])) {

                                    $Tanggal1 = mysqli_real_escape_string($conn, $_GET['tanggal_dari']);
                                    $Tanggal2 = mysqli_real_escape_string($conn, $_GET['tanggal_hingga']);
                                    $printMessage= "Dari Tanggal ".$Tanggal1." Hingga ".$Tanggal2 ;

                                    $q1 .= "   where (tanggal_pengeluaran BETWEEN '$Tanggal1' AND '$Tanggal2')   ";
                                }

                                $q1 .= "GROUP BY id_pengeluaran  order by tanggal_pengeluaran desc";
                                $totalPengeluaran = 0;
                                $w = mysqli_query($conn,  $q1);

                                while ($row = mysqli_fetch_object($w)) {

                                    $totalPengeluaran = $totalPengeluaran + $row->jumlah_pengeluaran;
                                ?>
                                    <tr>
                                        <td> <?php echo $pos++; ?> </td>
                                        <td><?php echo $row->tanggal_pengeluaran; ?></td>
                                        <td><?php echo rupiah($row->jumlah_pengeluaran); ?></td>
                                        <td><?php echo $row->keperluan; ?></td>
                                        <td>
                                            <p class="demo">

                                                <button data-toggle="modal" data-target="#exampleModalEdit<?php echo $row->id_pengeluaran; ?>" type="button" class="btn-sm btn btn-icon btn-round btn-info">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <a href="?page=pengeluaran&act=del&id=<?php echo $row->id_pengeluaran; ?>" onclick="return confirm('Apakah Anda Benar Benar Ingin Menghapus?')">
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
                                    <th rowspan="1" colspan="1"> Total</th>
                                    <th rowspan="1" colspan="1"><?php echo rupiah($totalPengeluaran); ?></th>
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



$q1 = "SELECT  * FROM tb_pengeluaran ";
$w = mysqli_query($conn, $q1);

while ($row = mysqli_fetch_object($w)) {

?>
    <div class="modal fade" id="exampleModalEdit<?php echo $row->id_pengeluaran; ?>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                        <input name="id_pengeluaran" type="hidden" value="<?php echo $row->id_pengeluaran; ?>">
                        <div class="form-group">
                            <label for="email2">Tanggal Pengeluaran</label>
                            <input placeholder="Tanggal Penarikan" type="date" value="<?php echo $row->tanggal_pengeluaran; ?>" class="form-control" name="tanggal_pengeluaran" id="">
                        </div>

                        <div class="form-group">
                            <label for="email2">Jumlah Pengeluaran</label>
                            <input placeholder="Jumlah Penarikan" type="number" value="<?php echo $row->jumlah_pengeluaran; ?>" class="form-control" name="jumlah_pengeluaran" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Keperluan</label>
                            <textarea name="keperluan" required placeholder="Keperluan" type="number" class="form-control" id="" cols="30" rows="5"><?php echo $row->keperluan; ?></textarea>
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
                        <label for="email2">Tanggal Pengeluaran</label>
                        <input required placeholder="Tanggal Keperluan" type="date" class="form-control" name="tanggal_pengeluaran" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Jumlah Pengeluaran</label>
                        <input required placeholder="Jumlah Keperluan" type="number" class="form-control" name="jumlah_pengeluaran" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Keperluan</label>
                        <textarea name="keperluan" required placeholder="Keperluan" type="number" class="form-control" id="" cols="30" rows="5"></textarea>
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
    $q =  "DELETE FROM tb_pengeluaran WHERE id_pengeluaran ='$id'";
    $e = mysqli_query($conn, $q);
    if ($e) {

        echo "<script>
        alert('Succes di hapus');
        window.location.href='?page=pengeluaran';
        </script>";
    } else {
        alert('gagal di hapus');
        echo "<script>alert('Error');window.history.go(-1);</script>";
    }
}
if (isset($_POST['edit'])) {
    $id_pengeluaran = mysqli_real_escape_string($conn, $_POST['id_pengeluaran']);
    $tanggal_pengeluaran = mysqli_real_escape_string($conn, $_POST['tanggal_pengeluaran']);
    $jumlah_pengeluaran = mysqli_real_escape_string($conn, $_POST['jumlah_pengeluaran']);
    $keperluan = mysqli_real_escape_string($conn, $_POST['keperluan']);

    $q =  "UPDATE tb_pengeluaran
	SET
		jumlah_pengeluaran='$jumlah_pengeluaran',
		keperluan='$keperluan',
		tanggal_pengeluaran='$tanggal_pengeluaran'
	WHERE id_pengeluaran='$id_pengeluaran'";

    $up = mysqli_query($conn, $q);

    if ($up) {
        echo "<script>
        alert('sukses menyimpan');
        window.location.href='?page=pengeluaran';
        </script>";
    } else {
        echo "<script>
            alert('gagal menyimpan');
            window.location.href='?page=pengeluaran';
            </script>";
    }
}
if (isset($_POST['save'])) {
    $tanggal_pengeluaran = mysqli_real_escape_string($conn, $_POST['tanggal_pengeluaran']);
    $jumlah_pengeluaran = mysqli_real_escape_string($conn, $_POST['jumlah_pengeluaran']);
    $keperluan = mysqli_real_escape_string($conn, $_POST['keperluan']);

    $q =  "INSERT INTO tb_pengeluaran
	(id_pengeluaran, jumlah_pengeluaran, keperluan, tanggal_pengeluaran)
	VALUES (NULL, '$jumlah_pengeluaran', '$keperluan', '$tanggal_pengeluaran')";

    $up = mysqli_query($conn, $q);

    if ($up) {
        echo "<script>
        alert('sukses menyimpan');
        window.location.href='?page=pengeluaran';
        </script>";
    } else {
        echo "<script>
            alert('gagal menyimpan');
            window.location.href='?page=pengeluaran';
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