package zhangwei.mycook.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import zhangwei.mycook.R;
import zhangwei.mycook.common.SimpleListener;
import zhangwei.mycook.common.util.ToastUtil;
import zhangwei.mycook.manager.Manager;
import zhangwei.mycook.model.Category;
import zhangwei.mycook.view.activity.SearchActivity;

/**
 * Created by zhangwei25 on 2016/4/26.
 */
public class CategoryFragment extends Fragment {

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    private View rootView;
    ExpandableListView expandableListView;
    ArrayList<Category> categoryArrayList;
    MyAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
//        expandableListView.smoothScrollToPosition(0);//滑动到置顶位置（动画）
//        for (int i = 0; i < adapter.getGroupCount(); i++) {
//            expandableListView.collapseGroup(i);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_category, null);
            initWidget(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    private void initWidget(View rootView) {
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.list);
        expandableListView.setGroupIndicator(null);//关闭小箭头
//        expandableListView .requestFocusFromTouch();
//        expandableListView .setSelection(0);

    }

    private void initData() {
        adapter = new MyAdapter();
        expandableListView.setAdapter(adapter);
        Manager.getInstance().getCookCategory(getContext(), new SimpleListener<ArrayList<Category>>() {
            @Override
            public void onFinish(ArrayList<Category> categories) {
                if (categories != null) {
                    categoryArrayList = categories;
                    ToastUtil.showLongToast(getContext(), "categoryArrayList.size() = " + categoryArrayList.size());
                    adapter.notifyDataSetChanged();

                } else {
                    ToastUtil.showLongToast(getContext(), "categoryArrayList == null");
                }
            }

            @Override
            public void onError(String message) {
                ToastUtil.showLongToast(getContext(), message);
            }
        });


    }

    private void initListener() {
        //每次只展开一个
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
//                expandableListView.smoothScrollToPositionFromTop(groupPosition,0);
                for (int i = 0; i < adapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);//收起第i个二级菜单
//                        expandableListView.expandGroup(i);//打开第i个二级菜单
                    }
                }
            }
        });
    }

    private class MyAdapter extends BaseExpandableListAdapter {
        LayoutInflater inflater;

        public MyAdapter() {
            this.inflater = LayoutInflater.from(getContext());
        }

        @Override
        public int getGroupCount() {
            return categoryArrayList == null ? 0 : categoryArrayList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return categoryArrayList.get(groupPosition).subcategories == null ?
                    0 : categoryArrayList.get(groupPosition).subcategories.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return categoryArrayList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return categoryArrayList.get(groupPosition).subcategories.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_textview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Category info = categoryArrayList.get(groupPosition);
            holder.tvName.setText(info.name);
            holder.tvName.setTextSize(20);
            holder.tvName.setTextColor(getResources().getColor(R.color.black));
            holder.tvName.setPadding(16, 20, 16, 20);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_textview, null);
                holder = new ChildViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ChildViewHolder) convertView.getTag();
            }

            final Category.Subcategory info = categoryArrayList.get(groupPosition).subcategories.get(childPosition);
            holder.tvName.setText(info.name);
            holder.tvName.setTextSize(18);
            holder.tvName.setTextColor(getResources().getColor(R.color.gray));
            holder.tvName.setPadding(26, 20, 16, 20);
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showLongToast(getContext(), info.name);
                    SearchActivity.start(getContext());
                }
            });
            return convertView;
        }

        public class ViewHolder {
            public TextView tvName;
            public View root;

            public ViewHolder(View root) {
                tvName = (TextView) root.findViewById(R.id.tv);
                this.root = root;
            }
        }

        public class ChildViewHolder {
            public TextView tvName;
            public View root;

            public ChildViewHolder(View root) {
                tvName = (TextView) root.findViewById(R.id.tv);
                this.root = root;
            }
        }

    }


}
