<div class="page-inner">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <!-- <h4 class="card-title">Basic</h4> -->
                    <div class="row">
                        <div class="col-6">
                            <h4 class="page-title">Data Mahasiswa</h4>
                        </div>
                        <div class="col-6">
                            <div class="text-right">
                                <button class="btn-sm btn btn-secondary" data-toggle="modal" data-target="#exampleModal">
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
                                    <th>Foto</th>
                                    <th>NPM</th>
                                    <th>Tempat Tanggal Lahir</th>
                                    <th>Nama Mahasiswa</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php
                                $printTitle = "Print Data Anggota ";
                                $pos = 1;
                                $w = mysqli_query($conn, "SELECT *from tb_anggota where level_anggota =2");

                                $data = array();
                                while ($row = mysqli_fetch_object($w)) {

                                ?>
                                    <tr>
                                        <td> <?php echo $pos++; ?> </td>
                                        <td><img   src="assets/img/anggota/<?php echo $row->foto; ?>"  alt="" width="80"> </td>
                                        <td><?php echo $row->npm; ?></td>
                                        <td><?php echo $row->tempat_lahir . ", " . $row->tanggal_lahir; ?></td>
                                        <td><?php echo $row->nama_anggota; ?></td>
                                        <td>
                                            <p class="demo">

                                            <button data-toggle="modal" data-target="#exampleModalProfile<?php echo $row->id_anggota; ?>" type="button" class="btn-sm btn btn-icon btn-round btn-primary">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                                <button data-toggle="modal" data-target="#exampleModalEdit<?php echo $row->id_anggota; ?>" type="button" class="btn-sm btn btn-icon btn-round btn-info">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <a href="?page=anggota&act=del&id=<?php echo $row->id_anggota; ?>" onclick="return confirm('Apakah Anda Benar Benar Ingin Menghapus?')">
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
$w = mysqli_query($conn, "SELECT *from tb_anggota where level_anggota =2");

$data = array();
while ($row = mysqli_fetch_object($w)) {

?>
    <div class="modal fade" id="exampleModalEdit<?php echo $row->id_anggota; ?>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form action="" method="post" enctype="multipart/form-data">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Edit Data</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input name="id_anggota" type="hidden" value="<?php echo $row->id_anggota; ?>">

                        <div class="form-group">
                            <label for="email2">NPM</label>
                            <input value="<?php echo $row->npm; ?>" placeholder="NPM" type="number" class="form-control" name="npm" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Foto</label>
                            <input placeholder="Foto" type="file" class="form-control" name="foto" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Nama Mahasiswa</label>
                            <input value="<?php echo $row->nama_anggota; ?>"  placeholder="Nama Mahasiswa" type="text" class="form-control" name="nama_anggota" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Tempat Lahir</label>
                            <input value="<?php echo $row->tempat_lahir; ?>"  placeholder="Tempat Lahir" type="text" class="form-control" name="tempat_lahir" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Tanggal Lahir</label>
                            <input value="<?php echo $row->tanggal_lahir; ?>"  placeholder="Tanggal Lahir" type="date" class="form-control" name="tanggal_lahir" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Jurusan</label>
                            <input value="<?php echo $row->jurusan; ?>"  placeholder="Jurusan" type="text" class="form-control" name="jurusan" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Semester</label>
                            <input value="<?php echo $row->semester; ?>"  placeholder="Semester" type="text" class="form-control" name="semester" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Alamat</label>
                            <textarea placeholder="Alamat" name="alamat" class="form-control" id="" cols="30" rows="5"><?php echo $row->alamat; ?></textarea>
                        </div>
                        <div class="form-group">
                            <label for="email2">No Hp</label>
                            <input value="<?php echo $row->no_hp; ?>"  placeholder="No Hp" type="text" class="form-control" name="no_hp" id="">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <input type="submit" class="btn btn-secondary" value="Simpan" name="edit">
                    </div>
                </div>
            </form>
        </div>
    </div>

    
    <div class="modal fade" id="exampleModalProfile<?php echo $row->id_anggota; ?>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form action="" method="post" enctype="multipart/form-data">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Lihat Profile</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <center><img   src="assets/img/anggota/<?php echo $row->foto; ?>"  alt="" width="150"></center>
                        <div class="form-group">
                            <label for="email2">NPM</label>
                            <input readonly value="<?php echo $row->npm; ?>" placeholder="NPM" type="number" class="form-control" name="npm" id="">
                        </div> 
                        <div class="form-group">
                            <label for="email2">Nama Mahasiswa</label>
                            <input readonly value="<?php echo $row->nama_anggota; ?>"  placeholder="Nama Mahasiswa" type="text" class="form-control" name="nama_anggota" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Tempat Lahir</label>
                            <input readonly value="<?php echo $row->tempat_lahir; ?>"  placeholder="Tempat Lahir" type="text" class="form-control" name="tempat_lahir" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Tanggal Lahir</label>
                            <input readonly value="<?php echo $row->tanggal_lahir; ?>"  placeholder="Tanggal Lahir" type="date" class="form-control" name="tanggal_lahir" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Jurusan</label>
                            <input readonly value="<?php echo $row->jurusan; ?>"  placeholder="Jurusan" type="text" class="form-control" name="jurusan" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Semester</label>
                            <input readonly value="<?php echo $row->semester; ?>"  placeholder="Semester" type="text" class="form-control" name="semester" id="">
                        </div>
                        <div class="form-group">
                            <label for="email2">Alamat</label>
                            <textarea readonly placeholder="Alamat" name="alamat" class="form-control" id="" cols="30" rows="5"><?php echo $row->alamat; ?></textarea>
                        </div>
                        <div class="form-group">
                            <label for="email2">No Hp</label>
                            <input readonly  value="<?php echo $row->no_hp; ?>"  placeholder="No Hp" type="text" class="form-control" name="no_hp" id="">
                        </div>
                    </div> 
                </div>
            </form>
        </div>
    </div>
<?php } ?>


<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="" method="post" enctype="multipart/form-data">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Tambah Data</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="email2">NPM</label>
                        <input placeholder="NPM" type="number" class="form-control" name="npm" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Foto</label>
                        <input placeholder="Foto" type="file" class="form-control" name="foto" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Nama Mahasiswa</label>
                        <input placeholder="Nama Mahasiswa" type="text" class="form-control" name="nama_anggota" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Tempat Lahir</label>
                        <input placeholder="Tempat Lahir" type="text" class="form-control" name="tempat_lahir" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Tanggal Lahir</label>
                        <input placeholder="Tanggal Lahir" type="date" class="form-control" name="tanggal_lahir" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Jurusan</label>
                        <input placeholder="Jurusan" type="text" class="form-control" name="jurusan" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Semester</label>
                        <input placeholder="Semester" type="text" class="form-control" name="semester" id="">
                    </div>
                    <div class="form-group">
                        <label for="email2">Alamat</label>
                        <textarea placeholder="Alamat" name="alamat" class="form-control" id="" cols="30" rows="5"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="email2">No Hp</label>
                        <input placeholder="No Hp" type="text" class="form-control" name="no_hp" id="">
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

    $result = mysqli_query($conn, "SELECT * FROM tb_anggota WHERE  id_anggota = '$id'");
    $row = mysqli_fetch_assoc($result);
    if ($row['foto']!='') {
        $del = unlink("assets/img/anggota/" . $row['foto']);
    }

    $q =  "DELETE FROM tb_anggota WHERE id_anggota ='$id'";
    $e = mysqli_query($conn, $q);
    if ($e) {

        echo "<script>
        alert('Succes di hapus');
        window.location.href='?page=anggota';
        </script>";
    } else {
        alert('gagal di hapus');
        echo "<script>alert('Error');window.history.go(-1);</script>";
    }
}
if (isset($_POST['edit'])) {
    $id_anggota = mysqli_real_escape_string($conn, $_POST['id_anggota']);
    $nama_anggota = mysqli_real_escape_string($conn, $_POST['nama_anggota']);
    $npm = mysqli_real_escape_string($conn, $_POST['npm']);
    $tempat_lahir = mysqli_real_escape_string($conn, $_POST['tempat_lahir']);
    $tanggal_lahir = mysqli_real_escape_string($conn, $_POST['tanggal_lahir']);
    $jurusan = mysqli_real_escape_string($conn, $_POST['jurusan']);
    $semester = mysqli_real_escape_string($conn, $_POST['semester']);
    $alamat = mysqli_real_escape_string($conn, $_POST['alamat']);
    $no_hp = mysqli_real_escape_string($conn, $_POST['no_hp']);


    $pict = $_FILES['foto']['name'];
    if ($pict == '') {
        $q =  "UPDATE tb_anggota
        SET 
            nama_anggota='$nama_anggota',
            npm='$npm',
            jurusan='$jurusan',
            semester='$semester',
            alamat='$alamat',
            no_hp='$no_hp',
            tempat_lahir='$tempat_lahir',
            tanggal_lahir='$tanggal_lahir' 
        WHERE id_anggota='$id_anggota'";

        $up = mysqli_query($conn, $q);

        if ($up) {
            echo "<script>
            alert('sukses menyimpan');
            window.location.href='?page=anggota';
            </script>";
        } else {
            echo "<script>
                alert('gagal menyimpan');
                window.location.href='?page=anggota';
                </script>";
        }
    } else {

        $result = mysqli_query($conn, "SELECT * FROM tb_anggota WHERE  id_anggota = '$id_anggota'");
        $row = mysqli_fetch_assoc($result);
        if ($row['foto']!='') {
            $del = unlink("assets/img/anggota/" . $row['foto']);
        }
      
        $extensi = explode(".", $_FILES['foto']['name']);
            $foto = "anggota-" . round(microtime(true)) . "." . end($extensi);
            $sumber = $_FILES['foto']['tmp_name'];

            $upload = move_uploaded_file($sumber, "assets/img/anggota/" . $foto);
            $q =  "UPDATE tb_anggota
            SET 
                nama_anggota='$nama_anggota',
                npm='$npm',
                jurusan='$jurusan',
                semester='$semester',
                alamat='$alamat',
                no_hp='$no_hp',
                tempat_lahir='$tempat_lahir',
                tanggal_lahir='$tanggal_lahir' ,
                foto='$foto' 
            WHERE id_anggota='$id_anggota'";

            $up = mysqli_query($conn, $q);

            if ($up) {
                echo "<script>
                alert('sukses menyimpan');
                window.location.href='?page=anggota';
                </script>";
            } else {
                echo "<script>
                    alert('gagal menyimpan');
                    window.location.href='?page=anggota';
                    </script>";
            }
    }
}
if (isset($_POST['save'])) {
    $nama_anggota = mysqli_real_escape_string($conn, $_POST['nama_anggota']);
    $npm = mysqli_real_escape_string($conn, $_POST['npm']);
    $tempat_lahir = mysqli_real_escape_string($conn, $_POST['tempat_lahir']);
    $tanggal_lahir = mysqli_real_escape_string($conn, $_POST['tanggal_lahir']);
    $jurusan = mysqli_real_escape_string($conn, $_POST['jurusan']);
    $semester = mysqli_real_escape_string($conn, $_POST['semester']);
    $alamat = mysqli_real_escape_string($conn, $_POST['alamat']);
    $no_hp = mysqli_real_escape_string($conn, $_POST['no_hp']);

    $extensi = explode(".", $_FILES['foto']['name']);
    $foto = "anggota-" . round(microtime(true)) . "." . end($extensi);
    $sumber = $_FILES['foto']['tmp_name'];

    $upload = move_uploaded_file($sumber, "assets/img/anggota/" . $foto);
    if ($upload) {
        $q =  "INSERT INTO tb_anggota
        (id_anggota, level_anggota, nama_anggota, npm, jurusan, semester, alamat, no_hp, tempat_lahir, tanggal_lahir, foto,  username, password)
        VALUES (NULL, '2', '$nama_anggota', '$npm', '$jurusan', '$semester', '$alamat', '$no_hp', '$tempat_lahir','$tanggal_lahir', '$foto','', '')";

        $up = mysqli_query($conn, $q);

        if ($up) {
            echo "<script>
            alert('sukses menyimpan');
            window.location.href='?page=anggota';
            </script>";
        } else {
            echo "<script>
                alert('gagal menyimpan');
                window.location.href='?page=anggota';
                </script>";
        }
    }
}
?>


<script>
    window.addEventListener('load', () => {

    });
</script>