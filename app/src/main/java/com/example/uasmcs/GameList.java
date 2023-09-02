package com.example.uasmcs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.uasmcs.GameListAdapter;
import com.example.uasmcs.GameModel;
import com.example.uasmcs.R;

import com.example.uasmcs.RetrofitClient;
import com.example.uasmcs.GameInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GameListAdapter adapter;
    private List<GameModel> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gameList = new ArrayList<>();
        adapter = new GameListAdapter(this, gameList);
        recyclerView.setAdapter(adapter);

        fetchGameList();
    }

    private void fetchGameList() {
        GameInterface gameInterface = RetrofitClient.getRetrofitInstance().create(GameInterface.class);

        Call<List<GameModel>> call = gameInterface.getGameList();
        call.enqueue(new Callback<List<GameModel>>() {
            @Override
            public void onResponse(Call<List<GameModel>> call, Response<List<GameModel>> response) {
                if (response.isSuccessful()) {
                    List<GameModel> gameModels = response.body();
                    gameList.addAll(gameModels);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("API_ERROR", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<GameModel>> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
                Toast.makeText(GameList.this, "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}