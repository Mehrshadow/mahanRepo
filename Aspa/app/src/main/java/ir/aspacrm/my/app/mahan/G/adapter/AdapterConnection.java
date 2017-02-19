package ir.aspacrm.my.app.mahan.G.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.G.G;
import ir.aspacrm.my.app.mahan.G.R;
import ir.aspacrm.my.app.mahan.G.model.Connection;


import java.util.List;

public class AdapterConnection extends RecyclerView.Adapter<AdapterConnection.FactorViewHolder> {

    List<Connection> connections;
    public AdapterConnection(List<Connection> connections) {
        this.connections = connections;
    }

    @Override
    public FactorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_connection_item,parent,false);
        return new FactorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FactorViewHolder holder, int position) {
        final Connection connection = connections.get(position);
        holder.txtConnectionStartDate.setText("" + connection.startTime);
        holder.txtConnectionExpireDate.setText("" + connection.endTime);
        holder.txtConnectionDuration.setText("" + connection.duration);
        holder.txtConnectionSend.setText("" + connection.send + " مگابایت");
        holder.txtConnectionReceive.setText("" + connection.recieve + " مگابایت");
        holder.txtConnectionTrafficUsed.setText("" + connection.trafficUsed + " مگابایت");
    }

    @Override
    public int getItemCount() {
        return connections.size();
    }

    public class FactorViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtConnectionStartDate) TextView txtConnectionStartDate;
        @Bind(R.id.txtConnectionExpireDate) TextView txtConnectionExpireDate;
        @Bind(R.id.txtConnectionDuration) TextView txtConnectionDuration;
        @Bind(R.id.txtConnectionSend) TextView txtConnectionSend;
        @Bind(R.id.txtConnectionReceive) TextView txtConnectionReceive;
        @Bind(R.id.txtConnectionTrafficUsed) TextView txtConnectionTrafficUsed;
        public FactorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void updateList(List<Connection> data){
        connections = data;
        notifyDataSetChanged();
    }
}
