package studioidan.com.parsetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import entities.MenuDrawerItem;

/**
 * Created by PopApp_laptop on 18/05/2015.
 */
public class AdapterMenuItem extends BaseAdapter {
    List<MenuDrawerItem> data;
    Context con;
    LayoutInflater inflater;

    public AdapterMenuItem(Context c,List<MenuDrawerItem> d)
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
        final MenuDrawerItem item = data.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.row_menu_list_item, null);

        TextView tvName = (TextView) vi.findViewById(R.id.tv_menu_item_name);
        tvName.setText(item.title.trim());
        ImageView imgIcon = (ImageView) vi.findViewById(R.id.img_menu_item_icon);
        imgIcon.setImageResource(item.image);

        return vi;
    }
}
