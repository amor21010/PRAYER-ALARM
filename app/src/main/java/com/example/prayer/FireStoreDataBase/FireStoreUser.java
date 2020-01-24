package com.example.prayer.FireStoreDataBase;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.prayer.Util.DateOprations;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FireStoreUser {
    DateOprations dateOps = new DateOprations();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("users");

    public FireStoreUser() {


    }


    private String userID;
    private String chalngeDateCount;
    private String Start;


    public String getChalngeDateCount() {
        return chalngeDateCount;
    }

    public void setChalngeDateCount(String chalngeDateCount) {
        this.chalngeDateCount = chalngeDateCount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public Map<String, Object> CreateDataDocument(String userID) {
        Start = java.text.DateFormat.getDateTimeInstance().format(new Date());
        Map<String, Object> user = new HashMap<>();


        CollectionReference users = db.collection("users");

        DocumentReference newUser = users.document("new User" + userID);

        newUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (!document.exists()) {
                    user.put("userID", userID);
                    user.put("StartingDate", Start);
                    user.put("chalngeDateCount", 0);
                    user.put("Fajr", 0);
                    user.put("Duhr", 0);
                    user.put("Asr", 0);
                    user.put("Maghrib", 0);
                    user.put("Isha", 0);
                    newUser.set(user).addOnSuccessListener(aVoid -> Log.d("FireBaseClass", "onSuccess: "))
                            .addOnFailureListener(e -> Log.d("FireBaseClass", "onFailure: " + e));
                }
            } else {
                Log.d("FireBaseClass", "get failed with ", task.getException());
            }


        });

        return user;

    }


    public MutableLiveData<String> getStartTime(String ID) {

        MutableLiveData<String> date = new MutableLiveData<>();

        DocumentReference newUser = users.document("new User" + ID);

        newUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    Log.d(("FireBaseClass"), "DocumentSnapshot data: " + document.getData());
                    date.setValue(String.valueOf(document.get("StartingDate")));
                } else date.setValue(null);
            }
        });


        return date;

    }

    public void markAsDone(String ID, String Name) {
        DocumentReference newUser = users.document("new User" + ID);


        newUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    Map<String, Object> old = document.getData();

                    int oldTime = Integer.parseInt(String.valueOf(old.get(Name)));
                    int NewTime = oldTime + 1;

                    old.put(Name, NewTime);
                    newUser.set(old).addOnSuccessListener(aVoid -> Log.d("updeter", "onSuccess: " + NewTime))
                            .addOnFailureListener(e -> Log.d("updeter", "onFailuer: " + e));


                }
            }
        });
    }

    public MutableLiveData<Integer> getTimesDone(String ID, String name) {

        MutableLiveData<Integer> date = new MutableLiveData<>();

        DocumentReference newUser = users.document("new User" + ID);

        newUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    Log.d(("FireBaseClass"), "DocumentSnapshot data: " + document.getData());
                    if (document.get(name) != null)
                        date.setValue(Integer.valueOf(String.valueOf(document.get(name))));
                } else date.setValue(100);
            }
        });


        return date;

    }


}
