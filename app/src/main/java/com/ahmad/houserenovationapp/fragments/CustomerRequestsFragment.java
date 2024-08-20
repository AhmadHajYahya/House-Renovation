package com.ahmad.houserenovationapp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.adapters.CustomerRequestAdapter;
import com.ahmad.houserenovationapp.callback.RequestsListCallBack;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.model.User;

import java.util.List;

public class CustomerRequestsFragment extends Fragment {
    private RecyclerView HRA_VIEW_customerRequests_recyclerView;
    private TextView HRA_TXT_customerRequests_noRequests;
    private CustomerRequestAdapter customerRequestAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_requests, container, false);

        findViews(view);
        setupRecyclerView();

        return view;
    }

    void setupRecyclerView() {

        DataBaseManager.getUserRequests(new RequestsListCallBack() {
            @Override
            public void onRequestsRetrieved(List<Request> requests) {
                if(!requests.isEmpty()){
                    HRA_TXT_customerRequests_noRequests.setVisibility(View.GONE);
                    HRA_VIEW_customerRequests_recyclerView.setVisibility(View.VISIBLE);
                    HRA_VIEW_customerRequests_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    customerRequestAdapter = new CustomerRequestAdapter(getActivity(), requests,new CustomerRequestAdapter.OnRequestActionListener() {
                        @Override
                        public void onRequestCancel(Request request, int position) {
                            DataBaseManager.deleteRequest(request);
                            requests.remove(position);
                            customerRequestAdapter.notifyItemRemoved(position);
                            Toast.makeText(getActivity(),"Request Canceled", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onRequestRateWorker(Request request, int position) {
                            showRatingDialog(request.getWorker(),request.getId());
                        }


                    });
                    HRA_VIEW_customerRequests_recyclerView.setAdapter(customerRequestAdapter);
                }else{
                    HRA_TXT_customerRequests_noRequests.setVisibility(View.VISIBLE);
                    HRA_VIEW_customerRequests_recyclerView.setVisibility(View.GONE);
                }

            }
        });

    }


    private void showRatingDialog(User worker,String requestId) {
        // Create a new AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Rate Worker");

        // Inflate the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rate_worker, null);
        builder.setView(dialogView);

        // Get references to dialog elements
        TextView workerNameTextView = dialogView.findViewById(R.id.HRA_TXT_rate_worker_name);
        RatingBar workerRatingBar = dialogView.findViewById(R.id.HRA_RATINGBAR_worker_rating);
        Button confirmButton = dialogView.findViewById(R.id.HRA_BTN_rate_confirm);

        // Set the worker's name in the dialog
        workerNameTextView.setText(worker.getPersonalName());

        // Create the dialog
        AlertDialog dialog = builder.create();

        // Confirm button listener
        confirmButton.setOnClickListener(v -> {
            float rating = workerRatingBar.getRating();
            if (rating > 0) {
                // Submit the rating (you would update the worker's rating in your database here)
                updateWorkerRating( worker,  rating);
                DataBaseManager.updateRequest(requestId,"workerRated",true);
                dialog.dismiss(); // Close the dialog
            } else {
                // Show a message if no rating is selected
                Toast.makeText(getActivity(), "Please select a rating", Toast.LENGTH_SHORT).show();
            }
        });
        // Show the dialog
        dialog.show();
    }

    private void updateWorkerRating(User worker, float newRating) {
        // Assuming worker.getRating() returns the current average rating
        double currentRating = worker.getRating();
        int numberOfRatings = worker.getNumberOfRatings();  // Number of ratings received so far

        // Calculate the new average rating
        double updatedRating = ((currentRating * numberOfRatings) + newRating) / (numberOfRatings + 1);
        double result = Math.floor(updatedRating * 10) / 10;
        // Update the worker's rating and number of ratings in the database
        DataBaseManager.updateUserData(worker.getId(), "rating", result);
        DataBaseManager.updateUserData(worker.getId(), "numberOfRatings", (numberOfRatings + 1));
    }
    void findViews(View view){
        this.HRA_VIEW_customerRequests_recyclerView = view.findViewById(R.id.HRA_VIEW_customerRequests_recyclerView);
        this.HRA_TXT_customerRequests_noRequests = view.findViewById(R.id.HRA_TXT_customerRequests_noRequests);

    }
}
