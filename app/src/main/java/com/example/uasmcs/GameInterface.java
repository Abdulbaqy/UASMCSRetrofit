package com.example.uasmcs;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface GameInterface {
    @GET("deals?storeID=1&lowerPrice=10&upperPrice=100")
    Call<List<GameModel>> getGameList();

    @GET("game/details/{gameId}")
    Call<GameModel> getGameDetails(@Path("gameId") String gameId);
}
