package com.example.prayer.FireStoreDataBase;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.prayer.Util.DateOprations;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireStoreUser {

    Context context ;

    public FireStoreUser(Context context) {
        this.context = context;
    }

    private DateOprations dateOps = new DateOprations(context);
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users = db.collection("users");


    private String Start;


    public Map<String, Object> CreateDataDocument(String userID) {
        Start = dateOps.getCurrentDate();
        Map<String, Object> user = new HashMap<>();
        user.put("userID", userID);
        user.put("StartingDate", Start);
        user.put("chalngeDateCount", 50);
        user.put("Fajr", 0);
        user.put("Duhr", 0);
        user.put("Asr", 0);
        user.put("Maghrib", 0);
        user.put("Isha", 0);

        CollectionReference users = db.collection("users");

        DocumentReference newUser = users.document("new User" + userID);

        newUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && !document.exists()) {

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
                if (document != null) {
                    if (document.exists()) {
                        String startingdate = String.valueOf(document.get("StartingDate"));
                        Log.d(("FireBaseClass"), "DocumentSnapshot data: " + document.getData());

                        date.setValue(startingdate);
                    } else date.setValue(null);
                }
            }
        });


        return date;

    }

    public void markAsDone(String ID, String Name) {
        DocumentReference newUser = users.document("new User" + ID);


        newUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {

                    Map<String, Object> old = document.getData();

                    int oldTime = 0;
                    if (old != null) {
                        oldTime = Integer.parseInt(String.valueOf(old.get(Name)));
                    }

                    int NewTime = oldTime + 1;

                    if (old != null) {
                        old.put(Name, NewTime);
                    }
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
                if (document != null) {
                    if (document.exists()) {

                        Log.d(("FireBaseClass"), "DocumentSnapshot data: " + document.getData());
                        if (document.get(name) != null)
                            date.setValue(Integer.valueOf(String.valueOf(document.get(name))));
                    } else date.setValue(100);
                }
            }
        });


        return date;

    }


}
