package firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseInitialize {

    public Firestore initialize(){
        try{
            InputStream fireKey = getClass().getResourceAsStream("/firekey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(fireKey))
                    .setDatabaseUrl("https://backendapptetris-b186f.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return FirestoreClient.getFirestore();
    }
}
