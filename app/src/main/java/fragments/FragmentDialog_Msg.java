package fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import studioidan.com.parsetest.AdapterMsg;
import studioidan.com.parsetest.App;
import studioidan.com.parsetest.R;

/**
 * Created by PopApp_laptop on 21/05/2015.
 */
public class FragmentDialog_Msg extends DialogFragment implements View.OnClickListener {
    ListView listView_Msgs;
    Button btnCreate;
    AdapterMsg adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_msg, container, false);
        listView_Msgs = (ListView) v.findViewById(R.id.listView_fragment_msg);
        adapter = new AdapterMsg(getActivity(), App.msgs);
        listView_Msgs.setAdapter(adapter);

        btnCreate = (Button) v.findViewById(R.id.btn_fragment_msg_create_msg);
        btnCreate.setOnClickListener(this);

        getActivity().registerReceiver(receiver,new IntentFilter("refresh"));

        return v;
    }

    BroadcastReceiver receiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.notifyDataSetChanged();
        }
    };
    @Override
    public void onClick(View view) {
        FragmentDialogSendMsg fragmentDialogSendMsg = new FragmentDialogSendMsg();
        fragmentDialogSendMsg.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        fragmentDialogSendMsg.show(getActivity().getSupportFragmentManager(), "msg");
    }
}
