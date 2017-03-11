package ir.aspacrm.my.app.mahan.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.component.PersianTextViewThin;
import ir.aspacrm.my.app.mahan.model.Notify;

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
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final Notify notify = notifies.get(position);
//        if (!notify.isSeen) {
//            holder.notifyCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.circle_background_color));
//            notify.isSeen = true;
//            notify.save();
//        } else {
//            holder.notifyCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.dark_grey));
//        }
        holder.txtNotifyMessage.setText("" + notify.message);
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new Update(Notify.class)
                            .set("IsSeen = 1")
                            .where("UserId = ? AND NotifyCode = ?", G.currentUser.userId, "" + notify.notifyCode)
                            .execute();

//                    new Delete()
//                            .from(Notify.class)
//                            .where("UserId = ? AND NotifyCode = ?", G.currentUser.userId, "" + notify.notifyCode)
//                            .execute();

                    getNotifyFromDB();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifies.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtNotifyMessage)
        PersianTextViewThin txtNotifyMessage;
        @Bind(R.id.notifyCardView)
        CardView notifyCardView;
        @Bind(R.id.btn_delete)
        ImageView btn_delete;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<Notify> data) {
        notifies = data;
        notifyDataSetChanged();
    }

    private void getNotifyFromDB() {
        notifies = new Select()
                .from(Notify.class)
                .where("UserId = ? AND IsSeen = 0", G.currentUser.userId)
                .orderBy("NotifyCode desc")
                .execute();
        updateList(notifies);
    }
}
