package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static in.droidparkz.droidband.Deviceinfo.MY_PREFS_BANDINFO;
import static in.droidparkz.droidband.Userinfo.MY_PREFS_USERINFO;

public class Personalinfo extends AppCompatActivity implements View.OnClickListener {

    TextView t1;

    TextView name,email,height,weight,phone,backtext;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextView) findViewById(R.id.personalinfoheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        name = (TextView) findViewById(R.id.personalinfousername);
        email = (TextView) findViewById(R.id.personalinfouseremail);
        height = (TextView) findViewById(R.id.personalinfopersonheight);
        weight = (TextView) findViewById(R.id.personalinfopersonweight);
        phone = (TextView) findViewById(R.id.personalinfouserphone);

        backtext = (TextView) findViewById(R.id.personalinfobacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.personalinfobackicon);
        back.setOnClickListener(this);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_USERINFO, MODE_PRIVATE);
        name.setText(prefs.getString("username", null));
        email.setText("Email : "+prefs.getString("useremail", null));
        height.setText("Height : "+prefs.getString("userheight", null));
        weight.setText("Weight : "+prefs.getString("userweight", null));
        phone.setText("Phone : "+prefs.getString("usercontact", null));
    }

    @Override
    public void onClick(View view) {

        if (view == back || view == backtext)
        {
            onBackPressed();
        }

    }
}
