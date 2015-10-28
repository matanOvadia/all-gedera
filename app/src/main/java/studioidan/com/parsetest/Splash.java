package studioidan.com.parsetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import org.json.JSONArray;
import org.json.XML;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.studioidan.popapplibrary.CPM;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import entities.Keys;
import entities.Msg;
import entities.Place;
import entities.Work;


public class Splash extends Activity {
    public boolean isFinished;
    int proccess = 0;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        handler = new Handler();
        LoadData();
        handler.postDelayed(runnable, 3000);
    }

    private void LoadData() {
        getWorks();
        getPlaces();
        getCities();
        getMsgs();

        isFinished = true;
    }

    private String tag = "Splash";
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (proccess < 3) {
                Log.i(tag, "notFinished");
                handler.postDelayed(this, 2000);
                return;
            }
            boolean isFirst = CPM.getBoolean(Keys.firstTime,true,Splash.this);
            Splash.this.finish();


            //startActivity(new Intent(Splash.this, FirstTime.class));
            if(isFirst==true)
                startActivity(new Intent(Splash.this, FirstTime.class));
            else
                startActivity(new Intent(Splash.this, MainActivity.class));

            ParseObject.create("").saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });
        }
    };

    public void getPlaces() {
        ParseQuery<Place> query = new ParseQuery<Place>("Place");
        query.findInBackground(new FindCallback<Place>() {
            @Override
            public void done(List<Place> list, ParseException e) {
                if (e == null) {
                    Log.i(tag, "got " + list.size() + " places!");
                    App.places = list;
                    proccess+=1;
                }
            }
        });
    }
    public void getMsgs() {
        ParseQuery<Msg> query = new ParseQuery<Msg>("Msg");
        query.findInBackground(new FindCallback<Msg>() {
            @Override
            public void done(List<Msg> list, ParseException e) {
                if (e == null) {
                    Log.i(tag, "got " + list.size() + " Msgs!");
                    App.msgs = list;
                    proccess+=1;
                }
            }
        });
    }
    public void getWorks() {
        //allWorkPlaces = new ArrayList<Work>();
        ParseQuery<Work> query = new ParseQuery<Work>("Work");
        query.findInBackground(new FindCallback<Work>() {
            @Override
            public void done(List<Work> list, ParseException e) {
                if (e == null) {
                    //for (Work w : list)
                    //allWorkPlaces.add(w);
                    Log.i(tag, "got " + list.size() + " Works!");
                    App.works=list;

                    proccess+=1;
                }
            }

        });
    }
    public void getCities() {
        JSONObject jsonObj = null;
        String xml = loadAssetTextAsString(this, "cities.xml");
        if (xml == null) {
            return;
        }
        try {
            List<String> cities = new ArrayList<String>();
            jsonObj = XML.toJSONObject(xml);
            JSONArray arr = jsonObj.getJSONObject("Root").getJSONArray("City");
            for (int i = 0; i < arr.length(); i++) {
                try {
                    String name = arr.getJSONObject(i).getString("Heb");
                    cities.add(name);
                } catch (Exception e) {
                    continue;
                }
            }
            App.cities.addAll(cities);
            cities.clear();
            proccess+=1;

        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
        }
    }

    private String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e(tag, "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(tag, "Error closing asset " + name);
                }
            }
        }

        return null;
    }
}
