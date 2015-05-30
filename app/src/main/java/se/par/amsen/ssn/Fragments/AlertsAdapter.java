package se.par.amsen.ssn.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import se.par.amsen.ssnmachan.R;


class AlertsAdapter extends BaseAdapter
{
    ArrayList<HashMap<String, String>> dataSource = null;
    Context context;
    LayoutInflater layoutInflater;
    Typeface advFont;

    public AlertsAdapter(Context context, ArrayList<HashMap<String, String>> dataSource)
    {
        this.context = context;
        this.dataSource = dataSource;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        advFont = Typeface.createFromAsset(context.getAssets(), "fonts/Adventure.otf");
    }

    @Override
    public int getCount()
    {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position)
    {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        MyHolder holder = null;
        if (v == null)
        {
            v = layoutInflater.inflate(R.layout.alerts_list_item, parent, false);
            holder = new MyHolder(v);
            v.setTag(holder);
        }
        else
            holder = (MyHolder) v.getTag();

        HashMap<String, String> currentItem = dataSource.get(position);
        holder.title.setText(currentItem.get("title"));
        holder.description.setText(currentItem.get("description"));
        return v;

    }

    class MyHolder
    {
        TextView title, description;

        public MyHolder(View view)
        {
            title = (TextView) view.findViewById(R.id.title);
            title.setTypeface(advFont);
            description = (TextView) view.findViewById(R.id.description);
        }

    }
}





