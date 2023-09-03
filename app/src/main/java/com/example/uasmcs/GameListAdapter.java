package com.example.uasmcs;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private List<GameModel> gameList;
    private Context context;
    private GameListener gameListener;
    private GameDataSource gameDataSource;


    public GameListAdapter(Context context, List<GameModel> gameList, GameListener gameListener, GameDataSource gameDataSource) {
        this.context = context;
        this.gameList = gameList;
        this.gameListener = gameListener;
        this.gameDataSource = gameDataSource;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameListAdapter.ViewHolder holder, int position) {
        GameModel model = gameList.get(position);

        holder.gameNameTextView.setText(model.getTitle());
        holder.gamePriceTextView.setText(String.format("Price: $%.2f", model.getNormalPrice()));
        holder.gameRatingTextView.setText(String.format("Rating: %.1f", model.getSteamRating()));

        if (model.isInLibrary()) {
            holder.addButton.setText("Delete from library");
        } else {
            holder.addButton.setText("Add to library");
        }

        Picasso.get()
                .load(model.getThumb())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.gameImageView);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setInLibrary(!model.isInLibrary());
                String buttonText = model.isInLibrary() ? "Delete from library" : "Add to library";
                holder.addButton.setText(buttonText);

                if (model.isInLibrary()) {
                    gameDataSource.addGameToLibrary(model.getGameId(), model.getTitle(), model.getNormalPrice(), model.getThumb(), model.getSteamRating());
                } else {
                    gameDataSource.removeGameFromLibrary(model.getGameId());
                }
                gameListener.onGameAddedToLibrary(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImageView;
        TextView gameNameTextView;
        TextView gamePriceTextView;
        TextView gameRatingTextView;
        Button addButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImageView = itemView.findViewById(R.id.gameImageView);
            gameNameTextView = itemView.findViewById(R.id.gameNameTextView);
            gamePriceTextView = itemView.findViewById(R.id.gamePriceTextView);
            gameRatingTextView = itemView.findViewById(R.id.gameRatingTextView);
            addButton = itemView.findViewById(R.id.addBtn);
        }
    }
}