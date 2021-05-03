package sg.edu.rp.c346.id20018318.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText amount;
    EditText numPax;
    ToggleButton svs;
    ToggleButton gst;
    EditText discount;
    RadioGroup rgPayment;
    RadioButton cash;
    Button split;
    Button reset;
    TextView totalBill;
    TextView eachPays;
    TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.editTextAmount);
        numPax = findViewById(R.id.editTextPax);
        svs = findViewById(R.id.toggleButtonSvs);
        gst = findViewById(R.id.toggleButtonGst);
        discount = findViewById(R.id.editTextDiscount);
        rgPayment = findViewById(R.id.radioGroupPayment);
        cash = findViewById(R.id.radioButtonCash);
        split = findViewById(R.id.buttonSplit);
        reset = findViewById(R.id.buttonReset);
        totalBill = findViewById(R.id.textViewBill);
        eachPays = findViewById(R.id.textViewEach);
        errorMsg = findViewById(R.id.textViewError);

        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().trim().length() > 0 && numPax.getText().toString().trim().length() > 0) {
                    double newAmt = 0.0;
                    if (!svs.isChecked() && !gst.isChecked()) {
                        newAmt = Double.parseDouble(amount.getText().toString());
                    } else if (svs.isChecked() && !gst.isChecked()) {
                        newAmt = Double.parseDouble(amount.getText().toString()) * 1.1;
                    } else if (!svs.isChecked() && gst.isChecked()) {
                        newAmt = Double.parseDouble(amount.getText().toString()) * 1.07;
                    } else if (svs.isChecked() && gst.isChecked()) {
                        newAmt = Double.parseDouble(amount.getText().toString()) * 1.17;
                    }

                    if (discount.getText().toString().trim().length() > 0) {
                        newAmt = newAmt * (1 -  (Double.parseDouble(discount.getText().toString()) / 100));
                    }

                    totalBill.setText("Total Bill: $" + String.format("%.2f", newAmt));

                    int newPax = Integer.parseInt(numPax.getText().toString());
                    if (newPax > 0) {
                        int checkedRadioId = rgPayment.getCheckedRadioButtonId();
                        if (checkedRadioId == R.id.radioButtonCash) {
                            eachPays.setText("Each Pays: $" + String.format("%.2f", newAmt / newPax) + " via Cash");
                        } else {
                            eachPays.setText("Each Pays: $" + String.format("%.2f", newAmt / newPax) + " Paynow to 86321944");
                        }
                    }

                } else {
                    errorMsg.setText("Please fill in all blanks.");
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                numPax.setText("");
                svs.setChecked(false);
                gst.setChecked(false);
                discount.setText("");
                cash.setChecked(true);
            }
        });
    }
}