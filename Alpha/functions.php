
<?php
header("Content-Type:text/html;charset=utf-8");
//   数据库连接函数
function dbconn(){

	$mysql_host = "localhost";
	$mysql_user = "root";
	$mysql_pass = "";
	$mysql_db = "gis";
  
	$link = mysqli_connect($mysql_host,$mysql_user,$mysql_pass);
	/* check connection */
	if ($link->connect_errno) {
		printf("Connect failed: %s\n", $link->connect_error);
		exit();
	}
	$link->query("SET NAMES UTF8");
	$link->query("SET collation_connection = 'utf8_general_ci'");
	$link->query("SET sql_mode=''");
	$link->select_db($mysql_db) or die ('dbconn:mysql_select_db:'+$link->error);
	userlogin();
	return $link;
}

// 数据库连接函数 

function userlogin()
{
	unset($GLOBALS["CURUSER"]);
	session_start();
	if(isset($_SESSION['id'])){
		$GLOBALS["CURUSER"] = array(
			'id'=>$_SESSION['id'],
		);
	}else{
	}
}

//sql语句转码函数 防止注入式sql攻击
function sqlesc($value,$link)
{
	//stripslashes
	if(get_magic_quotes_gpc())
	{
		$value = stripslashes($value);
	}
	//quote if not a number or a numeric string
	if(!is_numeric($value))
	{
		$value = "'".$link->real_escape_string($value)."'";
	}
	return $value;
}
function loginsession($id)
{
	$_SESSION['id'] = $id;

}
function logoutsession()
{
	unset($_SESSION['id']);
	session_destroy();
}

function stdhead()  //标准头函数
{

?>
		<!DOCTYPE html>
		<html>
		<head>
		<title>铁路预警系统</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="author" value="nullne">
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.ico">
		<!-- Bootstrap -->
		<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.10.1.custom.min.js"></script>
		<script type="text/javascript" src="js/jquery.form.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/yulei.js"></script>
		<script type="text/javascript" src="js/ol.js"></script>
		<link href="css/bootstrap.css" rel="stylesheet" media="screen">
		<!--[if lte IE 6]>	
		<link rel="stylesheet" type="text/css" href="bsie/bootstrap-ie6.min.css">
		<![endif]-->
		<link href="css/jquery-ui-1.10.1.custom.css" rel="stylesheet" media="screen">
		<link href="css/jquery-ui-1.10.1.custom.min.css" rel="stylesheet" media="screen">
		<link rel="stylesheet" href="css/ol.css" type="text/css">
		</head>

		<body>
		<!--navbar-->
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href="index.php">铁路预警系统</a>
					<div class="nav-collapse">
						<ul class="nav">
						<li class="active">
						<a href="realTimeLocal.php">实时位置</a>
						</li>
						</ul>
					</div>
					
					<div class="nav-collapse">
						<ul class="nav">
						<li class="active">
						<a href="drawUserHistory.php">历史移动轨迹</a>
						</li>
						</ul>
					</div>
					<ul class="nav pull-right">
<?php

    global $CURUSER;
	if(!$CURUSER)
	{
?>
						<li>
							<a data-toggle="modal" href="#log">登陆</a>
						</li>
<?php
	}else
	{
		echo ("<li><a href='index.php'>welcome,{$CURUSER['id']}!</a></li>");
		echo ("<li><a href='logout.php'>退出</a></li>");

	}
?>
					</ul>
					
				</div>
			</div>
		</div>
		<div style="height:40px">
		</div>
		<!-- end of navbar -->



		<div class="modal hide fade" id="log" style="display:none">
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<ul class="nav nav-tabs">
					<li><a  class="active" href="#login1" data-toggle="tab">登陆</a></li>
				</ul>
			</div>
			<div class="modal-body">
				<form id="" class="form-horizontal" action="takelogin.php" method="post">

					<div class="control-group">
						<label style="width:60px" class="control-label" >用户名</label>
						<div style="margin-left:80px" class="controls">
							<input oninput="UserNamekeypress(event)" onfocus="UserNamefocus()" onBlur="UserNameblur()" id="userName" type="text" required placeholder="工号" class="input-xlarge" name="userName" autofocus required>
                            <span id="userName_info"></span>
                            <div style="display:none; height:10px" id="numInput"></div>
                        </div>
					</div>

					<div class="control-group">
						<label style="width:60px" class="control-label">密码</label>
						<div  style="margin-left:80px" class="controls">
							<input oninput="Passwordkeypress(event)" id="passwd" onFocus="PasswordFocus()" onBlur="PasswordBlur()" type="password" class="input-xlarge" name="passwd" required maxlength="20">
							<span id="passwd_info"></span>
                        </div>
                        
					</div>
					<div class="control-group">
					<button id="submit" style="margin-left:60%" class="btn primary " type="submit" name="submit">登陆</button>
					</div>
				</form>
			</div>
		</div>

<?php
}

function stdfoot()
{
?>
<br />
<footer>
	<div class="container">
		<div class="row">
			<div class="span3">
			</div>
			<div class="span3">
			</div>
			<div class="span3">
			</div>
		</div>
		<div style="text-align:center">
			<p>2017 © power by BUPT</p>	
		</div>
	</div>
</footer>
		</body>
	</html>
<?php
}

?>

