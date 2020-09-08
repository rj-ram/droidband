package in.droidparkz.droidband;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CyclingAdapter extends RecyclerView.Adapter<CyclingAdapter.CyclingViewHolder> {
    private Context mContext;
    private ArrayList<CyclingItem> mCyclingList;

    public CyclingAdapter(Context context, ArrayList<CyclingItem> CyclingList) {
        mContext = context;
        mCyclingList = CyclingList;
    }

    @Override
    public CyclingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cycling_item, parent, false);
        return new CyclingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CyclingViewHolder holder, int position) {
        CyclingItem currentItem = mCyclingList.get(position);

        String calories = currentItem.getCalories();
        String distance = currentItem.getDistance();

        holder.mTextViewCalories.setText(calories);
        holder.mTextViewDistance.setText(distance);

    }

    @Override
    public int getItemCount() {

        return mCyclingList.size();
    }

    public class CyclingViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTextViewCalories,mTextViewDistance;

        public CyclingViewHolder(View itemView) {
            super(itemView);

            mTextViewCalories = itemView.findViewById(R.id.cyclingtxtmain);
            mTextViewDistance = itemView.findViewById(R.id.cyclingtxtsub);

        }
    }
}
