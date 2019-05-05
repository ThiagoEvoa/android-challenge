package es.npatarino.android.gotchallenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 15/12/2016.
 */

public class GoTListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static ListType type;
    private Context context;

    public GoTListAdapter(ListType type, Context context) {
        GoTListAdapter.type = type;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.got_character_row, parent, false);

            GotCharacterViewHolder gotCharacterViewHolder = new GotCharacterViewHolder(itemView);
            return gotCharacterViewHolder;
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.got_house_row, parent, false);

            return new GotHouseViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (type == ListType.Characters) ? 1 : 0;
    }

    class GotCharacterViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "GotCharacterViewHolder";
        ImageView imageView;
        TextView textViewName;

        GotCharacterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivBackground);
            textViewName = itemView.findViewById(R.id.tv_name);
        }
    }

    class GotHouseViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "GotCharacterViewHolder";
        ImageView imageView;

        GotHouseViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivBackground);
        }
    }
}
