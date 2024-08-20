package com.ahmad.houserenovationapp.logic;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmad.houserenovationapp.callback.RequestCallBack;
import com.ahmad.houserenovationapp.callback.RequestsListCallBack;
import com.ahmad.houserenovationapp.callback.UserCallBack;
import com.ahmad.houserenovationapp.callback.WorkerListCallback;
import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.Status;
import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.model.User;
import com.ahmad.houserenovationapp.utils.HelperFunctions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/** @noinspection ALL*/
public class DataBaseManager {

    private static FirebaseDatabase database;
    private static DatabaseReference usersRef;
    private static DatabaseReference requestsRef;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser firebaseUser;
    private static User user;
    private static List<User> workers;

    static {
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
        requestsRef = database.getReference("requests");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }


    public static void saveUser(User user){
        usersRef.child(user.getId()).setValue(user);
    }

    public static void updateUserData(String userId, String key, Object value){
        usersRef.child(userId).child(key).setValue(value);
    }

    public static void getUser(UserCallBack callBack){

        usersRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    setCurrentUser(user);
                    callBack.onUserRetrieved(user);
                } else {
                    callBack.onUserRetrieved(null); // Handle case where user is null
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack.onUserRetrieved(null); // Handle cancellation or error
            }
        });
    }


    public static User getCurrentUser(){
        return user;
    }

    public static void setCurrentUser(User u){
        user = u;
    }

    public static void logoutUser() {
        firebaseAuth.signOut();  // Sign out the current user
        user = null; // Clear the current user data
    }

    public static void getWorkersByCategory(Category category,WorkerListCallback callback) {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> workersList = new ArrayList<>();
                double userLatitude = getCurrentUser().getLatitude();
                double userLongitude = getCurrentUser().getLongitude();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User worker = userSnapshot.getValue(User.class);
                    if (worker != null && worker.getUserType().equals(UserType.WORKER)) {
                        if (category.equals(Category.ALL) || worker.getWorkCategory() == category) {
                            workersList.add(worker);
                        }
                    }
                }

                // Calculate distance and sort workers by ascending distance
                workersList.sort((worker1, worker2) -> {
                    double distance1 = HelperFunctions.calculateDistance(userLatitude, userLongitude, worker1.getLatitude(), worker1.getLongitude());
                    double distance2 = HelperFunctions.calculateDistance(userLatitude, userLongitude, worker2.getLatitude(), worker2.getLongitude());
                    return Double.compare(distance1, distance2);
                });

                setWorkers(workersList);
                callback.onWorkerListRetrieved(workersList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onWorkerListRetrieved(new ArrayList<>()); // Return an empty list on error
            }
        });
    }


    public static List<User> getWorkers(){
        return workers;
    }
    public static void setWorkers(List<User> w){
         workers = w;
    }


    public static void saveRequest(Request request){
        requestsRef.child(request.getId()).setValue(request);
    }

    public static void getUserRequests(RequestsListCallBack callBack){
        requestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userId = getCurrentUser().getId();
                List<Request> requests = new ArrayList<>();
                if(getCurrentUser().getUserType().equals(UserType.WORKER)){
                    for (DataSnapshot requestSnapshot : snapshot.getChildren()) {
                        Request request = requestSnapshot.getValue(Request.class);
                        if (request != null && request.getWorker().getId().equals(userId) && request.getStatus().equals(Status.PLACED)) {
                            requests.add(request);
                        }
                    }
                }else {
                    for (DataSnapshot requestSnapshot : snapshot.getChildren()) {
                        Request request = requestSnapshot.getValue(Request.class);
                        if (request != null && request.getCustomer().getId().equals(userId) && !request.getStatus().equals(Status.DONE)) {
                            requests.add(request);
                        }
                    }
                }
                callBack.onRequestsRetrieved(requests);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack.onRequestsRetrieved(null); // Handle error case
            }
        });

    }

    public static void updateRequest(String requestId, String key, Object value){
        requestsRef.child(requestId).child(key).setValue(value);
    }


    public static void deleteRequest(Request request){
        requestsRef.child(request.getId()).removeValue().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               Log.d("delete","Request deleted");
           }
        });
    }

    public static void addFavoriteWorkerToUser(String workerId) {
        if (user != null && user.getUserType() == UserType.CUSTOMER) {
            user.addFavoriteWorker(workerId);
            saveUser(user);  // Update the user in Firebase
        }
    }

    public static void removeFavoriteWorkerFromUser(String workerId) {
        if (user != null && user.getUserType() == UserType.CUSTOMER) {
            user.removeFavoriteWorker(workerId);
            saveUser(user);  // Update the user in Firebase
        }
    }

    public static void getWorkerCurrentJob(RequestCallBack callBack){
        requestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userId = getCurrentUser().getId();
                Request foundRequest = null;

                for (DataSnapshot requestSnapshot : snapshot.getChildren()) {
                    Request request = requestSnapshot.getValue(Request.class);
                    if (request != null && request.getWorker().getId().equals(userId) && (request.getStatus().equals(Status.ACCEPTED) || request.getStatus().equals(Status.IN_PROGRESS))) {
                        foundRequest = request;
                        break;
                    }
                }
                callBack.onRequestRetrieved(foundRequest);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack.onRequestRetrieved(null); // Handle error case
            }
        });
    }
    public static  void initUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User.Builder()
                .setId("1")
                .setEmail("fofo1@gmail.com")
                .setPersonalName("Fofo")
                .setPassword("123456789")
                .setPhoneNumber("0501234567")
                .setLocation(32.259271, 35.002112)
                .setUserType(UserType.WORKER)
                .setWorkCategory(Category.ELECTRICIAN)
                .setRating(0.0)
                .setWorking(false)
                .build());

        users.add(new User.Builder()
                .setId("2")
                .setEmail("gogo1@gmail.com")
                .setPersonalName("Gogo")
                .setPassword("123654")
                .setPhoneNumber("0501234565")
                .setLocation(32.264570, 35.005875)
                .setUserType(UserType.WORKER)
                .setWorkCategory(Category.PLUMBER)
                .setRating(0.0)
                .setWorking(false)
                .build());

        users.add(new User.Builder()
                .setId("3")
                .setEmail("soso1@gmail.com")
                .setPersonalName("Soso")
                .setPassword("123456as9")
                .setPhoneNumber("0501234551")
                .setLocation(32.265237, 35.017361)
                .setUserType(UserType.WORKER)
                .setWorkCategory(Category.BUILDER)
                .setRating(0.0)
                .setWorking(false)
                .build());

        users.add(new User.Builder()
                .setId("4")
                .setEmail("zozo1@gmail.com")
                .setPersonalName("Zozo")
                .setPassword("123456789")
                .setPhoneNumber("0501321456")
                .setLocation(32.271100, 35.012157)
                .setUserType(UserType.WORKER)
                .setWorkCategory(Category.DRYWALL_REPAIR)
                .setRating(0.0)
                .setWorking(false)
                .build());

        users.add(new User.Builder()
                .setId("5")
                .setEmail("gofo1@gmail.com")
                .setPersonalName("gofo")
                .setPassword("123456789")
                .setPhoneNumber("0501234567")
                .setLocation(32.275077, 35.007028)
                .setUserType(UserType.WORKER)
                .setWorkCategory(Category.GARDNER)
                .setRating(0.0)
                .setWorking(false)
                .build());

        for (User user : users) {
            usersRef.child(user.getId()).setValue(user);
        }
    }

}
