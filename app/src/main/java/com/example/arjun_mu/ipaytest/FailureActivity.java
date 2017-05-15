package com.example.arjun_mu.ipaytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.verse.mpayment.PaymentService;
import in.verse.mpayment.request.Item;
import in.verse.mpayment.response.FailedPaymentResponse;

public class FailureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FailedPaymentResponse failedPaymentResponse = (FailedPaymentResponse) getIntent().getExtras().getSerializable(PaymentService.FAILED_PAYMENT_RESPONSE);
        String failureReason = getFailureString(failedPaymentResponse);
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



    public String getFailureString(FailedPaymentResponse fpr)
    {
        Item item = fpr.getRequestedItem();
        return "Sorry !! your payment request for "+item.getItemDetail().getItemName()+" was not successful.nnReason: " + getReason(fpr) + "nnPlease try again after sometime.";
    }

    private String getReason(FailedPaymentResponse fpr)
    {
        String failureCode = fpr.getFailureCode();
        String errorCode = fpr.getErrorCode();
        if (failureCode != null && failureCode.trim().length() > 0)
        {
            if (failureCode.equalsIgnoreCase("LB"))
            {
                return "Insufficient funds in account.";
            }
            else if (failureCode.equalsIgnoreCase("IS"))
            {
                return "You selected the wrong operator.";
            }
            else if (failureCode.equalsIgnoreCase("PF"))
            {
                return "OTP attempts exceeded.";
            }
            else if (failureCode.equalsIgnoreCase("ST"))
            {
                return "Your session is timed out.";
            }
            else if (failureCode.equalsIgnoreCase("NE"))
            {
                return "Connectivity Error";
            }
            else
            {
                return "Payment Service Failure";
            }
        }
        else if (errorCode != null && errorCode.trim().length() > 0)
        {
            return "Unknown";
        }
        return "";
    }

}
