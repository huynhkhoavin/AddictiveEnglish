package khoavin.sillylearningenglish.FUNCTION.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.FUNCTION.Authentication.Login.ILoginPresenter;
import khoavin.sillylearningenglish.FUNCTION.Authentication.Login.ILoginView;
import khoavin.sillylearningenglish.FUNCTION.Authentication.Login.LoginPresenter;
import khoavin.sillylearningenglish.FUNCTION.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.R;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private ILoginPresenter loginPresenter;
    @BindView(R.id.host_ip)
    EditText mHostAddress;
    @BindView(R.id.btnHostChange)
    Button mHostChange;
    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //do not move to another line
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
        loginPresenter.onCreate(this);

        mHostChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginPresenter.LogOut();
                it = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(it);
            }
        });
        //endregion
    }
@Override
    public void onPause(){
    super.onPause();
        loginPresenter.DetachListener();
}
    @Override
    public void onResume(){
        super.onResume();
        loginPresenter.AttachListener();
    }

    @Override
    public void LoginSuccess() {
        it = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(it);
    }

    @Override
    public void LoginFail() {

    }
}