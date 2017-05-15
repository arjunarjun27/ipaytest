package com.example.arjun_mu.ipaytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.verse.mpayment.PaymentService;
import in.verse.mpayment.response.SuccessPaymentResponse;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SuccessPaymentResponse successPaymentResponse = (SuccessPaymentResponse) getIntent().getExtras().getSerializable(PaymentService.SUCCESS_PAYMENT_RESPONSE);
        TextView successpaymentMessage = new TextView(this);
        successpaymentMessage.setText("Congratulations!!! Your payment was successful.nTransaction ID:"+successPaymentResponse.getTransactionId()+"nAmount:"+successPaymentResponse.getAmountCharged());
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
        linearLayout.addView(successpaymentMessage);
        linearLayout.addView(finishButton);

        setContentView(linearLayout);
    }
}
