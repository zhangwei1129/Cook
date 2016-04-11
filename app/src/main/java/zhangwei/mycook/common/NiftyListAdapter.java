package zhangwei.mycook.common;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class NiftyListAdapter<T> extends BaseAdapter {

    private ArrayList<T> data = new ArrayList<T>();

    private Activity context;

    private LayoutInflater inflater;

    public NiftyListAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public Activity getContext() {
        return context;
    }

    public ArrayList<T> getList() {
        return data;
    }

    public void add(int index, List<T> items) {
        data.addAll(index, items);
        notifyDataSetChanged();
    }

    public void add(int index,T item){
        data.add(index,item);
        notifyDataSetChanged();
    }

    public void setList(List<T> items) {
        if (items != null) {
            data.clear();
            data.addAll(items);
            notifyDataSetChanged();
        } else {
            data.clear();
            notifyDataSetChanged();
        }

    }

    public void remove(T item){
        if (item != null) {
            data.remove(item);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data == null ? 0 : data.size();
    }

}
