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

public class GoTHousesListFragment  extends Fragment {
    private static final String TAG = "GoTHousesListFragment";

    public GoTHousesListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        final ContentLoadingProgressBar contentLoadingProgressBar = rootView.findViewById(R.id.pb);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv);

        final GoTHouseAdapter goTHouseAdapter = new GoTHouseAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(goTHouseAdapter);

        new Thread(new Runnable() {

            @Override
            public void run() {
                String url = "https://project-8424324399725905479.firebaseio.com/characters.json?print=pretty";

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
                    GoTHousesListFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<GoTHouse> goTHouses = new ArrayList<>();
                            for (int i = 0; i < characters.size(); i++) {
                                boolean isGothouse = false;
                                for (int j = 0; j < goTHouses.size(); j++) {
                                    if (goTHouses.get(j).houseName.equalsIgnoreCase(characters.get(i).houseName)) {
                                        isGothouse = true;
                                    }
                                }
                                if (!isGothouse) {
                                    if (characters.get(i).houseId != null && !characters.get(i).houseId.isEmpty()) {
                                        GoTHouse h = new GoTHouse();
                                        h.houseId = characters.get(i).houseId;
                                        h.houseName = characters.get(i).houseName;
                                        h.houseImageUrl = characters.get(i).houseImageUrl;
                                        goTHouses.add(h);
                                    }
                                }
                            }
                            goTHouseAdapter.addAll(goTHouses);
                            goTHouseAdapter.notifyDataSetChanged();
                            contentLoadingProgressBar.hide();
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
