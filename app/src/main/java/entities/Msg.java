package entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by PopApp_laptop on 21/05/2015.
 */
@ParseClassName("Msg")

public class Msg  extends ParseObject implements Serializable {

    public String getFrom(){return getString("from");};
    public void setFron(String from){put("from",from);};

    public String getContent(){return getString("content");};
    public void setContentt(String content){put("content", content);};

    public int getGuests(){return getInt("guests");};
    public void setGuests(int Guests){put("guests", Guests);};
}
