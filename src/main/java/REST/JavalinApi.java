package REST;

import brugerautorisation.data.Bruger;
import brugerautorisation.data.Spiller;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JavalinApi {
    @POST("login")
    Call<Bruger> login(@Body Spiller spiller);

    @POST("score")
    Call<Spiller> uploadHighscore(@Body Spiller spiller);
}