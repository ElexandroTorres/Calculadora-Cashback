package com.elexandro.calculadoracachback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    EditText etValue;
    Switch swtDiscount;
    EditText etDiscount;
    SeekBar skbPercent;
    TextView tvCachBack;
    TextView tvTotal;

    TextView tvLabelPercent;

    private double value = 0;
    private double discount = 0;
    private double percent = 0;
    private double total;
    private double cachBack = 0.0;

    private boolean hasDiscount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        etValue = findViewById(R.id.et_value);
        swtDiscount = findViewById(R.id.swt_discount);
        etDiscount = findViewById(R.id.et_discount);
        skbPercent = findViewById(R.id.skb_percent);
        tvCachBack = findViewById(R.id.tv_cach_back);
        tvTotal = findViewById(R.id.tv_total);
        tvLabelPercent = findViewById(R.id.tv_percent);

        etValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                value = s.toString().isEmpty() == true ? 0 : Double.parseDouble(s.toString());
                calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        skbPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percent = progress;
                calculateTotal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        swtDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasDiscount = isChecked;
                if(isChecked) {
                    etDiscount.setEnabled(isChecked);
                    etDiscount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            discount = s.toString().isEmpty() == true ? 0 : Double.parseDouble(s.toString());
                            calculateTotal();
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                }
                calculateTotal();
            }
        });


    }

    private void calculateTotal() {
        calculateCachBack();
        total = value - cachBack;

        if(hasDiscount) total -= discount;

        tvTotal.setText(String.format("R$ %.2f", total));
    }

    private void calculateCachBack() {
        value = etValue.getText().toString().isEmpty() == true ? 0 : Double.parseDouble(etValue.getText().toString());
        cachBack = (value * percent) / 100;
        tvCachBack.setText(String.format("R$ %.2f", cachBack));
        tvLabelPercent.setText(String.format("%.0f%%", percent));
    }
}