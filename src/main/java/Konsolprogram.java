import REST.JavalinApi;
import brugerautorisation.data.Spiller;
import com.google.cloud.firestore.Firestore;
import firebase.FirebaseInitialize;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.Scanner;

public class Konsolprogram {

    private JavalinApi javalinApi;
    private Scanner scanner = new Scanner(System.in);
    private static final String KEY_NAVN = "navn";
    private static final String KEY_SCORE = "score";
    private String userFirstName;

    private static FirebaseInitialize firebaseInitialize;
    private static Firestore db;

    public Konsolprogram(){
        firebaseInitialize = new FirebaseInitialize();
        db = firebaseInitialize.initialize();
        retrofitInitialize();
    }

    public void retrofitInitialize(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://18.219.143.210:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        javalinApi = retrofit.create(JavalinApi.class);
    }

    public void login(Spiller spiller){
        Call<String> call = javalinApi.login(spiller);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()){
                    userFirstName = response.body();
                    uploadHighscore(spiller.getStudienr());
                } else {
                    System.out.println("Fejl. Prøv venligst igen.");
                    velkomst();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void uploadHighscore(String studienr){
        System.out.println("Indtast score at gemme: ");
        String score = scanner.nextLine();
        Spiller spiller = new Spiller(studienr, "", score);

        Call<String> call = javalinApi.uploadHighscore(spiller);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });


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
