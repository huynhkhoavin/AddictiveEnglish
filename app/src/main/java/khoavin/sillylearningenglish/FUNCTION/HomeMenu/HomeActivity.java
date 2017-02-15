package khoavin.sillylearningenglish.FUNCTION.HomeMenu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.roughike.bottombar.BottomBar;

import khoavin.sillylearningenglish.FUNCTION.Arena.ArenaActivity;
import khoavin.sillylearningenglish.FUNCTION.HomeMenu.FriendList.FriendListAdapter;
import khoavin.sillylearningenglish.FUNCTION.HomeMenu.HomeFragment.FightingFragment;
import khoavin.sillylearningenglish.FUNCTION.HomeMenu.HomeFragment.TrainingFragment;
import khoavin.sillylearningenglish.FUNCTION.MailBox.MailActivity;
import khoavin.sillylearningenglish.FUNCTION.TrainingRoom.MediaPlayer.LessonDetailActivity;
import khoavin.sillylearningenglish.FUNCTION.TrainingRoom.TrainingActivity;
import khoavin.sillylearningenglish.PATTERN.FragmentPattern;
import khoavin.sillylearningenglish.PATTERN.ViewPagerAdapter;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;

import static khoavin.sillylearningenglish.R.id.viewPager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomeActivity";
    private BottomBar mBottomBar;
    private DrawerLayout drawer;
    private ViewPager mViewPager;
    private ViewPagerAdapter homeViewPagerAdapter;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    //region FRIEND
    private RecyclerView listFriends;
    private Friend[] friends= new Friend[]{
            new Friend(R.drawable.quang_le,"Quang Lê",true),
            new Friend(R.drawable.quang_le,"Quang Lê",false),
            new Friend(R.drawable.quang_le,"Quang Lê",true),
            new Friend(R.drawable.quang_le,"Quang Lê",true),
            new Friend(R.drawable.quang_le,"Quang Lê",false),
            new Friend(R.drawable.quang_le,"Quang Lê",false),
            new Friend(R.drawable.quang_le,"Quang Lê",true),
            new Friend(R.drawable.quang_le,"Quang Lê",true),
            new Friend(R.drawable.quang_le,"Quang Lê",true),
            new Friend(R.drawable.quang_le,"Quang Lê",true),
            new Friend(R.drawable.quang_le,"Quang Lê",true),
            new Friend(R.drawable.quang_le,"Quang Lê",true),
            new Friend(R.drawable.quang_le,"Quang Lê",true)
    };
    private FriendListAdapter friendListAdapter;
    //endregion
    private void initControl(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = (ViewPager)findViewById(viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        String[] TabTitle = {"Chinh Chiến","Luyện Tập"};
        FragmentPattern[] FragmentList = {new FightingFragment(),new TrainingFragment()} ;
        homeViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),TabTitle, FragmentList);

        //region Friend List

        listFriends = (RecyclerView)findViewById(R.id.friendRecycleView);
        listFriends.setAdapter(friendListAdapter);
        friendListAdapter = new FriendListAdapter(this,friends);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listFriends.setLayoutManager(linearLayoutManager);
        listFriends.setAdapter(friendListAdapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
        listFriends.addItemDecoration(dividerItemDecoration);
        //endregion
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        setTitle("");
        initControl();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

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
        //code here
            }
    //region Default Override
        boolean doubleBackToExitPressedOnce = false;

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            // Handle the camera action
//            ListeningFragment fragment = new ListeningFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.content_home, fragment).commit();
            Intent it = new Intent(HomeActivity.this,TrainingActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_lucky_spinning) {

        } else if (id == R.id.nav_profile) {
            Intent it = new Intent(HomeActivity.this,LessonDetailActivity.class);
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

    private void replaceHomeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                .replace(R.id.content_home, fragment)
                .addToBackStack(null)
                .commit();
    }
}
