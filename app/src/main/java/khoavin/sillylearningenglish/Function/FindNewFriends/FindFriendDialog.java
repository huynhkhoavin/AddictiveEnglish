package khoavin.sillylearningenglish.Function.FindNewFriends;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.FindNewFriends.Object.FindFriendItem;
import khoavin.sillylearningenglish.Function.FindNewFriends.View.FindFriendAdapter;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

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

    /*/
    Bind View
     */
    @BindView(R.id.floating_search_view)
    FloatingSearchView searchView;


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
//        NetworkProgress networkProgress = new NetworkProgress(activity) {
//            @Override
//            public void Response(String response) {
//                ArrayList<FindFriendItem> list = ArrayConvert.toArrayList(JsonConvert.getArray(response,FindFriendItem[].class));
//                ShowListFriend(list);
//            }
//
//            @Override
//            public Map<String, String> getListParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("ls_id",((Lesson)Storage.getValue(CURRENT_LESSON)).getLsId());
//                return params;
//            }
//        };
        //networkProgress.Execute(FIND_FRIEND_BY_NAME);
    }
    public void SetUpAdapter(){
//        findFriendAdapter = new FindFriendAdapter(getContext(), new ArrayList<>());
//        findFriendAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
//            @Override
//            public void OnClick(int ItemPosition, Object ItemObject) {
//
//            }
//        });
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(findFriendAdapter);
//        recyclerView.setNestedScrollingEnabled(false);
    }
    public void ShowListFriend(ArrayList<FindFriendItem> list){
        findFriendAdapter.setDataSource(ArrayConvert.toObjectArray(list));
    }
}
