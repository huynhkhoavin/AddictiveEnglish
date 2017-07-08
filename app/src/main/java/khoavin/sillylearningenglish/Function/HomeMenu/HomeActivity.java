package khoavin.sillylearningenglish.Function.HomeMenu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.auth.FirebaseAuth;

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
import khoavin.sillylearningenglish.Function.LuckySpinning.ActivitySpinning;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.MailActivity;
import khoavin.sillylearningenglish.Function.Ranking.Views.RankingActivity;
import khoavin.sillylearningenglish.Function.Social.SocialFragment.SocialFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonDetailActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.TrainingActivity;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IInboxService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.BlurBuilder;
import khoavin.sillylearningenglish.SingleViewObject.Common;

public class HomeActivity extends BaseDetailActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    private TextView newMailNumber;

    @Inject
    IVolleyService volleyService;

    @Inject
    IAuthenticationService authenticationService;

    @Inject
    IInboxService inboxService;

    @Inject
    IPlayerService playerService;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    boolean doubleBackToExitPressedOnce = false;

    private IFriendPresenter friendListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        //setStatusColor();

        ((SillyApp) getApplication()).getDependencyComponent().inject(this);

        friendListPresenter = new FriendPresenter(this);


        ButterKnife.bind(this);
        setupWindowAnimations();
        goToHomePage();
        ControlSetting();
        EventBus.getDefault().register(this);
        SetupInfo();
        friendListPresenter.DoFunction();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void SetupInfo() {
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        View headerLayout = navigationView.getHeaderView(0);

        final ImageView userAvatar = (ImageView) headerLayout.findViewById(R.id.imgUserAvatar);
        final ImageView blurBackground = (ImageView) headerLayout.findViewById(R.id.blur_background);
        final TextView userName = (TextView)headerLayout.findViewById(R.id.userName);
        userName.setText(authenticationService.getCurrentUser().getDisplayName());

        final TextView rankName = (TextView)headerLayout.findViewById(R.id.rank_name);
        rankName.setText(Common.GetMedalTitleFromLevel(playerService.GetCurrentUser().getLevel(),this));
        final ImageView rankIcon = (ImageView) headerLayout.findViewById(R.id.rank_icon);
        rankIcon.setImageResource(Common.getRankMedalImage(playerService.GetCurrentUser().getLevel()));

        final Bitmap theBitmap;
        Glide.with(this).load(authenticationService.getCurrentUser().getPhotoUrl()).into(userAvatar);
        //Glide.with(this).load(authenticationService.getCurrentUser().getPhotoUrl()).into(blurBackground);

        Glide.with(this)
                .load(authenticationService.getCurrentUser().getPhotoUrl())    // you can pass url too
//                .load(R.drawable.avatar)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        // you can do something with loaded bitmap here
                        Bitmap blurredBitmap = BlurBuilder.blur( HomeActivity.this, resource );

                        //blurBackground.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );
                        //blurBackground.setImageBitmap(blurredBitmap);
                        Blurry.with(HomeActivity.this).radius(30).from(resource).into(blurBackground);
                    }
                });
    }

    @Subscribe
    public void onEvent(String str) {
        if (str.equals(Constants.ACTION.GO_TO_DETAIL)) {
            Intent it = new Intent(HomeActivity.this, LessonDetailActivity.class);
            startActivity(it);
        }
    }

    private void goToHomePage() {
        FragmentTransaction transaction = ((FragmentActivity) this).getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content, new SocialFragment());
        transaction.addToBackStack(FragmentConstaint.HomePageFragment);
        transaction.commit();
    }

    //region Default Override
    public void ControlSetting() {

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        toolbar.setTitle("Addictive English");
        //drawer.setBackgroundColor(getResources().getColor(R.color.white));
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

//        MenuItem mailTitle = menu.findItem(R.id.nav_inbox);
//        mailTitle.setTitle("Dit con me may");
//        mailTitle.setIcon(getResources().getDrawable(R.drawable.medal_gold));

        //Setting up new mail checking.
        newMailNumber = (TextView) navigationView.getMenu().findItem(R.id.nav_inbox).getActionView().findViewById(R.id.counter_text);

        SpannableString s = new SpannableString(personTitle.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        personTitle.setTitle(s);

        s = new SpannableString(worldTitle.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        worldTitle.setTitle(s);

        s = new SpannableString(systemTitle.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        systemTitle.setTitle(s);
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("HOME_RESUME", "Success");
        inboxService.NewMailChecking(playerService.GetCurrentUser().getUserId(), this, volleyService, new IVolleyResponse<Integer>() {
            @Override
            public void onSuccess(Integer responseObj) {
                if (responseObj != null && responseObj > 0) {
                    newMailNumber.setVisibility(View.VISIBLE);
                    newMailNumber.setText(String.valueOf(responseObj));
                } else {
                    newMailNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(ErrorCode errorCode) {
                Log.e("INBOX_NEW_MAIL_CHECKING", errorCode.getDetails() + ", error: " + errorCode.getCode().toString());
                newMailNumber.setVisibility(View.GONE);
            }
        });
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
                doubleBackToExitPressedOnce = false;
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
        if (id == R.id.action_friends) {
            if (drawer != null) {
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
            Intent it = new Intent(HomeActivity.this, TrainingActivity.class);
            //startActivity(it);
            transitionTo(it);

        } else if (id == R.id.nav_lucky_spinning) {
            Intent it = new Intent(HomeActivity.this, ActivitySpinning.class);
            transitionTo(it);
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_arena) {

            Intent it = new Intent(HomeActivity.this, ArenaActivity.class);
            transitionTo(it);
            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        } else if (id == R.id.nav_leader_board) {
            Intent it = new Intent(HomeActivity.this, RankingActivity.class);
            transitionTo(it);

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_inbox) {
            Intent it = new Intent(HomeActivity.this, MailActivity.class);
            transitionTo(it);
        } else if (id == R.id.nav_logout) {
            authenticationService.Logout(this);
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //endregion

    //region transition
    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }
    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        // This view will not be affected by enter transition animation
        //enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        return enterTransition;
    }
    //endregion
}
