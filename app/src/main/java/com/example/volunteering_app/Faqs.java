package com.example.volunteering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.volunteering_app.R.id;

public class Faqs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

//Converting text into HTML text
        TextView faqstextView = findViewById(id.faqsTextView);
        String faqsContent = getString(R.string.app_faq);
        faqstextView.setText(HtmlCompat.fromHtml(faqsContent, HtmlCompat.FROM_HTML_MODE_COMPACT));
        faqstextView.setMovementMethod(LinkMovementMethod.getInstance());

//Back button to go back to previous activity
        ImageButton faqsbackButton = findViewById(id.faqsBackButton);
        faqsbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the current activity and return to the previous activity
                finish();
            }
        });
    }
}