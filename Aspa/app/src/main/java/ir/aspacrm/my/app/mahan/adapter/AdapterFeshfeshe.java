package ir.aspacrm.my.app.mahan.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.events.EventOnClickedStartFeshfeshe;
import ir.aspacrm.my.app.mahan.model.Feshfeshe;

public class AdapterFeshfeshe extends RecyclerView.Adapter<AdapterFeshfeshe.FeshfesheViewHolder> {

    List<Feshfeshe> feshfesheList;
    public AdapterFeshfeshe(List<Feshfeshe> feshfesheList) {
        this.feshfesheList = feshfesheList;
    }

    @Override
    public FeshfesheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_feshfeshe_item2, parent, false);
        return new FeshfesheViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeshfesheViewHolder holder, int position) {
        final Feshfeshe feshfeshe = feshfesheList.get(position);
        holder.txtFeshfesheName.setText("" + feshfeshe.packageName);
        holder.layEndFeshfeshe.setVisibility(View.GONE);
        if(feshfeshe.started == 1){
            holder.layEndFeshfeshe.setVisibility(View.GONE);

        }else{
            holder.layStartFeshfeshe.setVisibility(View.VISIBLE);
            holder.layStartFeshfeshe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new EventOnClickedStartFeshfeshe(feshfeshe.code));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return feshfesheList.size();
    }

    public class FeshfesheViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtFeshfesheName) TextView txtFeshfesheName;
        @Bind(R.id.layEndFeshfeshe) CardView layEndFeshfeshe;
        @Bind(R.id.layStartFeshfeshe) CardView layStartFeshfeshe;
        public FeshfesheViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<Feshfeshe> data) {
        feshfesheList = data;
        notifyDataSetChanged();
    }
}
