package kh.edu.rupp.ckccapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.RemoteMessage;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.fragment.ContactFragment;
import kh.edu.rupp.ckccapp.fragment.HomeFragment;
import kh.edu.rupp.ckccapp.fragment.NewsFragment;
import kh.edu.rupp.ckccapp.fragment.ProfileFragment;
import kh.edu.rupp.ckccapp.fragment.TrainingCoursesFragment;
import kh.edu.rupp.ckccapp.model.App;

/**
 * CKCCApp
 * Created by leapkh on 24/8/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Replace built-in ActionBar with Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tlb_main);
        setSupportActionBar(toolbar);
        setTitle("CKCC Mobile App");

        // Add actionbar drawer toggle
        drawerLayout = (DrawerLayout) findViewById(R.id.lyt_drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Add listener to NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_main);
        navigationView.setNavigationItemSelectedListener(this);

        // Add listener to Logout button
        TextView txtLogout = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txt_logout);
        txtLogout.setOnClickListener(this);

        drawerLayout.closeDrawers();
        NewsFragment newsFragment = new NewsFragment();
        onMenuItemClickChange(newsFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnu_home) {
            drawerLayout.closeDrawers();
            HomeFragment homeFragment = new HomeFragment();
            onMenuItemClickChange(homeFragment);
            return true;
        } else if (item.getItemId() == R.id.mnu_news) {
            drawerLayout.closeDrawers();
            NewsFragment newsFragment = new NewsFragment();
            onMenuItemClickChange(newsFragment);
            return true;
        } else if (item.getItemId() == R.id.mnu_training_courses) {
            drawerLayout.closeDrawers();
            TrainingCoursesFragment coursesFragment = new TrainingCoursesFragment();
            onMenuItemClickChange(coursesFragment);
            return true;
        } else if (item.getItemId() == R.id.mnu_profile) {
            drawerLayout.closeDrawers();
            ProfileFragment profileFragment = new ProfileFragment();
            onMenuItemClickChange(profileFragment);
            return true;
        } else if (item.getItemId() == R.id.mnu_contact) {
            drawerLayout.closeDrawers();
            ContactFragment contactFragment = new ContactFragment();
            onMenuItemClickChange(contactFragment);
            return true;
        }
        return false;
    }

    private void displayProfileImage() {
        //String profileImageUrl = "http://10.0.2.2/file/image/profile.jpg";
        String profileImageUrl = "http://test.js-cambodia.com/ckcc/image/profile.png";
        ImageRequest request = new ImageRequest(profileImageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                View navigationHeader = navigationView.getHeaderView(0);
                CircleImageView imgProfile = (CircleImageView) navigationHeader.findViewById(R.id.img_profile);
                imgProfile.setImageBitmap(response);
            }
        }, 256, 256, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error while loading image from Server", Toast.LENGTH_LONG).show();
            }
        });
        App.getInstance(this).addRequest(request);
    }

    private void onMenuItemClickChange(Fragment fragment){
        // Replace NewsFragment in container
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lyt_content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.txt_logout){
            // Logout from Facebook
            LoginManager.getInstance().logOut();
            // Logout from Firebase
            FirebaseAuth.getInstance().signOut();
            // Start login activity and finish main activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void sendMessage(){
        Bundle bundle = null;
        RemoteMessage.Builder builder = new RemoteMessage.Builder("hello");
    }
}

















