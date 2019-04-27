package com.example.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable; // Для обработки событий EditText 8
 import android.text.TextWatcher; // Слушатель EditText 9
 import android.widget.EditText; // Для ввода счета
import android.widget.SeekBar; // Для изменения процента чаевых 11
 import android.widget.SeekBar.OnSeekBarChangeListener; // Слушатель SeekBar 12
 import android.widget.TextView; // Для вывода текста
import java.text.NumberFormat; // Для форматирования денежных сумм

public class MainActivity extends AppCompatActivity {
    // Форматировщики денежных сумм и процентов 20
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private double billAmount = 0.0; // Сумма счета, введенная пользователем 26
    private double percent = 0.15; // Исходный процент чаевых 27
    private TextView amountTextView; // Для отформатированной суммы счета 28
    private TextView percentTextView; // Для вывода процента чаевых 29
    private TextView tipTextView; // Для вывода вычисленных чаевых 30
    private TextView totalTextView; // Для вычисленной общей суммы
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Получение ссылок на компоненты TextView, с которыми 38
        // MainActivity взаимодействует на программном уровне 39
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0)); // Заполнить 0 44
        totalTextView.setText(currencyFormat.format(0)); // Заполнить 0
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);
        // Назначение слушателя OnSeekBarChangeListener для percentSeekBar 52
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }
    private void calculate() {
        // Форматирование процентов и вывод в percentTextView
        percentTextView.setText(percentFormat.format(percent));
        // Вычисление чаевых и общей суммы 63
        double tip = billAmount * percent;
        double total = billAmount + tip;
        // Вывод чаевых и общей суммы в формате денежной величины 67
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
        }
    // Объект слушателя для событий изменения состояния SeekBar 72
    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        // Обновление процента чаевых и вызов calculate 7
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,  boolean fromUser) {
            percent = progress / 100.0; // Назначение процента чаевых 79
            calculate(); // Вычисление и вывод чаевых и суммы 80
        }
             @Override
             public void onStartTrackingTouch(SeekBar seekBar) { }
             @Override
             public void onStopTrackingTouch(SeekBar seekBar) { }
    };
    // Объект слушателя для событий изменения текста в EditText 90
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        // Вызывается при изменении пользователем величины счета 92
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try { // Получить счет и вывести в формате денежной суммы 97
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e) { // Если s пусто или не число 101
                amountTextView.setText("");
                billAmount = 0.0;
            }
            calculate(); // Обновление полей с чаевыми и общей суммой
             }
             @Override
             public void afterTextChanged(Editable s) { }
             @Override
             public void beforeTextChanged(
                     CharSequence s, int start, int count, int after) { }
    };

}
