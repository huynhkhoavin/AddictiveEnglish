package khoavin.sillylearningenglish.NetworkService.Implementation;

public class ArenaService implements IArenaService {

    //the arena service tag
    private static final String ARENA_SERVICE_TAG = "Arena_service:";

    //Create battle request
    @Override
    public void CreateBattle(String user_id, String enemy_id, final IServerResponse<BattleInformation> serverResponse) {

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
                            serverResponse.onError(new NetworkError(e));
                            Log.e(ARENA_SERVICE_TAG, "Can not create battle:");
                            Log.e(ARENA_SERVICE_TAG, e.toString());
                        }

                        @Override
                        public void onNext(BattleInformation battleInformation) {
                            serverResponse.onSuccess(battleInformation);
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
