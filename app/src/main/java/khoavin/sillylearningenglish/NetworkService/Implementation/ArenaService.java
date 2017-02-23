package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleInformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ArenaService implements IArenaService {

    //the arena service tag
    private static final String ARENA_SERVICE_TAG = "Arena_service:";

    //Create battle request
    @Override
    public void CreateBattle(String user_id, String enemy_id, final Func1<BattleInformation, Void> receiver) {

        IApiServices APIService = ApiUntils.getAPIService();
        if(APIService != null)
        {
            APIService.createBattle(user_id, enemy_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BattleInformation>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            receiver.call(null);
                            Log.e(ARENA_SERVICE_TAG, "Can not create battle:");
                            Log.e(ARENA_SERVICE_TAG, e.toString());
                        }

                        @Override
                        public void onNext(BattleInformation battleInformation) {
                            receiver.call(battleInformation);
                            Log.i(ARENA_SERVICE_TAG, "Create battle successfully!");
                            Log.i(ARENA_SERVICE_TAG, "BattleID: " + battleInformation.getBattleId());
                        }
                    });
        }
    }

    //Get battle result
    @Override
    public void GetBattleResult(String battle_id, String user_id) {

    }
}
