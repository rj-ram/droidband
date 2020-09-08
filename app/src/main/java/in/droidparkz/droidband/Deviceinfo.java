package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static in.droidparkz.droidband.PairingselectionAdapter.PairingselectionViewHolder.MY_PREFS_NAME;

public class Deviceinfo extends AppCompatActivity implements View.OnClickListener {

    TextView t1,macid,name,bluetooth,battery,firmware,backtext;

    String mac,imgurl,brandurl,newfirmware,updatelink,oldfirmware;

    String o,n;

    ImageView bandimage,back;

    Context context;

    RelativeLayout update;

    ProgressDialog progressDialog;

    private final int SPLASH_DISPLAY_LENGTH = 8000;

    String URL_DEVICEINFO = "http://192.168.1.101/droidband/bandinfo.php";

    public static final String MY_PREFS_BANDINFO = "MyPrefsBandInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceinfo);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextView) findViewById(R.id.deviceinfoheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mac = prefs.getString("bandmacid", null);

        macid = (TextView) findViewById(R.id.devicebandmacid);
        name = (TextView) findViewById(R.id.devicebandname);
        bluetooth = (TextView) findViewById(R.id.devicebandbluetooth);
        battery = (TextView) findViewById(R.id.devicebandbattery);
        firmware = (TextView) findViewById(R.id.devicebandfirmware);
        bandimage = (ImageView) findViewById(R.id.devicebandimage);
        update = (RelativeLayout) findViewById(R.id.deviceupdate);

        backtext = (TextView) findViewById(R.id.deviceinfobacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.deviceinfobackicon);
        back.setOnClickListener(this);

        loadProducts();
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DEVICEINFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i <= array.length(); i++) {

                                JSONObject bandinfo = array.getJSONObject(i);

                                if (bandinfo.getString("macid").equals(mac))
                                {
                                    name.setText(bandinfo.getString("name"));
                                    macid.setText("MAC ID : "+bandinfo.getString("macid"));
                                    bluetooth.setText("Bluetooth Version : "+bandinfo.getString("bluetooth"));
                                    battery.setText("Battery Status : "+bandinfo.getString("battery"));
                                    firmware.setText("Firmware : "+bandinfo.getString("firmware"));
                                    imgurl = bandinfo.getString("image");
                                    oldfirmware = bandinfo.getString("firmware");
                                    newfirmware = bandinfo.getString("nfirmware");
                                    updatelink = bandinfo.getString("updatelink");
                                    brandurl = bandinfo.getString("brandimage");

                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_BANDINFO, MODE_PRIVATE).edit();
                                    editor.putString("bandname", name.getText().toString());
                                    editor.putString("bandmacid", macid.getText().toString());
                                    editor.putString("bluetooth",bluetooth.getText().toString());
                                    editor.putString("battery", battery.getText().toString());
                                    editor.putString("firmware", oldfirmware);
                                    editor.putString("bandimage",imgurl);
                                    editor.putString("newfirmware",newfirmware);
                                    editor.putString("updatelink",updatelink);
                                    editor.putString("brandimage",brandurl);
                                    editor.apply();

                                    Picasso.with(context).load(imgurl).fit().centerInside().into(bandimage);

                                    update.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            progress();
                                        }
                                    });

                                }

                            }
                        }

                        catch (JSONException e) {
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

    public void progress()
    {
        progressDialog = new ProgressDialog(Deviceinfo.this);
        progressDialog.setMessage("Checking..."); // Setting Message
        progressDialog.setTitle("Firmware Update"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();
        progressDialog.setCancelable(true);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_BANDINFO, MODE_PRIVATE);
                o = prefs.getString("firmware", null);
                n = prefs.getString("newfirmware", null);

                if (o.equals(n))
                {
                    progressDialog.dismiss();
                    Toast.makeText(Deviceinfo.this,"No Updates Available",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(Deviceinfo.this,Updatewindow.class);
                    startActivity(intent);
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onClick(View view) {

        if (view == back || view == backtext)
        {
            Intent intent = new Intent(Deviceinfo.this,Profilewindow.class);
            startActivity(intent);
        }

    }
}
