package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Addreminder extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout datelayout,timelayout;

    TextView t1,backtext,date,time;

    EditText nm;

    ImageView back,submit;

    Spinner spinner1,spinner4;

    String name,category,dt,tm,priority;

    String Getname,Getcategory,Getdate,Gettime,Getpriority;

    String DataParseUrl = "http://192.168.1.101/droidband/reminderset.php";

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "Addreminder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextView) findViewById(R.id.addreminderheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        backtext = (TextView) findViewById(R.id.addreminderbacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.addreminderbackicon);
        back.setOnClickListener(this);

        nm = (EditText) findViewById(R.id.addremindername);

        addItemsOnSpinner1();
        addItemsOnSpinner4();

        datelayout = (RelativeLayout) findViewById(R.id.addreminderdate);
        timelayout = (RelativeLayout) findViewById(R.id.addremindertime);

        submit = (ImageView) findViewById(R.id.addremindersubmit);

        date = (TextView) findViewById(R.id.addreminderdatespinner);
        time = (TextView) findViewById(R.id.addremindertimespinner);

        datelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Addreminder.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date1 = day + "/" + month + "/" + year;
                date.setText(date1);
            }
        };

        timelayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Addreminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        submit.setOnClickListener(this);

    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.addremindercategoryspinner);
        List<String> list3 = new ArrayList<String>();
        list3.add("Category");
        list3.add("Transanction");
        list3.add("Events");
        list3.add("Recharges");
        list3.add("Energy Payments");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text_color, list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinner4() {

        spinner4 = (Spinner) findViewById(R.id.addreminderpriorityspinner);
        List<String> list3 = new ArrayList<String>();
        list3.add("Priority");
        list3.add("High");
        list3.add("Medium");
        list3.add("Low");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text_color, list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter);
    }

    public void getInput()
    {
        name = nm.getText().toString().trim();
        category = String.valueOf(spinner1.getSelectedItem());
        dt = date.getText().toString().trim();
        tm = time.getText().toString().trim();
        priority = String.valueOf(spinner4.getSelectedItem());

        if(name == null)
        {
            Toast.makeText(Addreminder.this, "Enter Reminder Name", Toast.LENGTH_LONG).show();
        }

        else if(category == null || category.equals("Category"))
        {
            Toast.makeText(Addreminder.this, "Select Category", Toast.LENGTH_LONG).show();
        }

        else if(dt == null)
        {
            Toast.makeText(Addreminder.this, "Select Date", Toast.LENGTH_LONG).show();
        }

        else if(tm == null)
        {
            Toast.makeText(Addreminder.this, "Select Time", Toast.LENGTH_LONG).show();
        }

        else if(priority == null || priority.equals("Priority"))
        {
            Toast.makeText(Addreminder.this, "Select Priority", Toast.LENGTH_LONG).show();
        }

        else
        {
            GetData();
        }

    }

    public void GetData(){

        Getname = name;
        Getcategory = category;
        Getdate = dt;
        Gettime = tm;
        Getpriority = priority;

        SendDataToServer();

    }

    public void SendDataToServer(){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", Getname));
                nameValuePairs.add(new BasicNameValuePair("category", Getcategory));
                nameValuePairs.add(new BasicNameValuePair("date", Getdate));
                nameValuePairs.add(new BasicNameValuePair("time", Gettime));
                nameValuePairs.add(new BasicNameValuePair("priority", Getpriority));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(DataParseUrl);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Submit Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(Addreminder.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Addreminder.this,Reminder.class));

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    @Override
    public void onClick(View view) {

        if (view == back || view == backtext)
        {
            onBackPressed();
        }

        if (view == submit)
        {
            getInput();
        }

    }
}
