package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import static in.droidparkz.droidband.PairingselectionAdapter.PairingselectionViewHolder.MY_PREFS_NAME;

public class Bandpairing extends AppCompatActivity implements View.OnClickListener {

    TextView t1,t2;

    ImageView band;

    String bandname,bandmac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandpairing);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextView) findViewById(R.id.bandpairingheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        t2 = (TextView) findViewById(R.id.bandpairingbottomtext);

        Typeface custom_font1 = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/arial.ttf");

        t2.setTypeface(custom_font1);

        checkpairing();

        band = (ImageView) findViewById(R.id.bandpairingimage);
        band.setOnClickListener(this);

    }

    public void checkpairing()
    {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        bandname = prefs.getString("bandname", null);
        bandmac = prefs.getString("bandmacid", null);

        if (bandname != null && bandmac != null )
        {
            startActivity(new Intent(Bandpairing.this, Homewindow.class));
        }

    }

    @Override
    public void onClick(View view) {

        if (view == band)
        {
            startActivity(new Intent(Bandpairing.this, Pairingselection.class));
        }
    }
}
