package com.ahmad.houserenovationapp.callback;

import com.ahmad.houserenovationapp.model.Request;

import java.util.List;

public interface RequestsListCallBack {
    void onRequestsRetrieved(List<Request> requests);
}
