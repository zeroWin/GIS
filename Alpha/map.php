<?php
require_once("functions.php");
dbconn();


if(!isset($_SESSION['id']))
{
	echo "<script>alert('请先登录');";
	echo "window.location='index.php';</script>";
	die();
}

stdhead();
?>
<div class="modal-body">
<div class="control-group">
<a class = "btn" href="./test/drawpoint.html">画点</a>
<a class = "btn" href="./test/drawline.html">画线</a>
<a class = "btn" href="./test/drawFunction.html">画函数生成线</a>
<a class = "btn" href="./test/drawDB.php">画数据库读出线</a>
<a class = "btn" href="./test/drawDynamicsUser.php">画动态的点</a>
<a class = "btn" href="./test/drawDBDynamicsCar.php">画数据库读出火车实时位置</a>
<a class = "btn" href="./test/drawDBDynamicsCar_User.php">画火车巡检员实时位置</a>
<a class = "btn" href="drawUserHistory.php">画巡检员历史移动轨迹</a>
<a class = "btn" href="realTimeLocal.php">实时位置</a>


</div>
</div>
<?php
stdfoot();
?>