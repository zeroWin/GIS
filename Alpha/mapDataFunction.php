<?php
require_once('functions.php');
$link = dbconn();

$action = $_POST['action'];
if($action == 'RailWayRoute'){
	echo getRailWayRoute($link);
}else if ($action == 'RailWayLocal'){
	echo getRailWayLocal($link);
}else if ($action == 'IPQCLocal'){
	echo getIPQCLocal($link);
}

// 读取火车路径
function getRailWayRoute($link){
	// 获取铁路的路径
	$res = $link->query("SELECT `Latitude`,`Longitude` FROM `railwaygps` WHERE 1");
	//$res = $link->query("SELECT `Latitude`,`Longitude` FROM `xingtanlugps` WHERE 1");
	while($arr = $res->fetch_row())
	{
		$result[] = $arr;
	}
	$length = $res->num_rows;
	$res->free();
	return json_encode($result);
	//return $length;
} 

// 读取当前火车位置函数
function getRailWayLocal($link){

	$res = $link->query("select a.Latitude,a.Longitude from railwayrealtimegps a where not exists(select 1 from railwayrealtimegps b where b.railwayNum=a.railwayNum and b.TimeSys>a.TimeSys)");
	$result = $res->fetch_row();
	$length = $res->num_rows;
	$res->free();
	return json_encode($result);
	//return $length;
}


// 读取所有巡检员当前位置
function getIPQCLocal($link){
	$res = $link->query("select a.peopleNum,a.Latitude,a.Longitude from ipqclocal a where not exists(select 1 from ipqclocal b where b .peopleNum=a.peopleNum and b.TimeSys>a.TimeSys)");
	while($arr = $res->fetch_row())
	{
		$result[] = $arr;
	}
	$length = $res->num_rows;
	$res->free();
	return json_encode($result);	
}

?>

