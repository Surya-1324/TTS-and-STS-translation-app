package com.example.myapplication;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Adapter extends BaseAdapter implements Filterable {
Context c;
ArrayList<Model> models;
    ArrayList<Model> filterList;
    CustomFilter filter;
public Adapter(Context ctx,ArrayList<Model>models){
    this.c=ctx;
    this.models=models;
    this.filterList=models;
}

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return models.indexOf(getItem(i));
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if(convertView==null)
    {
        convertView=inflater.inflate(R.layout.model,null);
    }
        TextView nameTxt=(TextView) convertView.findViewById(R.id.nameTv);
        ImageView img= (ImageView) convertView.findViewById(R.id.imageView1);
nameTxt.setText(models.get(i).getName());
img.setImageResource(models.get(i).getImg());
        return convertView;
    }

    @Override
    public Filter getFilter() {
    if(filter==null){
        filter=new CustomFilter();
    }
        return filter;
    }
    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
   FilterResults results=new FilterResults();
 if(constraint!=null && constraint.length()>0){
     constraint=constraint.toString().toUpperCase();
     ArrayList<Model>filters=new ArrayList<Model>();
     for (int i=0;i<filterList.size();i++){
         if(filterList.get(i).getName().toUpperCase().contains(constraint)){
             Model m =new Model(filterList.get(i).getName(),filterList.get(i).getImg());
             filters.add(m);
         }
     }
     results.count=filters.size();
     results.values=filters;
 }else {
     results.count=filterList.size();
     results.values=filterList;
 }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
models=(ArrayList<Model>) results.values;
notifyDataSetChanged();
        }
    }
}
