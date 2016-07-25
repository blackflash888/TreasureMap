package com.feicui.TreasureMap.home.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.TreasureMap.home.Treasure;
import com.feicui.TreasureMap.home.TreasureView;
import com.feicui.TreasureMap.home.detail.TreasureDetailActivity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 16-7-25.
 */
public class TreasureListAdapter extends RecyclerView.Adapter<TreasureListAdapter.MyViewHodel> {

    private ArrayList<Treasure> datas = new ArrayList<Treasure>();
    public final void addItems(Collection<Treasure> items){
        if (items != null){
            datas.addAll(items);
            notifyItemRangeChanged(0,datas.size());
        }
    }

    @Override
    public MyViewHodel onCreateViewHolder(ViewGroup parent, int viewType) {
        TreasureView treasureView = new TreasureView(parent.getContext());
        return new MyViewHodel(treasureView);
    }

    //绑定ViewHodel上
    @Override
    public void onBindViewHolder(MyViewHodel holder, int position) {
        final Treasure treasure = datas.get(position);
        holder.treasureView.bindTreasure(treasure);
        holder.treasureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreasureDetailActivity.open(v.getContext(), treasure);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static final class MyViewHodel extends RecyclerView.ViewHolder{
        private TreasureView treasureView;
        public MyViewHodel(TreasureView itemView) {
            super(itemView);
            this.treasureView = itemView;
        }
    }
}
