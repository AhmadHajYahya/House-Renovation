package com.ahmad.houserenovationapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.model.User;
import com.ahmad.houserenovationapp.utils.HelperFunctions;

import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> {

    private final Context context;
    private final List<User> users;
    private final WorkerAdapter.OnHireListener hireListener;

    public WorkerAdapter(Context context, List<User> users, OnHireListener hireListener) {
        this.context = context;
        this.users = users;
        this.hireListener = hireListener;
    }

    @NonNull
    @Override
    public WorkerAdapter.WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_worker_card, parent, false);

        return new WorkerAdapter.WorkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerAdapter.WorkerViewHolder holder, int position) {
        User user = users.get(position);

        holder.HRA_TXT_workerCard_personalName.setText("Personal Name: " + user.getPersonalName());
        holder.HRA_TXT_workerCard_category.setText("Category: " + user.getWorkCategory());
        holder.HRA_TXT_workerCard_rating.setText("Rating: " + user.getRating() + "/5");

        int distance = HelperFunctions.calculateDistance(DataBaseManager.getCurrentUser().getLatitude(), DataBaseManager.getCurrentUser().getLongitude(), user.getLatitude(),user.getLongitude());

        holder.HRA_TXT_workerCard_distance.setText("Distance: " + distance + "m"); // TODO calcualte the distance between this user and the workers location

        // Check if the worker is already in favorites and update the heart icon accordingly
        if (DataBaseManager.getCurrentUser().getFavoriteWorkers().contains(user.getId())) {
            holder.HRA_BTN_workerCard_favorite.setImageResource(R.drawable.ic_heart_filled); // Set to filled heart
        } else {
            holder.HRA_BTN_workerCard_favorite.setImageResource(R.drawable.ic_heart); // Set to outline heart
        }

        // Set up the cancel button click listener
        holder.HRA_BTN_workerCard_hire.setOnClickListener(v -> {
            if (hireListener != null) {
                hireListener.onHire(user, holder.getAdapterPosition());
            }
        });

        holder.HRA_BTN_workerCard_favorite.setOnClickListener(v -> {
            if (DataBaseManager.getCurrentUser().getFavoriteWorkers().contains(user.getId())) {
                // If the worker is already a favorite, remove them
                DataBaseManager.removeFavoriteWorkerFromUser(user.getId());
                holder.HRA_BTN_workerCard_favorite.setImageResource(R.drawable.ic_heart); // Set to outline heart
            } else {
                // If the worker is not a favorite, add them
                DataBaseManager.addFavoriteWorkerToUser(user.getId());
                holder.HRA_BTN_workerCard_favorite.setImageResource(R.drawable.ic_heart_filled); // Set to filled heart
            }
            if (hireListener != null) {
                hireListener.onFavorite(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class WorkerViewHolder extends RecyclerView.ViewHolder {

        TextView HRA_TXT_workerCard_personalName;
        TextView HRA_TXT_workerCard_category;
        TextView HRA_TXT_workerCard_rating;
        TextView HRA_TXT_workerCard_distance;
        AppCompatButton HRA_BTN_workerCard_hire;
        ImageButton HRA_BTN_workerCard_favorite;

        public WorkerViewHolder(@NonNull View itemView) {
            super(itemView);

            HRA_TXT_workerCard_personalName = itemView.findViewById(R.id.HRA_TXT_workerCard_personalName);
            HRA_TXT_workerCard_category = itemView.findViewById(R.id.HRA_TXT_workerCard_category);
            HRA_TXT_workerCard_rating = itemView.findViewById(R.id.HRA_TXT_workerCard_rating);
            HRA_TXT_workerCard_distance = itemView.findViewById(R.id.HRA_TXT_workerCard_distance);
            HRA_BTN_workerCard_hire = itemView.findViewById(R.id.HRA_BTN_workerCard_hire);
            HRA_BTN_workerCard_favorite = itemView.findViewById(R.id.HRA_BTN_workerCard_favorite);
        }
    }

    // Interface for handling cancel button click
    public interface OnHireListener {
        void onHire(User user, int position);
        void onFavorite(User user);
    }
}
