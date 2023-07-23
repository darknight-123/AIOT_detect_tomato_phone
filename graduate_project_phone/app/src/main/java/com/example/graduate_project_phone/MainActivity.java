package com.example.graduate_project_phone;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText account,password;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account=findViewById(R.id.editTextID);
        password=findViewById(R.id.editTextPassword);
        btn=findViewById(R.id.button);
        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:

                try {
                    String field[]=new String[2],data[]=new String[2];
                    field[0]="Username";
                    field[1]="Password";
                    data[0]= String.valueOf(account.getText());
                    data[1]=String.valueOf(password.getText());
                    PutData putdata=new PutData("http://10.0.2.2:80/tomato/phone/login.php","POST",field,data);
                    if(putdata.startPut()){
                        if(putdata.onComplete()){
                            String result=putdata.getData();


                            if(result.equals("success")){

                                Intent intent=new Intent();
                                intent.setClass(MainActivity.this,Condition.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("Username",data[0]);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                Log.i("POST", result);
                            }else{
                                Toast.makeText(this,"username or password error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(this,"server broken",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}