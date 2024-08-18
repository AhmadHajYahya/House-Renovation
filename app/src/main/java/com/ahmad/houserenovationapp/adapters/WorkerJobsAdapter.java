package com.ahmad.houserenovationapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.model.Request;

import java.util.List;

public class WorkerJobsAdapter extends RecyclerView.Adapter<WorkerJobsAdapter.JobViewHolder> {

    private final Context context;
    private final List<Request> requests;
    private final WorkerJobsAdapter.OnJobAcceptListener jobListener;

    public WorkerJobsAdapter(Context context, List<Request> requests, WorkerJobsAdapter.OnJobAcceptListener jobListener) {
        this.context = context;
        this.requests = requests;
        this.jobListener = jobListener;
    }

    @NonNull
    @Override
    public WorkerJobsAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_worker_jobs, parent, false);

        return new WorkerJobsAdapter.JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Request request = requests.get(position);

        holder.HRA_TXT_job_title.setText("Title: " + request.getTitle());
        holder.HRA_TXT_job_description.setText("Description: " + request.getDescription());
        holder.HRA_TXT_job_category.setText("Category: " + request.getCategory());
        holder.HRA_TXT_job_date.setText("Date: " + request.getDate());

        holder.HRA_TXT_job_customer_personalName.setText("Personal Name: " + request.getCustomer().getPersonalName());
        holder.HRA_TXT_job_customer_phoneNumber.setText("Phone number: " + request.getCustomer().getPhoneNumber());
        //holder.HRA_TXT_job_customer_location.setText("Location: " + request.getCustomer().getAddress()); TODO

        // Set up the cancel button click listener
        holder.HRA_BTN_job_accept.setOnClickListener(v -> {
            if (jobListener != null) {
                jobListener.onJobAccept(request, holder.getAdapterPosition());
            }
        });
        holder.HRA_BTN_job_decline.setOnClickListener(v -> {
            if (jobListener != null) {
                jobListener.onJobDecline(request, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {

        TextView HRA_TXT_job_title;
        TextView HRA_TXT_job_description;
        TextView HRA_TXT_job_category;
        TextView HRA_TXT_job_date;
        TextView HRA_TXT_job_customer_personalName;
        TextView HRA_TXT_job_customer_phoneNumber;
        TextView HRA_TXT_job_customer_location;
        AppCompatButton HRA_BTN_job_accept;
        AppCompatButton HRA_BTN_job_decline;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            HRA_TXT_job_title = itemView.findViewById(R.id.HRA_TXT_job_title);
            HRA_TXT_job_description = itemView.findViewById(R.id.HRA_TXT_job_description);
            HRA_TXT_job_category = itemView.findViewById(R.id.HRA_TXT_job_category);
            HRA_TXT_job_date = itemView.findViewById(R.id.HRA_TXT_job_date);
            HRA_TXT_job_customer_personalName = itemView.findViewById(R.id.HRA_TXT_job_customer_personalName);
            HRA_TXT_job_customer_phoneNumber = itemView.findViewById(R.id.HRA_TXT_job_customer_phoneNumber);
            HRA_TXT_job_customer_location = itemView.findViewById(R.id.HRA_TXT_job_customer_location);
            HRA_BTN_job_accept = itemView.findViewById(R.id.HRA_BTN_job_accept);
            HRA_BTN_job_decline = itemView.findViewById(R.id.HRA_BTN_job_decline);
        }
    }

    // Interface for handling cancel button click
    public interface OnJobAcceptListener {
        void onJobDecline(Request request, int position);
        void onJobAccept(Request request, int position);
    }
}
