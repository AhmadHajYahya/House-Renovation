package com.ahmad.houserenovationapp.callback;

import com.ahmad.houserenovationapp.model.User;

import java.util.List;

public interface WorkerListCallback {
    void onWorkerListRetrieved(List<User> workerList);
}
