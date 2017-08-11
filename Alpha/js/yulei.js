// JavaScript Document
//登陆UserName被点中
function UserNamefocus()
{
	var UserName = document.getElementById("userName");
	var UserName_info = document.getElementById("userName_info");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="请输入用户名(8-10数字)";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="用户名必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 10 || strlen(UserName.value) < 8)
				{
					UserName_info.innerText="8-10个数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin();
	}
}
//登陆UserName失焦
function UserNameblur()
{
	var UserName = document.getElementById("userName");
	var UserName_info = document.getElementById("userName_info");
	if(UserName.blur)
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="用户名不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="用户名必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 10 || strlen(UserName.value) < 8)
				{
					UserName_info.innerText="8-10个数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin();
	}
	
}
//UserNameInput
function UserNamekeypress(event)
{
	var UserName = document.getElementById("userName");
	var numInput = document.getElementById("numInput");
	if(UserName.value == "")
	{
		numInput.style.display = "none";
	}
	else
	{
		var num = strlen(UserName.value);
		numInput.style.display = "";
		numInput.innerHTML = "<p >已输入" + num + "个字符</p>";
	}
	
	judgeLogin();
}

//判断字符串长度
function strlen(str) {
	var len = 0;
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		//单字节加1 
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			len++;
		}
		else{
			len += 2;
		}
	}
	return len;
}
//密码获得焦点
function PasswordFocus(){
	var password = document.getElementById("passwd");	
	var password_info = document.getElementById("passwd_info");
	if(password.focus)
	{
		if(password.value == "")
		{
			password_info.innerText="请输入密码";
		}
		else
		{
		}
		
		judgeLogin();
	}
}
//密码失焦
function PasswordBlur(){
	var password = document.getElementById("passwd");
	var password_info = document.getElementById("passwd_info");
	if(password.blur)
	{
		if(password.value=="")
		{
			password_info.innerText="密码不能为空";
		}
		else
		{
		}
		judgeLogin();
	}
}

//密码输入
function Passwordkeypress(event)
{
	judgeLogin();
}






//login界面使用
//登陆UserName被点中
function UserNamefocus1()
{
	var UserName = document.getElementById("userName1");
	var UserName_info = document.getElementById("userName_info1");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="请输入用户名(8-10数字)";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="用户名必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 10 || strlen(UserName.value) < 8)
				{
					UserName_info.innerText="8-10个数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin1();
		
		
	}
}
//登陆UserName失焦
function UserNameblur1()
{
	var UserName = document.getElementById("userName1");
	var UserName_info = document.getElementById("userName_info1");
	if(UserName.blur)
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="用户名不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="用户名必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 10 || strlen(UserName.value) < 8)
				{
					UserName_info.innerText="8-10个数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin1();
	}
	
}
//UserNameInput
function UserNamekeypress1(event)
{
	var UserName = document.getElementById("userName1");
	var numInput = document.getElementById("numInput1");
	if(UserName.value == "")
	{
		numInput.style.display = "none";
	}
	else
	{
		var num = strlen(UserName.value);
		numInput.style.display = "";
		numInput.innerHTML = "<p >已输入" + num + "个字符</p>";
	}
	
	judgeLogin1();
}

//密码获得焦点
function PasswordFocus1(){
	var password = document.getElementById("passwd1");
	var password_info = document.getElementById("passwd_info1");
	if(password.focus)
	{
		if(password.value == "")
		{
			password_info.innerText="请输入密码";
		}
		else
		{
		}
		
		judgeLogin1();
	}
}
//密码失焦
function PasswordBlur1(){
	
	var password = document.getElementById("passwd1");
	var password_info = document.getElementById("passwd_info1");
	if(password.blur)
	{
		if(password.value=="")
		{
			password_info.innerText="密码不能为空";
		}
		else
		{
		}
		
		judgeLogin1();
	}
}

function Passwordkeypress1(event)
{
	judgeLogin1();
}

