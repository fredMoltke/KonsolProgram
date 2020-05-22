package firebase;

import brugerautorisation.data.Spiller;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;

public class FirebaseService {

    private Firestore dbFirestore = FirestoreClient.getFirestore();

    public Spiller getBrugerDetails(String studienr){
        CollectionReference collectionRef = dbFirestore.collection("Highscores");
        DocumentReference documentReference = collectionRef.document(studienr);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        Spiller bruger = null;
        DocumentSnapshot document;
        try {
            document = future.get();

            if (document.exists()){
                bruger = document.toObject(Spiller.class);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bruger;
    }

}
