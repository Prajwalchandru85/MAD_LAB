package com.example.link_click;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText urlInput;
    private Button visitButton;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        urlInput = findViewById(R.id.urlInput);
        visitButton = findViewById(R.id.visitButton);
        webView = findViewById(R.id.webView);

        // Configure WebView
        webView.setWebViewClient(new WebViewClient()); // Ensure links open in WebView
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript (optional)

        // Set click listener for the button
        visitButton.setOnClickListener(v -> {
            String url = urlInput.getText().toString().trim();
            if (!url.isEmpty()) {
                // Ensure the URL starts with "http://" or "https://"
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }
                webView.loadUrl(url); // Load the URL in the WebView
            }
        });
    }
}