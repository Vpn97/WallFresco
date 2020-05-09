package com.apkzube.wallfresco.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.databinding.ActivityDashboardBinding;
import com.apkzube.wallfresco.db.repo.WallRepository;
import com.apkzube.wallfresco.ui.favorite.FavoriteFragment;
import com.apkzube.wallfresco.ui.trending.TrendingFragment;
import com.apkzube.wallfresco.ui.wallpaper.WallpaperFragment;
import com.apkzube.wallfresco.util.BroadcastListener;
import com.apkzube.wallfresco.util.Constant;
import com.apkzube.wallfresco.util.DataStorage;
import com.apkzube.wallfresco.util.DateUtil;
import com.apkzube.wallfresco.util.NetworkReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BroadcastListener {

    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_MEDIA_LOCATION};
    public static final Integer REQUEST_CODE = 99;
    private DataStorage storage;
    int useCount;
    boolean isPermissionGranted;
    private ActivityDashboardBinding mBinding;
    private Fragment wallpaperFragment, trendingFragment, favoriteFragment, activeFragment;
    private FragmentManager fragmentManager;
    private Snackbar snack;
    private NetworkReceiver networkReceiver;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        storage = new DataStorage(this, getString(R.string.user_data));
        useCount = (int) storage.read(getString(R.string.user_count_key), DataStorage.INTEGER);
        if (useCount == 0)
            useCount = 1;
        else
            useCount++;

        storage.write(getString(R.string.user_count_key), useCount);

        if(!DateUtil.isSameDateRequest(storage)){
            new WallRepository(getApplication()).deleteAllWallpaper();
        }

        allocation();
        takePermission();
        setEvent();

    }

    private void allocation() {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        //-----------------------------------------------------------

        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.bottom_menu_wallpaper, R.id.bottom_menu_trend, R.id.bottom_menu_favorite)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        //-------------------------------------------------------------

        bottomNavigationView = mBinding.appBarDashboard.bottomNavigation;

        wallpaperFragment = new WallpaperFragment();
        trendingFragment = new TrendingFragment();
        favoriteFragment = new FavoriteFragment();
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.rootFram, favoriteFragment, "favorite").hide(favoriteFragment).commit();
        fragmentManager.beginTransaction().add(R.id.rootFram, trendingFragment, "trending").hide(trendingFragment).commit();
        fragmentManager.beginTransaction().add(R.id.rootFram, wallpaperFragment, "wallpaper").commit();

        Toolbar toolbar = mBinding.appBarDashboard.toolbar;
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mBinding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        mBinding.navView.setNavigationItemSelectedListener(this);

        //set Snack bar for Internet Connection
        snack = Snackbar.make(mBinding.appBarDashboard.conLayout, getString(R.string.no_internet_msg), Snackbar.LENGTH_INDEFINITE);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snack.getView().getLayoutParams();
        params.setAnchorId(bottomNavigationView.getId());
        params.anchorGravity = Gravity.TOP;
        params.gravity = Gravity.TOP;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        snack.getView().setLayoutParams(params);
        snack.getView().setPadding(0, 0, 0, 0);
        snack.setTextColor(getResources().getColor(android.R.color.white));

        networkReceiver = new NetworkReceiver(this);
        try {
            broadcastIntent();
        } catch (Exception e) {
            Log.e(Constant.TAG, "onRequestPermissionsResult: ", e);
        }
    }

    private void setEvent() {
        activeFragment = wallpaperFragment;

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.bottom_menu_wallpaper:
                    fragmentManager.beginTransaction().hide(activeFragment).show(wallpaperFragment).commit();
                    activeFragment = wallpaperFragment;


                    break;

                case R.id.bottom_menu_trend:
                    fragmentManager.beginTransaction().hide(activeFragment).show(trendingFragment).commit();
                    activeFragment = trendingFragment;

                    break;

                case R.id.bottom_menu_favorite:
                    fragmentManager.beginTransaction().hide(activeFragment).show(favoriteFragment).commit();
                    activeFragment = favoriteFragment;

                    break;
            }
            return true;
        });
    }

    public void broadcastIntent() {
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            broadcastIntent();
        } catch (Exception e) {
            Log.e(Constant.TAG, "onRequestPermissionsResult: ", e);
        }
    }

    private void takePermission() {
        //check Build version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(Dashboard.this, permissions[0]) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(Dashboard.this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
                //permission is not granted need to get permission
                this.requestPermissions(permissions, REQUEST_CODE);
            } else {
                //Permission Already Granted
                isPermissionGranted = true;

            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_menu_rating:

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));

                break;
            case R.id.nav_menu_share_app:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Download best " + getString(R.string.app_name) + " : https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n" +
                                getString(R.string.app_name)+" Wallpapers is a free app that has a large varieties of 4K (UHD | Ultra HD) as well as Full HD (High Definition) wallpapers | backgrounds.\n"
                                + "\n\n" + "follow apkzube.dev on Instagram : https://www.instagram.com/apkzube\n");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;

            case R.id.nav_menu_about_app:
                setAboutAppDialog();
                break;

            case R.id.nav_contact_us:
                sendEmail();
                break;

            case R.id.nav_privacy_policy:
                setPrivacyPolicy();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_instagram) {
            Intent likeIng = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/apkzube"));
            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/apkzube")));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == RESULT_OK && grantResults[1] == RESULT_OK) {
                //permission not granted
                Toast.makeText(this, "We need permission for features", Toast.LENGTH_SHORT).show();
            } else {
                isPermissionGranted = true;
            }
        }
    }


    @Override
    public void updateUI(boolean isInternet) {

        Log.d(Constant.TAG, "updateUI: " + isInternet);
        if (isInternet) {

            if (snack.isShown()) {
                snack.setText("online now");
                snack.getView().setBackgroundColor(getResources().getColor(R.color.green));
                (new Handler()).postDelayed(() -> {
                    snack.dismiss();
                }, 3000);

                Log.d(Constant.TAG, "updateUI: dismiss");
            }

        } else {
            snack.getView().setBackgroundColor(getResources().getColor(R.color.red));
            snack.setText("No connection");
            if (!snack.isShown())
                snack.show();
            Log.d(Constant.TAG, "updateUI: show");
        }
    }

    @Override
    public void onBackPressed() {
        if (useCount % 3 == 0) {
            storage.write(getString(R.string.user_count_key), ++useCount);
            final AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
            LayoutInflater inflater = LayoutInflater.from(Dashboard.this);
            final View view = inflater.inflate(R.layout.layout_rating_dialog, null);
            builder.setView(view);
            final AlertDialog dialog = builder.create();
            Button btnNo = view.findViewById(R.id.btnNo);
            Button btnRate = view.findViewById(R.id.btnRate);

            btnNo.setOnClickListener(view12 -> dialog.dismiss());

            btnRate.setOnClickListener(view1 -> {
                dialog.dismiss();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dialog.show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(networkReceiver);
        } catch (Exception e) {
            Log.d(Constant.TAG, "onPause: " + e.getMessage());
        }
    }


    private void setAboutAppDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        LayoutInflater inflater = LayoutInflater.from(Dashboard.this);
        final View aboutAppView = inflater.inflate(R.layout.layout_about_app, null);
        builder.setView(aboutAppView);
        Button btnPrivacyPolicy = aboutAppView.findViewById(R.id.btnPrivacyPolicy);
        btnPrivacyPolicy.setOnClickListener(view -> {
            builder.create().dismiss();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.uri_privacy_policy))));
        });
        builder.create().show();
    }

    private void setPrivacyPolicy() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        LayoutInflater inflater = LayoutInflater.from(Dashboard.this);
        final View PolicyView = inflater.inflate(R.layout.layout_privacy_policy, null);
        builder.setView(PolicyView);
        builder.setTitle(getResources().getString(R.string.app_name) + " Privacy Policy");
        builder.setIcon(R.mipmap.ic_launcher);
        final WebView webView = PolicyView.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(getString(R.string.privacy_policy_path));
                return false;
            }
        });
        webView.loadUrl(getString(R.string.privacy_policy_path));
        builder.setPositiveButton("AGREE", (dialog, which) -> builder.create().dismiss());
        builder.create().show();
    }

    protected void sendEmail() {
        String TO = "";
        String CC = getString(R.string.devloper_mail);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + CC));


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " : Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "ArcX Dev : Send Email"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Dashboard.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }


       /* try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            this.startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            Toast.makeText(Dashboard.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
*/
    }

}
