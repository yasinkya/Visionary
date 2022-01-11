package com.yaska.visionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.yaska.visionary.adapter.SlidingViewAdapter;
import com.yaska.visionary.adapter.CategoryRecyclerAdapter;
import com.yaska.visionary.database.MovieDB;
import com.yaska.visionary.model.AllCategories;
import com.yaska.visionary.model.Movie;
import com.yaska.visionary.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    User user;
    TextView menu_username;

    MovieDB moviedatabase = new MovieDB();

    List<Movie> allMovies = new ArrayList<>();
    List<Movie> mbannersList;
    List<AllCategories> allCategoriesList = new ArrayList<>();

    SlidingViewAdapter slidingViewAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerPageView;

    Timer timerSlide;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    CategoryRecyclerAdapter categoryRecyclerAdapter;
    RecyclerView recyclerView;

    // Sliding Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    
    // Drawer Toggle
    ActionBarDrawerToggle toggle;

    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Sliding menu items
        drawerLayout = findViewById(R.id.drawerlay_main);
        navigationView = findViewById(R.id.view_nav);
        
        // Navigation drawer menu items
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opennavdrawer, R.string.closenavdrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.menu_home);

        user = (User) getIntent().getSerializableExtra("user");

        menu = navigationView.getMenu();

        menu_username = navigationView.getHeaderView(0).findViewById(R.id.header_username);
        menu_username.setText(user.UserName);


        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tablay);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbarLayout);

        setSlidingWindow("");

        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        setScrDef();
                        setSlidingWindow("vizyon");
                        break;
                    case 1:
                        setScrDef();
                        setSlidingWindow("soon");

                        break;
                    case 2:
                        setScrDef();
                        setSlidingWindow("top");
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

    }

    /* menüde seçilen iteme göre işlem yap */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_allmovies:
                intent = new Intent(MainPageActivity.this, AllMoviesActivity.class);
                intent.putExtra("allmovies", (Serializable) allMovies);
                intent.putExtra("username", user.UserName);
                this.startActivity(intent);
                break;
            case R.id.menu_favorites:
                intent = new Intent(MainPageActivity.this, FavoritesActivity.class);
                intent.putExtra("username", user.UserName);
                this.startActivity(intent);
                break;
            case R.id.menu_movie_theaters:
                intent = new Intent(MainPageActivity.this, CityTheaterActivity.class);
                intent.putExtra("user", user.UserName);
                this.startActivity(intent);
                break;
            case R.id.menu_share:
                intent= new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/yasinkya/Visionary.git");
                startActivity(Intent.createChooser(intent, "Thanks For Sharing Our App :')"));
                break;
            case R.id.menu_settings:
                intent = new Intent(MainPageActivity.this, SettingsActivity.class);
                intent.putExtra("user", user);
                this.startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }


    // When Back Pressed close slide menu if its open
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }


    //CATEGORY PAGE
    private void setCategories() {
        List<Movie> HorrorCat = new ArrayList<>();
        List<Movie> ActionCat = new ArrayList<>();
        List<Movie> ComediCat = new ArrayList<>();
        List<Movie> AnimationCat = new ArrayList<>();
        List<Movie> FamilyCat = new ArrayList<>();
        List<Movie> DramCat = new ArrayList<>();

        for (Movie m: allMovies){
            if (m.Genre != null){
                if(m.Genre.contains("Korku")){
                    HorrorCat.add(m);
                }
                if(m.Genre.contains("Aksiyon")){
                    ActionCat.add(m);
                }
                if(m.Genre.contains("Animasyon")){
                    AnimationCat.add(m);
                }
                if(m.Genre.contains("Komedi")){
                    ComediCat.add(m);
                }
                if(m.Genre.contains("Aile")){
                    FamilyCat.add(m);
                }
                if(m.Genre.contains("Dram")){
                    DramCat.add(m);
                }
            }

        }
        allCategoriesList.add(new AllCategories(0, "Dram", DramCat));
        allCategoriesList.add(new AllCategories(0, "Family", FamilyCat));
        allCategoriesList.add(new AllCategories(0, "Horror", HorrorCat));
        allCategoriesList.add(new AllCategories(0, "Action", ActionCat));
        allCategoriesList.add(new AllCategories(0, "Comedy", ComediCat));
        allCategoriesList.add(new AllCategories(0, "Animation", AnimationCat));
        setCategoriesMainRecycler(allCategoriesList);

    }

    // Kategori sayfasının recyclerı
    private void setCategoriesMainRecycler(List<AllCategories> allCategoriesList){
        recyclerView = findViewById(R.id.main_recycle);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        categoryRecyclerAdapter = new CategoryRecyclerAdapter(this,allCategoriesList, user.UserName);
        recyclerView.setAdapter(categoryRecyclerAdapter);
    }

    // category changed trigger
    private void setScrDef(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }


    // kayan pencere itemlerini ayarlama
    private void setSlidingWindow(String element) {
        mbannersList = new ArrayList<>();

        if (allMovies.size() == 0){
            moviedatabase.retMoviesList(getMoviesList -> {
                allMovies = moviedatabase.movieList;
                for (int i =47; i<53; i ++){
                    mbannersList.add(allMovies.get(i));
                }

                setSlidingWindowAdapter(mbannersList);
                setCategories();

            });
        }

        switch (element) {
            case "soon":
                for (int i = 25; i < 30; i++) {
                    mbannersList.add(allMovies.get(i));
                }
                setSlidingWindowAdapter(mbannersList);

                break;
            case "top":
                for (int i = 40; i < 45; i++) {
                    mbannersList.add(allMovies.get(i));
                }
                setSlidingWindowAdapter(mbannersList);
                break;
            case "vizyon":
                for (int i = 47; i < 53; i++) {
                    mbannersList.add(allMovies.get(i));
                }
                setSlidingWindowAdapter(mbannersList);
                break;
        }


    }

    private void setSlidingWindowAdapter(List<Movie> listBanners){

        timerSlide =new Timer();
        timerSlide.scheduleAtFixedRate(new AutoSlider(),10000,12000);
        indicatorTab.setupWithViewPager(bannerPageView,true);

        bannerPageView = findViewById(R.id.bannerView);
        slidingViewAdapter = new SlidingViewAdapter(this, listBanners, user.UserName);
        bannerPageView.setAdapter(slidingViewAdapter);
        indicatorTab.setupWithViewPager(bannerPageView);
    }

    // otomatik değiştirme
    class AutoSlider extends TimerTask {
        @Override
        public void run() {
            MainPageActivity.this.runOnUiThread(()->{
                if(bannerPageView.getCurrentItem() < bannerPageView.getChildCount() - 1){
                    bannerPageView.setCurrentItem(bannerPageView.getCurrentItem() + 1);
                }
                else
                    bannerPageView.setCurrentItem(0);

            });
        }
    }





}