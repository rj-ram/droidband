package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Heartrate extends AppCompatActivity implements View.OnClickListener {

    TextView t1,backtext,current,rate,date,time,status;

    int currentbeat;

    ImageView back,refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextView) findViewById(R.id.heartrateheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        backtext = (TextView) findViewById(R.id.heartratebacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.heartratebackicon);
        back.setOnClickListener(this);

        refresh = (ImageView) findViewById(R.id.heartraterefresh);
        refresh.setOnClickListener(this);

        current = (TextView) findViewById(R.id.bpmtext);
        rate = (TextView)findViewById(R.id.ratetxt);
        date = (TextView)findViewById(R.id.heartratedatetxt);
        time = (TextView)findViewById(R.id.heartratetimetxt);
        status = (TextView)findViewById(R.id.heartratestatustxt);

        heartRate();
    }

    public void heartRate()
    {

        int min = 55;
        int max = 110;
        currentbeat = new Random().nextInt((max - min) + 1) + min;

        if (currentbeat > 85)
        {
            status.setText("STATUS : HIGH BPM");
        }

        if (currentbeat < 85)
        {
            status.setText("STATUS : NORMAL BPM");
        }
        if(currentbeat <70)
        {
            status.setText("STATUS : LOW BPM");
        }

        current.setText(String.valueOf(currentbeat));

        rate.setText("RATE : "+String.valueOf(currentbeat)+ " BPM");

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = mdformat.format(c);

        date.setText("DATE : "+formattedDate);
        time.setText("TIME : "+strDate);

    }

    @Override
    public void onClick(View view) {

        if (view == back || view == backtext)
        {
            onBackPressed();
        }

        if (view == refresh)
        {
            heartRate();
        }

    }
}
