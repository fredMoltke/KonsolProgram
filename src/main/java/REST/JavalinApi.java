package REST;

import brugerautorisation.data.Spiller;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JavalinApi {
    @POST("login")
    Call<String> login(@Body Spiller spiller);

    @POST("score")
    Call<String> uploadHighscore(@Body Spiller spiller);
}