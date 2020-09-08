package in.droidparkz.droidband;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private Context mContext;
    private ArrayList<StepsItem> mStepsList;

    public StepsAdapter(Context context, ArrayList<StepsItem> StepsList) {
        mContext = context;
        mStepsList = StepsList;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.step_item, parent, false);
        return new StepsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        StepsItem currentItem = mStepsList.get(position);

        String steps = currentItem.getSteps();
        String time = currentItem.getTime();

        holder.mTextViewSteps.setText(steps);
        holder.mTextViewTime.setText(time);

    }

    @Override
    public int getItemCount() {

        return mStepsList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTextViewSteps,mTextViewTime;

        public StepsViewHolder(View itemView) {
            super(itemView);

            mTextViewSteps = itemView.findViewById(R.id.steptxtmain);
            mTextViewTime = itemView.findViewById(R.id.steptxtsub);

        }
    }
}
