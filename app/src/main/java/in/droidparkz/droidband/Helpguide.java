package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Helpguide extends AppCompatActivity implements View.OnClickListener {

    TextView t1,backtext;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpguide);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextView)findViewById(R.id.helpguideheadtext);
        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");
        t1.setTypeface(custom_font);

        backtext = (TextView) findViewById(R.id.helpguidebacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.helpguidebackicon);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == back || view == backtext)
        {
            onBackPressed();
        }

    }
}
