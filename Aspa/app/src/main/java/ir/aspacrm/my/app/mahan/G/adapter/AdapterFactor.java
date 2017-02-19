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
import ir.aspacrm.my.app.mahan.G.R;
import ir.aspacrm.my.app.mahan.G.classes.Logger;
import ir.aspacrm.my.app.mahan.G.enums.EnumFactorStatus;
import ir.aspacrm.my.app.mahan.G.events.EventOnClickedFactorMoreDetail;
import ir.aspacrm.my.app.mahan.G.events.EventOnClickedStartFactor;
import ir.aspacrm.my.app.mahan.G.model.Factor;

import java.util.List;

public class AdapterFactor extends RecyclerView.Adapter<AdapterFactor.FactorViewHolder> {

    List<Factor> factors;
    public AdapterFactor(List<Factor> factors) {
        this.factors = factors;
    }

    @Override
    public FactorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_factor_item,parent,false);
        return new FactorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FactorViewHolder holder, int position) {
        final Factor factor = factors.get(position);
        holder.txtFactorAmount.setText("" + factor.amount + " تومان");
        holder.txtFactorCode.setText("" + factor.factorId);
        if(factor.des.length() == 0){
            holder.layFactorDescription.setVisibility(View.GONE);
        }else{
            holder.layFactorDescription.setVisibility(View.VISIBLE);
            holder.txtFactorDes.setText("" + factor.des);
        }
        holder.txtFactorDiscount.setText("" + factor.discount + " تومان");
        holder.txtFactorDate.setText("" + factor.date);
        holder.txtFactorEndDate.setText("" + factor.expireDate);
        holder.txtFactorStartDate.setText("" + factor.startDate);
        holder.txtFactorTax.setText("" + factor.tax + " تومان");
        holder.txtFactorPrice.setText("" + factor.price + " تومان");

        holder.layMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedFactorMoreDetail(factor.factorId));
            }
        });

       if(factor.status == EnumFactorStatus.NOT_START){
           holder.layBtnStartFactor.setVisibility(View.VISIBLE);
           holder.layBtnStartFactor.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   EventBus.getDefault().post(new EventOnClickedStartFactor(factor.factorId));
               }
           });
       }else{
           holder.layBtnStartFactor.setVisibility(View.GONE);
       }
    }

    @Override
    public int getItemCount() {
        return factors.size();
    }

    public class FactorViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtFactorAmount) TextView txtFactorAmount;
        @Bind(R.id.txtFactorCode) TextView txtFactorCode;
        @Bind(R.id.txtFactorDes) TextView txtFactorDes;
        @Bind(R.id.txtFactorDiscount) TextView txtFactorDiscount;
        @Bind(R.id.txtFactorEndDate) TextView txtFactorEndDate;
        @Bind(R.id.txtFactorStartDate) TextView txtFactorStartDate;
        @Bind(R.id.txtFactorPrice) TextView txtFactorPrice;
        @Bind(R.id.txtFactorTax) TextView txtFactorTax;
        @Bind(R.id.txtFactorDate) TextView txtFactorDate;
        @Bind(R.id.layMoreDetail) LinearLayout layMoreDetail;
        @Bind(R.id.layFactorDescription) LinearLayout layFactorDescription;
        @Bind(R.id.layBtnStartFactor) CardView layBtnStartFactor;
        public FactorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void updateList(List<Factor> data){
        factors = data;
        notifyDataSetChanged();
        Logger.d("AdapterPayment : payments size is " + factors.size());
    }
}
