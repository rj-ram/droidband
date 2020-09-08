package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Reminder extends AppCompatActivity implements View.OnClickListener {

    TextView t1,backtext,date;

    ImageView add,back;

    String day,month;

    private RecyclerView mRecyclerView,mRecyclerView1,mRecyclerView2,mRecyclerView3,mRecyclerView4;
    private UpcomingtaskAdapter mUpcomingtaskAdapter;
    private ArrayList<UpcomingtaskItem> mUpcomingtaskList;
    private ArrayList<RemindercontentItem> mReminderpaymentList;
    private ArrayList<RemindercontentItem> mRemindereventList;
    private ArrayList<RemindercontentItem> mReminderrechargeList;
    private ArrayList<RemindercontentItem> mReminderenergyList;
    private RequestQueue mRequestQueue;

    String URL_UPCOMINGTASK = "http://192.168.1.101/droidband/reminderfetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        t1 = (TextView) findViewById(R.id.reminderheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        backtext = (TextView) findViewById(R.id.reminderbacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.reminderbackicon);
        back.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recycler_view_upcomingtasks);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView1 = findViewById(R.id.recycler_view_remindercard);
        mRecyclerView1.setHasFixedSize(true);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView2 = findViewById(R.id.recycler_view_reminderevents);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView3 = findViewById(R.id.recycler_view_reminderrecharge);
        mRecyclerView3.setHasFixedSize(true);
        mRecyclerView3.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView4 = findViewById(R.id.recycler_view_reminderenergy);
        mRecyclerView4.setHasFixedSize(true);
        mRecyclerView4.setLayoutManager(new LinearLayoutManager(this));

        mUpcomingtaskList = new ArrayList<>();
        mReminderpaymentList = new ArrayList<>();
        mRemindereventList = new ArrayList<>();
        mReminderrechargeList = new ArrayList<>();
        mReminderenergyList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        loadProducts();
        loadPayment();
        loadEvent();
        loadRecharge();
        loadEnergy();

        date = (TextView) findViewById(R.id.reminderdate);

        add = (ImageView) findViewById(R.id.reminderadd);
        add.setOnClickListener(this);

        getDate();
    }

    public void getDate()
    {

        Calendar c = Calendar.getInstance();
        int dt = c.get(Calendar.DAY_OF_MONTH);
        int mt = c.get(Calendar.MONTH);
        int yr = c.get(Calendar.YEAR);

        switch (mt)
        {
            case 0:
                month = "JAN";
                day = dt +" " + month;
                break;

            case 1:
                month = "FEB";
                day = dt +" " + month;
                break;

            case 2:
                month = "MAR";
                day = dt + month;
                break;

            case 3:
                month = "APR";
                day = dt +" " + month;
                break;

            case 4:
                month = "MAY";
                day = dt +" " + month;
                break;

            case 5:
                month = "JUN";
                day = dt +" " + month;
                break;

            case 6:
                month = "JUL";
                day = dt +" " + month;
                break;

            case 7:
                month = "AUG";
                day = dt +" " + month;
                break;

            case 8:
                month = "SEP";
                day = dt +" " + month;
                break;

            case 9:
                month = "OCT";
                day = dt +" " + month;
                break;

            case 10:
                month = "NOV";
                day = dt +" " + month;
                break;

            case 11:
                month = "DEC";
                day = dt +" " + month;
                break;
        }

        date.setText(day);

    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_UPCOMINGTASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mUpcomingtaskList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {

                                JSONObject bandinfo = array.getJSONObject(i);

                                mUpcomingtaskList.add(new UpcomingtaskItem(
                                        bandinfo.getString("name")

                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            UpcomingtaskAdapter adapter = new UpcomingtaskAdapter(Reminder.this, mUpcomingtaskList);
                            mRecyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadPayment() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_UPCOMINGTASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mReminderpaymentList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {

                                JSONObject bandinfo = array.getJSONObject(i);

                                if(bandinfo.getString("category").equals("Transanction"))
                                {

                                    mReminderpaymentList.add(new RemindercontentItem(
                                            bandinfo.getString("name")

                                    ));

                                }
                            }
                            //creating adapter object and setting it to recyclerview
                            RemindercontentAdapter adapter = new RemindercontentAdapter(Reminder.this, mReminderpaymentList);
                            mRecyclerView1.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadEvent() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_UPCOMINGTASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mRemindereventList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {

                                JSONObject bandinfo = array.getJSONObject(i);

                                if(bandinfo.getString("category").equals("Events"))
                                {

                                    mRemindereventList.add(new RemindercontentItem(
                                            bandinfo.getString("name")

                                    ));

                                }
                            }
                            //creating adapter object and setting it to recyclerview
                            RemindercontentAdapter adapter = new RemindercontentAdapter(Reminder.this, mRemindereventList);
                            mRecyclerView2.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadRecharge() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_UPCOMINGTASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mReminderrechargeList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {

                                JSONObject bandinfo = array.getJSONObject(i);

                                if(bandinfo.getString("category").equals("Recharges"))
                                {

                                    mReminderrechargeList.add(new RemindercontentItem(
                                            bandinfo.getString("name")

                                    ));

                                }
                            }
                            //creating adapter object and setting it to recyclerview
                            RemindercontentAdapter adapter = new RemindercontentAdapter(Reminder.this, mReminderrechargeList);
                            mRecyclerView3.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadEnergy() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_UPCOMINGTASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mReminderenergyList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {

                                JSONObject bandinfo = array.getJSONObject(i);

                                if(bandinfo.getString("category").equals("Energy Payments"))
                                {

                                    mReminderenergyList.add(new RemindercontentItem(
                                            bandinfo.getString("name")

                                    ));

                                }
                            }
                            //creating adapter object and setting it to recyclerview
                            RemindercontentAdapter adapter = new RemindercontentAdapter(Reminder.this, mReminderenergyList);
                            mRecyclerView4.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onClick(View view) {

        if (view == add)
        {
            Intent intent = new Intent(Reminder.this, Addreminder.class);
            startActivity(intent);
        }

        if (view == back || view == backtext)
        {
            onBackPressed();
        }

    }
}
