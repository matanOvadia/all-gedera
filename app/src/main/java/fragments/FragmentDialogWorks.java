package fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import entities.Place;
import entities.Work;
import studioidan.com.parsetest.R;

/**
 * Created by PopApp_laptop on 19/05/2015.
 */


public class FragmentDialogWorks extends DialogFragment implements View.OnClickListener {

    Work work;
    Button btnNav;
    ImageView imgExit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        work = (Work) getArguments().getSerializable("work");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_work, container, false);
        imgExit = (ImageView) v.findViewById(R.id.img_exit);
        imgExit.setOnClickListener(this);

        TextView tvName = ((TextView) v.findViewById(R.id.tv_fragment_work_name));
        if (work.getName() != null)
            tvName.setText(work.getName());
        TextView tvAdress = ((TextView) v.findViewById(R.id.tv_fragment_work_about));
        if (work.getAddress() != null)
            tvAdress.setText(work.getAddress());
        TextView tvAbout = ((TextView) v.findViewById(R.id.tv_fragment_work_addess));
        if (work.getAbout() != null)
            tvAbout.setText(work.getAbout());
        TextView tvPay = ((TextView) v.findViewById(R.id.tv_fragment_work_pay));
        if (work.getPay() != null)
            tvPay.setText(work.getPay());
        TextView tvPhone = ((TextView) v.findViewById(R.id.tv_fragment_work_phone));
        if (work.getPhone() != null)
            tvPhone.setText(work.getPhone());
        btnNav = (Button) v.findViewById(R.id.btn_fragment_work_nav);
        btnNav.setOnClickListener(this);

        final ParseImageView imageView = (ParseImageView) v.findViewById(R.id.img_fragment_work_image);
        ParseFile img = work.getImage();
        if (img != null) {
            String url = img.getUrl();
            // UrlImageViewHelper.setUrlDrawable(imageView,url);
            imageView.setParseFile(img);
            imageView.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bmp);
                }
            });
        }
        return v;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.img_exit)
        {
            dismiss();
        }
        else {
            try {
                String uri = "geo: " + work.getLocation().getLatitude() + "," + work.getLocation().getLongitude();
                getActivity().startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
            } catch (Exception e) {
            }
        }

    }
}
