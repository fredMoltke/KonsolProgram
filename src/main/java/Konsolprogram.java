import REST.JavalinApi;
import brugerautorisation.data.Bruger;
import brugerautorisation.data.Spiller;
import com.google.cloud.firestore.Firestore;
import com.google.gson.Gson;
import firebase.FirebaseInitialize;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import retrofit2.converter.gson.GsonConverterFactory;

public class Konsolprogram {

    private JavalinApi javalinApi;
    private Scanner scanner = new Scanner(System.in);
    private String userFirstName;

    public Konsolprogram(){
        retrofitInitialize();
    }

    public void retrofitInitialize(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        javalinApi = retrofit.create(JavalinApi.class);
    }

    public void login(Spiller spiller){
        Call<Bruger> call = javalinApi.login(spiller);

        call.enqueue(new Callback<Bruger>() {
            @Override
            public void onResponse(Call<Bruger> call, Response<Bruger> response) {

                if (response.isSuccessful()){
                    Bruger bruger = response.body();
                    if (bruger != null) {
                        userFirstName = bruger.fornavn;
                    } else {
                        System.out.println("Fejl: tomt brugerobjekt.");
                        return;
                    }
                    spiller.setFornavn(bruger.fornavn);
                    uploadHighscore(spiller);
                } else {
                    System.out.println("Fejl. Prøv venligst igen.");
                    velkomst();
                }
            }

            @Override
            public void onFailure(Call<Bruger> call, Throwable t) {

            }
        });
    }

    public void uploadHighscore(Spiller spiller){
        System.out.println("Indtast score at gemme: ");
        String score = scanner.nextLine();
        spiller.setScore(score);

        Call<Spiller> call = javalinApi.uploadHighscore(spiller);
        call.enqueue(new Callback<Spiller>() {
            @Override
            public void onResponse(Call<Spiller> call, Response<Spiller> response) {
                if (response.isSuccessful()){
                    System.out.println("Score gemt.");
                } else {
                    System.out.println("Der opstod en fejl. Kunne ikke gemme scoren.");
                }

            }

            @Override
            public void onFailure(Call<Spiller> call, Throwable throwable) {

            }
        });
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        velkomst();
    }

    public void velkomst(){
        System.out.println("#####################################" +
                "\n#  Velkommen til konsolprogrammet!  #" +
                "\n#  Indtast studienummer og kodeord  #" +
                "\n#         for at fortsætte.         #" +
                "\n#####################################");
        System.out.println("Studienummer: ");
        String studienr = scanner.nextLine();
        System.out.println("Kodeord: ");
        String kodeord = scanner.nextLine();

        Spiller spiller = new Spiller(studienr, kodeord, "0");

        login(spiller);
    }


}
