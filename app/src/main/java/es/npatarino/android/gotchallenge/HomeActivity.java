package es.npatarino.android.gotchallenge;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSectionsPagerAdapter(new SectionsPagerAdapter(this.getBaseContext(), getSupportFragmentManager()));

        setViewPager((ViewPager) findViewById(R.id.container));
        getViewPager().setAdapter(getSectionsPagerAdapter());

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(getViewPager());
    }

    public SectionsPagerAdapter getSectionsPagerAdapter() {
        return sectionsPagerAdapter;
    }

    public void setSectionsPagerAdapter(SectionsPagerAdapter sectionsPagerAdapter) {
        this.sectionsPagerAdapter = sectionsPagerAdapter;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
