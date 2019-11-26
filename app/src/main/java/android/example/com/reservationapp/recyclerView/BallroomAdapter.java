package android.example.com.reservationapp.recyclerView;

import android.content.Context;
import android.example.com.reservationapp.R;
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

public class BallroomAdapter extends FirestoreRecyclerAdapter<Ballroom, BallroomAdapter.FoodHolder> {
    private BallroomAdapter.OnItemClickListener listener;
    private Context context;
    private static final String TAG = "DetailFoodAct";
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    public BallroomAdapter(@NonNull FirestoreRecyclerOptions<Ballroom> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Ballroom model) {
        holder.textViewTitle.setText(model.getNama());
        holder.tv_prize.setText("Rp. "+String.valueOf(model.getHarga())+" / Jam");
        holder.tv_rating.setText(String.valueOf(model.getRating()));
        Picasso.get()
                .load(model.getImgUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgFood);
    }



    @NonNull
    @Override
    public BallroomAdapter.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);
        return new BallroomAdapter.FoodHolder(v);
    }


    class FoodHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView tv_prize;
        TextView tv_rating;
        ImageView imgFood;

        public FoodHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_name_ballroom);
            tv_prize = itemView.findViewById(R.id.tv_prize);
            imgFood = itemView.findViewById(R.id.img_ballroom1);
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

    public void setOnItemClickListener(BallroomAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}