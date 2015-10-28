package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studioidan.com.parsetest.R;

/**
 * Created by PopApp_laptop on 21/05/2015.
 */
public class Fr_1 extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_1, container, false);
        return  v;
    }
}
