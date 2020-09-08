package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static in.droidparkz.droidband.Deviceinfo.MY_PREFS_BANDINFO;
import static in.droidparkz.droidband.PairingselectionAdapter.PairingselectionViewHolder.MY_PREFS_NAME;

public class Updatewindow extends AppCompatActivity implements View.OnClickListener {

    ImageView brandimage,close;

    TextView firmware;

    ProgressBar mProgressBar;

    RelativeLayout update;

    String nfirmware,imgurl,mac,Getmac,Getfirmware;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    String DataParseUrl = "http://192.168.1.101/droidband/firmwareupdate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatewindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        brandimage = (ImageView) findViewById(R.id.updatewindowbrandimage);
        firmware = (TextView) findViewById(R.id.updatewindowbandfirmware);
        update = (RelativeLayout) findViewById(R.id.updatewindowbutton);
        mProgressBar = (ProgressBar) findViewById(R.id.updatewindowprogressbar);
        close = (ImageView) findViewById(R.id.updatewindowclose);

        mProgressBar.setVisibility(View.INVISIBLE);
        update.setVisibility(View.VISIBLE);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_BANDINFO, MODE_PRIVATE);
        nfirmware = prefs.getString("newfirmware", null);
        imgurl = prefs.getString("brandimage", null);

        SharedPreferences prefs1 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mac = prefs1.getString("bandmacid", null);

        firmware.setText("Update Version :"+nfirmware);

        Picasso.with(this).load(imgurl).fit().centerInside().into(brandimage);

        update.setOnClickListener(this);
        close.setOnClickListener(this);

    }

    public void GetData(){

        Getmac = mac;
        Getfirmware = nfirmware;

        SendDataToServer();

    }

    public void SendDataToServer(){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("mac", Getmac));
                nameValuePairs.add(new BasicNameValuePair("firmware", Getfirmware));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(DataParseUrl);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Submit Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Updatewindow.this,"Updation Completed Successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Updatewindow.this,Deviceinfo.class));

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    @Override
    public void onClick(View view) {

        if (view == update)
        {
            update.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mProgressStatus < 100){
                        mProgressStatus++;
                        android.os.SystemClock.sleep(100);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(mProgressStatus);
                            }
                        });
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            GetData();

                        }
                    });
                }
            }).start();
        }

        if (view == close)
        {
            finish();
        }

    }
}
