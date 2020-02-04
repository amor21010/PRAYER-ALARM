package com.example.prayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prayer.FireStoreDataBase.FireStoreUser;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.perf.FirebasePerformance;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity FireBase";


    String userID;
    FireStoreUser fireStoreUser;
    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindView(R.id.container)
    ConstraintLayout container;

    @SuppressLint("HardwareIds")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fireStoreUser = new FireStoreUser();
        getUserInfo();


        MobileAds.initialize(this,
                getString(R.string.app_ad_id));
        MobileAds.initialize(this, initializationStatus -> {
            Log.d("initializationStatus", initializationStatus.toString());
            Toast.makeText(this, initializationStatus.toString(), Toast.LENGTH_SHORT).show();
        });

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // keep this comment to remove app bar
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        // FirebasePerformance.startTrace()
        FirebasePerformance.getInstance().setPerformanceCollectionEnabled(true);
        //MediationTestSuite.launch(this);


    }

    private void getUserInfo() {

        @SuppressLint("HardwareIds") String userID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        fireStoreUser.CreateDataDocument(userID);
    }


}
