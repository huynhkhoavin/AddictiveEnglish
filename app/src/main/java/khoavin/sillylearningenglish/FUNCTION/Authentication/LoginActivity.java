package khoavin.sillylearningenglish.FUNCTION.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import khoavin.sillylearningenglish.FUNCTION.Authentication.Login.ILoginView;
import khoavin.sillylearningenglish.FUNCTION.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.VolleySingleton;

import static khoavin.sillylearningenglish.SYSTEM.Constant.ActionCode.ACTION;
import static khoavin.sillylearningenglish.SYSTEM.Constant.ActionCode.CHECKING_FB_ID;
import static khoavin.sillylearningenglish.SYSTEM.Constant.RequestMethodCode.FACEBOOK_ID;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.WEBSERVICE_ADDRESS_USER_MANAGEMENT;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements ILoginView {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mHostAddress;
    private Button mHostChange;

    private View mProgressView;
    private View mLoginFormView;
    //region FB
    private LoginButton mFacebookLogin;
    private CallbackManager callbackManager;
    private Intent it;
    private String Facebook_ID;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region Facebook Login
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        //do not move to another line
        setContentView(R.layout.activity_login);

        mHostAddress = (EditText)findViewById(R.id.host_ip);
        mHostChange = (Button)findViewById(R.id.btnHostChange);
        mHostChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                                it = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(it);
            }
        });


        mFacebookLogin = (LoginButton)findViewById(R.id.login_button);



        mFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                startActivity(it);
                AccessToken accessToken = AccessToken.getCurrentAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                System.out.println(response.toString());
                                final JSONObject jsonObject = response.getJSONObject();
                                try {
                                    System.out.println(jsonObject.get("id"));
                                    Facebook_ID = jsonObject.get("id").toString();
                                    System.out.println(jsonObject.get("name"));

                                    RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
                                    String parameterGet = "";
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, WEBSERVICE_ADDRESS_USER_MANAGEMENT,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    HashMap<String,String> result = JsonConvert.JsonToHashMap(response);
                                                    System.out.print(result.get("0"));
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.println("Error");
                                        }
                                    }){
                                        @Override
                                        protected Map<String,String> getParams(){
                                            Map<String,String> params = new HashMap<String, String>();
                                            params.put(ACTION,CHECKING_FB_ID);
                                            params.put(FACEBOOK_ID,Facebook_ID);
                                            return params;
                                        }
                                    };
                                    //queue.add(stringRequest);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("Facebook Cancel");
            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("Facebook Error");
            }
        });
        //endregion

    }

    @Override
    public void onLoginSuccess(String message) {

    }

    @Override
    public void onLoginFailure(String message) {

    }
}