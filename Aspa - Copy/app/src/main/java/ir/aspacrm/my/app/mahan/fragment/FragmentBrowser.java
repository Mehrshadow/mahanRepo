package ir.aspacrm.my.app.mahan.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.ActivityChargeOnline;
import ir.aspacrm.my.app.mahan.ActivityPayments;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.classes.Logger;


public class FragmentBrowser extends Fragment {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;
    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;

    public static FragmentBrowser newInstance(String url, String bankName) {

        Bundle args = new Bundle();
        args.putString("PAY_URL", url);
        args.putString("BankName", bankName);
        FragmentBrowser fragment = new FragmentBrowser();
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentBrowser newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("PAY_URL", url);
        FragmentBrowser fragment = new FragmentBrowser();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (ActivityPayments.isParent)
            ActivityPayments.isReturnedFromBrowser = true;
        else
            ActivityChargeOnline.isReturnedFromBrowser = true;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        Bundle bundle = getArguments();
        String url = bundle.getString("PAY_URL");
        String bankName = bundle.getString("BankName");
        TextView bankTitle = (TextView) view.findViewById(R.id.txtPageTitle);
        bankTitle.setText(String.format(getActivity().getString(R.string.bank_portal), bankName));
        Logger.d("FragmentBrowser : url is " + url);
        webView = (WebView) view.findViewById(R.id.webView);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setWebViewClient(new MyWebViewClient());

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    for (int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
                        fm.popBackStack();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //dlg.showDialogLoadingEnableCancelable(G.currentActivity);
            layLoading.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //dlg.closeDialogLoading();
            layLoading.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }
}
