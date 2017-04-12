<?php
require_once('functions.php');
$link = dbconn();
if(isset($_SESSION['id'])){
	header('location:'.$_SERVER['HTTP_REFERER']);
	die();
}

$_SESSION['num'] = '0';
if(empty($_POST['userName']) || empty($_POST["passwd"]))
{
	header('location:login.php?codeError=empty');
	die();
}
$userName = $_POST['userName'];
$passwd = md5($_POST['passwd']);
//$passwd = $_POST['passwd'];
$res = $link->query("SELECT * FROM `account`  WHERE id = ".sqlesc($userName,$link)." LIMIT 1 ");
$row = $res->num_rows;
if($row == 0){
	header('location:login.php?codeError=Userempty');
	die();
}
$res = $link->query("SELECT * FROM `account` WHERE id = ".sqlesc($userName,$link)." AND password =".sqlesc($passwd,$link)." LIMIT 1") ;
$row = $res->num_rows;
if($row == 0){
	header('location:login.php?codeError=PasswordError');
	die();
}else{
	$name = $res->fetch_assoc();
	loginsession($name['id']);
	header('Location:index.php');
}

?>
