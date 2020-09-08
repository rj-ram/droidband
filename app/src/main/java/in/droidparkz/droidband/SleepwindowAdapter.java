package in.droidparkz.droidband;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SleepwindowAdapter extends RecyclerView.Adapter<SleepwindowAdapter.SleepwindowViewHolder> {
    private Context mContext;
    private ArrayList<SleepwindowItem> mSleepwindowList;

    public SleepwindowAdapter(Context context, ArrayList<SleepwindowItem> SleepwindowList) {
        mContext = context;
        mSleepwindowList = SleepwindowList;
    }

    @Override
    public SleepwindowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.sleepwindow_item, parent, false);
        return new SleepwindowViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SleepwindowViewHolder holder, int position) {
        SleepwindowItem currentItem = mSleepwindowList.get(position);

        String day = currentItem.getDay();
        String start = currentItem.getStart();
        String stop = currentItem.getStop();
        String duration = currentItem.getDuration();
        String progress = currentItem.getProgress();

        String time = start + "  -  " + stop;

        holder.mTextViewDay.setText(day);
        holder.mTextViewTime.setText(time);
        holder.mTextViewDuration.setText(duration);
        holder.progressBar.setProgress(Integer.valueOf(progress));

    }

    @Override
    public int getItemCount() {

        return mSleepwindowList.size();
    }

    public class SleepwindowViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTextViewDay,mTextViewTime,mTextViewDuration;

        public ProgressBar progressBar;

        public SleepwindowViewHolder(View itemView) {
            super(itemView);

            mTextViewDay = itemView.findViewById(R.id.sleepwindowday);
            mTextViewTime = itemView.findViewById(R.id.sleepwindowtime);
            mTextViewDuration = itemView.findViewById(R.id.sleepwindowhours);
            progressBar = itemView.findViewById(R.id.sleepwindowitemprogress);
        }
    }
}
