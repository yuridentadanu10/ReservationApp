package android.example.com.reservationapp.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.com.reservationapp.DetailRoom;
import android.example.com.reservationapp.R;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ListFOod";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    private BallroomAdapter adapter;
    private BallroomAdapter adapter2;
    public static final String MOVIE_ITEM = "film";
    TextView tv_waktuMakan;
    Toolbar mTopToolbar;
    String waktuMakan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerView();
        setUpRecyclerViewConvention();

    }

    private void setUpRecyclerView() {
        Query query = db.collection("ballroom").orderBy("nama").limit(10);

        FirestoreRecyclerOptions<Ballroom> options = new FirestoreRecyclerOptions.Builder<Ballroom>()
                .setQuery(query, Ballroom.class)
                .build();

        adapter = new BallroomAdapter(options);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = findViewById(R.id.rv_ballroom);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BallroomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                Intent intent = new Intent(MainActivity.this, DetailRoom.class);
                //intent.putExtra("model", model);
                intent.putExtra("jangkrik", id);
                startActivity(intent);
            }
        });


    }

    private void setUpRecyclerViewConvention() {
        Query query = db.collection("ballroom").orderBy("nama").limit(10);

        FirestoreRecyclerOptions<Ballroom> options = new FirestoreRecyclerOptions.Builder<Ballroom>()
                .setQuery(query, Ballroom.class)
                .build();

        adapter2 = new BallroomAdapter(options);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = findViewById(R.id.rv_ballroom1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter2);

        adapter2.setOnItemClickListener(new BallroomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                Intent intent = new Intent(MainActivity.this, DetailRoom.class);
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
        adapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }
}
