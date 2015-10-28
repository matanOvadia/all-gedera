package studioidan.com.parsetest;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import entities.MenuDrawerItem;
import entities.Msg;

/**
 * Created by PopApp_laptop on 21/05/2015.
 */
public class AdapterMsg extends BaseAdapter {
    List<Msg> data;
    Context con;
    LayoutInflater inflater;

    public AdapterMsg(Context c,List<Msg> d)
    {
        con = c;
        data = d;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Msg item = data.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.row_msg, null);

        TextView tvFrom = (TextView) vi.findViewById(R.id.tv_row_msg_from);
        tvFrom.setText(item.getFrom());
        try {
            TextView tvDate = (TextView) vi.findViewById(R.id.tv_row_msg_date);
            tvDate.setText(DateFormat.format("dd:MM:yyyy", item.getCreatedAt()));
        }catch (Exception e){}

        TextView tvContent = (TextView) vi.findViewById(R.id.tv_row_msg_content);
        tvContent.setText(item.getContent());

        return vi;
    }
}
