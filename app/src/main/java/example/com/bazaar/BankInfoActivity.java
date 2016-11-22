package com.shrey.ubs_trade_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BankInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_info);
    }

    protected void openBankInfoSuccess(View view)
    {
        Intent intent = new Intent(this, BankInfoAddedSuccessActivity.class);
        startActivity(intent);
    }
}
