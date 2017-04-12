<?php
require_once('functions.php');
stdhead();

?>

<?php
session_start();
if(isset($_SESSION['id'])){         //如果没有登陆 那么返回主页
	header('location:index.php');
	die();
}
else
{
?>
	<div style="height:500px; margin:0 auto">
		<div style=" height:auto; width:750px;margin:0 auto">
			<div class="modal-header">
				<ul class="nav nav-tabs">
					<li><a  class="active" href="#login1" data-toggle="tab">欢迎登陆铁路预警系统</a></li>
				</ul>
			</div>
            <div style="width:560px;margin:0 auto">         
            <div style=" width:100px">
 			<?php
			if(isset($_SESSION['num']))
			{
				if(isset($_GET['codeError']))
				{		
					if($_GET['codeError'] == "Userempty")
					{
						echo "用户名不存在";
						unset($_SESSION['num']);
					}
					else if($_GET['codeError'] == "PasswordError")
					{
						echo "用户名与密码不匹配";
						unset($_SESSION['num']);
						
					}
					else
					{
					}
				}
				else
				{
				}
			}
			?>
            </div>
            </div>
			<div class="modal-body">
				<form id="" class="form-horizontal" action="takelogin.php" method="post">
					<div class="control-group">
						<label style="width:60px" class="control-label" >用户名</label>
						<div style="margin-left:80px" class="controls">
							<input oninput="UserNamekeypress1(event)" onfocus="UserNamefocus1()" onBlur="UserNameblur1()" id="userName1" type="text" required placeholder="工号" class="input-xlarge" name="userName" autofocus required>
                            <span id="userName_info1"></span>
                            <div style="display:none; height:10px" id="numInput1"></div>
                        </div>
					</div>

					<div class="control-group">
						<label style="width:60px" class="control-label">密码</label>
						<div  style="margin-left:80px" class="controls">
							<input oninput="Passwordkeypress1(event)" id="passwd1" onFocus="PasswordFocus1()" onBlur="PasswordBlur1()" type="password" class="input-xlarge" name="passwd" required maxlength="20">
							<span id="passwd_info1"></span>
                        </div>
                        
					</div>
					<div class="control-group">
					<button id="submit1" style="margin-left:60%" class="btn primary " type="submit" name="submit">登陆</button>
					</div>
				</form>
			</div>
		</div>
	</div>
<?php
}
stdfoot();
?>