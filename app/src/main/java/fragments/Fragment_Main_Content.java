package fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.util.HashMap;
import java.util.List;

import entities.Place;
import entities.Work;
import studioidan.com.parsetest.R;

/**
 * Created by PopApp_laptop on 18/05/2015.
 */
public class Fragment_Main_Content extends Fragment {
    private String tag = "Fragment Main Content";
    private GoogleMap map;
    List<Place> allPlaces;
    List<Work> allWorks;
    HashMap<Marker, Place> mapPlaces = new HashMap<Marker, Place>();
    HashMap<Marker, Work> mapWorks = new HashMap<Marker, Work>();
    public String shown = "";
    public boolean gotLocation = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_content, container, false);
        map = getMapFragment().getMap();
        map.setInfoWindowAdapter(infoWindowAdapter);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        goToMyLocation();
        return v;
    }

    private SupportMapFragment getMapFragment() {
        FragmentManager fm = null;

        Log.d(tag, "sdk: " + Build.VERSION.SDK_INT);
        Log.d(tag, "release: " + Build.VERSION.RELEASE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Log.d(tag, "using getFragmentManager");
            fm = getActivity().getSupportFragmentManager();
            fm = getChildFragmentManager();
        } else {
            Log.d(tag, "using getChildFragmentManager");
            fm = getChildFragmentManager();
        }
        return (SupportMapFragment) fm.findFragmentById(R.id.map);
    }
    private void goToMyLocation() {
        System.out.println("here");
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);
        if(myLocation!=null) {
            double latitude = myLocation.getLatitude();
            double longitude = myLocation.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(14));
        }
    }

    public void SetPlaces(List<Place> places) {
        shown = Place.class.getSimpleName();
        allPlaces = places;
        map.clear();
        mapPlaces.clear();
        map.setOnInfoWindowClickListener(onInfoWindowClickListenerPlace);
        for (Place p : places) {
            LatLng latLng = new LatLng(p.getLocation().getLatitude(), p.getLocation().getLongitude());
            String name = p.getName();
            String address = p.getAddress();
            address += getActivity().getResources().getString(R.string.press_to_see_more);
            MarkerOptions op = new MarkerOptions()
                    .position(latLng)
                    .title(name)
                    .snippet(address);
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_place));
            Marker m = map.addMarker(op);
            mapPlaces.put(m, p);
        }
    }

    public void SetWorks(List<Work> works) {
        shown = Work.class.getSimpleName();

        allWorks = works;
        map.clear();
        mapPlaces.clear();
        map.setOnInfoWindowClickListener(onInfoWindowClickListenerWork);
        for (Work w : works) {
            LatLng latLng = new LatLng(w.getLocation().getLatitude(), w.getLocation().getLongitude());
            String name = w.getName();
            String address = w.getAddress();
            MarkerOptions op = new MarkerOptions()
                    .position(latLng)
                    .title(name)
                    .snippet(address + "\n" + getActivity().getResources().getString(R.string.press_to_see_more));
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.img_pin))
            Marker m = map.addMarker(op);
            mapWorks.put(m, w);
        }
    }

    GoogleMap.OnInfoWindowClickListener onInfoWindowClickListenerPlace = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Log.i(tag, "info clicked");
            Place place = mapPlaces.get(marker);
            FragmentDialogPlace fragmentDialogPlace = new FragmentDialogPlace();
            Bundle bundle = new Bundle();
            bundle.putSerializable("place",place);
            fragmentDialogPlace.setArguments(bundle);
            fragmentDialogPlace.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
            fragmentDialogPlace.show(getActivity().getSupportFragmentManager(), "place");
        }
    };
    GoogleMap.OnInfoWindowClickListener onInfoWindowClickListenerWork = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Log.i(tag, "info clicked");
            Work work = mapWorks.get(marker);
                FragmentDialogWorks fragmentDialogWorks = new FragmentDialogWorks();
                Bundle bundle = new Bundle();
                bundle.putSerializable("work",work);
                fragmentDialogWorks.setArguments(bundle);
                fragmentDialogWorks.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                fragmentDialogWorks.show(getActivity().getSupportFragmentManager(), "work");
        }
    };

    GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.info_window_adapter, null);
            TextView tvName = (TextView) v.findViewById(R.id.tv_info_adapter_name);
            TextView tvAddress = (TextView) v.findViewById(R.id.tv_info_adapter_address);

            if (shown.equals(Work.class.getSimpleName())) {
                Work w = mapWorks.get(marker);
                tvName.setText(w.getName());
                tvAddress.setText(w.getAddress());
            } else {
                Place p = mapPlaces.get(marker);
                tvName.setText(p.getName());
                tvAddress.setText(p.getAddress());
            }

            return v;
        }
    };

}
