package fpt.aptech.pjs4.configs;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig{
    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\PC\\Desktop\\reatjs\\projectapi BE\\MedCareBE\\src\\main\\resources\\notidemo-47adf-firebase-adminsdk-fbsvc-20bfe77e23.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();


        return FirebaseApp.initializeApp(options);
    }
g
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }
}
