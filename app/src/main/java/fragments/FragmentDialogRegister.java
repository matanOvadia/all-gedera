package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;

import studioidan.com.parsetest.App;
import studioidan.com.parsetest.R;

/**
 * Created by PopApp_laptop on 20/05/2015.
 */
public class FragmentDialogRegister extends DialogFragment implements View.OnClickListener {

    EditText etName, etPhone, etBornDate, etEmail;
    AutoCompleteTextView etCity;
    Button btnSend;
    CheckBox cbTakanon;
    TextView tvTakanon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register_to_find_job, container, false);

        etName = (EditText) v.findViewById(R.id.et_fragment_send_msg_from);
        etPhone = (EditText) v.findViewById(R.id.et_fragment_send_msg_content);
        etBornDate = (EditText) v.findViewById(R.id.et_fragment_register_born_date);
        etEmail = (EditText) v.findViewById(R.id.et_fragment_register_email);
        etCity = (AutoCompleteTextView) v.findViewById(R.id.et_auto_fragment_register_city);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, App.cities);
        etCity.setAdapter(adapter);
        btnSend = (Button) v.findViewById(R.id.btn_fragment_register_send);
        btnSend.setOnClickListener(this);

        tvTakanon = (TextView) v.findViewById(R.id.tv_takanon);
        cbTakanon = (CheckBox) v.findViewById(R.id.cbTakanon);
        tvTakanon.setText("קראתי ואני מאשר את התקנון");
        SpannableString ss = new SpannableString(tvTakanon.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                showTakanon();
            }
        };
        ss.setSpan(clickableSpan, 19, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTakanon.setText(ss);
        tvTakanon.setMovementMethod(LinkMovementMethod.getInstance());
        return v;
    }

    private void showTakanon() {
        try {
            AssetManager am = getActivity().getAssets();
            InputStream inputStream = am.open("takanon.txt");
            //InputStream inputStream = am.open("takanon.docx");
            System.out.println(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            int i;
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
            String takanonStr = byteArrayOutputStream.toString();

            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.takanon);
            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.tvTakanonText);
            text.setText(takanonStr);
            Button btnOk = (Button) dialog.findViewById(R.id.btnTakanonOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "אירעה שגיאה בהצגת התקנון"  + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String born_date = etBornDate.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        if (name.length() == 0 || email.length() == 0 || phone.length() == 0 || born_date.length() == 0 || city.length() == 0) {
            Toast.makeText(getActivity(), "יש למלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.contains("@")) {
            Toast.makeText(getActivity(), "יש להזין כתובת מייל תקינה", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.length() < 10 || !phone.startsWith("0")) {
            Toast.makeText(getActivity(), "יש להזין מספר טלפון תקין ובעל 10 ספרות", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cbTakanon.isChecked()) {
            Toast.makeText(getActivity(), "יש לאשר את התקנון", Toast.LENGTH_SHORT).show();
            return;
        }

        ParseObject obj = new ParseObject("Lid");
        obj.put("name", name);
        obj.put("email", email);
        obj.put("phone", phone);
        obj.put("city", city);
        obj.put("born_date", born_date);

        obj.saveEventually();

        Toast.makeText(getActivity(), "פרטיך נשלחו בהצלחה", Toast.LENGTH_SHORT).show();
        this.dismiss();
    }
}
