package entities;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by PopApp_laptop on 14/05/2015.
 */
@ParseClassName("Place")
public class Place extends ParseObject implements Serializable {

    public String getName(){return getString("name");};
    public void setName(String name){put("name",name);};

    public String getCategory(){return getString("category");};
    public void setCategoty(String cat){put("category", cat);};

    public String getAddress(){return getString("address");};
    public void setAddress(String add){put("address", add);};

    public String getPhone(){return getString("phone");};
    public void setPhone(String phone){put("phone", phone);};

    public ParseGeoPoint getLocation(){return getParseGeoPoint("location");};
    public void setLocation(ParseGeoPoint loc){put("location", loc);};

    public String getMinAge(){return getString("minAge");};
    public void setMinAge(String age){put("minAge", age);};

    public String getArea(){return getString("area");};
    public void setArea(String area){put("area", area);};

    public String getPrices(){return getString("prices");};
    public void setPrices(String age){put("prices", age);};

    public String getAbout(){return getString("about");};
    public void setAbbout(String about){put("about", about);};

    public ParseFile getImage(){return getParseFile("image");};
    public void setImage(ParseFile image){put("image", image);};

    @Override
    public String toString() {
        return getName();
    }
}
