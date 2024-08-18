package com.ahmad.houserenovationapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.adapters.CustomerRequestAdapter;
import com.ahmad.houserenovationapp.callback.RequestCallBack;
import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.model.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerRequestsFragment extends Fragment {
    private RecyclerView HRA_VIEW_customerRequests_recyclerView;
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

        DataBaseManager.getUserRequests(new RequestCallBack() {
            @Override
            public void onRequestsRetrieved(List<Request> requests) {
                HRA_VIEW_customerRequests_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                customerRequestAdapter = new CustomerRequestAdapter(getActivity(), requests,new CustomerRequestAdapter.OnRequestCancelListener() {
                    @Override
                    public void onRequestCancel(Request request, int position) {
                        DataBaseManager.deleteRequest(request);
                        requests.remove(position);
                        customerRequestAdapter.notifyItemRemoved(position);
                        Toast.makeText(getActivity(),"Request Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                HRA_VIEW_customerRequests_recyclerView.setAdapter(customerRequestAdapter);
            }
        });

    }

    void findViews(View view){
        this.HRA_VIEW_customerRequests_recyclerView = view.findViewById(R.id.HRA_VIEW_customerRequests_recyclerView);

    }
}
