package in.droidparkz.droidband;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PairingselectionAdapter extends RecyclerView.Adapter<PairingselectionAdapter.PairingselectionViewHolder> {
    private Context mContext;
    private ArrayList<PairingselectionItem> mPairingselectionList;

    public PairingselectionAdapter(Context context, ArrayList<PairingselectionItem> PairingselectionList) {
        mContext = context;
        mPairingselectionList = PairingselectionList;
    }

    @Override
    public PairingselectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.pairingselection_item, parent, false);
        return new PairingselectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PairingselectionViewHolder holder, int position) {
        PairingselectionItem currentItem = mPairingselectionList.get(position);

        String name = currentItem.getName();
        String macid = currentItem.getMacid();

        holder.mTextViewName.setText(name);
        holder.mac=macid;
    }

    @Override
    public int getItemCount() {

        return mPairingselectionList.size();
    }

    public class PairingselectionViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTextViewName;

        public String mac;

        public RelativeLayout band;

        public static final String MY_PREFS_NAME = "MyPrefsFile";

        public PairingselectionViewHolder(View itemView) {
            super(itemView);

            mTextViewName = itemView.findViewById(R.id.pairingselectionbandname);

            band = itemView.findViewById(R.id.pairingselectionbanddetails);
            band.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Context context = view.getContext();
                    SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("bandname", mTextViewName.getText().toString());
                    editor.putString("bandmacid", mac);
                    editor.apply();
                    Intent intent = new Intent(context,Pairingwindow.class);
                    context.startActivity(intent);

                }
            });
        }
    }
}
