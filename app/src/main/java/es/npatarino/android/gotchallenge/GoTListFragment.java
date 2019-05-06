package es.npatarino.android.gotchallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    SearchView searchView;
    List<GoTCharacter> characters = new ArrayList<>();
    GoTAdapter goTAdapter = null;
    ContentLoadingProgressBar progressBar = null;

    public GoTListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        progressBar = rootView.findViewById(R.id.pb);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv);

        goTAdapter = new GoTAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(goTAdapter);

        new Thread(new Runnable() {

            @Override
            public void run() {
                URL obj;
                try {
                    obj = new URL(URL_CHARACTER);
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
                    characters = new Gson().fromJson(response.toString(), listType);
                    GoTListFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goTAdapter.addAll(characters);
                            goTAdapter.notifyDataSetChanged();
                            progressBar.hide();
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }


            }
        }).start();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        MenuItem itemSearch = menu.findItem(R.id.action_search);
        searchView = (SearchView) itemSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String text) {
                goTAdapter.filter(text);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });
    }
}
