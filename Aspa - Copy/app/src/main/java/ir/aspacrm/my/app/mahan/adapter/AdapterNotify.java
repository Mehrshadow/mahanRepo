package ir.aspacrm.my.app.mahan.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.component.PersianTextViewThin;
import ir.aspacrm.my.app.mahan.model.Notify;

import java.util.List;

public class AdapterNotify extends RecyclerView.Adapter<AdapterNotify.NewsViewHolder> {

    List<Notify> notifies;

    public AdapterNotify(List<Notify> notifies) {
        this.notifies = notifies;
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_notify_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        final Notify notify = notifies.get(position);
        if(!notify.isSeen){
            holder.notifyCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.circle_background_color));
            notify.isSeen = true;
            notify.save();
        }else{
            holder.notifyCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.dark_grey));
        }
        holder.txtNotifyMessage.setText("" + notify.message);
    }
    @Override
    public int getItemCount() {
        return notifies.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtNotifyMessage) PersianTextViewThin txtNotifyMessage;
        @Bind(R.id.notifyCardView) CardView notifyCardView;
        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void updateList(List<Notify> data) {
        notifies = data;
        notifyDataSetChanged();
    }
}
