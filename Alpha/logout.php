<?php
require_once("functions.php");
dbconn();
logoutsession();
header("Location:index.php");
?>
