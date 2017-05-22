package khoavin.sillylearningenglish.Function.HomeMenu;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.ArenaActivity;
import khoavin.sillylearningenglish.Function.Friend.Presenter.FriendPresenter;
import khoavin.sillylearningenglish.Function.Friend.Presenter.IFriendPresenter;
import khoavin.sillylearningenglish.Function.HomeMenu.FragmentConstant.FragmentConstaint;
import khoavin.sillylearningenglish.Function.HomeMenu.HomePage.HomePageFragment;
import khoavin.sillylearningenglish.Function.HomeMenu.HomePage.IHomePagePresenter;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonInfoFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.LessonPlayFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.Reading.FragmentA;
import khoavin.sillylearningenglish.Function.TrainingRoom.TrainingActivity;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "HomeActivity";

    @BindView(R.id.drawer_layout)DrawerLayout drawer;

    @BindView(R.id.nav_view) NavigationView navigationView;

    @BindView(R.id.toolbar)Toolbar toolbar;

    boolean doubleBackToExitPressedOnce = false;

    private IFriendPresenter friendListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        friendListPresenter = new FriendPresenter(this);

        friendListPresenter.DoFunction();

        ButterKnife.bind(this);

        goToHomePage();
        ControlSetting();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(String str){
        if (str.equals("Training")) {
            FragmentTransaction transaction = ((FragmentActivity) this).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, new LessonInfoFragment());
            transaction.addToBackStack(FragmentConstaint.LessonInfoFragment);
            transaction.commit();
        }
    }
    private void goToHomePage(){
        FragmentTransaction transaction = ((FragmentActivity) this).getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content, new HomePageFragment());
        transaction.addToBackStack(FragmentConstaint.HomePageFragment);
        transaction.commit();
    }
    //region Default Override
    public void ControlSetting(){
        setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Double Tap To Close!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.myactionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_friends){
            if (drawer!=null){
                if (drawer.isDrawerOpen(GravityCompat.END))
                    drawer.closeDrawer(GravityCompat.END);
                else
                    drawer.openDrawer(GravityCompat.END);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trainning_room) {
            Intent it = new Intent(HomeActivity.this,TrainingActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_lucky_spinning) {

        } else if (id == R.id.nav_profile) {
            Intent it = new Intent(HomeActivity.this,LessonInfoFragment.class);
            startActivity(it);
        } else if (id == R.id.nav_arena) {

            Intent it = new Intent(HomeActivity.this,ArenaActivity.class);
            startActivity(it);
            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        } else if (id == R.id.nav_leader_board) {

        } else if (id == R.id.nav_help) {

        }
        else if (id == R.id.nav_setting) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //endregion
    }
