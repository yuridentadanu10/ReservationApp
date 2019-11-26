package android.example.com.reservationapp.recyclersound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.com.reservationapp.DetailRoom;
import android.example.com.reservationapp.R;
import android.example.com.reservationapp.recyclerView.Ballroom;
import android.example.com.reservationapp.recyclerView.BallroomAdapter;
import android.example.com.reservationapp.recyclerView.MainActivity;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class trip3 extends AppCompatActivity {
    private static final String TAG = "ListFOod";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    private SpeakerAdapter adapter;
    private BallroomAdapter adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip3);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = db.collection("speaker").orderBy("nama").limit(10);

        FirestoreRecyclerOptions<Speaker> options = new FirestoreRecyclerOptions.Builder<Speaker>()
                .setQuery(query, Speaker.class)
                .build();

        adapter = new SpeakerAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.rv_speaker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SpeakerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                Intent intent = new Intent(trip3.this, DetailRoom.class);
                //intent.putExtra("model", model);
                intent.putExtra("jangkrik", id);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
