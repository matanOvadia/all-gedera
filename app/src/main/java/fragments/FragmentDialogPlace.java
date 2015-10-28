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
import studioidan.com.parsetest.R;

/**
 * Created by PopApp_laptop on 19/05/2015.
 */
public class FragmentDialogPlace extends DialogFragment implements View.OnClickListener {

    Place place;
    Button btnNav;
    private ImageView imgExit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        place = (Place) getArguments().getSerializable("place");
        //...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_window_place, container, false);
        imgExit = (ImageView) v.findViewById(R.id.img_exit);
        imgExit.setOnClickListener(this);
        TextView tvName = ((TextView) v.findViewById(R.id.tv_info_window_place_name));
        if (tvName != null)
            tvName.setText(place.getName());
        TextView tvAdress = ((TextView) v.findViewById(R.id.tv_info_window_place_addess));
        if (tvAdress != null)
            tvAdress.setText(place.getAddress());
        TextView tvAbout = ((TextView) v.findViewById(R.id.tv_info_window_place_about));
        if (tvAbout != null)
            tvAbout.setText(place.getAbout());
        TextView tvMinAge = ((TextView) v.findViewById(R.id.tv_info_window_place_min_age));
        if (tvMinAge != null)
            tvMinAge.setText(place.getMinAge());
        TextView tvPrices = ((TextView) v.findViewById(R.id.tv_info_window_place_prices));
        if (tvPrices != null)
            tvPrices.setText(place.getPrices());
        TextView tvPhone = ((TextView) v.findViewById(R.id.tv_info_window_place_phone));
        if (tvPhone != null)
            tvPhone.setText(place.getPhone());
        btnNav = (Button) v.findViewById(R.id.btn_info_window_nav);
        btnNav.setOnClickListener(this);
        final ParseImageView imageView = (ParseImageView) v.findViewById(R.id.img_info_window_place);
        ParseFile img = place.getImage();
        if (img != null) {
            String url = img.getUrl();
            //UrlImageViewHelper.setUrlDrawable(imageView,url);
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
                String uri = "geo: " + place.getLocation().getLatitude() + "," + place.getLocation().getLongitude();
                getActivity().startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
            } catch (Exception e) {
            }
        }
    }
}
