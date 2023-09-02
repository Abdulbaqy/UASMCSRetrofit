package com.example.uasmcs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyGame extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GameListAdapter adapter;
    private List<GameModel> myGameList;

    private FirebaseUser currentUser;
    private DatabaseReference userGamesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_game);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userGamesRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(currentUser.getUid())
                .child("purchased_games");

        recyclerView = findViewById(R.id.myGameRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myGameList = new ArrayList<>();
        adapter = new GameListAdapter(this, myGameList);
        recyclerView.setAdapter(adapter);

        loadMyGames();
    }

    private void loadMyGames() {
        userGamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myGameList.clear();

                for (DataSnapshot gameSnapshot : dataSnapshot.getChildren()) {
                    GameModel model = gameSnapshot.getValue(GameModel.class);
                    myGameList.add(model);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error here
            }
        });
    }
}