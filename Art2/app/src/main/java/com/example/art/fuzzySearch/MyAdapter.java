package com.example.art.fuzzySearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.art.R;
import com.example.art.info.ScanItem;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Filterable {

    private List<ScanItem> list;
    private Context context;
    private MyFilter filter = null;// 创建MyFilter对象
    private FilterListener listener = null;// 接口对象

    public MyAdapter(List<ScanItem> list, Context context, FilterListener filterListener) {
        this.list = list;
        this.context = context;
        this.listener = filterListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.tv_ss = convertView.findViewById(R.id.tv_ss);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_ss.setText(list.get(position).getName());
        return convertView;
    }
    @Override
    public Filter getFilter() {
        // 如果MyFilter对象为空，那么重写创建一个
        if (filter == null) {
            filter = new MyFilter(list);
        }
        return filter;
    }
    class MyFilter extends Filter {
        private List<ScanItem> original = new ArrayList<ScanItem>();
        public MyFilter(List<ScanItem> list) {
            this.original = list;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(TextUtils.isEmpty(constraint)){
                results.values = original;
                results.count = original.size();
            }else {
                List<ScanItem> mList = new ArrayList<ScanItem>();
                for(ScanItem s: original){
                    if(s.getName().trim().toLowerCase().contains(constraint.toString().trim().toLowerCase())){
                        mList.add(s);
                    }
                }
                results.values = mList;
                results.count = mList.size();
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            list = (List<ScanItem>) results.values;
            if(listener != null){
                listener.getFilterData(list);
            }
            notifyDataSetChanged();
        }
    }
    class ViewHolder {
        TextView tv_ss;
    }

}