package in.droidparkz.droidband;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RemindercontentAdapter extends RecyclerView.Adapter<RemindercontentAdapter.RemindercontentViewHolder> {
    private Context mContext;
    private ArrayList<RemindercontentItem> mRemindercontentList;

    public RemindercontentAdapter(Context context, ArrayList<RemindercontentItem> RemindercontentList) {
        mContext = context;
        mRemindercontentList = RemindercontentList;
    }

    @Override
    public RemindercontentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.reminder_content, parent, false);
        return new RemindercontentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RemindercontentViewHolder holder, int position) {
        RemindercontentItem currentItem = mRemindercontentList.get(position);

        String name = currentItem.getName();

        holder.mTextViewName.setText(name);
    }

    @Override
    public int getItemCount() {

        return mRemindercontentList.size();
    }

    public class RemindercontentViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTextViewName;

        public RemindercontentViewHolder(View itemView) {
            super(itemView);

            mTextViewName = itemView.findViewById(R.id.remindercontenttxtmain);

        }
    }
}
