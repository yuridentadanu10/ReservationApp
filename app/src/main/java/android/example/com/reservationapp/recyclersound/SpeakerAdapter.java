package android.example.com.reservationapp.recyclersound;

import android.content.Context;
import android.example.com.reservationapp.R;
import android.example.com.reservationapp.recyclerView.Ballroom;
import android.example.com.reservationapp.recyclerView.BallroomAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SpeakerAdapter extends FirestoreRecyclerAdapter<Speaker, SpeakerAdapter.FoodHolder> {
    private SpeakerAdapter.OnItemClickListener listener;
    private Context context;
    private static final String TAG = "DetailFoodAct";

    public SpeakerAdapter(@NonNull FirestoreRecyclerOptions<Speaker> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SpeakerAdapter.FoodHolder holder, int position, @NonNull Speaker model) {
        holder.textViewTitle.setText(model.getNama());
        holder.tv_rating.setText(String.valueOf(model.getRating()));
    }



    @NonNull
    @Override
    public SpeakerAdapter.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sound,
                parent, false);
        return new SpeakerAdapter.FoodHolder(v);
    }


    class FoodHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView tv_rating;

        public FoodHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.profile_name_tv);
            tv_rating = itemView.findViewById(R.id.tv_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(SpeakerAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}