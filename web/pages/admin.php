<div class="page-inner">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">

                    <div class="card-header">
                        <!-- <h4 class="card-title">Basic</h4> -->
                        <div class="row">
                            <div class="col-6">
                                <h4 class="page-title">Data Admin</h4>
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
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table id="basic-datatables" class="display table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Username</th>
                                    <th>Pasword</th>
                                    <th>Nama</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php
                                $printTitle = "Print Data Admin ";
                                $pos = 1;
                                $w = mysqli_query($conn, "SELECT *from tb_anggota where level_anggota =1");

                                $data = array();
                                while ($row = mysqli_fetch_object($w)) {

                                ?>
                                    <tr>
                                        <td> <?php echo $pos++; ?> </td>
                                        <td><?php echo $row->username; ?></td>
                                        <td><?php echo $row->password; ?></td>
                                        <td><?php echo $row->nama_anggota; ?></td>
                                        <td>
                                            <p class="demo">

                                                <button data-toggle="modal" data-target="#exampleModalEdit<?php echo $row->id_anggota; ?>" type="button" class="btn-sm btn btn-icon btn-round btn-info">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <a href="?page=admin&act=del&id=<?php echo $row->id_anggota; ?>" onclick="return confirm('Apakah Anda Benar Benar Ingin Menghapus?')">
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
$w = mysqli_query($conn, "SELECT *from tb_anggota where level_anggota =1");

$data = array();
while ($row = mysqli_fetch_object($w)) {

?>
    <div class="modal fade" id="exampleModalEdit<?php echo $row->id_anggota; ?>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                        <div class="form-group">
                            <label for="">Username</label>
                            <input readonly placeholder="Username" type="text" class="form-control" name="username"value="<?php echo $row->username; ?>">
                        </div>
                        <div class="form-group">
                            <label for="">Password</label>
                            <input placeholder="Password" type="text" class="form-control" name="password" value="<?php echo $row->password; ?>">
                        </div>
                        <div class="form-group">
                            <label for="">Nama Anggota</label>
                            <input placeholder="Nama Anggota" type="text" value="<?php echo $row->nama_anggota; ?>" class="form-control" name="nama_anggota" id="">
                        </div>
                        <input name="id_anggota" type="hidden" value="<?php echo $row->id_anggota; ?>">
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
                    <input placeholder="Username" type="text" class="form-control" name="username" id="">
                </div>
                <div class="modal-body">
                    <input placeholder="Password" type="text" class="form-control" name="password" id="">
                </div>
                <div class="modal-body">
                    <input placeholder="Nama" type="text" class="form-control" name="nama_anggota" id="">
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
    $q =  "DELETE FROM tb_anggota WHERE id_anggota ='$id'";
    $e = mysqli_query($conn, $q);
    if ($e) {
        echo "<script>
        alert('Succes di hapus');
        window.location.href='?page=admin';
        </script>";
    } else {
        alert('gagal di hapus');
        echo "<script>alert('Error');window.history.go(-1);</script>";
    }
}
if (isset($_POST['edit'])) {
    $id_anggota = mysqli_real_escape_string($conn, $_POST['id_anggota']);
    $nama_anggota = mysqli_real_escape_string($conn, $_POST['nama_anggota']);
    $username = mysqli_real_escape_string($conn, $_POST['username']);

    $password =  mysqli_real_escape_string($conn, $_POST['password']);
    $count = mysqli_num_rows(mysqli_query($conn, "select * from tb_anggota where id_anggota = '$id_anggota' and password = '$password' "));

    if ($count==1) {
        $q =  "UPDATE tb_anggota
        SET  
            nama_anggota='$nama_anggota',
            username='$username' 
        WHERE id_anggota='$id_anggota'";
    
        $up = mysqli_query($conn, $q);
    
        if ($up) {
            echo "<script>
            alert('sukses menyimpan');
            window.location.href='?page=admin';
            </script>";
        } else {
            echo "<script>
                alert('gagal menyimpan');
                window.location.href='?page=admin';
                </script>";
        }
    }else{
        $password =  md5(mysqli_real_escape_string($conn, $_POST['password']));
        $q =  "UPDATE tb_anggota
        SET  
            nama_anggota='$nama_anggota',
            username='$username',
            password='$password'
        WHERE id_anggota='$id_anggota'";
    
        $up = mysqli_query($conn, $q);
    
        if ($up) {
            echo "<script>
            alert('sukses menyimpan');
            window.location.href='?page=admin';
            </script>";
        } else {
            echo "<script>
                alert('gagal menyimpan');
                window.location.href='?page=admin';
                </script>";
        }
    }

    
}
if (isset($_POST['save'])) {
    $nama_anggota = mysqli_real_escape_string($conn, $_POST['nama_anggota']);
    $username = mysqli_real_escape_string($conn, $_POST['username']);
    $password = md5(mysqli_real_escape_string($conn, $_POST['password']));

    $q =  "INSERT INTO tb_anggota
	(id_anggota, level_anggota, nama_anggota, username, password)
	VALUES (NULL, 1, '$nama_anggota', '$username', '$password')";

    $up = mysqli_query($conn, $q);

    if ($up) {
        echo "<script>
        alert('sukses menyimpan');
        // window.location.href='?page=admin';
        </script>";
    } else {
        echo "<script>
            alert('gagal menyimpan');
            window.location.href='?page=admin';
            </script>";
    }
}
?>