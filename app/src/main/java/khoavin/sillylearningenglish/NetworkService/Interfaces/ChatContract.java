package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.content.Context;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseChat;

/**
 * Created by Khoavin on 3/11/2017.
 */

public interface ChatContract {
    interface View {
        void onSendMessageSuccess();

        void onSendMessageFailure(String message);

        void onGetMessagesSuccess(FirebaseChat firebaseChat);

        void onGetMessagesFailure(String message);
    }

    interface Presenter {
        void sendMessage(Context context, FirebaseChat firebaseChat, String receiverFirebaseToken);

        void getMessage(String senderUid, String receiverUid);
    }

    interface Interactor {
        void sendMessageToFirebaseUser(Context context, FirebaseChat firebaseChat, String receiverFirebaseToken);

        void getMessageFromFirebaseUser(String senderUid, String receiverUid);
    }

    interface OnSendMessageListener {
        void onSendMessageSuccess();

        void onSendMessageFailure(String message);
    }

    interface OnGetMessagesListener {
        void onGetMessagesSuccess(FirebaseChat firebaseChat);

        void onGetMessagesFailure(String message);
    }
}
