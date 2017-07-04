package khoavin.sillylearningenglish.Function.HomeMenu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.ArenaActivity;
import khoavin.sillylearningenglish.Function.Friend.Presenter.FriendPresenter;
import khoavin.sillylearningenglish.Function.Friend.Presenter.IFriendPresenter;
import khoavin.sillylearningenglish.Function.HomeMenu.FragmentConstant.FragmentConstaint;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.MailActivity;
import khoavin.sillylearningenglish.Function.Ranking.Views.RankingActivity;
import khoavin.sillylearningenglish.Function.Social.SocialFragment.SocialFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonDetailActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.TrainingActivity;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.BlurBuilder;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "HomeActivity";
    @Inject
    IVolleyService volleyService;
    @Inject
    IAuthenticationService authenticationService;

    @BindView(R.id.drawer_layout)DrawerLayout drawer;

    @BindView(R.id.nav_view) NavigationView navigationView;

    @BindView(R.id.toolbar)Toolbar toolbar;


    boolean doubleBackToExitPressedOnce = false;

    private IFriendPresenter friendListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        ((SillyApp) getApplication()).getDependencyComponent().inject(this);

        friendListPresenter = new FriendPresenter(this);



        ButterKnife.bind(this);

        goToHomePage();
        ControlSetting();
        EventBus.getDefault().register(this);
        SetupInfo();
        friendListPresenter.DoFunction();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void SetupInfo(){

        View headerLayout = navigationView.getHeaderView(0);

        final ImageView userAvatar = (ImageView)headerLayout.findViewById(R.id.imgUserAvatar);
        final ImageView blurBackground = (ImageView)headerLayout.findViewById(R.id.blur_background);
        final Bitmap theBitmap;
        Glide.with(this).load(authenticationService.getCurrentUser().getPhotoUrl()).into(userAvatar);
        Glide.with(this).load(authenticationService.getCurrentUser().getPhotoUrl()).into(blurBackground);
    }
    @Subscribe
    public void onEvent(String str){
        if (str.equals(Constants.ACTION.GO_TO_DETAIL)) {
            Intent it = new Intent(HomeActivity.this, LessonDetailActivity.class);
            startActivity(it);
        }
    }
    private void goToHomePage(){
        FragmentTransaction transaction = ((FragmentActivity) this).getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content, new SocialFragment());
        transaction.addToBackStack(FragmentConstaint.HomePageFragment);
        transaction.commit();
    }
    //region Default Override
    public void ControlSetting(){
        setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        Menu menu = navigationView.getMenu();

        MenuItem personTitle = menu.findItem(R.id.personal_title);
        MenuItem worldTitle = menu.findItem(R.id.world_title);
        MenuItem systemTitle = menu.findItem(R.id.system_title);


        SpannableString s = new SpannableString(personTitle.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        personTitle.setTitle(s);

        s = new SpannableString(worldTitle.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        worldTitle.setTitle(s);

        s = new SpannableString(systemTitle.getTitle());
        s.setSpan(new TextAppearanceSpan(this,R.style.TextAppearance44),0,s.length(),0);
        systemTitle.setTitle(s);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            finish();
            System.exit(0);
            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
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
        } else if (id == R.id.nav_arena) {

            Intent it = new Intent(HomeActivity.this,ArenaActivity.class);
            startActivity(it);
            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        } else if (id == R.id.nav_leader_board) {
            Intent it = new Intent(HomeActivity.this, RankingActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_help) {

        }
        else if (id == R.id.nav_setting) {

        }
        else if (id == R.id.nav_inbox) {
            Intent it = new Intent(HomeActivity.this, MailActivity.class);
            startActivity(it);
        }
        else if (id == R.id.nav_logout){
            authenticationService.Logout(this);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //endregion
    }
