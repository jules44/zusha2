package com.example.jules.zusha;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import com.github.clans.fab.FloatingActionButton;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.jules.zusha.fragments.fragabout;
import com.example.jules.zusha.fragments.fraghome;
import com.example.jules.zusha.fragments.fragrules;
import com.example.jules.zusha.fragments.fragsend;
import com.example.jules.zusha.fragments.fragsigns;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MyMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_IMAGE_CAPTURE_CODE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final  int IMAGE_PICKER_SELECT=1;


    ImageView imageView;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       final FloatingActionButton camera = (FloatingActionButton) findViewById(R.id.cam);
       FloatingActionButton gallery= (FloatingActionButton) findViewById(R.id.gal);
        final FloatingActionButton video= (FloatingActionButton) findViewById(R.id.vid);

//camera clicking
       camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                    if(camera.isClickable()) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE_CODE );
                        }

                    }
            }
        });
//video clicking
       video.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(video.isClickable()) {

                   Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                   if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                       startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                   }
               }
           }
       });

       //gallery
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/* video/*");
                pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(pickIntent, IMAGE_PICKER_SELECT);
            }
        });





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_home);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new fraghome();
                break;
            case R.id.nav_rules:
                fragment = new fragrules();
                break;
            case R.id.nav_signs:
                fragment = new fragsigns();
                break;
            case R.id.nav_send:
                fragment = new fragsend();
                break;
            case R.id.nav_about:
                fragment = new fragabout();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        imageView = findViewById(R.id.myImage);
        videoView = findViewById(R.id.myVideo);
        if (requestCode == REQUEST_IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {

            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Toast.makeText(this, "Bundle not empty", Toast.LENGTH_SHORT).show();
                Bitmap imageBitmap = (Bitmap) bundle.get("data");
//                if (imageBitmap != null) {
//                   // videoView.setVideoURI(null);
//                    Toast.makeText(this, "imageBitmap not empty", Toast.LENGTH_SHORT).show();
//                    imageView.setImageBitmap(imageBitmap);
//
//                }
                uploadImage(imageBitmap);
            }
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Toast.makeText(this, "Bundle not empty", Toast.LENGTH_SHORT).show();
                Uri videoUri = (Uri) data.getData();
//                if (videoUri != null) {
//                    //imageView.setImageBitmap(null);
//                    Toast.makeText(this, "uriVideo not empty", Toast.LENGTH_SHORT).show();
//                    videoView.setVideoURI(videoUri);
//                    videoView.start();
//
//                }
                uploadVideo(videoUri);

            }
        }


        if (requestCode==IMAGE_PICKER_SELECT && requestCode ==RESULT_OK){
            Bundle bundle = data.getExtras();
            if (bundle!=null){
                Toast.makeText(this, "Bundle not empty", Toast.LENGTH_SHORT).show();
                Uri videoUri=(Uri) data.getData();
                Bitmap imageBitmap=(Bitmap) bundle.get("data");
                if (videoUri.toString().contains("video")){
                    uploadVideo(videoUri);
                }else{
                    uploadImage(imageBitmap);
                }
            }
        }

    }

    private void uploadImage(Bitmap imageBitmap) {
        if (imageBitmap != null) {
            // videoView.setVideoURI(null);
            Toast.makeText(this, "imageupBitmap not empty", Toast.LENGTH_SHORT).show();
            imageView.setImageBitmap(imageBitmap);

        }


    }


    private void uploadVideo(Uri videoUri) {
        if (videoUri != null) {
            imageView.setImageBitmap(null);
            Toast.makeText(this, "uriVideo not empty", Toast.LENGTH_SHORT).show();
            videoView.setVideoURI(videoUri);
            videoView.start();

        }

    }


}
