package com.example.romankolinconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity
{
    EditText editTextNumberDecimal1from;
    TextView textView2to;
    Spinner spinner1from;
    Spinner spinner2to;
    RadioButton radioButton1length;
    RadioButton radioButton2weight;
    RadioButton radioButton3temperature;
    RadioButton radioButton4currency;
    CheckBox checkBox1sign;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumberDecimal1from = findViewById(R.id.editTextNumberDecimal1from);
        textView2to = findViewById(R.id.textView1to);
        spinner1from = findViewById(R.id.spinner1from);
        spinner2to = findViewById(R.id.spinner2to);
        radioButton1length = findViewById(R.id.radioButton1length);
        radioButton2weight = findViewById(R.id.radioButton2weight);
        radioButton3temperature = findViewById(R.id.radioButton3temperature);
        radioButton4currency = findViewById(R.id.radioButton4currency);
        checkBox1sign = findViewById(R.id.checkBox1sign);

        radioButton4currency.callOnClick();
    }

    public void onclickcurrency(View c)
    {
        checkBox1sign.setVisibility(View.INVISIBLE);
        checkBox1sign.setChecked(false);

        if (radioButton4currency.isChecked())
        {
            radioButton1length.setChecked(false);
            radioButton2weight.setChecked(false);
            radioButton3temperature.setChecked(false);
        }

        ArrayAdapter<String> currency = new <String> ArrayAdapter(this, android.R.layout.simple_list_item_1);
        currency.add("₽"); //0
        currency.add("$"); //1
        currency.add("€"); //2
        spinner1from.setAdapter(currency);
        spinner2to.setAdapter(currency);
    }
    public void onclicklength(View l)
    {
        checkBox1sign.setVisibility(View.INVISIBLE);
        checkBox1sign.setChecked(false);

        if (radioButton1length.isChecked())
        {
            radioButton2weight.setChecked(false);
            radioButton3temperature.setChecked(false);
            radioButton4currency.setChecked(false);
        }

        ArrayAdapter <String> length = new <String> ArrayAdapter(this, android.R.layout.simple_list_item_1);
        length.add("mm"); //0
        length.add("cm"); //1
        length.add("dm"); //2
        length.add("m"); //3
        length.add("km"); //4
        length.add("in"); //5
        length.add("ft"); //6
        length.add("mi"); //7
        length.add("pt"); //8
        length.add("ly"); //9
        spinner1from.setAdapter(length);
        spinner2to.setAdapter(length);
    }
    public void onclickweight(View w)
    {
        checkBox1sign.setVisibility(View.INVISIBLE);
        checkBox1sign.setChecked(false);

        if (radioButton2weight.isChecked())
        {
            radioButton1length.setChecked(false);
            radioButton3temperature.setChecked(false);
            radioButton4currency.setChecked(false);
        }

        ArrayAdapter <String> weight = new <String> ArrayAdapter(this, android.R.layout.simple_list_item_1);
        weight.add("g"); //0
        weight.add("kg"); //1
        weight.add("z"); //2
        weight.add("t"); //3
        weight.add("lb"); //4
        weight.add("ct"); //5
        spinner1from.setAdapter(weight);
        spinner2to.setAdapter(weight);
    }
    public void onclicktemperature(View t)
    {
        checkBox1sign.setVisibility(View.VISIBLE);

        if (radioButton3temperature.isChecked())
        {
            radioButton1length.setChecked(false);
            radioButton2weight.setChecked(false);
            radioButton4currency.setChecked(false);
        }

        ArrayAdapter <String> temperature = new <String> ArrayAdapter(this, android.R.layout.simple_list_item_1);
        temperature.add("℃"); //0
        temperature.add("℉"); //1
        temperature.add("K"); //2
        spinner1from.setAdapter(temperature);
        spinner2to.setAdapter(temperature);
    }
    public void onclickchangesign(View c)
    {
        if (editTextNumberDecimal1from.getText().toString().equals(""))
        {
            Toast empsign = Toast.makeText(getApplicationContext(), "Print a \"from\" number", Toast.LENGTH_SHORT);
            empsign.show();
            checkBox1sign.setChecked(false);
        }
        else
        {
            double sign = Double.parseDouble(editTextNumberDecimal1from.getText().toString());
            editTextNumberDecimal1from.setText(String.valueOf(sign - (sign * 2)));
            char[] zero = editTextNumberDecimal1from.getText().toString().toCharArray();
            if (zero[zero.length - 1] == '0' && zero[zero.length - 2] == '.')
                editTextNumberDecimal1from.setText(editTextNumberDecimal1from.getText().toString().replace(".0", ""));
        }
    }

    @SuppressLint("DefaultLocale")
    public void onclickconvert(View convert)
    {
        if (editTextNumberDecimal1from.getText().toString().equals(""))
        {
            Toast empnum = Toast.makeText(getApplicationContext(), "Print a \"from\" number", Toast.LENGTH_SHORT);
            empnum.show();
        }
        else
        {
            double from = Double.parseDouble(editTextNumberDecimal1from.getText().toString());
            AtomicReference<Double> res = new AtomicReference<>((double) 0);
            int spfrom = spinner1from.getSelectedItemPosition();
            int spto = spinner2to.getSelectedItemPosition();

            if (radioButton1length.isChecked())
            {
                if (spfrom >= 0 && spfrom <= 3)
                {
                    if (spto >= 0 && spto <= 3)
                        res.set(from * Math.pow(10, spfrom - spto));
                    else if (spto == 4)
                        res.set(from / (1000000 / Math.pow(10, spfrom)));
                    else if (spto == 5)
                        res.set(from / (25.4 / Math.pow(10, spfrom)));
                    else if (spto == 6)
                        res.set(from / (304.8 / Math.pow(10, spfrom)));
                    else if (spto == 7)
                        res.set(from / (1609344 / Math.pow(10, spfrom)));
                    else if (spto == 8)
                        res.set(from / (0.3528 / Math.pow(10, spfrom)));
                    else if (spto == 9)
                        res.set(from / ((9.46 * Math.pow(10, 18)) / Math.pow(10, spfrom)));
                }
                if (spfrom == 4)
                {
                    if (spto >= 0 && spto <= 3)
                        res.set(from * 100 * Math.pow(10, spfrom - spto));
                    if (spto == 4)
                        res.set(from);
                    if (spto == 5)
                        res.set(from / 0.0000254);
                    if (spto == 6)
                        res.set(from / 0.0003048);
                    if (spto == 7)
                        res.set(from / 1.609344);
                    if (spto == 8)
                        res.set(from / 0.0000003528);
                    if (spto == 9)
                        res.set(from / (9.46 * Math.pow(10, 12)));
                }
                if (spfrom == 5)
                {
                    if (spto >= 0 && spto <= 3)
                        res.set(from * 25.4 / Math.pow(10, spto));
                    if (spto == 4)
                        res.set(from * 0.0000254);
                    if (spto == 5)
                        res.set(from);
                    if (spto == 6)
                        res.set(from * 0.083);
                    if (spto == 7)
                        res.set(from * 0.000016);
                    if (spto == 8)
                        res.set(from * 72);
                    if (spto == 9)
                        res.set(from * (2.68 * Math.pow(10, -18)));
                }
                if (spfrom == 6)
                {
                    if (spto >= 0 && spto <= 3)
                        res.set(from * 304.8 / Math.pow(10, spto));
                    if (spto == 4)
                        res.set(from * 0.0003048);
                    if (spto == 5)
                        res.set(from * 12);
                    if (spto == 6)
                        res.set(from);
                    if (spto == 7)
                        res.set(from * 0.00019);
                    if (spto == 8)
                        res.set(from * 864);
                    if (spto == 9)
                        res.set(from * (3.22 * Math.pow(10, -17)));
                }
                if (spfrom == 7)
                {
                    if (spto >= 0 && spto <= 3)
                        res.set(from * 1609344 / Math.pow(10, spto));
                    if (spto == 4)
                        res.set(from * 1.609344);
                    if (spto == 5)
                        res.set(from * 63360);
                    if (spto == 6)
                        res.set(from * 5280);
                    if (spto == 7)
                        res.set(from);
                    if (spto == 8)
                        res.set(from * (2.1 * Math.pow(10, 7)));
                    if (spto == 9)
                        res.set(from * (1.7 * Math.pow(10, -13)));
                }
                if (spfrom == 8)
                {
                    if (spto >= 0 && spto <= 3)
                        res.set(from * 0.3528 / Math.pow(10, spto));
                    if (spto == 4)
                        res.set(from * (3.528 * Math.pow(10, -7)));
                    if (spto == 5)
                        res.set(from * 0.0138);
                    if (spto == 6)
                        res.set(from * 0.001157);
                    if (spto == 7)
                        res.set(from * (4.75 * Math.pow(10, -8)));
                    if (spto == 8)
                        res.set(from);
                    if (spto == 9)
                        res.set(from * (3.73 * Math.pow(10, -20)));
                }
                if (spfrom == 9)
                {
                    if (spto >= 0 && spto <= 3)
                        res.set(from * 9.46 * Math.pow(10, 18) / Math.pow(10, spto));
                    if (spto == 4)
                        res.set(from * (9.46 * Math.pow(10, 12)));
                    if (spto == 5)
                        res.set(from * (3.72 * Math.pow(10, 17)));
                    if (spto == 6)
                        res.set(from * (3.1 * Math.pow(10, 16)));
                    if (spto == 7)
                        res.set(from * (5.88 * Math.pow(10, 12)));
                    if (spto == 8)
                        res.set(from * (2.68 * Math.pow(10, 19)));
                    if (spto == 9)
                        res.set(from);
                }
            }
            if (radioButton2weight.isChecked())
            {
                if (spto == 2)
                {
                    if (spfrom == 0)
                        res.set(from / 100000);
                    if (spfrom == 1)
                        res.set(from / 100);
                    if (spfrom == 2)
                        res.set(from);
                    if (spfrom == 3)
                        res.set(from * 10);
                    if (spfrom == 4)
                        res.set(from * 0.0045359);
                    if (spfrom == 5)
                        res.set(from * 0.000002);
                }
                else
                {
                    if (spfrom >= 0 && spfrom <= 3)
                    {
                        if (spfrom == 2)
                        {
                            if (spto == 0)
                                res.set(from * 100000);
                            if (spto == 1)
                                res.set(from * 100);
                            if (spto == 3)
                                res.set(from / 10);
                            if (spto == 4)
                                res.set(from / 0.0045359);
                            if (spto == 5)
                                res.set(from / 0.000002);
                        }
                        else if (spto >= 0 && spto <= 3)
                        {
                            if (spto == 3)
                            {
                                if (spfrom == 0)
                                    res.set(from * 0.000001);
                                else if (spfrom == 3)
                                    res.set(from);
                                else
                                    res.set(from / Math.pow(1000, spfrom));
                            }
                            else if (spfrom == 3)
                            {
                                if (spto == 1)
                                    res.set(from * 1000);
                                else
                                    res.set(from * 1000 * Math.pow(10, spfrom - spto));
                            }
                            else
                                res.set(from * Math.pow(1000, spfrom - spto));
                        }
                        else if (spto == 4)
                        {
                            if (spfrom == 3)
                                res.set(from / (453.59 / Math.pow(1000, 2)));
                            else
                                res.set(from / (453.59 / Math.pow(1000, spfrom)));
                        }
                        else if (spto == 5)
                        {
                            if (spfrom == 3)
                                res.set(from / (0.2 / Math.pow(1000, 2)));
                            else
                                res.set(from / (0.2 / Math.pow(1000, spfrom)));
                        }
                    }
                    if (spfrom == 4)
                    {
                        if (spto >=0 && spto <= 3)
                        {
                            if (spto == 3)
                                res.set(from * (453.59 / Math.pow(1000, 2)));
                            else
                                res.set(from * (453.59 / Math.pow(1000, spto)));
                        }
                        if (spto == 4)
                            res.set(from);
                        if (spto == 5)
                            res.set(from * 2267.96);
                    }
                    if (spfrom == 5)
                    {
                        if (spto >=0 && spto <= 3)
                        {
                            if (spto == 3)
                                res.set(from * (0.2 / Math.pow(1000, 2)));
                            else
                                res.set(from * (0.2 / Math.pow(1000, spto)));
                        }
                        if (spto == 4)
                            res.set(from * 0.00044);
                        if (spto == 5)
                            res.set(from);
                    }
                }
            }
            if (radioButton3temperature.isChecked())
            {
                if (spfrom == 0)
                {
                    if (spto == 0)
                        res.set(from);
                    if (spto == 1)
                        res.set(from * 1.8 + 32);
                    if (spto == 2)
                        res.set(from + 273.15);
                }
                if (spfrom == 1)
                {
                    if (spto == 0)
                        res.set((from - 32) / 1.8);
                    if (spto == 1)
                        res.set(from);
                    if (spto == 2)
                        res.set((from + 459.67) / 1.8);
                }
                if (spfrom == 2)
                {
                    if (spto == 0)
                        res.set(from - 273.15);
                    if (spto == 1)
                        res.set(1.8 * (from - 273.15) + 32);
                    if (spto == 2)
                        res.set(from);
                }
            }
            if (radioButton4currency.isChecked())
            {
                Thread t = new Thread(() ->
                {
                    try
                    {
                        URL u = new URL("http://api.currencylayer.com/live?access_key=2a220de23174b672c7b8f255172f21ce");
                        HttpURLConnection h = (HttpURLConnection) u.openConnection();
                        InputStream is = h.getInputStream();
                        byte [] b = new byte[1024];
                        String currs = "";
                        while (true)
                        {
                            int l = is.read(b, 0, b.length);
                            if (l < 0) break;
                            currs += new String(b, 0, l);
                        }
                        h.disconnect();
                        Log.d("json", currs);

                        JSONObject currsi = new JSONObject(currs);
                        JSONObject quot = currsi.getJSONObject("quotes");
                        if (spfrom == 0)
                        {
                            if (spto == 0)
                                res.set(from);
                            if (spto == 1)
                            {
                                double currrub = quot.getDouble("USDRUB");
                                res.set(from / currrub);
                            }
                            if (spto == 2)
                            {
                                double curreur = quot.getDouble("USDEUR");
                                double currrub = quot.getDouble("USDRUB");
                                res.set(from / currrub * curreur);
                            }
                        }
                        if (spfrom == 1)
                        {
                            if (spto == 0)
                            {
                                double currrub = quot.getDouble("USDRUB");
                                res.set(from * currrub);
                            }
                            if (spto == 1)
                                res.set(from);
                            if (spto == 2)
                            {
                                double curreur = quot.getDouble("USDEUR");
                                res.set(from * curreur);
                            }
                        }
                        if (spfrom == 2)
                        {
                            if (spto == 0)
                            {
                                double curreur = quot.getDouble("USDEUR");
                                double currrub = quot.getDouble("USDRUB");
                                res.set(from * currrub / curreur);
                            }
                            if (spto == 1)
                            {
                                double curreur = quot.getDouble("USDEUR");
                                res.set(from / curreur);
                            }
                            if (spto == 2)
                                res.set(from);
                        }
                        DecimalFormat df = new DecimalFormat("0.##");
                        textView2to.setText(String.valueOf(df.format(res.get())));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                });
                t.start();
            }
            if (spto == 9)
            {
                try
                {
                    StringBuilder e = new StringBuilder(String.valueOf(res));
                    int inde = e.indexOf("E");
                    int inddot = e.indexOf(".");
                    textView2to.setText(e.delete(inddot + 2, inde - 1));
                }
                catch (Exception e)
                {
                    textView2to.setText(String.valueOf(res.get()));
                    char[] zero = textView2to.getText().toString().toCharArray();
                    if (zero[zero.length - 1] == '0' && zero[zero.length - 2] == '.')
                    {
                        textView2to.setText(textView2to.getText().toString().replace(".0", ""));
                    }
                }
            }
            else if (spfrom == 8 && spto == 7 && editTextNumberDecimal1from.getText().toString().equals("1"))
            {
                DecimalFormat df = new DecimalFormat("0.########");
                textView2to.setText(String.valueOf(df.format(res.get())));
            }
            else
            {
                DecimalFormat df = new DecimalFormat("0.#######");
                textView2to.setText(String.valueOf(df.format(res.get())));
            }
        }
    }

    public void onclickclear(View clear)
    {
        editTextNumberDecimal1from.setText("");
        textView2to.setText("");
        checkBox1sign.setChecked(false);
    }
}