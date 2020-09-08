package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static in.droidparkz.droidband.Deviceinfo.MY_PREFS_BANDINFO;

public class Connectedapps extends AppCompatActivity implements View.OnClickListener {

    TextView t1,backtext;

    String bandimage,a1,a2,a3,a4,b1,b2,b3,b4;

    ImageView band,back;

    Switch s1,s2,s3,s4;

    public static final String MY_PREFS_APPINFO = "MyPrefsAppInfo";

    String URL_APPINFO = "http://192.168.1.101/droidband/connectedapp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectedapps);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextView) findViewById(R.id.connectedappheadtext);

        band = (ImageView)findViewById(R.id.connectedappbandimage);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_BANDINFO, MODE_PRIVATE);
        bandimage = prefs.getString("bandimage", null);

        Picasso.with(this).load(bandimage).fit().centerInside().into(band);

        s1 = (Switch) findViewById(R.id.switch1);
        s2 = (Switch) findViewById(R.id.switch2);
        s3 = (Switch) findViewById(R.id.switch3);
        s4 = (Switch) findViewById(R.id.switch4);

        backtext = (TextView) findViewById(R.id.connectedappbacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.connectedappbackicon);
        back.setOnClickListener(this);

        getState();

    }

    public void getState()
    {

        try
        {
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_APPINFO, MODE_PRIVATE);
            b1 = prefs.getString("incoming", null);
            b2 = prefs.getString("whatsapp", null);
            b3 = prefs.getString("facebook", null);
            b4 = prefs.getString("messages", null);

            if(b1.equals("1"))
            {
                s1.setChecked(true);
            }
            else
            {
                s1.setChecked(false);
            }

            if(b2.equals("1"))
            {
                s2.setChecked(true);
            }
            else
            {
                s2.setChecked(false);
            }

            if(b3.equals("1"))
            {
                s3.setChecked(true);
            }
            else
            {
                s3.setChecked(false);
            }

            if(b4.equals("1"))
            {
                s4.setChecked(true);
            }
            else
            {
                s4.setChecked(false);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            setState();
        }

    }

    public void setState()
    {

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked)
            {
                a1="1";
            }
            else
            {
                 a1="0";
            }
         }

         });
        if(s2.isChecked())
        {
            a2="1";
        }
        else
        {
            a2="0";
        }
        if(s3.isChecked())
        {
            a3="1";
        }
        else
        {
            a3="0";
        }
        if(s4.isChecked())
        {
            a4="1";
        }
        else
        {
            a4="0";
        }

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_APPINFO, MODE_PRIVATE).edit();
        editor.putString("incoming", a1);
        editor.putString("whatsapp", a2 );
        editor.putString("facebook",a3);
        editor.putString("messages",a4);
        editor.apply();

    }

    @Override
    public void onClick(View view) {

        if (view == back || view == backtext)
        {
            onBackPressed();
        }

    }
}
