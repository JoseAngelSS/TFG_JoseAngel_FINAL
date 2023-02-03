package com.example.tfg_joseangel;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;

import com.example.tfg_joseangel.controladores.AdapterFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TabLayout tb1;
    private ViewPager2 vp2;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();

        tb1 = findViewById(R.id.tabL);
        vp2 = findViewById(R.id.viewP);

        if(user == null){

        }else{
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
        }

        vp2.setAdapter(new AdapterFragment(getSupportFragmentManager(),getLifecycle()));
        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tb1.selectTab(tb1.getTabAt(position));
            }
        });

        tb1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}