function judgeLogin()
{
	var UserName = document.getElementById("userName");
	var password = document.getElementById("passwd");
	var button = document.getElementById("submit");
	
	var reg=/^\d+$/;
	if(UserName.value=="" || reg.test(UserName.value) == false || strlen(UserName.value) > 10 || strlen(UserName.value) < 8
	|| password.value == "")
	{
		button.disabled = true;
	}
	else
	{
		button.disabled = false;
	}
}
function judgeLogin1()
{
	var UserName = document.getElementById("userName1");
	var password = document.getElementById("passwd1");
	var button = document.getElementById("submit1");
	
	var reg=/^\d+$/;
	if(UserName.value=="" || reg.test(UserName.value) == false || strlen(UserName.value) > 10 || strlen(UserName.value) < 8
	|| password.value == "")
	{
		button.disabled = true;
	}
	else
	{
		button.disabled = false;
	}
}





//注册界面使用
//UserName被点中
function UserNamefocus2()
{
	var UserName = document.getElementById("userName2");
	var UserName_info = document.getElementById("userName_info2");
	var numInput = document.getElementById("numInput2");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="请输入用户名(8-10数字)";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="用户名必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 10 || strlen(UserName.value) < 8)
				{
					UserName_info.innerText="8-10个数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin2();
	}
}
//UserName失焦
function UserNameblur2()
{
	var UserName = document.getElementById("userName2");
	var UserName_info = document.getElementById("userName_info2");
	if(UserName.blur)
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="用户名不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="用户名必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 10 || strlen(UserName.value) < 8)
				{
					UserName_info.innerText="8-10个数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin2();
	}
	
}
//UserNameInput
function UserNamekeypress2(event)
{
	var UserName = document.getElementById("userName2");
	var numInput = document.getElementById("numInput2");
	if(UserName.value == "")
	{
		numInput.style.display = "none";
	}
	else
	{
		var num = strlen(UserName.value);
		numInput.style.display = "";
		numInput.innerHTML = "<p >已输入" + num + "个字符</p>";
	}
	
	judgeLogin2();
}

//密码获得焦点
function PasswordFocus2_1(){
	var password = document.getElementById("passwd2_1");	
	var password_info = document.getElementById("passwd_info2_1");
	if(password.focus)
	{
		if(password.value == "")
		{
			password_info.innerText="请输入密码";
		}
		else
		{
			password_info.innerText="正确";
		}
		
		judgeLogin2();
	}
}
//密码失焦
function PasswordBlur2_1(){
	var password = document.getElementById("passwd2_1");
	var password_info = document.getElementById("passwd_info2_1");
	if(password.blur)
	{
		if(password.value=="")
		{
			password_info.innerText="密码不能为空";
		}
		else
		{
			password_info.innerText="正确";		
		}
		judgeLogin2();
	}
}

//密码输入
function Passwordkeypress2_1(event)
{
	var password = document.getElementById("passwd2_1");	
	var password2 = document.getElementById("passwd2_2");	
	var password_info2 = document.getElementById("passwd_info2_2");

	if(password2.value != "")
	{
		if(password.value != password2.value)
		{
			password_info2.innerText="两次密码输入不相同";
		}
		else
		{
			password_info2.innerText="密码相同";
		}
	}			
	judgeLogin2();
}


//再次输入密码
//密码获得焦点
function PasswordFocus2_2(){
	var password = document.getElementById("passwd2_1");	
	var password2 = document.getElementById("passwd2_2");	
	var password_info2 = document.getElementById("passwd_info2_2");
	if(password2.focus)
	{
		
		if(password2.value == "")
		{
			password_info2.innerText="请输入密码";
		}
		else
		{
			if(password.value != password2.value)
			{
				password_info2.innerText="两次密码输入不相同";
			}
			else
			{
				password_info2.innerText="密码相同";
			}
		}	
	}
	judgeLogin2();
}
//密码失焦
function PasswordBlur2_2(){
	var password = document.getElementById("passwd2_1");	
	var password2 = document.getElementById("passwd2_2");
	var password_info2 = document.getElementById("passwd_info2_2");
	if(password2.blur)
	{
		if(password2.value=="")
		{
			password_info2.innerText="密码不能为空";
		}
		else
		{
			if(password.value != password2.value)
			{
				password_info2.innerText="两次密码输入不相同";
			}
			else
			{
				password_info2.innerText="密码相同";
			}	
		}
	}
	judgeLogin2();
}

