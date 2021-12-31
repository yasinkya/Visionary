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
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.yaska.visionary.adapter.BannerPageAdapter;
import com.yaska.visionary.adapter.MainRecyclerAdapter;
import com.yaska.visionary.model.AllCategories;
import com.yaska.visionary.model.Banners;
import com.yaska.visionary.model.CategoryItem;
import com.yaska.visionary.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    User user;

    TextView menu_username;

    List<Banners> bannersList;
    List<CategoryItem> categoryItemList;
    List<AllCategories> allCategoriesList;

    BannerPageAdapter bannerPageAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerPageView;

    Timer timerSlide;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    MainRecyclerAdapter mainRecyclerAdapter;
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

//        user = (User) getIntent().getSerializableExtra("user");
        user = new User("yasin", "kaya", "yaska.com", "yaska", "1");

        menu = navigationView.getMenu();

        menu_username = (TextView)navigationView.getHeaderView(0).findViewById(R.id.header_username);
        menu_username.setText(user.UserName);


        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tablay);
        nestedScrollView = findViewById(R.id.nested_scroll);

        fetchBannerItems("");

        fetchAllCategories("");

        // todo Tabbar değişiminde içerik değişimi
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 1:
//                        setScrDef();
                        Toast.makeText(MainPageActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
//                        fetchBannerItems("Top","Movies");
                        break;
                    case 2:
//                        setScrDef();
                        Toast.makeText(MainPageActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();

//                        fetchBannerItems("Top","Movies");

                        break;
                    case 3:
//                        setScrDef();
                        Toast.makeText(MainPageActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();

//                        fetchBannerItems("Top","Movies");
                        break;
                    default:
                        Toast.makeText(MainPageActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();

//                        setScrDef();
//                        fetchBannerItems("Top","Movies");

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });


        List<CategoryItem> homeCatItemList = new ArrayList<>();
        homeCatItemList.add(new CategoryItem(100,"Yenilmez 3","https://upload.wikimedia.org/wikipedia/tr/8/82/Undistuped_III.jpg","J6foXCWtPu4"));
        homeCatItemList.add(new CategoryItem(101,"Hayalet Sürücü 2","https://i.ytimg.com/vi/Hy_lpDRdojE/maxresdefault.jpg","Hy_lpDRdojE"));
        homeCatItemList.add(new CategoryItem(102,"Ölümsüzler Tanrıların Savaşı","https://tr.web.img4.acsta.net/medias/nmedia/18/86/26/26/19831918.jpg","iBYmIQRrsp4"));
        homeCatItemList.add(new CategoryItem(103,"Chernobyl","https://64.media.tumblr.com/ec7ab4470919ada6a1b4bbdb55a5dbc1/tumblr_pswgoaTGiO1tuobsoo1_1280.jpg","https://drive.google.com/file/d/1nkAHD4dR5k9j8GUy09Curgs9CC76P9y0/view?usp=sharing"));

        List<CategoryItem> homeCatItemList2 = new ArrayList<>();
        homeCatItemList2.add(new CategoryItem(104,"Görevimiz Tatil","https://tr.web.img4.acsta.net/pictures/18/01/12/14/36/3191471.jpg","H7lvU8yY_xQ"));
        homeCatItemList2.add(new CategoryItem(105,"Hugo","https://foto.sondakika.com/haber/2011/11/30/hugo-filmi-3160674-3160674_o.jpg","_5g9rL9dQXw"));
        homeCatItemList2.add(new CategoryItem(106,"Joker","data:image/jpeg;base64,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","https://s330.fdfiles.net/download.php?name=Joker.2019.mp4&md5=0429f13319b33d3b9e9474c2585b9d5a&fid=oga0xspncsgz&uid=free&speed=59&till=1622387315&trycount=1&ip=81.215.160.21&sid=a4dc4012a97898c6462f321c792b6b8b&browser=0d9b315c9114fee54103aa841dbc2e27&did=2471170939&secure=1&sign=7fee1b00380335604d5a78ce1ff89225"));


        List<CategoryItem> homeCatItemList3 = new ArrayList<>();
        homeCatItemList3.add(new CategoryItem(107,"Gattaca","https://64.media.tumblr.com/07a939107981389d5213072b21069bbb/tumblr_pxeuw6FJ3M1tuobsoo1_1280.jpg","https://r6---sn-u0g3uxax3-5nge.googlevideo.com/videoplayback?expire=1622252637&ei=_EexYKbxPIOpgAeW1ZegAw&ip=88.252.134.44&id=o-ACh6wlL7Un4YqLqlKkv-MRGTE5bNajmHERskvIg-6IER&itag=18&source=youtube&requiressl=yes&mh=qr&mm=31%2C29&mn=sn-u0g3uxax3-5nge%2Csn-nv47lney&ms=au%2Crdu&mv=m&mvi=6&pcm2cms=yes&pl=24&ctier=A&pfa=5&initcwndbps=657500&hightc=yes&vprv=1&mime=video%2Fmp4&ns=mR7a_KUn9PAu1n2HQ2SM8SwF&gir=yes&clen=44913882&ratebypass=yes&dur=1203.165&lmt=1540938972819852&mt=1622230852&fvip=6&fexp=24001373%2C24007246&c=WEB&txp=5431432&n=IJ4b9uHUDUWynRQKH9&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cctier%2Cpfa%2Chightc%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgGe6utuDyQUT7MSjlMzq2v1A8pJhjZDrK3xgISHViyPICIQCOqSs8Rj7yuAXp2kqPm9Ig6owaZxVCeMC4Ee_ppGkG4g%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRAIgcyKm7AQ06Xa6lto1WcCvWyrb_VK_fgpEpU4TFGiQ4Q0CIEWXkCgj8fvP5r-ZrmDvP2AuTJ4uwlTps_Ty9wDVzQ9d"));
        homeCatItemList3.add(new CategoryItem(108,"Creation","https://64.media.tumblr.com/69dd95732f7830896427958b9d68bbf0/tumblr_pssiqhUfnR1tuobsoo1_1280.jpg","https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homeCatItemList3.add(new CategoryItem(109,"Altered Carbon","https://64.media.tumblr.com/e950c52ee46f2dae9e030eed1ab55c1e/tumblr_psskl7qmFc1tuobsoo1_r1_1280.jpg","https://cload13.cf/hls/oncayoksullukvarken-2020-trdubmp4-b4C2N6HscFH.mp4"));
        homeCatItemList3.add(new CategoryItem(110,"Hızlı Ve Öfkeli","data:image/jpeg;base64,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","mgE4RpiW8Uw"));

        //-------------- CREATE AND SET CATEGORIES
        allCategoriesList = new ArrayList<>();
        allCategoriesList.add(new AllCategories(1,"Horror",homeCatItemList));
        allCategoriesList.add(new AllCategories(2,"Comedi",homeCatItemList2));
        allCategoriesList.add(new AllCategories(3,"Action",homeCatItemList3));

        setCategoriesMainRecycler(allCategoriesList);



    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // todo conf here
        switch (item.getItemId()){
            case R.id.menu_home:

                break;
            case R.id.menu_search:
                Toast.makeText(MainPageActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_top:
                Toast.makeText(MainPageActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_favorites:
                Toast.makeText(MainPageActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_theaters:
                Intent intent = new Intent(MainPageActivity.this, CityTheaterActivity.class);
                this.startActivity(intent);
                break;
            case R.id.menu_account:
                Toast.makeText(MainPageActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();

                break;
            case R.id.menu_share:
                Toast.makeText(MainPageActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent_share = new Intent(Intent.ACTION_SEND);
                intent_share.setType("text/plain");
                intent_share.putExtra(Intent.EXTRA_TEXT, "https://github.com/yasinkya/Visionary.git");
                startActivity(Intent.createChooser(intent_share, "Thanks For Sharing Our App :')"));;
                break;
            case R.id.menu_settings:
                Toast.makeText(MainPageActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();

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
    private void fetchAllCategories(String element) {
        //    todo get datas from database
//        fetchCategoryItem = new FetchCategoryItem(element);
//
//        categoryItems =new ArrayList<>();
//
//        fetchCategoryItem.readData(list -> {
//            for (Integer i =0; i<list.size();i++){
//                try {
//                    categoryItems.add(new CategoryItem(
//
//                            list.get(i).getMovieId(),
//                            list.get(i).getMovieName(),
//                            list.get(i).getMovieImgUrl(),
//                            list.get(i).getMovieFileUrl()
//                    ));
//
//                }catch (Exception e){
//
//                }
//
//            }
//            allCategoriesList = new ArrayList<>();
//            allCategoriesList.add(new AllCategories(1, String.valueOf(element),categoryItems));
//            setCategoriesMainRecycler(allCategoriesList);
//
//
//
//        });

    }

    // Set recycler
    private void setCategoriesMainRecycler(List<AllCategories> allCategoriesList){
        recyclerView = findViewById(R.id.main_recycle);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this,allCategoriesList);
        recyclerView.setAdapter(mainRecyclerAdapter);
    }

    // category changed trigger
    private void setScrDef(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }


    //BANNERS PAGE
    private void fetchBannerItems(String element) {
        //    todo get datas from database
//        firebaseData = new FirebaseData(element);
//        bannerPage =new ArrayList<>();
//
//        firebaseData.readData(list -> {
//            for (Integer i =0; i<list.size();i++){
//                try {
//                    bannerPage.add(new Banners(
//                            list.get(i).getMovieId(),
//                            list.get(i).getMovieName(),
//                            list.get(i).getMovieImgUrl(),
//                            list.get(i).getMovieFileUrl()
//                    ));
//
//                }catch (Exception e){
//
//                }
//
//            }
//            setBannerPageAdapter(bannerPage);
//        });
        bannersList = new ArrayList<>();
        bannersList.add(new Banners(107,"Gattaca","https://64.media.tumblr.com/07a939107981389d5213072b21069bbb/tumblr_pxeuw6FJ3M1tuobsoo1_1280.jpg","https://r6---sn-u0g3uxax3-5nge.googlevideo.com/videoplayback?expire=1622252637&ei=_EexYKbxPIOpgAeW1ZegAw&ip=88.252.134.44&id=o-ACh6wlL7Un4YqLqlKkv-MRGTE5bNajmHERskvIg-6IER&itag=18&source=youtube&requiressl=yes&mh=qr&mm=31%2C29&mn=sn-u0g3uxax3-5nge%2Csn-nv47lney&ms=au%2Crdu&mv=m&mvi=6&pcm2cms=yes&pl=24&ctier=A&pfa=5&initcwndbps=657500&hightc=yes&vprv=1&mime=video%2Fmp4&ns=mR7a_KUn9PAu1n2HQ2SM8SwF&gir=yes&clen=44913882&ratebypass=yes&dur=1203.165&lmt=1540938972819852&mt=1622230852&fvip=6&fexp=24001373%2C24007246&c=WEB&txp=5431432&n=IJ4b9uHUDUWynRQKH9&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cctier%2Cpfa%2Chightc%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgGe6utuDyQUT7MSjlMzq2v1A8pJhjZDrK3xgISHViyPICIQCOqSs8Rj7yuAXp2kqPm9Ig6owaZxVCeMC4Ee_ppGkG4g%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRAIgcyKm7AQ06Xa6lto1WcCvWyrb_VK_fgpEpU4TFGiQ4Q0CIEWXkCgj8fvP5r-ZrmDvP2AuTJ4uwlTps_Ty9wDVzQ9d"));
        bannersList.add(new Banners(108,"Creation","https://64.media.tumblr.com/69dd95732f7830896427958b9d68bbf0/tumblr_pssiqhUfnR1tuobsoo1_1280.jpg","https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        bannersList.add(new Banners(109,"Altered Carbon","https://64.media.tumblr.com/e950c52ee46f2dae9e030eed1ab55c1e/tumblr_psskl7qmFc1tuobsoo1_r1_1280.jpg","https://cload13.cf/hls/oncayoksullukvarken-2020-trdubmp4-b4C2N6HscFH.mp4"));
        bannersList.add(new Banners(110,"Hızlı Ve Öfkeli","data:image/jpeg;base64,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","mgE4RpiW8Uw"));

        setBannerPageAdapter(bannersList);

    }

    private void setBannerPageAdapter(List<Banners> listBanners){

        timerSlide =new Timer();
        timerSlide.scheduleAtFixedRate(new AutoSlider(),8000,10000);
        indicatorTab.setupWithViewPager(bannerPageView,true);

        bannerPageView = findViewById(R.id.bannerView);
        bannerPageAdapter = new BannerPageAdapter(this, listBanners);
        bannerPageView.setAdapter(bannerPageAdapter);
        indicatorTab.setupWithViewPager(bannerPageView);
    }

    // BANNER PAGE OUTO SLİDE NEXT
    class AutoSlider extends TimerTask {
        @Override
        public void run() {
            MainPageActivity.this.runOnUiThread(()->{
                if(bannerPageView.getCurrentItem() < bannerPageView.getChildCount()-1){
                    bannerPageView.setCurrentItem(bannerPageView.getCurrentItem()+1);
                }
                else
                    bannerPageView.setCurrentItem(0);

            });
        }
    }





}