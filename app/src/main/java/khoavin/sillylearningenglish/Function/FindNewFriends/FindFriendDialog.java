package khoavin.sillylearningenglish.Function.FindNewFriends;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.FindNewFriends.Object.FindFriendItem;
import khoavin.sillylearningenglish.Function.FindNewFriends.View.FindFriendAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IRankingService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.FIND_FRIEND_BY_NAME;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.REQUEST_ADD_FRIEND;

/**
 * Created by KhoaVin on 22/06/2017.
 */

public class FindFriendDialog extends Dialog {
    Activity activity;
    public String SearchKeyword;
    @Inject
    IVolleyService volleyService;
    @Inject
    IAuthenticationService authenticationService;

    @Inject
    IRankingService rankingService;
    /*/
    Bind View
     */
    @BindView(R.id.floating_search_view)
    FloatingSearchView searchView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    FindFriendAdapter findFriendAdapter;

    public FindFriendDialog(@NonNull Context context, Activity activity) {
        super(context);
        this.activity = activity;
        ((SillyApp) activity.getApplication()).getDependencyComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_search_friends);
        setTitle("Search friend");
        ButterKnife.bind(this);
        SetUpAdapter();
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Toast.makeText(getContext(),"Suggestion Click",Toast.LENGTH_SHORT);
            }

            @Override
            public void onSearchAction(String currentQuery) {
                getListFriendFound(currentQuery);
            }
        });
    }

    public void searchViewSetting(){
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                System.out.print(oldQuery+"/n");
                System.out.print(newQuery+"/n");
            }
        });
    }
    public void getListFriendFound(final String search_keyword){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(activity) {
            @Override
            public void Response(String response) {
                ArrayList<FindFriendItem> list = ArrayConvert.toArrayList(JsonConvert.getArray(response,FindFriendItem[].class));
                ShowListFriend(list);
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",authenticationService.getCurrentUser().getUid());
                params.put("search_keyword",search_keyword);
                return params;
            }

            @Override
            public String getAPI_URL() {
                return FIND_FRIEND_BY_NAME;
            }
        };
        networkAsyncTask.execute();
    }
    public void SetUpAdapter(){
        findFriendAdapter = new FindFriendAdapter(getContext(), new ArrayList<>());
        findFriendAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, Object ItemObject) {
                // Send Friend Request

                FindFriendItem findFriendItem = (FindFriendItem)ItemObject;
                rankingService.AddFriend(authenticationService.getCurrentUser().getUid(), findFriendItem.getUserId(), getContext(), volleyService, new IVolleyResponse<ErrorCode>() {
                    @Override
                    public void onSuccess(ErrorCode responseObj) {
                        Toast.makeText(getContext(),"Friend request success!",Toast.LENGTH_LONG);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage("Your request was sent!");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }

                    @Override
                    public void onError(ErrorCode errorCode) {
                        Toast.makeText(getContext(),errorCode.getDetails(),Toast.LENGTH_LONG);
                    }
                });
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(findFriendAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
    public void addFriendRequest(final String FriendUid){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(activity) {
            @Override
            public void Response(String response) {

            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",authenticationService.getCurrentUser().getUid());
                params.put("friend_id",FriendUid);
                return params;
            }

            @Override
            public String getAPI_URL() {
                return REQUEST_ADD_FRIEND;
            }
        };
    }


    public void ShowListFriend(ArrayList<FindFriendItem> list){
        findFriendAdapter.setDataSource(ArrayConvert.toObjectArray(list));
    }
}
