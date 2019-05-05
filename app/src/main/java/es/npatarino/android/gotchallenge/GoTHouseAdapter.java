package es.npatarino.android.gotchallenge;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class GoTHouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<GoTHouse> goTHouses;
    private Activity activity;

     GoTHouseAdapter(Activity activity) {
        this.goTHouses = new ArrayList<>();
        this.activity = activity;
    }

    void addAll(Collection<GoTHouse> collection) {
        for (int i = 0; i < collection.size(); i++) {
            goTHouses.add((GoTHouse) collection.toArray()[i]);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_house_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
        gotCharacterViewHolder.render(goTHouses.get(position));
    }

    @Override
    public int getItemCount() {
        return goTHouses.size();
    }

    class GotCharacterViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "GotCharacterViewHolder";
        ImageView imageView;

        GotCharacterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivBackground);
        }

        void render(final GoTHouse goTHouse) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url;
                    try {
                        url = new URL(goTHouse.houseImageUrl);
                        final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    } catch (IOException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }
            }).start();
        }
    }

}
