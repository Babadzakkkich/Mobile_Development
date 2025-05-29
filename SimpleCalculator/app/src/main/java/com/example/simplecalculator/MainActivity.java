package com.example.simplecalculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etNum1, etNum2;
    Button btnAdd, btnSub, btnMult, btnDiv;
    TextView tvResult;

    final int MENU_RESET_ID = 1;
    final int MENU_QUIT_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMult = findViewById(R.id.btnMult);
        btnDiv = findViewById(R.id.btnDiv);
        tvResult = findViewById(R.id.tvResult);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String sNum1 = etNum1.getText().toString();
        String sNum2 = etNum2.getText().toString();

        if (TextUtils.isEmpty(sNum1) || TextUtils.isEmpty(sNum2)) {
            Toast.makeText(this, "Введите оба числа", Toast.LENGTH_SHORT).show();
            return;
        }

        float num1 = Float.parseFloat(sNum1);
        float num2 = Float.parseFloat(sNum2);
        float result = 0;
        String oper = "";

        int id = v.getId();

        switch (id) {
            case R.id.btnAdd:
                oper = "+";
                result = num1 + num2;
                break;
            case R.id.btnSub:
                oper = "-";
                result = num1 - num2;
                break;
            case R.id.btnMult:
                oper = "×";
                result = num1 * num2;
                break;
            case R.id.btnDiv:
                if (num2 == 0) {
                    tvResult.setText("Ошибка: деление на ноль");
                    return;
                }
                oper = "÷";
                result = num1 / num2;
                break;
            default:
                return; 
        }

        tvResult.setText(num1 + " " + oper + " " + num2 + " = " + result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_RESET_ID, 0, "Очистить");
        menu.add(0, MENU_QUIT_ID, 0, "Выход");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_RESET_ID:
                etNum1.setText("");
                etNum2.setText("");
                tvResult.setText("");
                return true;
            case MENU_QUIT_ID:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
