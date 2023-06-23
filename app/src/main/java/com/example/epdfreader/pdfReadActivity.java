package com.example.epdfreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class pdfReadActivity extends AppCompatActivity {
  WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_read);

        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().getJavaScriptEnabled();
        // now taking the file name and url from adapter
        String filename =  getIntent().getStringExtra("filename");
        String fileurl = getIntent().getStringExtra("fileurl");
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("pdf opening.....!!!");

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });
          String url ="";
          try {
              url = URLEncoder.encode(fileurl, "UTF-8");
          } catch (Exception e) {

          }
          webView.loadUrl("https://docs.google.com/viewer?embedded=true&url=" +  url);


    }
}