package com.example.myapplication;

// Adapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class Adapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private ArrayList<Model> mModels;
    private ArrayList<Model> mFilterList;
    private CustomFilter mFilter;
    private int mLayoutId; // Added to store the layout resource ID

    public Adapter(Context context, ArrayList<Model> models, int layoutId) {
        mContext = context;
        mModels = models;
        mFilterList = models;
        mLayoutId = layoutId; // Store the layout resource ID
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        if (convertView == null) {
            convertView = inflater.inflate(mLayoutId, parent, false); // Inflate layout based on mLayoutId
        }

        TextView nameTxt = convertView.findViewById(R.id.nameTv);
        ImageView img = convertView.findViewById(R.id.imageView1);

        nameTxt.setText(mModels.get(position).getName());
        img.setImageResource(mModels.get(position).getImg());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new CustomFilter();
        }
        return mFilter;
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<Model> filters = new ArrayList<Model>();
                for (int i = 0; i < mFilterList.size(); i++) {
                    if (mFilterList.get(i).getName().toUpperCase().contains(constraint)) {
                        Model m = new Model(mFilterList.get(i).getName(), mFilterList.get(i).getImg());
                        filters.add(m);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = mFilterList.size();
                results.values = mFilterList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mModels = (ArrayList<Model>) results.values;
            notifyDataSetChanged();
        }
    }
}
