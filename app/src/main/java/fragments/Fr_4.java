package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import studioidan.com.parsetest.MainActivity;
import studioidan.com.parsetest.R;

/**
 * Created by PopApp_laptop on 21/05/2015.
 */
public class Fr_4 extends Fragment implements View.OnClickListener {
    Button btn_next;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_4, container, false);
       btn_next = (Button) v.findViewById(R.id.btn_fist_time_next);
        btn_next.setOnClickListener(this);
        return  v;
    }

    @Override
    public void onClick(View view) {
        getActivity().finish();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}
