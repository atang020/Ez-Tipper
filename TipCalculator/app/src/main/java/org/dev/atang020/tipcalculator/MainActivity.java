package org.dev.atang020.tipcalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends ActionBarActivity {

    private BigDecimal amountValue;
    private int tipValue;
    private AlertDialog.Builder dialogBuilder;
    EditText tip;
    EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        amount = (EditText) findViewById(R.id.amount_editText);
        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
            }
        });

        tip = (EditText) findViewById(R.id.tip_editText);
        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip.setText("");
            }
        });
        Button calcButton = (Button) findViewById(R.id.button);

        final TextView tipText = (TextView) findViewById(R.id.tip_amount_TextView);
        final TextView total = (TextView) findViewById(R.id.total_amount_TextView);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().equals("") || tip.getText().toString().equals("") || amount.getText().toString().equals("Enter amount here") || tip.getText().toString().equals("Enter Tip Percentage")){
                    valueValidation();
                    return;
                }
                amountValue = new BigDecimal(amount.getText().toString());
                tipValue = Integer.parseInt(tip.getText().toString());

                if(tipValue > 100 || tipValue < 1){
                    tipValidation();
                    return;
                }
                amountValue = amountValue.setScale(2, RoundingMode.CEILING);
                double calc = calcTotal(amountValue,tipValue);
                double tipCalc = calcTip(amountValue,tipValue);
                total.setText("$" + Double.toString(calc));
                tipText.setText("$" + Double.toString(tipCalc));
            }
        });

    }

    private double calcTip(BigDecimal amount, int tip){
        double a = amount.doubleValue();
        double t = tip*.01;
        BigDecimal d = new BigDecimal(a*t);
        d = d.setScale(2, RoundingMode.CEILING);
        return d.doubleValue();
    }
    private double calcTotal(BigDecimal amount, int tip){
        double a = amount.doubleValue();
        double t = tip*.01+1;
        BigDecimal d = new BigDecimal(a*t);
        d = d.setScale(2, RoundingMode.CEILING);
        return d.doubleValue();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void tipValidation()
    {
        dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setTitle("Error");
        dialogBuilder.setMessage("Please enter a number between 1 and 100");
        dialogBuilder.setPositiveButton("OK", null);
        AlertDialog dialog = dialogBuilder.show();

        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tip.getText().clear();
            }
        });

    }

    private void valueValidation()
    {
        dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setTitle("Error");
        dialogBuilder.setMessage("Please enter a number for bill amount and tips");
        dialogBuilder.setPositiveButton("OK", null);
        AlertDialog dialog = dialogBuilder.show();

        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tip.getText().clear();
                amount.getText().clear();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
