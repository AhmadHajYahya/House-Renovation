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
import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.UserType;
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
        List<Request> requests = new ArrayList<>();
        User customer = new User.Builder()
                .setId("1")
                .setUsername("user1")
                .setPersonalName("fofo")
                .setPassword("123456789")
                .setAddress("tel aviv")
                .setPhoneNumber("0501234567")
                .setUserType(UserType.CUSTOMER)
                .build();

        Request request = new Request.Builder()
                .setId("request123")
                .setTitle("Fix the plumbing")
                .setDescription("Need to fix a leaking pipe in the kitchen")
                .setCategory(Category.PLUMBER)
                .setDate(LocalDate.now())
                .setCustomer(customer)
                .setWorker(null)
                .build();

        requests.add(request);
        requests.add(request);
        requests.add(request);
        requests.add(request);
        requests.add(request);

        HRA_VIEW_worker_home_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        workerJobsAdapter = new WorkerJobsAdapter(getActivity(), requests,new WorkerJobsAdapter.OnJobAcceptListener() {
            @Override
            public void onJobDecline(Request request, int position) {
                requests.remove(position);
                workerJobsAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onJobAccept(Request request, int position) {

            }
        });
        HRA_VIEW_worker_home_recyclerView.setAdapter(workerJobsAdapter);
    }

    void findViews(View view){
        HRA_VIEW_worker_home_recyclerView = view.findViewById(R.id.HRA_VIEW_worker_home_recyclerView);
    }
}
