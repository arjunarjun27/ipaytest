package com.example.arjun_mu.ipaytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CanceledActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canceled);

        String failureReason = getIntent().getStringExtra("RESPONSE");
        TextView paymentFailureMessage = new TextView(this);
        paymentFailureMessage.setText(failureReason);

        Button finishButton = new Button(this);
        finishButton.setText("Finish");
        finishButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(paymentFailureMessage);
        linearLayout.addView(finishButton);
        setContentView(linearLayout);
    }
}
