<?php
require_once('functions.php');
$link = dbconn();


$_SESSION['num1'] = '0';
if(!isset($_POST['ipqcName']))
{
	header('location:drawUserHistory.php');	
	die();
}

$userName = $_POST['ipqcName'];
$res = $link->query("SELECT Latitude,Longitude FROM ipqclocal  WHERE peopleNum = ".sqlesc($userName,$link));
$row = $res->num_rows;
if($row == 0){
	echo json_encode("Userempty");
	die();
}

while($arr = $res->fetch_row()){
	$result[] = $arr;
}
$length = $res->num_rows;
$res->free();
echo json_encode($result);
?>
