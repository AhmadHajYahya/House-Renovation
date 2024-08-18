package com.ahmad.houserenovationapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.adapters.WorkerJobsAdapter;
import com.ahmad.houserenovationapp.callback.RequestCallBack;
import com.ahmad.houserenovationapp.callback.WorkerListCallback;
import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.logic.MyDataManager;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkerHomeFragment extends Fragment {
    private RecyclerView HRA_VIEW_worker_home_recyclerView;
    private WorkerJobsAdapter workerJobsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker_home, container, false);

        findViews(view);
        setupRecyclerView();

        return view;
    }

    void setupRecyclerView() {
        DataBaseManager.getUserRequests(new RequestCallBack() {
            @Override
            public void onRequestsRetrieved(List<Request> requests) {
                HRA_VIEW_worker_home_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                workerJobsAdapter = new WorkerJobsAdapter(getActivity(), requests,new WorkerJobsAdapter.OnJobAcceptListener() {
                    @Override
                    public void onJobDecline(Request request, int position) {
                        requests.remove(position);
                        workerJobsAdapter.notifyItemRemoved(position);
                    }

                    @Override
                    public void onJobAccept(Request request, int position) {
                        MyDataManager.setCurrentJob(request);
                        requests.remove(position);
                        workerJobsAdapter.notifyItemRemoved(position);
                        
                    }
                });
                HRA_VIEW_worker_home_recyclerView.setAdapter(workerJobsAdapter);
            }
        });


    }

    void findViews(View view){
        HRA_VIEW_worker_home_recyclerView = view.findViewById(R.id.HRA_VIEW_worker_home_recyclerView);
    }
}
