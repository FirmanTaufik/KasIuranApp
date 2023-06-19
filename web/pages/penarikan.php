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
                            <h4 class="page-title">Data Penarikan Tabungan</h4>
                        </div>
                        <div class="col-8">
                            <form action="" method="get">
                                <input type="hidden" name="page" value="penarikan">
                                <div class="row  ">
                                    <div class="col-3">
                                        <div class="form-group">
                                            <label for="exampleFormControlSelect1">Pilih Anggota</label>
                                            <select required name="id_anggota" class="form-control" id="exampleFormControlSelect1">
                                                <?php
                                                foreach ($data as  $value) { ?>
                                                    <option <?php if (isset($_GET['id_anggota']))   if($_GET['id_anggota']==$value->id_anggota ) { echo "selected";}   ?> value="<?php echo $value->id_anggota; ?>"><?php echo $value->nama_anggota; ?></option>
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
                                    <th>Nama Anggota</th>
                                    <th>Jumlah Penarikan</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php
                                $pos = 1;
                                $q1 = "SELECT tb_penarikan.*, tb_anggota.nama_anggota
                                FROM tb_penarikan
                                LEFT JOIN   tb_anggota ON tb_anggota.id_anggota = tb_penarikan.id_anggota ";

                                if (isset($_GET['id_anggota'])) {

                                    $Tanggal1 = mysqli_real_escape_string($conn, $_GET['tanggal_dari']);
                                    $Tanggal2 = mysqli_real_escape_string($conn, $_GET['tanggal_hingga']);
                                    $id = mysqli_real_escape_string($conn, $_GET['id_anggota']);

                                    $q1 .= "   where (tb_penarikan.tanggal_penarikan BETWEEN '$Tanggal1' AND '$Tanggal2') AND tb_penarikan.id_anggota = '$id' ";
                                }

                                $q1 .= "GROUP BY tb_penarikan.id_penarikan";

                                $w = mysqli_query($conn,  $q1);

                                while ($row = mysqli_fetch_object($w)) {

                                ?>
                                    <tr>
                                        <td> <?php echo $pos++; ?> </td>
                                        <td><?php echo $row->tanggal_penarikan; ?></td>
                                        <td><?php echo $row->nama_anggota; ?></td>
                                        <td><?php echo $row->jumlah_penarikan; ?></td>
                                        <td>
                                            <p class="demo">

                                                <button data-toggle="modal" data-target="#exampleModalEdit<?php echo $row->id_penarikan; ?>" type="button" class="btn-sm btn btn-icon btn-round btn-info">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <a href="?page=penarikan&act=del&id=<?php echo $row->id_penarikan; ?>" onclick="return confirm('Apakah Anda Benar Benar Ingin Menghapus?')">
                                                    <button class="btn-sm btn btn-icon btn-round btn-danger"><i class="fa fa-trash"></i></button>
                                                </a>

                                            </p>
                                        </td>
                                    </tr>
                                <?php } ?>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<?php



$q1 = "SELECT tb_penarikan.*, tb_anggota.nama_anggota
FROM tb_penarikan
LEFT JOIN   tb_anggota ON tb_anggota.id_anggota = tb_penarikan.id_anggota 
GROUP BY tb_penarikan.id_penarikan";
$w = mysqli_query($conn, $q1);

while ($row = mysqli_fetch_object($w)) {

?>
    <div class="modal fade" id="exampleModalEdit<?php echo $row->id_penarikan; ?>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                        <input name="id_penarikan" type="hidden" value="<?php echo $row->id_penarikan; ?>">
                        <div class="form-group">
                            <label for="email2">Tanggal Penarikan</label>
                            <input placeholder="Tanggal Penarikan" type="date" value="<?php echo $row->tanggal_penarikan; ?>" class="form-control" name="tanggal_penarikan" id="">
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
                            <label for="email2">Jumlah Penarikan</label>
                            <input placeholder="Jumlah Penarikan" type="number" value="<?php echo $row->jumlah_penarikan; ?>" class="form-control" name="jumlah_penarikan" id="">
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
                    <?php echo count($data); ?>
                    <div class="form-group">
                        <label for="email2">Tanggal Penarikan</label>
                        <input required placeholder="Tanggal Penarikan" type="date" class="form-control" name="tanggal_penarikan" id="">
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
                        <label for="email2">Jumlah Penarikan</label>
                        <input required placeholder="Jumlah Penarikan" type="number" class="form-control" name="jumlah_penarikan" id="">
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
    $q =  "DELETE FROM tb_penarikan WHERE id_penarikan ='$id'";
    $e = mysqli_query($conn, $q);
    if ($e) {

        echo "<script>
        alert('Succes di hapus');
        window.location.href='?page=penarikan';
        </script>";
    } else {
        alert('gagal di hapus');
        echo "<script>alert('Error');window.history.go(-1);</script>";
    }
}
if (isset($_POST['edit'])) {
    $tanggal_penarikan = mysqli_real_escape_string($conn, $_POST['tanggal_penarikan']);
    $id_penarikan = mysqli_real_escape_string($conn, $_POST['id_penarikan']);
    $jumlah_penarikan = mysqli_real_escape_string($conn, $_POST['jumlah_penarikan']);
    $id_anggota = mysqli_real_escape_string($conn, $_POST['id_anggota']);

    $q =  "UPDATE tb_penarikan
	SET 
		jumlah_penarikan='$jumlah_penarikan',
		id_anggota='$id_anggota',
		tanggal_penarikan='$tanggal_penarikan'
	WHERE id_penarikan='$id_penarikan'";

    $up = mysqli_query($conn, $q);

    if ($up) {
        echo "<script>
        alert('sukses menyimpan');
        window.location.href='?page=penarikan';
        </script>";
    } else {
        echo "<script>
            alert('gagal menyimpan');
            window.location.href='?page=penarikan';
            </script>";
    }
}
if (isset($_POST['save'])) {
    $tanggal_penarikan = mysqli_real_escape_string($conn, $_POST['tanggal_penarikan']);
    $id_anggota = mysqli_real_escape_string($conn, $_POST['id_anggota']);
    $jumlah_penarikan = mysqli_real_escape_string($conn, $_POST['jumlah_penarikan']);

    $q =  "INSERT INTO tb_penarikan
	(id_penarikan, jumlah_penarikan, id_anggota, tanggal_penarikan)
	VALUES (NULL,  '$jumlah_penarikan','$id_anggota', '$tanggal_penarikan')";

    $up = mysqli_query($conn, $q);

    if ($up) {
        echo "<script>
        alert('sukses menyimpan');
        window.location.href='?page=penarikan';
        </script>";
    } else {
        echo "<script>
            alert('gagal menyimpan');
            window.location.href='?page=penarikan';
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