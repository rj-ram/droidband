package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.Random;

import static in.droidparkz.droidband.Homewindow.MY_PREFS_SETCURRENT;

public class Sleepwindow extends AppCompatActivity implements View.OnClickListener {

    TextView t1,backtext,avgtxt;

    ImageView back,refresh;

    ProgressBar sunday,monday,tuesday,wednesday,thursday,friday,saturday;

    SharedPreferences prefs;

    private RecyclerView mRecyclerView;
    private SleepwindowAdapter mSleepwindowAdapter;
    private ArrayList<SleepwindowItem> mSleepwindowList;
    private RequestQueue mRequestQueue;

    String URL_SLEEPWINDOW = "http://192.168.1.101/droidband/sleepwindow.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepwindow);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefs = getSharedPreferences(MY_PREFS_SETCURRENT, MODE_PRIVATE);

        t1 = (TextView) findViewById(R.id.sleepwindowheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        backtext = (TextView) findViewById(R.id.sleepwindowbacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.sleepwindowbackicon);
        back.setOnClickListener(this);

        refresh = (ImageView) findViewById(R.id.sleepwindowrefresh);
        refresh.setOnClickListener(this);

        avgtxt = (TextView) findViewById(R.id.sleepwindowavg);

        sunday = (ProgressBar) findViewById(R.id.sleepwindowgraphsunday);
        monday = (ProgressBar) findViewById(R.id.sleepwindowgraphmonday);
        tuesday = (ProgressBar) findViewById(R.id.sleepwindowgraphtuesday);
        wednesday = (ProgressBar) findViewById(R.id.sleepwindowgraphwednesday);
        thursday = (ProgressBar) findViewById(R.id.sleepwindowgraphthursday);
        friday = (ProgressBar) findViewById(R.id.sleepwindowgraphfriday);
        saturday = (ProgressBar) findViewById(R.id.sleepwindowgraphsaturday);

        if (prefs.getString("sundaypercentage",null )== null)
        {
            getSleep();
        }
        else
        {
            sunday.setProgress(Integer.valueOf(prefs.getString("sundaypercentage",null)));
            monday.setProgress(Integer.valueOf(prefs.getString("mondaypercentage",null)));
            tuesday.setProgress(Integer.valueOf(prefs.getString("tuesdaypercentage",null)));
            wednesday.setProgress(Integer.valueOf(prefs.getString("wednesdaypercentage",null)));
            thursday.setProgress(Integer.valueOf(prefs.getString("thursdaypercentage",null)));
            friday.setProgress(Integer.valueOf(prefs.getString("fridaypercentage",null)));
            saturday.setProgress(Integer.valueOf(prefs.getString("saturdaypercentage",null)));
            avgtxt.setText(prefs.getString("averagesleep",null)+" Hr Avg");
        }

        mRecyclerView = findViewById(R.id.recycler_view_sleepwindow);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSleepwindowList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        loadProducts();

    }

    public void getSleep()
    {
        int min = 3;
        int max = 8;

        int random1 = new Random().nextInt((max - min) + 1) + min;
        int random2 = new Random().nextInt((max - min) + 1) + min;
        int random3 = new Random().nextInt((max - min) + 1) + min;
        int random4 = new Random().nextInt((max - min) + 1) + min;
        int random5 = new Random().nextInt((max - min) + 1) + min;
        int random6 = new Random().nextInt((max - min) + 1) + min;
        int random7 = new Random().nextInt((max - min) + 1) + min;

        double avg = (random1+random2+random3+random4+random5+random6+random7)/7;

        double per1 = 100.0 * random1 / max;
        double per2 = 100.0 * random2 / max;
        double per3 = 100.0 * random3 / max;
        double per4 = 100.0 * random4 / max;
        double per5 = 100.0 * random5 / max;
        double per6 = 100.0 * random6 / max;
        double per7 = 100.0 * random7 / max;

        int percentage1 = (int) per1;
        int percentage2 = (int) per2;
        int percentage3 = (int) per3;
        int percentage4 = (int) per4;
        int percentage5 = (int) per5;
        int percentage6 = (int) per6;
        int percentage7 = (int) per7;
        int average = (int) avg;

        sunday.setProgress(percentage1);
        monday.setProgress(percentage2);
        tuesday.setProgress(percentage3);
        wednesday.setProgress(percentage4);
        thursday.setProgress(percentage5);
        friday.setProgress(percentage6);
        saturday.setProgress(percentage7);
        avgtxt.setText(String.valueOf(average)+" Hr Avg");

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_SETCURRENT, MODE_PRIVATE).edit();
        editor.putString("sundaypercentage",String.valueOf(percentage1));
        editor.putString("mondaypercentage",String.valueOf(percentage2));
        editor.putString("tuesdaypercentage",String.valueOf(percentage3));
        editor.putString("wednesdaypercentage",String.valueOf(percentage4));
        editor.putString("thursdaypercentage",String.valueOf(percentage5));
        editor.putString("fridaypercentage",String.valueOf(percentage6));
        editor.putString("saturdaypercentage",String.valueOf(percentage7));
        editor.putString("averagesleep",String.valueOf(average));
        editor.apply();

    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SLEEPWINDOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mSleepwindowList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {


                                JSONObject bandinfo = array.getJSONObject(i);

                                mSleepwindowList.add(new SleepwindowItem(
                                        bandinfo.getString("day"),
                                        bandinfo.getString("start"),
                                        bandinfo.getString("stop"),
                                        bandinfo.getString("duration"),
                                        bandinfo.getString("progress")
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            SleepwindowAdapter adapter = new SleepwindowAdapter(Sleepwindow.this, mSleepwindowList);
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

    @Override
    public void onClick(View view) {

        if (view == back || view == backtext)
        {
            onBackPressed();
        }

        if (view == refresh)
        {
            getSleep();
            loadProducts();
        }

    }
}