//密码输入
function Passwordkeypress2_2(event)
{
	var password = document.getElementById("passwd2_1");	
	var password2 = document.getElementById("passwd2_2");	
	var password_info2 = document.getElementById("passwd_info2_2");

	if(password2.value != "")
	{
		if(password.value != password2.value)
		{
			password_info2.innerText="两次密码输入不相同";
		}
		else
		{
			password_info2.innerText="密码相同";
		}
	}	
	else
	{
		password_info2.innerText="请输入密码";
	}
	judgeLogin2();
}

function NameFocus()
{
	var name = document.getElementById("name");	
	var name_info = document.getElementById("name_info");
	if(name.focus)
	{
		if(name.value == "")
		{
			name_info.innerText="请输入姓名";
		}
		else
		{
			if(strlen(name.value) > 10)
			{
				name_info.innerText="输入过长";
			}
		}
	
	}
	judgeLogin2();
}

function NameBlur()
{
	var name = document.getElementById("name");	
	var name_info = document.getElementById("name_info");
	if(name.blur)
	{
		if(name.value == "")
		{
			name_info.innerText="姓名不能为空";
		}
		else
		{
			if(strlen(name.value) > 10)
			{
				name_info.innerText="输入过长";
			}
		}
	}
	judgeLogin2();
}

function Namekeypress(event)
{
	var Name = document.getElementById("name");
	var numInput = document.getElementById("numInput3");
	if(Name.value == "")
	{
		numInput.style.display = "none";
	}
	else
	{
		var num = strlen(Name.value);
		numInput.style.display = "";
		numInput.innerHTML = "<p >已输入" + num + "个字符</p>";
	}
	
	judgeLogin2();
}

function paimingFocus()
{
	var paiming = document.getElementById("paiming");	
	var paiming_info = document.getElementById("paiming_info");
	if(paiming.focus)
	{
		if(paiming.value == "")
		{
			paiming_info.innerText="请输入排名";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(paiming.value) == false)
			{
				paiming_info.innerText="排名必须是纯数字";
			}
			else if(strlen(paiming.value) > 3)
			{
				paiming_info.innerText="输入数字过大";
			}
			else
			{
				paiming_info.innerText="正确";
			}
		}
	
	}
	judgeLogin2();
}

function paimingBlur()
{
	var paiming = document.getElementById("paiming");	
	var paiming_info = document.getElementById("paiming_info");
	if(paiming.blur)
	{
		if(paiming.value == "")
		{
			paiming_info.innerText="排名不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(paiming.value) == false)
			{
				paiming_info.innerText="排名必须是纯数字";
			}
			else if(strlen(paiming.value) > 3)
			{
				paiming_info.innerText="输入数字过大";
			}
			else
			{
				paiming_info.innerText="正确";
			}
		}
	}
	judgeLogin2();
}

function paimingkeypress(event)
{
	var paiming = document.getElementById("paiming");	
	var paiming_info = document.getElementById("paiming_info");
	if(paiming.blur)
	{
		if(paiming.value == "")
		{
			paiming_info.innerText="排名不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(paiming.value) == false)
			{
				paiming_info.innerText="排名必须是纯数字";
			}
			else if(strlen(paiming.value) > 3)
			{
				paiming_info.innerText="输入数字过大";
			}
			else
			{
				paiming_info.innerText="正确";
			}
		}
		judgeLogin2();
	}
}

