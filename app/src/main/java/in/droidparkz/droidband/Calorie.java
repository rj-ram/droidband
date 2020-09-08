package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

import static in.droidparkz.droidband.Homewindow.MY_PREFS_SETCURRENT;
import static in.droidparkz.droidband.Setgoal.MY_PREFS_SETGOAL;

public class Calorie extends AppCompatActivity implements View.OnClickListener {

    TextView t1,backtext;

    ImageView back,refresh;

    PieChart pieChart;

    SharedPreferences prefs,prefs1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefs = getSharedPreferences(MY_PREFS_SETGOAL, MODE_PRIVATE);

        prefs1 = getSharedPreferences(MY_PREFS_SETCURRENT, MODE_PRIVATE);

        t1 = (TextView) findViewById(R.id.calorieheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        backtext = (TextView) findViewById(R.id.caloriebacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.caloriebackicon);
        back.setOnClickListener(this);

        refresh = (ImageView) findViewById(R.id.calorierefresh);
        refresh.setOnClickListener(this);

        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.animateY(1000,Easing.EaseInOutCubic);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        if (prefs1.getString("stepcalorie",null )== null)
        {
            getCalories();
        }
        else
        {
            ArrayList<PieEntry> yValues = new ArrayList<>();

            yValues.add(new PieEntry(Integer.valueOf(prefs1.getString("stepcalorie",null)),"Steps"));
            yValues.add(new PieEntry(Integer.valueOf(prefs1.getString("runningcalorie",null)),"Running"));
            yValues.add(new PieEntry(Integer.valueOf(prefs1.getString("cyclingcalorie",null)),"Cycling"));
            yValues.add(new PieEntry(Integer.valueOf(prefs1.getString("inactivecalorie",null)),"Inactive Calories"));

            PieDataSet dataSet = new PieDataSet(yValues,"Calories");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

            PieData data = new PieData((dataSet));
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);

            pieChart.setData(data);
        }

    }

    public void getCalories()
    {
        int min = 0;
        int max = Integer.valueOf(prefs.getString("targetcalorie",null));

        int random1 = new Random().nextInt((max - min) + 1) + min;
        int random2 = new Random().nextInt((max - min) + 1) + min;
        int random3 = new Random().nextInt((max - min) + 1) + min;
        int random4 = new Random().nextInt((max - min) + 1) + min;

        double per1 = 100.0 * random1 / max;
        double per2 = 100.0 * random2 / max;
        double per3 = 100.0 * random3 / max;
        double per4 = 100.0 * random4 / max;

        int percentage1 = (int) per1;
        int percentage2 = (int) per2;
        int percentage3 = (int) per3;
        int percentage4 = (int) per4;

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(percentage1,"Steps"));
        yValues.add(new PieEntry(percentage2,"Running"));
        yValues.add(new PieEntry(percentage3,"Cycling"));
        yValues.add(new PieEntry(percentage4,"Inactive Calories"));

        PieDataSet dataSet = new PieDataSet(yValues,"Calories");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_SETCURRENT, MODE_PRIVATE).edit();
        editor.putString("stepcalorie",String.valueOf(percentage1));
        editor.putString("runningcalorie",String.valueOf(percentage2));
        editor.putString("cyclingcalorie",String.valueOf(percentage3));
        editor.putString("inactivecalorie",String.valueOf(percentage4));
        editor.apply();

    }

    @Override
    public void onClick(View view) {

        if (view == back || view == backtext)
        {
            onBackPressed();
        }

        if (view == refresh)
        {
            getCalories();
        }

    }
}
