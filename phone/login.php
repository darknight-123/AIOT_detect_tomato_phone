<?php

 if(isset($_POST['Username'])&& isset($_POST['Password']))
 {
	 require_once "../conn.php";
	 $Username=$_POST['Username'];
	 $Password=$_POST['Password'];
	 $sql="select * from user where Username='$Username'";
	 $result=$conn->query($sql);
	 $row = $result->fetch_assoc();
	 $password_hash= $row['Password'];
	if($result->num_rows >0&&password_verify($Password, $password_hash))
	{
			echo "success";
			$_POST['Result']="success";
	}
	else
	{
		 echo "failure";
		 $_POST['Result']="failure";
	}
	 
	 
 }
?>