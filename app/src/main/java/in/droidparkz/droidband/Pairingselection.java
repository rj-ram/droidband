package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

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

public class Pairingselection extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout refresh,exit;

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    private RecyclerView mRecyclerView;
    private PairingselectionAdapter mPairingselectionAdapter;
    private ArrayList<PairingselectionItem> mPairingselectionList;
    private RequestQueue mRequestQueue;

    String URL_BLUETOOTH = "http://192.168.1.101/droidband/bluetooth.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairingselection);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mRecyclerView = findViewById(R.id.recycler_view_bandlist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPairingselectionList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        delay();

        refresh = (RelativeLayout) findViewById(R.id.pairingselectionrefresh);
        refresh.setOnClickListener(this);

        exit = (RelativeLayout) findViewById(R.id.pairingselectionexit);
        exit.setOnClickListener(this);

    }

    private void delay()
    {
        mRecyclerView.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                mRecyclerView.setVisibility(View.VISIBLE);
                loadProducts();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BLUETOOTH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            int min = 0;
                            int max = array.length();
                            int random1 = new Random().nextInt((max - min) + 1) + min;

                            int random2= new Random().nextInt((max - random1) + 1) + random1;

                            mPairingselectionList.clear();

                            for (int i = random1; i <= random2; i++) {


                                JSONObject bandinfo = array.getJSONObject(i);

                                    mPairingselectionList.add(new PairingselectionItem(
                                            bandinfo.getString("name"),
                                            bandinfo.getString("macid")
                                    ));
                            }
                            //creating adapter object and setting it to recyclerview
                            PairingselectionAdapter adapter = new PairingselectionAdapter(Pairingselection.this, mPairingselectionList);
                            mRecyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            delay();
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

        if (view == refresh)
        {
            delay();
        }

        if (view == exit)
        {
            startActivity(new Intent(Pairingselection.this, Bandpairing.class));
        }
    }
}
