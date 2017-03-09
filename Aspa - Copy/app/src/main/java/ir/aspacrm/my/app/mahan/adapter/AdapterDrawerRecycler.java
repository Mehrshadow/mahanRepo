package ir.aspacrm.my.app.mahan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import ir.aspacrm.my.app.mahan.ActivityChargeOnline;
import ir.aspacrm.my.app.mahan.ActivityPayments;
import ir.aspacrm.my.app.mahan.ActivityShowClubScores;
import ir.aspacrm.my.app.mahan.ActivityShowConnections;
import ir.aspacrm.my.app.mahan.ActivityShowFeshfeshe;
import ir.aspacrm.my.app.mahan.ActivityShowGraph;
import ir.aspacrm.my.app.mahan.ActivityShowNews;
import ir.aspacrm.my.app.mahan.ActivityShowNotify;
import ir.aspacrm.my.app.mahan.ActivityShowTickets;
import ir.aspacrm.my.app.mahan.ActivityShowUserInfo;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.U;

/**
 * Created by HaMiD on 1/22/2017.
 */

public class AdapterDrawerRecycler extends RecyclerView.Adapter<AdapterDrawerRecycler.DrawerRecyclerViewHolder> {

    List<String> list;
    Context context;
    private String link = "http://google.com";
    private String txt = "این متن جهت ارسال به کاربران نوشته شده و کاربرد دیگری ندارد! از لینک زیر استفاده نمایید!";


    public AdapterDrawerRecycler(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public DrawerRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_drawre_item, parent, false);
        return new DrawerRecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final DrawerRecyclerViewHolder holder, final int position) {
        if (position == 0) {
            holder.imgImage.setBackgroundResource(R.drawable.ic_person_dark);
        } else {
            holder.imgImage.setBackgroundResource(R.drawable.ic_circle);
        }

        holder.txtText.setText(list.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()) {
                    case 0:
                        G.context.startActivity(new Intent(context, ActivityShowUserInfo.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 1:
                        G.context.startActivity(new Intent(context, ActivityShowConnections.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 2:
                        if (G.currentLicense != null) {
                            if (G.currentLicense.chargeOnline) {
                                G.context.startActivity(new Intent(context, ActivityChargeOnline.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                U.toast("امکان شارژ آنلاین برای شما فعال نمیباشد.");
                            }
                        }
                        break;
                    case 3:
                        G.context.startActivity(new Intent(context, ActivityPayments.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 4:
                        G.context.startActivity(new Intent(context, ActivityShowGraph.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 5:
                        new DialogClass().showMessageDialog(context.getString(R.string.alert), context.getString(R.string.item_available_in_future));
                        break;

                    case 6:
                        G.context.startActivity(new Intent(context, ActivityShowTickets.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 7:// davat az doostan
                        sendText();
                        break;

                    case 8:
                        G.context.startActivity(new Intent(context, ActivityShowClubScores.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 9:
                        G.context.startActivity(new Intent(context, ActivityShowNotify.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 10:
                        new DialogClass().showMessageDialog(context.getString(R.string.alert), context.getString(R.string.item_available_in_future));
                        break;
                    case 11:
                        G.context.startActivity(new Intent(context, ActivityShowFeshfeshe.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 12:
                        G.context.startActivity(new Intent(context, ActivityShowNews.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 13:
                        DialogClass dialogExit = new DialogClass();
                        dialogExit.showExitDialog();
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class DrawerRecyclerViewHolder extends RecyclerView.ViewHolder {
        ir.aspacrm.my.app.mahan.component.PersianTextViewNormal txtText;
        ImageView imgImage;
        LinearLayout layout;

        public DrawerRecyclerViewHolder(View itemView) {
            super(itemView);
            txtText = (ir.aspacrm.my.app.mahan.component.PersianTextViewNormal) itemView.findViewById(R.id.txtTitle);
            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);
            layout = (LinearLayout) itemView.findViewById(R.id.lay);

        }
    }

    private void sendText() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, txt + "\n\n" + link);
        try {
            context.startActivity(Intent.createChooser(sendIntent, context.getString(R.string.choose_sender)));
        } catch (android.content.ActivityNotFoundException ex) {
            new DialogClass().showMessageDialog(context.getString(R.string.error), context.getString(R.string.send_failure));
        }
    }
}
