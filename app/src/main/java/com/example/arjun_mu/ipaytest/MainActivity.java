package com.example.arjun_mu.ipaytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.math.BigDecimal;

import in.verse.ipayy.crypto.CryptoException;
import in.verse.mpayment.PaymentService;
import in.verse.mpayment.enums.Currency;
import in.verse.mpayment.enums.DiscountType;
import in.verse.mpayment.request.Item;
import in.verse.mpayment.request.ItemDetail;
import in.verse.mpayment.response.FailedPaymentResponse;
import in.verse.mpayment.response.SuccessPaymentResponse;

public class MainActivity extends AppCompatActivity {


    String merchantKey="xaBofKEOc_StwPZMJOccsQ";
    String applicationKey="7C3APQpvdzNCpEzKUz_C2w";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    protected void onResume() {
        super.onResume();
        Button payButton = (Button) findViewById(R.id.button1);
        payButton.setText(getString(R.string.btn_pay_now));
        payButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ItemDetail itemDetail = new ItemDetail("ItemTest", "TestGame",
                        new BigDecimal("1.0"), BigDecimal.ZERO,
                        DiscountType.AMOUNT);
                Item item = new Item(itemDetail, Currency.INR);
                Intent paymentIntent = null;
                try {
                    final String requestId = RequestGenerator.getNewRequestId(MainActivity.this);

                    paymentIntent = PaymentService.getInstance()
                            .getPaymentIntent(
                                    requestId,
                                    merchantKey, applicationKey,
                                    "9003249761", "airtel",
                                    MainActivity.this, null, item);
                } catch (CryptoException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                startActivityForResult(paymentIntent, 1);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 1) {
                switch (resultCode) {
                    case PaymentService.RESULT_OK: {
                        Intent intent = new Intent(this, SuccessActivity.class);
                        SuccessPaymentResponse successPaymentResponse = (SuccessPaymentResponse) data
                                .getExtras().getSerializable(
                                        PaymentService.SUCCESS_PAYMENT_RESPONSE);
                        Log.d("Success Response",
                                successPaymentResponse.toString());
                        intent.putExtra(PaymentService.SUCCESS_PAYMENT_RESPONSE,
                                successPaymentResponse);
                        startActivity(intent);
                        break;
                    }
                    case PaymentService.RESULT_FAILURE: {
                        Intent intent = new Intent(this, FailureActivity.class);
                        FailedPaymentResponse failedPaymentResponse = (FailedPaymentResponse) data
                                .getExtras().getSerializable(
                                        PaymentService.FAILED_PAYMENT_RESPONSE);
                        Log.d("Failed Payment Response",
                                failedPaymentResponse.toString());
                        intent.putExtra(PaymentService.FAILED_PAYMENT_RESPONSE,
                                failedPaymentResponse);
                        startActivity(intent);
                        break;
                    }
                    case PaymentService.RESULT_CANCELED: {
                        if (data != null) {
                            Intent intent = new Intent(this, CanceledActivity.class);
                            Item item = (Item) data.getExtras().getSerializable(
                                    PaymentService.ITEM);
                            Log.d("Result Cancelled", "Cancelled");
                            intent.putExtra("RESPONSE",
                                    getString(R.string.cancel_purchase));
                            intent.putExtra(PaymentService.ITEM, item);
                            startActivity(intent);
                            break;
                        }
                    }
                    default:
                        break;
                }
            }
        }



}
