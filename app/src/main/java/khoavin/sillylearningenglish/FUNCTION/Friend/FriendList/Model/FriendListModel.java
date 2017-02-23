package khoavin.sillylearningenglish.FUNCTION.Friend.FriendList.Model;

import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class FriendListModel implements IFriendListModel {
private Friend[] friends;

    public FriendListModel(){
        FakeData();
    }
    private void FakeData(){
        friends= new Friend[]{
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
    }

    @Override
    public Friend[] GetFriendList() {
        if (friends==null)
            FakeData();
        return friends;
    }
}
