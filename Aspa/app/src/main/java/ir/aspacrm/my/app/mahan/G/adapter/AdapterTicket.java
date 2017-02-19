package ir.aspacrm.my.app.mahan.G.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.G.G.events.EventOnClickedTicketItem;
import ir.aspacrm.my.app.mahan.G.G.gson.TicketResponse;

import java.util.List;

public class AdapterTicket extends RecyclerView.Adapter<AdapterTicket.TicketViewHolder> {

    List<TicketResponse> tickets;
    public AdapterTicket(List<TicketResponse> tickets) {
        this.tickets = tickets;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_ticket_item,parent,false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder holder, int position) {
        final TicketResponse ticket = tickets.get(position);
        holder.txtTicketTitle.setText("" + ticket.Title);
        holder.txtTicketDate.setText("" + ticket.Date);
        holder.txtTicketStatus.setText("" + ticket.State);
        holder.txtTicketPriority.setText("" + ticket.Priority);
        holder.layMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedTicketItem(ticket.Code));
            }
        });
    }
    @Override
    public int getItemCount() {
        return tickets.size();
    }
    public class TicketViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtTicketTitle) TextView txtTicketTitle;
        @Bind(R.id.txtTicketDate) TextView txtTicketDate;
        @Bind(R.id.txtTicketStatus) TextView txtTicketStatus;
        @Bind(R.id.txtTicketPriority) TextView txtTicketPriority;
        @Bind(R.id.cardTicketMain) CardView cardTicketMain;
        @Bind(R.id.layMoreDetail) LinearLayout layMoreDetail;
        public TicketViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void updateList(List<TicketResponse> data){
        tickets = data;
        notifyDataSetChanged();
    }
}
