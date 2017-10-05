package kh.edu.rupp.ckccapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import kh.edu.rupp.ckccapp.R;

/**
 * CKCCApp
 * Created by leapkh on 24/8/17.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Replace built-in ActionBar with Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tlb_main);
        setSupportActionBar(toolbar);
        setTitle("CKCC Mobile App");

        // Add actionbar drawer toggle
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.lyt_drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Add listener to NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.mnu_news){
            Intent newsIntent = new Intent(this, NewsActivity.class);
            startActivity(newsIntent);
        }

        return false;
    }
}
