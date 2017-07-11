package khoavin.sillylearningenglish.SYSTEM.ToolFactory;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 7/10/2017.
 */

public class EnglishDictionaryController {
    Context context;
    DictionaryHelper dictionaryHelper;
    BottomSheetDialog bottomSheetDialog;
    SearchView searchView;
    TextView translateContent;
    BottomSheetBehavior behavior;
    View bottomSheetView;

    /**
     * Initialize the english dictionary controller.
     *
     * @param parentContext The parent context.
     */
    public EnglishDictionaryController(Context parentContext) {
        this.context = parentContext;
        dictionaryHelper = new DictionaryHelper();
        Initialize();
    }

    public void showDictionaryHelper() {
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetView.setVisibility(View.VISIBLE);
        bottomSheetDialog.show();
    }

    public void hideDictionaryHelper() {
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetDialog.dismiss();
    }

    private void Initialize() {

        /**
         * Create bottom sheet view
         */
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetView = View.inflate(context, R.layout.bottom_dictionary_helper, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        behavior = BottomSheetBehavior.from(bottomSheetView.findViewById(R.id.bottom_sheet_dictionary));

        translateContent = (TextView) bottomSheetView.findViewById(R.id.translate_content);
        searchView = (SearchView) bottomSheetView.findViewById(R.id.dic_search_view);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setPeekHeight(0);
                    bottomSheetView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryDictionary(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        queryDictionary("a");
    }

    /**
     * Query dictionary.
     *
     * @param query The word.
     */
    private void queryDictionary(String query) {
        QueryWordWithDictionaryHelper async = new QueryWordWithDictionaryHelper();
        async.execute(query);
    }

    /**
     * The async task for word search.
     */
    public class QueryWordWithDictionaryHelper extends AsyncTask<String, String, String> {
        String dicResult = "";

        @Override
        protected String doInBackground(String... params) {
            dicResult = dictionaryHelper.getHtmlContentFromUrl(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            org.jsoup.nodes.Document document = Jsoup.parse(dicResult);
            Element e = document.getElementById("partofspeech_0");
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
            if (e != null) {
                translateContent.setText(Html.fromHtml(e.html()));
            } else {
                translateContent.setText("Not found");
            }

        }
    }

}
