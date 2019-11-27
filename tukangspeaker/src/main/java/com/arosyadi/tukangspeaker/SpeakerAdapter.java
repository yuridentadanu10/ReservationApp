package com.arosyadi.tukangspeaker;

import android.content.Context;
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

public class SpeakerAdapter extends FirestoreRecyclerAdapter<Speaker, SpeakerAdapter.SpeakerHolder> {
    private SpeakerAdapter.OnItemClickListener listener;
    private Context context;
    private static final String TAG = "DetailFoodAct";
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    public SpeakerAdapter(@NonNull FirestoreRecyclerOptions<Speaker> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SpeakerHolder holder, int position, @NonNull Speaker model) {
        holder.tvPemesan.setText(model.getPemesan());
        holder.tvKelengkapan.setText(model.getKelengkapan());
        holder.tvPlace.setText(model.getBallroom());
    }



    @NonNull
    @Override
    public SpeakerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_speaker,
                parent, false);
        return new SpeakerHolder(v);
    }


    class SpeakerHolder extends RecyclerView.ViewHolder {
        TextView tvPlace;
        TextView tvPemesan;
        TextView tvKelengkapan;

        public SpeakerHolder(View itemView) {
            super(itemView);
            tvPlace = itemView.findViewById(R.id.tv_name_ballroom);
            tvPemesan = itemView.findViewById(R.id.tv_pemesan);
            tvKelengkapan = itemView.findViewById(R.id.tv_kelengkapan);

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
