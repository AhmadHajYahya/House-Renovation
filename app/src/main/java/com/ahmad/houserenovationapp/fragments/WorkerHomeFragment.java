package com.ahmad.houserenovationapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.adapters.WorkerJobsAdapter;
import com.ahmad.houserenovationapp.callback.RequestCallBack;
import com.ahmad.houserenovationapp.callback.RequestsListCallBack;
import com.ahmad.houserenovationapp.callback.UserCallBack;
import com.ahmad.houserenovationapp.enums.Status;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.logic.MyDataManager;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.model.User;

import java.util.List;

public class WorkerHomeFragment extends Fragment {
    private RecyclerView HRA_VIEW_worker_home_recyclerView;
    private TextView HRA_TXT_worker_home_noRequests;
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
        DataBaseManager.getUserRequests(new RequestsListCallBack() {
            @Override
            public void onRequestsRetrieved(List<Request> requests) {
                if(!requests.isEmpty()){
                    HRA_VIEW_worker_home_recyclerView.setVisibility(View.GONE);
                    HRA_VIEW_worker_home_recyclerView.setVisibility(View.VISIBLE);
                    HRA_VIEW_worker_home_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    workerJobsAdapter = new WorkerJobsAdapter(getActivity(), requests,new WorkerJobsAdapter.OnJobAcceptListener() {
                        @Override
                        public void onJobDecline(Request request, int position) {
                            DataBaseManager.updateRequest(request.getId(),"status",Status.DECLINED.name());
                            requests.remove(position);
                            workerJobsAdapter.notifyItemRemoved(position);
                        }

                        @Override
                        public void onJobAccept(Request request, int position) {
                            if(MyDataManager.getCurrentJob()==null){
                                DataBaseManager.updateRequest(request.getId(), "status", Status.ACCEPTED.name());
                                DataBaseManager.getWorkerCurrentJob(new RequestCallBack() {
                                    @Override
                                    public void onRequestRetrieved(Request request) {
                                        MyDataManager.setCurrentJob(request);
                                    }
                                });
                                DataBaseManager.updateUserData(DataBaseManager.getCurrentUser().getId(),"working",true);
                                DataBaseManager.getUser(new UserCallBack() {
                                    @Override
                                    public void onUserRetrieved(User user) {
                                        DataBaseManager.setCurrentUser(user);
                                    }
                                });
                                requests.remove(position);
                                workerJobsAdapter.notifyItemRemoved(position);
                            }else{
                                Toast.makeText(getActivity(), "Please finish the current job.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    HRA_VIEW_worker_home_recyclerView.setAdapter(workerJobsAdapter);
                }
                else{
                    HRA_VIEW_worker_home_recyclerView.setVisibility(View.VISIBLE);
                    HRA_VIEW_worker_home_recyclerView.setVisibility(View.GONE);
                }

            }
        });


    }

    void findViews(View view){
        HRA_VIEW_worker_home_recyclerView = view.findViewById(R.id.HRA_VIEW_worker_home_recyclerView);
        HRA_TXT_worker_home_noRequests = view.findViewById(R.id.HRA_TXT_worker_home_noRequests);
    }
}
