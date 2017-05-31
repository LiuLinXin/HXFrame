package com.csd.activitybase.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csd.activitybase.activity.ListActivity;
import com.csd.activitybase.adapers.AdapterEmpty;
import com.csd.activitybase.adapers.BaseRecycleAdapter;
import com.csd.activitybase.adapers.ItemRecycleAdapter;
import com.csd.activitybase.adapers.MultypeRecycleAdapter;
import com.csd.activitybase.adapers.SectionRecycleAdapter;
import com.csd.hx.frame.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterTest extends ListActivity {

    MultypeRecycleAdapter<String> innerAdapter;
    private TTAdapter ttAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected SectionRecycleAdapter setListContent(SectionRecycleAdapter sectionAdapter) {
        innerAdapter = new MultypeRecycleAdapter<>(this);
//        SectionRecycleAdapter sectionAdapter = new SectionRecycleAdapter();

        sectionAdapter.addSection(new ItemText(this));
        sectionAdapter.addSection(new AdapterEmpty(this, 20));
        sectionAdapter.addSection(ttAdapter = new TTAdapter(this));
        sectionAdapter.addSection(innerAdapter);

//        sectionAdapter.addSection(ttAdapter = new TTAdapter(this));

        listView.setAdapter(sectionAdapter);

        innerAdapter.addType(new TypeOne());
        innerAdapter.addType(new TypeTow());
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dates.add("date : " + i);
        }
        innerAdapter.replaceAll(dates);
        innerAdapter.setSelectMode(true);

        List<String> ttt = new ArrayList<>();
        ttt.add("asdf");
        ttt.add("asdf");
        ttAdapter.replaceAll(ttt);
        return sectionAdapter;
    }

    class ItemText extends ItemRecycleAdapter {

        private View view;
        private RecyclerView rv;

        public ItemText(Activity context) {
            super(context);
        }

        @Override
        public View getView(ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(activity).inflate(R.layout.item_ttt, viewGroup, false);
                rv = (RecyclerView) view.findViewById(R.id.rv);
//                rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                rv.setLayoutManager(new GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false));
                HoAdapter ad = new HoAdapter(activity);
                List<Integer> imgs = new ArrayList<>();
                imgs.add(R.mipmap.home_bg_sz1);
                imgs.add(R.mipmap.home_bg_sz2);
                imgs.add(R.mipmap.home_bg_sz3);
                imgs.add(R.mipmap.home_icon_me);
                imgs.add(R.mipmap.home_bg_sz1);
                imgs.add(R.mipmap.home_bg_sz2);
                imgs.add(R.mipmap.home_bg_sz3);
                imgs.add(R.mipmap.home_icon_me);
                imgs.add(R.mipmap.home_bg_sz1);
                imgs.add(R.mipmap.home_bg_sz2);
                imgs.add(R.mipmap.home_bg_sz3);
                imgs.add(R.mipmap.home_icon_me);
                imgs.add(R.mipmap.home_bg_sz1);
                imgs.add(R.mipmap.home_bg_sz2);
                imgs.add(R.mipmap.home_bg_sz3);
                imgs.add(R.mipmap.home_icon_me);
                imgs.add(R.mipmap.home_bg_sz1);
                imgs.add(R.mipmap.home_bg_sz2);
                imgs.add(R.mipmap.home_bg_sz3);
                imgs.add(R.mipmap.home_icon_me);
                rv.setAdapter(ad);
                ad.replaceAll(imgs);
            }
            return view;
        }
    }

    class HoAdapter extends BaseRecycleAdapter<Integer, HoAdapter.Innerhhh> {
        public HoAdapter(Activity activity) {
            super(activity, R.layout.ttttt);
        }

        @Override
        protected void setContent(Innerhhh holder, Integer bean, int position) {
            holder.iv.setImageResource(bean);
        }

        @Override
        protected Innerhhh createHolder(View inflate, int type) {
            return new Innerhhh(inflate);
        }

        class Innerhhh extends RecyclerView.ViewHolder {
            ImageView iv;

            public Innerhhh(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.iv);
            }
        }
    }

    class TTAdapter extends BaseRecycleAdapter<String, TTAdapter.InnerHolder> {

        public TTAdapter(Activity activity) {
            super(activity, R.layout.ttasl);
        }

        @Override
        protected void setContent(InnerHolder holder, String bean, int position) {

        }

        @Override
        protected InnerHolder createHolder(View inflate, int type) {
            return new InnerHolder(inflate);
        }

        class InnerHolder extends RecyclerView.ViewHolder {
            public InnerHolder(View itemView) {
                super(itemView);
            }
        }
    }

    class TypeOne extends MultypeRecycleAdapter.RecycleType {

        @Override
        protected boolean accept(Object bean, int position) {
            return position % 3 == 1;
        }

        @Override
        protected RecyclerView.ViewHolder getHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(activity).inflate(R.layout.item_test, parent, false);
            return new InnerHoder(inflate);
        }

        @Override
        protected void setContent(RecyclerView.ViewHolder holder, Object bean, int position) {
            InnerHoder trueHolder = (InnerHoder) holder;
            trueHolder.tvName.setText(bean.toString());
            if (isSelected(bean)) {
                trueHolder.viewTag.setVisibility(View.VISIBLE);
            } else {
                trueHolder.viewTag.setVisibility(View.GONE);
            }
        }

        class InnerHoder extends RecyclerView.ViewHolder {
            private TextView tvName;
            private View viewTag;

            public InnerHoder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                viewTag = itemView.findViewById(R.id.viewTag);
            }
        }
    }


    class TypeTow extends MultypeRecycleAdapter.RecycleType {

        @Override
        protected boolean accept(Object bean, int position) {
            return position % 3 != 1;
        }

        @Override
        protected RecyclerView.ViewHolder getHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(activity).inflate(R.layout.item_test_tow, parent, false);
            return new InnerHoder(inflate);
        }

        @Override
        protected void setContent(RecyclerView.ViewHolder holder, Object bean, int position) {
            InnerHoder trueHolder = (InnerHoder) holder;
            trueHolder.tvName.setText(bean.toString());
            if (isSelected(bean)) {
                trueHolder.viewTag.setVisibility(View.VISIBLE);
            } else {
                trueHolder.viewTag.setVisibility(View.GONE);
            }
        }

        class InnerHoder extends RecyclerView.ViewHolder {
            private TextView tvName;
            private View viewTag;

            public InnerHoder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                viewTag = itemView.findViewById(R.id.viewTag);
            }
        }
    }
}
