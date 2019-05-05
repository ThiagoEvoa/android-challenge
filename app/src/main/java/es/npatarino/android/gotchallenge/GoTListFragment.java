package es.npatarino.android.gotchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static es.npatarino.android.gotchallenge.Constants.URL_CHARACTER;


public class GoTListFragment extends Fragment {

    private static final String TAG = "GoTListFragment";

    public GoTListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        final ContentLoadingProgressBar pb = rootView.findViewById(R.id.pb);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv);

        final GoTAdapter goTAdapter = new GoTAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(goTAdapter);

        new Thread(new Runnable() {

            @Override
            public void run() {
                String url = URL_CHARACTER;

                URL obj;
                try {
                    obj = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = bufferedReader.readLine()) != null) {
                        response.append(inputLine);
                    }
                    bufferedReader.close();

                    Type listType = new TypeToken<ArrayList<GoTCharacter>>() {
                    }.getType();
                    final List<GoTCharacter> characters = new Gson().fromJson(response.toString(), listType);
                    GoTListFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goTAdapter.addAll(characters);
                            goTAdapter.notifyDataSetChanged();
                            pb.hide();
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }


            }
        }).start();
        return rootView;
    }
}
