package khoavin.sillylearningenglish.Function.HomeMenu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.ArenaActivity;
import khoavin.sillylearningenglish.Function.Friend.Presenter.FriendPresenter;
import khoavin.sillylearningenglish.Function.Friend.Presenter.IFriendPresenter;
import khoavin.sillylearningenglish.Function.Friend.View.FriendListAdapter;
import khoavin.sillylearningenglish.Function.Friend.View.IFriendListView;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeFragment.Fighting.View.FightingFragment;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeFragment.Training.TrainingFragment;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.MailActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonInfo.View.LessonInfoActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.TrainingActivity;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleObject.Friend;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,IFriendListView {
    private static final String TAG = "HomeActivity";
    @BindView(R.id.drawer_layout)DrawerLayout drawer;
    @BindView((R.id.viewPager)) ViewPager mViewPager;
    private ViewPagerAdapter homeViewPagerAdapter;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.tab_layout) TabLayout tabLayout;

    private IFriendPresenter friendListPresenter;
    //region FRIEND
    @BindView(R.id.friendRecycleView) RecyclerView listFriends;
    private FriendListAdapter friendListAdapter;

    //endregion
    private void initControl(){
        //inject by dagger

        setSupportActionBar(toolbar);
        String[] TabTitle = {"Chinh Chiến","Luyện Tập"};
        FragmentPattern[] FragmentList = {new FightingFragment(),new TrainingFragment()} ;
        homeViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),TabTitle, FragmentList);

        //region Friend List
        friendListPresenter = new FriendPresenter(this);
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setTitle("");
        initControl();
        ControlSetting();
        showListFriendFirst();
        UpdateListFriends();
    }
    //region Default Override
        boolean doubleBackToExitPressedOnce = false;

    public void ControlSetting(){

        //region VIEWPAGER
        mViewPager.setAdapter(homeViewPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(homeViewPagerAdapter);
        //endregion
        //region TOOLBAR + FLOATINGBTN + DRAWERLAYOUT + NAVIGATIONVIEW


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //endregion
    }
    @Override
    public void onBackPressed() {
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

        if (id == R.id.action_mailbox) {
            Intent it = new Intent(HomeActivity.this,MailActivity.class);
            startActivity(it);

        }
        else if(id == R.id.action_friends){
            if (drawer!=null){
                if (drawer.isDrawerOpen(GravityCompat.END))
                    drawer.closeDrawer(GravityCompat.END);
                else drawer.openDrawer(GravityCompat.END);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trainning_room) {
            Intent it = new Intent(HomeActivity.this,TrainingActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_lucky_spinning) {
            Intent it = new Intent(HomeActivity.this,LessonInfoActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_profile) {
            Intent it = new Intent(HomeActivity.this,LessonInfoActivity.class);
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


    public void showListFriendFirst(){
        friendListPresenter.GetAllFriendsImmediatly(new FriendEvent() {
            @Override
            public void onListFriendsUid(ArrayList<String> listFriendsUid) {
            }
            @Override
            public void onFindUser(FirebaseAccount userAccount) {
            }
            @Override
            public void onGetAllFriends(ArrayList<FirebaseAccount> list) {
                ArrayList<Friend> friends = new ArrayList<Friend>();
                for(int i = 0; i<list.size();i++){
                    friends.add(new Friend(list.get(i).getAvatarUrl(),list.get(i).getName(),list.get(i).isOnlineStatus()));
                }
                friendListAdapter = new FriendListAdapter(getApplicationContext(), ArrayConvert.toObjectArray(friends));
                listFriends.setAdapter(friendListAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                listFriends.setLayoutManager(linearLayoutManager);
                RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getApplicationContext());
                listFriends.addItemDecoration(dividerItemDecoration);
            }
        });
    }
    @Override
    public void UpdateListFriends() {
        friendListPresenter.GetAllFriendUid(new FriendEvent() {
            @Override
            public void onListFriendsUid(ArrayList<String> listFriendsUid) {
                friendListPresenter.GetAllFriendsRealtime(listFriendsUid,this);
            }

            @Override
            public void onFindUser(FirebaseAccount userAccount) {

            }

            @Override
            public void onGetAllFriends(ArrayList<FirebaseAccount> listFriends) {
                ArrayList<Friend> friends = new ArrayList<Friend>();
                for(int i = 0; i<listFriends.size();i++){
                    friends.add(new Friend(listFriends.get(i).getAvatarUrl(),listFriends.get(i).getName(),listFriends.get(i).isOnlineStatus()));
                }
                friendListAdapter.UpdateDataSource(ArrayConvert.toObjectArray(friends));
            }
        });
    }
}
