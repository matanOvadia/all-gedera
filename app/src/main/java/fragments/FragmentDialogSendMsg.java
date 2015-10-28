package fragments;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import entities.Msg;
import studioidan.com.parsetest.App;
import studioidan.com.parsetest.R;

/**
 * Created by PopApp_laptop on 21/05/2015.
 */

public class FragmentDialogSendMsg extends DialogFragment implements View.OnClickListener {
    EditText etFrom, etContent;
    Button btnSend;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dialog_send_msg, container, false);
        etFrom = (EditText) v.findViewById(R.id.et_fragment_send_msg_from);
        etContent = (EditText) v.findViewById(R.id.et_fragment_send_msg_content);
        btnSend = (Button) v.findViewById(R.id.btn_fragment_send_msg_send);
        btnSend.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        String from = etFrom.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        if (from.length() == 0 || content.length() == 0) {
            Toast.makeText(getActivity(), "יש למלא את תוכן השדות", Toast.LENGTH_SHORT).show();
            return;
        }
        Msg m = new Msg();
        m.setFron(from);
        m.setContentt(content);
        m.setGuests(0);
        App.msgs.add(m);
        Intent intent = new Intent("refresh");
        getActivity().sendBroadcast(intent);
        m.saveEventually();
        Toast.makeText(getActivity(), "הודעתך נשלחה בהצלחה", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
