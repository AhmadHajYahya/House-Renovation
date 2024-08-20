package com.ahmad.houserenovationapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.enums.Status;
import com.ahmad.houserenovationapp.model.Request;

import java.util.List;

public class CustomerRequestAdapter extends RecyclerView.Adapter<CustomerRequestAdapter.RequestViewHolder> {

    private final Context context;
    private final List<Request> requests;
    private final OnRequestActionListener cancelListener;

    public CustomerRequestAdapter(Context context, List<Request> requests, OnRequestActionListener cancelListener) {
        this.context = context;
        this.requests = requests;
        this.cancelListener = cancelListener;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer_request, parent, false);

        return new RequestViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Request request = requests.get(position);

        holder.HRA_TXT_request_title.setText("Title: " + request.getTitle());
        holder.HRA_TXT_request_description.setText("Description: " + request.getDescription());
        holder.HRA_TXT_request_category.setText("Category: " + request.getCategory());
        holder.HRA_TXT_request_date.setText("Date: " + request.getDate());
        holder.HRA_TXT_request_status.setText("Status: " + request.getStatus());
        holder.HRA_TXT_request_worker_personalName.setText("Personal Name: " + request.getWorker().getPersonalName());
        holder.HRA_TXT_request_worker_category.setText("Category: " +request.getWorker().getWorkCategory());
        holder.HRA_TXT_request_worker_rating.setText("Rating: " + request.getWorker().getRating() + "/5.0");

        if(request.getStatus().equals(Status.IN_PROGRESS) && !request.getWorkerRated()){
            holder.HRA_BTN_request_rateWorker.setVisibility(View.VISIBLE);
            holder.HRA_BTN_request_cancel.setVisibility(View.GONE);
        }else if(request.getStatus().equals(Status.IN_PROGRESS) && request.getWorkerRated()){
            holder.HRA_BTN_request_rateWorker.setVisibility(View.GONE);
            holder.HRA_BTN_request_cancel.setVisibility(View.GONE);
        }else {
            holder.HRA_BTN_request_rateWorker.setVisibility(View.GONE);
            holder.HRA_BTN_request_cancel.setVisibility(View.VISIBLE);
        }

        if(request.getStatus().equals(Status.DECLINED)){
            holder.HRA_TXT_request_declined_Message.setVisibility(View.VISIBLE);
            holder.HRA_BTN_request_rateWorker.setVisibility(View.GONE);
            holder.HRA_BTN_request_cancel.setVisibility(View.VISIBLE);
        }

        // Set up the cancel button click listener
        holder.HRA_BTN_request_cancel.setOnClickListener(v -> {
            if (cancelListener != null) {
                cancelListener.onRequestCancel(request, holder.getAdapterPosition());
            }
        });

        holder.HRA_BTN_request_rateWorker.setOnClickListener(v -> {
            if (cancelListener != null) {
                cancelListener.onRequestRateWorker(request, holder.getAdapterPosition());
                holder.HRA_BTN_request_rateWorker.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView HRA_TXT_request_title;
        TextView HRA_TXT_request_description;
        TextView HRA_TXT_request_category;
        TextView HRA_TXT_request_date;
        TextView HRA_TXT_request_status;
        TextView HRA_TXT_request_worker_personalName;
        TextView HRA_TXT_request_worker_category;
        TextView HRA_TXT_request_worker_rating;
        TextView HRA_TXT_request_declined_Message;
        AppCompatButton HRA_BTN_request_cancel;
        AppCompatButton HRA_BTN_request_rateWorker;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            HRA_TXT_request_title = itemView.findViewById(R.id.HRA_TXT_request_title);
            HRA_TXT_request_description = itemView.findViewById(R.id.HRA_TXT_request_description);
            HRA_TXT_request_category = itemView.findViewById(R.id.HRA_TXT_request_category);
            HRA_TXT_request_date = itemView.findViewById(R.id.HRA_TXT_request_date);
            HRA_BTN_request_cancel = itemView.findViewById(R.id.HRA_BTN_request_cancel);
            HRA_BTN_request_rateWorker = itemView.findViewById(R.id.HRA_BTN_request_rateWorker);
            HRA_TXT_request_worker_personalName= itemView.findViewById(R.id.HRA_TXT_request_worker_personalName);
            HRA_TXT_request_worker_category= itemView.findViewById(R.id.HRA_TXT_request_worker_category);
            HRA_TXT_request_worker_rating= itemView.findViewById(R.id.HRA_TXT_request_worker_rating);
            HRA_TXT_request_status= itemView.findViewById(R.id.HRA_TXT_request_status);
            HRA_TXT_request_declined_Message= itemView.findViewById(R.id.HRA_TXT_request_declined_Message);
        }
    }

    // Interface for handling cancel button click
    public interface OnRequestActionListener {
        void onRequestCancel(Request request, int position);
        void onRequestRateWorker(Request request, int position);
    }
}
