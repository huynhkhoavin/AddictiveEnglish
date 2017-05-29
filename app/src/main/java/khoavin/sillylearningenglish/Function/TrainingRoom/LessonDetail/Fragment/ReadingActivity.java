package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.File;

import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BASE_URL;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.SERVER_URL;

/**
 * Created by KhoaVin on 28/05/2017.
 */

public class ReadingActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fragment_reading);
        WebView webView = (WebView)findViewById(R.id.myWebView);
        Lesson ls = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
        String url = SERVER_URL + ls.getLsFileUrl();
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
        super.onCreate(savedInstanceState);
    }
}
