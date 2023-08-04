package com.example.graduate_project_phone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Condition extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private TextView text_growth,text_date;
    private LineChart chart;
    private Button query,date;
    private String username="None";
    private String default_raspberry="None",default_date="None",default_object="None";
    private ArrayList<Rasberry_pie>rasberry=new ArrayList<>();
    private String min_date="None";
    private String max_date="None";
    private DatePickerDialog.OnDateSetListener datePicker;
    private Calendar calendar = Calendar.getInstance();
    private Spinner spinner_ID,spinner_object;
    private String default_temperature;
    private FragmentManager fmgr;
    private Button chart_btn,image_btn;
    private ArrayList<Object>objects=new ArrayList<>();
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);

        Bundle bundle= getIntent().getExtras();

        username=bundle.getString("Username");

        query=findViewById(R.id.query);
        query.setOnClickListener(this);
        //得到pi
        getRaspberry();
        default_raspberry=rasberry.get(0).getID();
        //得到日期
        getdate();
        default_date=max_date;

        //得到object數據
        getObject();

        //日期選擇
        text_date=findViewById(R.id.date_indicate);
        date=findViewById(R.id.date_picker);
        date.setOnClickListener(this);
        text_date.setText("日期:  "+max_date.substring(0,4)+"/"+max_date.substring(5,7)+"/"+max_date.substring(8,10));
        datePicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                text_date.setText("日期：" + sdf.format(calendar.getTime()));
                default_date=sdf.format(calendar.getTime()).substring(0,4)+"-"+sdf.format(calendar.getTime()).substring(5,7)+"-"+sdf.format(calendar.getTime()).substring(8,10);
            }
        };
        //機台選單創建
        spinner_ID=findViewById(R.id.spinner);
        String ID[]=new String[rasberry.size()];
        for (int i=0;i<rasberry.size();i++){
            ID[i]=rasberry.get(i).getID();
        }
        spinner_ID.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence>adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,ID);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ID.setAdapter(adapter);
        //生長階段
        text_growth=findViewById(R.id.growth);
        getGrowth();
        //物件選單創建
        spinner_object=findViewById(R.id.object);
        adapter=ArrayAdapter.createFromResource(this,
                R.array.object,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_object.setAdapter(adapter);
        spinner_object.setOnItemSelectedListener(this);

        default_object="moisture";

        chart_btn=findViewById(R.id.chart_button);
        image_btn=findViewById(R.id.image_button);
        chart_btn.setOnClickListener(this);
        image_btn.setOnClickListener(this);
        img=findViewById(R.id.imageView2);




        //圖表創建
        chart=findViewById(R.id.lineChart);

        chart.setData(getLineData(default_object));
        chart.notifyDataSetChanged();
        chart.invalidate();

    }



    //獲得生長期
    private void getGrowth(){
        try{
            String field[]=new String[2];
            String data[]=new String[2];
            field[0]="ID";
            field[1]="Day";
            data[0]=default_raspberry;
            data[1]=default_date;
            PutData putData=new PutData("http://10.0.2.2:80/tomato/phone/growth.php","POST",field,data);
            if(putData.startPut()){
                if(putData.onComplete()){
                    String result=putData.getResult();
                    text_growth.setText("生長階段:無此資料");
                    switch(result){
                        case "0":
                            text_growth.setText("生長階段:發芽期");
                            break;
                        case "1":
                            text_growth.setText("生長階段:成長期");
                            break;
                        case "2":
                            text_growth.setText("生長階段:開花期");
                            break;
                        case "3":
                            text_growth.setText("生長階段:結果期");
                            break;

                    }

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //獲得物件
    private  void getObject(){
        objects.clear();
        try{
            String field[]=new String[2];
            String data[]=new String[2];
            field[0]="ID";
            field[1]="Day";
            data[0]=default_raspberry;
            data[1]=default_date;
            PutData putData=new PutData("http://10.0.2.2:80/tomato/phone/condition.php","POST",field,data);
            if(putData.startPut()){
                if(putData.onComplete()){
                    String result=putData.getResult();

                    JSONArray array=new JSONArray(result);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        objects.add(new Object(object.getString("Time"),Integer.valueOf(object.getString("moisture")),object.getString("Picture"),0,Integer.valueOf(object.getString("TomatoWorm")),Integer.valueOf(object.getString("BeetWorm")),Integer.valueOf(object.getString("TabaccoWorm")),Integer.valueOf(object.getString("Problems")),Integer.valueOf(object.getString("None")),Integer.valueOf(object.getString("Tomato")),Integer.valueOf(object.getString("TomatoFlower"))));

                    }

                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        try {
            String field[]=new String[2];
            String data[]=new String[2];
            field[0]="ID";
            field[1]="Day";
            data[0]=default_raspberry;
            data[1]=default_date;
            PutData putData=new PutData("http://10.0.2.2:80/tomato/phone/temperature.php","POST",field,data);
            if(putData.startPut()){
                if(putData.onComplete()){
                    String result=putData.getResult();

                    JSONArray array=new JSONArray(result);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                       for(int t=0;t<objects.size();t++){

                           if(objects.get(t).getTime().equals(object.getString("Time"))){

                            objects.get(t).setTemperature(Integer.valueOf(object.getString("Tempature")));
                           }
                       }

                    }

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //獲得機台
    private  void getRaspberry(){
        try {
            String field[]=new String[1];
            String data[]=new String[1];
            field[0]="Username";
            data[0]=username;
            PutData putdata=new PutData("http://10.0.2.2:80/tomato/phone/Raspberry.php","POST",field,data);
            if(putdata.startPut()){
                if(putdata.onComplete()){

                    String result=putdata.getResult();
                    JSONArray array = new JSONArray(result);
                    for(int i=0;i< array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        rasberry.add(new Rasberry_pie(object.getString("RaspberryID"),object.getString("is_tempature")));
                        if(object.getString("is_tempature")=="Y"){
                            default_temperature=object.getString("RaspberryID");
                        }

                    }

                }
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //獲得最大小日期
    private  void getdate(){
        try{
            String field[]=new String[1];
            String data[]=new String[1];
            field[0]="ID";
            data[0]=default_raspberry;
            PutData putdata=new PutData("http://10.0.2.2:80/tomato/phone/min_max_date.php","POST",field,data);
            if(putdata.startPut()){
                if(putdata.onComplete()){
                    String result=putdata.getResult();
                    JSONArray array=new JSONArray(result);
                    JSONObject object;

                    object = array.getJSONObject(0);
                    min_date=object.getString("Day");
                    object = array.getJSONObject(array.length()-1);
                    max_date=object.getString("Day");


                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //圖表建造

    private LineData getLineData(String object_name){
        LineDataSet dataSetA = new LineDataSet(getChartData(object_name), "LabelA");

        List<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA); // add the datasets

        return new LineData(getLabels(), dataSets);
    }
    private List<String> getLabels(){
        List<String> chartLabels = new ArrayList<>();
        for(int i=0;i<objects.size();i++){
            chartLabels.add(objects.get(i).getTime());
        }
        return chartLabels;
    }

    private List<Entry> getChartData(String object_name){


        List<Entry> chartData = new ArrayList<>();
        for(int i=0;i<objects.size();i++){
            switch (object_name){
                case "moisture":
                    chartData.add(new BarEntry(objects.get(i).getMoisture(),i));
                    break;
                case "temperature":
                    chartData.add(new BarEntry(objects.get(i).getTemperature(),i));
                    break;
                case "TomatoWorm":
                    chartData.add(new BarEntry(objects.get(i).getTomatoWorm(),i));
                    break;
                case "BeetWorm":
                    chartData.add(new BarEntry(objects.get(i).getBeetWorm(),i));
                    break;
                case "TabacooWorm":
                    chartData.add(new BarEntry(objects.get(i).getTabacooWorm(),i));
                    break;
                case "Problem_leaf":
                    chartData.add(new BarEntry(objects.get(i).getProblems(),i));
                    break;
                case "None":
                    chartData.add(new BarEntry(objects.get(i).getNone(),i));
                    break;
                case "Tomato":
                    chartData.add(new BarEntry(objects.get(i).getTomato(),i));
                    break;
                case "TomatoFlower":
                    chartData.add(new BarEntry(objects.get(i).getTomatoFlower(),i));
                    break;
            }

        }
        return chartData;
    }

    @Override
    public void onClick(View v) {
        ArrayAdapter<CharSequence>adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item);
        switch (v.getId()){
            case R.id.query:

                //Toast.makeText(this,"Date="+default_raspberry,Toast.LENGTH_LONG).show();
                getObject();
                getGrowth();

                chart.setData(getLineData(default_object));
                chart.notifyDataSetChanged();
                chart.invalidate();
                chart_btn.performClick();

                break;
            case R.id.date_picker:
                DatePickerDialog dialog = new DatePickerDialog(Condition.this,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                Date mindate=new Date();
                Date maxdate=new Date();
                Calendar mcalendar=Calendar.getInstance();
                mcalendar.set(Integer.valueOf(min_date.substring(0,4)),Integer.valueOf(min_date.substring(5,7))-1,Integer.valueOf(min_date.substring(8,10)));
                mindate.setTime(mcalendar.getTimeInMillis());
                dialog.getDatePicker().setMinDate(mindate.getTime());


                Calendar macalendar=Calendar.getInstance();
                macalendar.set(Integer.valueOf(max_date.substring(0,4)),Integer.valueOf(max_date.substring(5,7))-1,Integer.valueOf(max_date.substring(8,10)));
                maxdate.setTime(macalendar.getTimeInMillis());
                dialog.getDatePicker().setMaxDate(maxdate.getTime());

                dialog.show();
                break;
            case R.id.chart_button:
                chart.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);

                adapter=ArrayAdapter.createFromResource(this,
                        R.array.object,
                        android.R.layout.simple_spinner_dropdown_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_object.setAdapter(adapter);
                break;
            case R.id.image_button:

                chart.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                String Time[]=new String[objects.size()];
                if(objects.size()==0){
                    Time=new String[1];
                    Time[0]="無此資料";
                    img.setImageResource(0);
                }
                else {
                    for (int i = 0; i < objects.size(); i++) {
                        Time[i] = objects.get(i).getTime();
                    }

                    Glide.with(this).load("http://10.0.2.2:80/tomato/"+objects.get(0).getPicture_url()).into(img);
                }

                ArrayAdapter<CharSequence>adapter1=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,Time);
                spinner_object.setAdapter(adapter1);

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.object){
            if(chart.getVisibility()==View.VISIBLE) {
                default_object= (String) spinner_object.getSelectedItem();
            }
            else{

                Glide.with(this).load("http://10.0.2.2:80/tomato/"+objects.get(position).getPicture_url()).into(img);
            }

        }
        else if(parent.getId()==R.id.spinner){

                default_raspberry = String.valueOf(spinner_ID.getSelectedItem());
                getObject();

        }

        chart.setData(getLineData(default_object));
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}