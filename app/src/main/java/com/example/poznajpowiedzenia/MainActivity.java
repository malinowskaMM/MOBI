package com.example.poznajpowiedzenia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button profile;
    FirebaseAuth mAuth;
    QuoteViewPagerAdapter quoteViewPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteViewPagerAdapter = new QuoteViewPagerAdapter(getSupportFragmentManager(), getFragments());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(quoteViewPagerAdapter);

        mAuth = FirebaseAuth.getInstance();

        profile = findViewById(R.id.profile);

        profile.setOnClickListener( view -> {
            startActivity(new Intent(this, HomePage.class));
        });

        new QuoteData().getQuotes();

    }
        @Override
        protected void onStart() {
            super.onStart();
            FirebaseUser user = mAuth.getCurrentUser();
            if (user == null){
                startActivity(new Intent(this, LoginActivity.class));
            }
        }

        private List<Fragment> getFragments() {
            List<Fragment> fragmentList = new ArrayList<>();
            for(int i = 0; i < /*fragmentList.size()*/5; i++) {
                QuoteFragment quoteFragment = QuoteFragment.newInstance("example", "example meaning");
                fragmentList.add(quoteFragment);
            }
            return fragmentList;
        }

    }