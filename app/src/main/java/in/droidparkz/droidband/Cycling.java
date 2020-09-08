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
import static in.droidparkz.droidband.Setgoal.MY_PREFS_SETGOAL;

public class Cycling extends AppCompatActivity implements View.OnClickListener {

    TextView t1,tardis,curdis,backtext;

    int currentdistance, progresspercentage;

    ImageView cycling,back,refresh;

    String targetdistance;

    SharedPreferences prefs,prefs1;

    ProgressBar progress;

    private RecyclerView mRecyclerView;
    private CyclingAdapter mCyclingAdapter;
    private ArrayList<CyclingItem> mCyclingList;
    private RequestQueue mRequestQueue;

    String URL_CYCLING = "http://192.168.1.101/droidband/cycling.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycling);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefs = getSharedPreferences(MY_PREFS_SETGOAL, MODE_PRIVATE);
        targetdistance = (prefs.getString("targetcycling", null)+" KM");

        prefs1 = getSharedPreferences(MY_PREFS_SETCURRENT, MODE_PRIVATE);

        t1 = (TextView) findViewById(R.id.cyclingheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        backtext = (TextView) findViewById(R.id.cyclingbacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.cyclingbackicon);
        back.setOnClickListener(this);

        refresh = (ImageView) findViewById(R.id.cyclingrefresh);
        refresh.setOnClickListener(this);

        tardis = (TextView) findViewById(R.id.cyclingtarget);
        tardis.setText(targetdistance);

        curdis = (TextView) findViewById(R.id.cyclingcurrent);
        progress = (ProgressBar) findViewById(R.id.cyclingprogressbar);

        cycling = (ImageView) findViewById(R.id.cyclingcycling);

        if (prefs1.getString("cyclingdistance",null )== null)
        {
            getDistance();
        }
        else
        {
            curdis.setText(prefs1.getString("cyclingdistance",null)+ " KM");
            progresspercentage = Integer.valueOf(prefs1.getString("cyclingpercentage",null));
            progress.setProgress(progresspercentage);

            if (progresspercentage >= 50)
            {
                cycling.setImageResource(R.drawable.cycling);
            }
            else
            {
                cycling.setImageResource(R.drawable.cycling1);
            }
        }

        mRecyclerView = findViewById(R.id.recycler_view_cycling);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCyclingList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        loadProducts();

    }

    public void getDistance()
    {
        int min = 0;
        int max = Integer.valueOf(prefs.getString("targetcycling",null));
        currentdistance = new Random().nextInt((max - min) + 1) + min;
        curdis.setText(String.valueOf(currentdistance)+" KM");
        percentage(currentdistance,max);

    }

    public void percentage(int min,int max)
    {
        double per = 100.0 * min / max;
        int percentage = (int) per;
        progress.setProgress(percentage);
        if (percentage >= 50)
        {
            cycling.setImageResource(R.drawable.cycling);
        }
        else
        {
            cycling.setImageResource(R.drawable.cycling1);
        }
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_SETCURRENT, MODE_PRIVATE).edit();
        editor.putString("cyclingdistance", String.valueOf(currentdistance));
        editor.putString("cyclingpercentage",String.valueOf(percentage));
        editor.apply();
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CYCLING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mCyclingList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {

                                JSONObject bandinfo = array.getJSONObject(i);

                                mCyclingList.add(new CyclingItem(
                                        bandinfo.getString("calories"),
                                        bandinfo.getString("distance")

                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            CyclingAdapter adapter = new CyclingAdapter(Cycling.this, mCyclingList);
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
            getDistance();
            loadProducts();
        }

    }
}