function judgeLogin2()
{
	var UserName = document.getElementById("userName2");
	var password = document.getElementById("passwd2_1");
	var password1 = document.getElementById("passwd2_2");
	var paiming = document.getElementById("paiming");
	var name = document.getElementById("name");
	var CET6 = document.getElementById("CET6");
		
	var button = document.getElementById("submit2");
	var reg=/^\d+$/;
	if(UserName.value=="" || reg.test(UserName.value) == false || strlen(UserName.value) > 10 || strlen(UserName.value) < 8
	|| password.value == "" || password.value != password1.value || name.value == "" || 
	strlen(name.value) > 10 || paiming.value == "" || reg.test(paiming.value) == false || strlen(paiming.value) > 4||
	name.value == "" || CET6.value == "" || reg.test(CET6.value) == false || strlen(CET6.value) < 1 || strlen(CET6.value) > 3)
	{
		button.disabled = true;
	}
	else
	{
		button.disabled = false;
	}
}

function resetOnclick()
{
	var paiming_info = document.getElementById("paiming_info");
	var numInput1 = document.getElementById("numInput3");
	var name_info = document.getElementById("name_info");
	var password_info2 = document.getElementById("passwd_info2_2");
	var password_info = document.getElementById("passwd_info2_1");
	var UserName_info = document.getElementById("userName_info2");
	var CET6_info = document.getElementById("CET6_info");
	var numInput2 = document.getElementById("numInput2");
	var numInput3 = document.getElementById("numInput4");
	var button = document.getElementById("submit2");
	
	numInput3.style.display = "none";
	numInput2.style.display = "none";
	numInput1.style.display = "none";
	paiming_info.innerText ="";
	name_info.innerText = "";
	CET6_info.innerText = "";
	password_info.innerText = "";
	password_info2.innerText = "";	
	UserName_info.innerText = "";
	button.disabled = true;
	
}


function CET6focus()
{
	var UserName = document.getElementById("CET6");
	var UserName_info = document.getElementById("CET6_info");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="请输入六级成绩";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="成绩必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 3 || strlen(UserName.value) < 1)
				{
					UserName_info.innerText="1-3位数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin2();
	}
}
//登陆UserName失焦
function CET6blur()
{
	var UserName = document.getElementById("CET6");
	var UserName_info = document.getElementById("CET6_info");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="六级成绩不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="成绩必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 3 || strlen(UserName.value) < 1)
				{
					UserName_info.innerText="1-3位数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin2();
	}
}
//UserNameInput
function CET6keypress(event)
{
	var UserName = document.getElementById("CET6");
	var UserName_info = document.getElementById("CET6_info");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="请输入六级成绩";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="成绩必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 3 || strlen(UserName.value) < 1)
				{
					UserName_info.innerText="1-3位数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin2();
	}
}



//修改信息界面
//旧密码获得焦点
function oldPassFocus(){
	var password = document.getElementById("oldPass");	
	var password_info = document.getElementById("oldPass_info");
	if(password.focus)
	{
		if(password.value == "")
		{
			password_info.innerText="请输入密码";
		}
		else
		{
			password_info.innerText="正确";
		}
		
		judgeLogin3();
	}
}
//密码失焦
function oldPassBlur(){
	var password = document.getElementById("oldPass");	
	var password_info = document.getElementById("oldPass_info");
	if(password.blur)
	{
		if(password.value=="")
		{
			password_info.innerText="密码不能为空";
		}
		else
		{
			password_info.innerText="正确";		
		}
		judgeLogin3();
	}
}

//密码输入
function oldPasskeypress(event)
{
	var password = document.getElementById("oldPass");	
	var password2 = document.getElementById("newPass");	
	var password_info2 = document.getElementById("newPass_info");

	if(password2.value != "")
	{
		if(password.value != password2.value)
		{
			password_info2.innerText="";
		}
		else
		{
			password_info2.innerText="新旧密码不能相同";
		}
	}			
	judgeLogin3();
}


