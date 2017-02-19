package ir.aspacrm.my.app.mahan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ir.aspacrm.my.app.mahan.ActivityLogin;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;

/**
 * Created by HaMiD on 1/22/2017.
 */

public class AdapterDrawerRecycler extends RecyclerView.Adapter<AdapterDrawerRecycler.DrawerRecyclerViewHolder> {

    List<String> list;
    Context context;


    public AdapterDrawerRecycler(List<String> list,Context context) {
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


        holder.imgImage.setBackgroundResource(R.drawable.ic_club);
        holder.txtText.setText(list.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()){
                    case 0:
                        Intent intent = new Intent(G.context, ActivityLogin.class);
                        G.context.startActivity(intent);
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
        TextView txtText;
        ImageView imgImage;
        LinearLayout layout;

        public DrawerRecyclerViewHolder(View itemView) {
            super(itemView);
            txtText = (TextView) itemView.findViewById(R.id.txtTitle);
            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);
            layout = (LinearLayout)itemView.findViewById(R.id.lay);

        }

    }
}
