<?php
//$_POST['Username']='ccc';
if(isset($_POST['Username']))
{
	require_once "../conn.php";
	$Username=$_POST['Username'];
	$sql="select * from raspberry where Username='$Username'";
	$result=$conn->query($sql);
	while ($row = $result->fetch_assoc()) {
    
	$list[]=$row;
	
  }
  print_r(json_encode($list, JSON_UNESCAPED_UNICODE));
  
}

?>