//新密码
//密码获得焦点
function newPassFocus(){
	var password = document.getElementById("oldPass");	
	var password2 = document.getElementById("newPass");	
	var password_info2 = document.getElementById("newPass_info");
	if(password2.focus)
	{
		
		if(password2.value == "")
		{
			password_info2.innerText="请输入密码";
		}
		else
		{
			if(password.value != password2.value)
			{
				password_info2.innerText="";
			}
			else
			{
				password_info2.innerText="新旧密码不能相同";
			}
		}	
	}
	judgeLogin3();
}
//密码失焦
function newPassBlur(){
	var password = document.getElementById("oldPass");	
	var password2 = document.getElementById("newPass");	
	var password_info2 = document.getElementById("newPass_info");
	if(password2.blur)
	{
		if(password2.value=="")
		{
			password_info2.innerText="密码不能为空";
		}
		else
		{
			if(password.value != password2.value)
			{
				password_info2.innerText="";
			}
			else
			{
				password_info2.innerText="新旧密码不能相同";
			}
		}
	}
	judgeLogin3();
}

//密码输入
function newPasskeypress(event)
{
	var password = document.getElementById("oldPass");	
	var password2 = document.getElementById("newPass");	
	var password_info2 = document.getElementById("newPass_info");

	if(password2.value != "")
	{
		if(password.value != password2.value)
		{
			password_info2.innerText="";
		}
		else
		{
			password_info2.innerText="新旧密码不能相同";
		}
	}	
	else
	{
		password_info2.innerText="请输入密码";
	}
	judgeLogin3();
}

function NameFocus1()
{
	var name = document.getElementById("name1");	
	var name_info = document.getElementById("name_info1");
	if(name.focus)
	{
		if(name.value == "")
		{
			name_info.innerText="请输入姓名";
		}
		else
		{
			if(strlen(name.value) > 10)
			{
				name_info.innerText="输入过长";
			}
		}
	
	}
	judgeLogin3();
}

function NameBlur1()
{
	var name = document.getElementById("name1");	
	var name_info = document.getElementById("name_info1");
	if(name.blur)
	{
		if(name.value == "")
		{
			name_info.innerText="姓名不能为空";
		}
		else
		{
			if(strlen(name.value) > 10)
			{
				name_info.innerText="输入过长";
			}
		}
	}
	judgeLogin3();
}

function Namekeypress1(event)
{
	var name = document.getElementById("name1");	
	var name_info = document.getElementById("name_info1");
	var numInput = document.getElementById("numInput5");
	if(name.value == "")
	{
	
		numInput.style.display = "none";
	}
	else
	{
		var num = strlen(name.value);
		numInput.style.display = "";
		numInput.innerHTML = "<p >已输入" + num + "个字符</p>";
	}
	
	judgeLogin3();
}

function paimingFocus1()
{
	var paiming = document.getElementById("paiming1");	
	var paiming_info = document.getElementById("paiming_info1");
	if(paiming.focus)
	{
		if(paiming.value == "")
		{
			paiming_info.innerText="请输入排名";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(paiming.value) == false)
			{
				paiming_info.innerText="排名必须是纯数字";
			}
			else if(strlen(paiming.value) > 3)
			{
				paiming_info.innerText="输入数字过大";
			}
			else
			{
				paiming_info.innerText="正确";
			}
		}
	
	}
	judgeLogin3();
}

function paimingBlur1()
{
	var paiming = document.getElementById("paiming1");	
	var paiming_info = document.getElementById("paiming_info1");
	if(paiming.blur)
	{
		if(paiming.value == "")
		{
			paiming_info.innerText="排名不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(paiming.value) == false)
			{
				paiming_info.innerText="排名必须是纯数字";
			}
			else if(strlen(paiming.value) > 3)
			{
				paiming_info.innerText="输入数字过大";
			}
			else
			{
				paiming_info.innerText="正确";
			}
		}
	}
	judgeLogin3();
}

function paimingkeypress1(event)
{
	var paiming = document.getElementById("paiming1");	
	var paiming_info = document.getElementById("paiming_info1");
	if(paiming.blur)
	{
		if(paiming.value == "")
		{
			paiming_info.innerText="排名不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(paiming.value) == false)
			{
				paiming_info.innerText="排名必须是纯数字";
			}
			else if(strlen(paiming.value) > 3)
			{
				paiming_info.innerText="输入数字过大";
			}
			else
			{
				paiming_info.innerText="正确";
			}
		}
		judgeLogin3();
	}
}

