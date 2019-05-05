package es.npatarino.android.gotchallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import static es.npatarino.android.gotchallenge.Constants.BUNDLE_DESCRIPTION;
import static es.npatarino.android.gotchallenge.Constants.BUNDLE_IMG_URL;
import static es.npatarino.android.gotchallenge.Constants.BUNDLE_NAME;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView imageView = findViewById(R.id.iv_photo);
        final TextView textViewName = findViewById(R.id.tv_name);
        final TextView textViewDescription = findViewById(R.id.tv_description);

        final String description = getIntent().getStringExtra(BUNDLE_DESCRIPTION);
        final String name = getIntent().getStringExtra(BUNDLE_NAME);
        final String imageUrl = getIntent().getStringExtra(BUNDLE_IMG_URL);

        Toolbar toolbar = findViewById(R.id.t);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(imageUrl);
                    final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                            textViewName.setText(name);
                            textViewDescription.setText(description);
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }
        }).start();
    }
}
