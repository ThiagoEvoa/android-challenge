package es.npatarino.android.gotchallenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class GoTAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<GoTCharacter> goTCharacters;
    private Activity activity;

    GoTAdapter(Activity activity) {
        this.goTCharacters = new ArrayList<>();
        this.activity = activity;
    }

    void addAll(Collection<GoTCharacter> collection) {
        for (int i = 0; i < collection.size(); i++) {
            goTCharacters.add((GoTCharacter) collection.toArray()[i]);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_character_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
        gotCharacterViewHolder.render(goTCharacters.get(position));
        ((GotCharacterViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(((GotCharacterViewHolder) holder).itemView.getContext(), DetailActivity.class);
                intent.putExtra("description", goTCharacters.get(position).description);
                intent.putExtra("name", goTCharacters.get(position).name);
                intent.putExtra("imageUrl", goTCharacters.get(position).imageUrl);
                ((GotCharacterViewHolder) holder).itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goTCharacters.size();
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

        void render(final GoTCharacter goTCharacter) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url;
                    try {
                        url = new URL(goTCharacter.imageUrl);
                        final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bmp);
                                textViewName.setText(goTCharacter.name);
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