function judgeLogin3()
{
	var password = document.getElementById("oldPass");
	var password1 = document.getElementById("newPass");
	var paiming = document.getElementById("paiming1");
	var name = document.getElementById("name1");
	var CET6 = document.getElementById("CET6_1");
		
	var button = document.getElementById("submit3");
	var reg=/^\d+$/;
	if(password.value == "" || password.value == password1.value || password1.value == "" || name.value == "" || 
	strlen(name.value) > 10 || paiming.value == "" || reg.test(paiming.value) == false || strlen(paiming.value) > 4||
	name.value == "" || CET6.value == "" || reg.test(CET6.value) == false || strlen(CET6.value) < 1 || strlen(CET6.value) > 3)
	{
		button.disabled = true;
	}
	else
	{
		button.disabled = false;
	}
}



function CET6focus1()
{
	var UserName = document.getElementById("CET6_1");
	var UserName_info = document.getElementById("CET6_info1");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="请输入六级成绩";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="成绩必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 3 || strlen(UserName.value) < 1)
				{
					UserName_info.innerText="1-3位数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin3();
	}
}
//登陆UserName失焦
function CET6blur1()
{
	var UserName = document.getElementById("CET6_1");
	var UserName_info = document.getElementById("CET6_info1");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="六级成绩不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="成绩必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 3 || strlen(UserName.value) < 1)
				{
					UserName_info.innerText="1-3位数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin3();
	}
}
//UserNameInput
function CET6keypress1(event)
{
	var UserName = document.getElementById("CET6_1");
	var UserName_info = document.getElementById("CET6_info1");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="请输入六级成绩";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="成绩必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 3 || strlen(UserName.value) < 1)
				{
					UserName_info.innerText="1-3位数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeLogin3();
	}
}

// 巡检员搜索界面使用
//ipqcInput
function ipqckeypress(event)
{
	var UserName = document.getElementById("ipqcName");
	var numInput = document.getElementById("ipqcNumInput");
	if(UserName.value == "")
	{
		numInput.style.display = "none";
	}
	else
	{
		var num = strlen(UserName.value);
		numInput.style.display = "";
		numInput.innerHTML = "<p >已输入" + num + "个字符</p>";
	}
	
	judgeIpqc();
}

//ipqc 聚焦
function ipqcFocus()
{
	var UserName = document.getElementById("ipqcName");
	var UserName_info = document.getElementById("ipqc_info");
	if(UserName.focus)	//input聚焦
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="请输入巡检员编号(1-10位数字)";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="编号必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 10)
				{
					UserName_info.innerText="1-10个数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeIpqc();
	}
}

//ipqc失焦
function ipqcBlur()
{
	var UserName = document.getElementById("ipqcName");
	var UserName_info = document.getElementById("ipqc_info");
	if(UserName.blur)
	{
		if(UserName.value=="")
		{
			UserName_info.innerText="巡检员编号不能为空";
		}
		else
		{
			var reg=/^\d+$/;
			if(reg.test(UserName.value) == false)
			{
				UserName_info.innerText="巡检员编号必须是纯数字";
			}
			else
			{
				if(strlen(UserName.value) > 10)
				{
					UserName_info.innerText="1-10个数字";
				}
				else
				{
					UserName_info.innerText="正确";
				}
			}
		}
		
		judgeIpqc();
	}
	
}

function judgeIpqc()
{
	var UserName = document.getElementById("ipqcName");
	var button = document.getElementById("ipqcSubmit");
	
	var reg=/^\d+$/;
	if(UserName.value=="" || reg.test(UserName.value) == false || strlen(UserName.value) > 10)
	{
		button.disabled = true;
	}
	else
	{
		button.disabled = false;
	}
}


