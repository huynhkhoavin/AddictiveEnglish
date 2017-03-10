package khoavin.sillylearningenglish.Function.Friend.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import khoavin.sillylearningenglish.R;

/**
 * Created by Khoavin on 3/12/2017.
 */

public class ChatDialog extends Dialog {

    public ChatDialog(@NonNull Context context) {
        super(context);
    }

    public ChatDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ChatDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_dialog);
    }
}
