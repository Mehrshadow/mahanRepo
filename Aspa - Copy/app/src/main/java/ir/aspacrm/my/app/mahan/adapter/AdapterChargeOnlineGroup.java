package ir.aspacrm.my.app.mahan.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.component.PersianTextViewThin;
import ir.aspacrm.my.app.mahan.events.EventOnClickedChargeOnlineGroup;
import ir.aspacrm.my.app.mahan.gson.ChargeOnlineGroup;

public class AdapterChargeOnlineGroup extends RecyclerView.Adapter<AdapterChargeOnlineGroup.GroupViewHolder> {

    List<ChargeOnlineGroup> groups;
    int isClub;
    int whichMenuItem;

    public AdapterChargeOnlineGroup(List<ChargeOnlineGroup> groups, int isClub, int whichMenuItem) {
        this.groups = groups;
        this.isClub = isClub;
        this.whichMenuItem = whichMenuItem;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_charge_online_group_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        final ChargeOnlineGroup group = groups.get(position);
        holder.txtGroupName.setText("" + group.des);
        if (group.now)
            holder.img_current_service.setVisibility(View.VISIBLE);
//        holder.layMainGroup.setCardBackgroundColor(Color.parseColor(group.color));
        holder.layMainGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedChargeOnlineGroup(group.code, isClub, whichMenuItem));
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtGroupName)
        PersianTextViewThin txtGroupName;
        @Bind(R.id.layMainGroup)
        CardView layMainGroup;
        @Bind(R.id.img_current_service)
        ImageView img_current_service;

        public GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<ChargeOnlineGroup> data) {
        groups = data;
        notifyDataSetChanged();
    }
}
