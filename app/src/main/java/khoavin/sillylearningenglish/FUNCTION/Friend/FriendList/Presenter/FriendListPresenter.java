package khoavin.sillylearningenglish.FUNCTION.Friend.FriendList.Presenter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.FUNCTION.Friend.FriendList.View.IFriendListView;
import khoavin.sillylearningenglish.FirebaseObject.RegisterUser;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class FriendListPresenter implements IFriendListPresenter {

    private IFriendListView friendListView;
    @Inject
    IFriendService friendService;
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
    public FriendListPresenter(IFriendListView flv){
        this.friendListView = flv;
        ((SillyApp) ((AppCompatActivity) flv).getApplication())
                .getFriendComponent()
                .inject(this);
        friendService.getAllFriend();
    }
    @Override
    public void ShowFriendList() {
        friendListView.ShowFriendList(friends);
    }

    @Override
    public void searchUser(String user) {
        //friendService.findFriendByName(user);
        SearchUser searchUser = new SearchUser();
        searchUser.execute(user);
    }
    class SearchUser extends AsyncTask<String,Long,RegisterUser>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Long... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected RegisterUser doInBackground(String... params) {
            return friendService.findFriendByName(params[0]);
        }

        @Override
        protected void onPostExecute(RegisterUser registerUser) {
            super.onPostExecute(registerUser);
            friendListView.displaySearchedUser(registerUser);
        }
    }
}
