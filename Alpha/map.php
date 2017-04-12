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
<a class = "btn" href="test1.html">模板1</a>
<a class = "btn" href="test2.html">模板2</a>
<a class = "btn" href="test3.html">模板3</a>
</div>
</div>
<?php
stdfoot();
?>