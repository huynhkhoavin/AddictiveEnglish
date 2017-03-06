package khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation;

import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkError;

import javax.inject.Inject;

import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.IArenaPresenter;
import khoavin.sillylearningenglish.FUNCTION.Arena.Views.IArenaView;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Common;
import retrofit2.http.Url;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ArenaPresenter implements IArenaPresenter {

    //Tag name
    private static final String ARENA_TAG = "Anrena presenter: ";

    //The view
    private IArenaView arenaView;

    @Inject
    IPlayerService playerService;

    public ArenaPresenter(final  IArenaView arenaView)
    {
        this.arenaView = arenaView;

        //inject arena service
        ((SillyApp) ((AppCompatActivity) arenaView).getApplication())
                .getNetworkComponent()
                .inject(this);

        GetUserInformation();

    }

    private void GetUserInformation()
    {
        if(playerService != null)
        {
            //final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            playerService.GetuserInformation("b1d7dd8f11b32c9a0f66ea3c4416ca7f0aa02c80", new IServerResponse<User>() {
                @Override
                public void onSuccess(User user) {
                    //Find winrate
                    float winRate = 1.0f *user.getWinMatch() / user.getTotalMatch();
                    winRate *= 100;

                    arenaView.SetCoins(user.getCoin());
                    //arenaView.SetAvatar(firebaseUser.getPhotoUrl());
                    arenaView.SetBattleChain("00000");
                    arenaView.SetAvatar(Uri.parse(user.getAvatarUrl()));
                    arenaView.SetName(user.getName());
                    arenaView.SetLevel(Common.RankMedal.Sliver);
                    arenaView.SetTotalBattle(user.getTotalMatch());
                    arenaView.SetWinRate(String.format(java.util.Locale.US,"%.2f", winRate) + "%");
                }

                @Override
                public void onError(NetworkError error) {
                    Toast.makeText((AppCompatActivity) arenaView, "Can not get player information!", Toast.LENGTH_LONG).show();
                    Log.e(ARENA_TAG, "Can not get player information!");
                    Log.e(ARENA_TAG, error.getCause().toString());
                }
            });
        }
    }

}
