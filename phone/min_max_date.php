<?php
//$_POST['ID']=3;
if(isset($_POST['ID'])){
	require_once "../conn.php";
	$ID=$_POST['ID'];
	$sql="SELECT `Day` FROM `conditions` WHERE '$ID'=`RaspberryID` ORDER BY `Day`,`Time`";
	$result=$conn->query($sql);
	
	while ($row = $result->fetch_assoc()) {
    
	$list[]=$row;
	
  }
  print_r(json_encode($list, JSON_UNESCAPED_UNICODE));
}
?>