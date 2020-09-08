package in.droidparkz.droidband;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class UpcomingtaskAdapter extends RecyclerView.Adapter<UpcomingtaskAdapter.UpcomingtaskViewHolder> {
    private Context mContext;
    private ArrayList<UpcomingtaskItem> mUpcomingtaskList;

    public UpcomingtaskAdapter(Context context, ArrayList<UpcomingtaskItem> UpcomingtaskList) {
        mContext = context;
        mUpcomingtaskList = UpcomingtaskList;
    }

    @Override
    public UpcomingtaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.reminder_upcoming_list, parent, false);
        return new UpcomingtaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UpcomingtaskViewHolder holder, int position) {
        UpcomingtaskItem currentItem = mUpcomingtaskList.get(position);

        String name = currentItem.getName();

        holder.mTextViewName.setText(name);
    }

    @Override
    public int getItemCount() {

        return mUpcomingtaskList.size();
    }

    public class UpcomingtaskViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTextViewName;

        public UpcomingtaskViewHolder(View itemView) {
            super(itemView);

            mTextViewName = itemView.findViewById(R.id.upcomingremindertxtmain);

        }
    }
}
