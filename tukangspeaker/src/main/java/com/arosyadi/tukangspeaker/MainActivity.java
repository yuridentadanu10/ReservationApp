package com.arosyadi.tukangspeaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;

    private SpeakerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukang_speaker);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = db.collection("speakerApp").orderBy("kelengkapan").limit(10);

        FirestoreRecyclerOptions<Speaker> options = new FirestoreRecyclerOptions.Builder<Speaker>()
                .setQuery(query, Speaker.class)
                .build();

        adapter = new SpeakerAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.rv_speaker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


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
