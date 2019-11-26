package android.example.com.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.example.com.reservationapp.recyclerView.MainActivity;
import android.example.com.reservationapp.recyclersound.trip3;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class DetailRoom extends AppCompatActivity implements View.OnClickListener {
    TextView balroomAddress, balroomName, balroomHarga, balroomRating;
    Button btnAddDetail;
    ProgressBar progressBar;
    ImageView imgFood;
    String waktuMakan;
    Toolbar mTopToolbar;
    private FancyButton btn_add_speaker;
    String img;
    private static final String TAG = "DetailFoodAct";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        balroomName = findViewById(R.id.tv_name_ballroom);
        balroomAddress = findViewById(R.id.tv_address_balroom);
        balroomHarga = findViewById(R.id.tv_prize);
        balroomRating = findViewById(R.id.tv_rating);
        btnAddDetail = findViewById(R.id.btn_book_now);
        btn_add_speaker = findViewById(R.id.btn_add_sound);
        btn_add_speaker.setOnClickListener(this);
        imgFood = findViewById(R.id.img_ballroom);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


        btnAddDetail.setOnClickListener(this);
        //RECEIVE OUR DATA
        Intent i = getIntent();
        final String balroom = i.getExtras().getString("jangkrik");


        mTopToolbar = findViewById(R.id.toolbar_support);
        setSupportActionBar(mTopToolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();


        Intent i = getIntent();
        final String food = i.getExtras().getString("jangkrik");

        DocumentReference docRef = db.collection("ballroom").document(food);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Long rating = document.getLong("rating");
                        Long harga = document.getLong("harga");
                        String nama = document.getString("nama");
                        String alamat = document.getString("alamat");
                        img = document.getString("imgUrl");

                        balroomRating.setText(String.valueOf(rating));
                        balroomHarga.setText("Rp. " + String.valueOf(harga) + " / jam");
                        balroomName.setText(nama);
                        balroomAddress.setText(alamat);
                        Picasso.get()
                                .load(img)
                                .placeholder(R.mipmap.ic_launcher)
                                .into(imgFood);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }


    private void writeHistory(double calorie, String nama, String imgUrl, String waktuMakan) {

        Map<String, Object> history = new HashMap<>();
        history.put("name", nama);
        history.put("calorie", calorie);
        history.put("imageUrl", imgUrl);
        history.put("waktuMakan", waktuMakan);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users")
                .document(uid).collection("historiMakanan").document()
                .set(history)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_sound:
                Intent intent = new Intent(DetailRoom.this, trip3.class);
                startActivity(intent);
                break;

        }
    }
}

