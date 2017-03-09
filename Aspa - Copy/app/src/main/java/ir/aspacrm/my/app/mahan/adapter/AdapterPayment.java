package ir.aspacrm.my.app.mahan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.enums.EnumPaymentType;
import ir.aspacrm.my.app.mahan.model.Payment;
public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.PaymentViewHolder> {

    List<Payment> payments;
    public AdapterPayment(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_payment_item,parent,false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        final Payment payment = payments.get(position);
        holder.txtPaymentDate.setText(payment.date);
        holder.txtPaymentPrice.setText(payment.price);
        switch (payment.type) {
            case EnumPaymentType.Cash :
                holder.txtPaymentType.setText("نقدی");
                break;
            case EnumPaymentType.ChargeOnline :
                holder.txtPaymentType.setText("شارژ آنلاین");
                break;
            case EnumPaymentType.PayOnline :
                holder.txtPaymentType.setText("پرداخت آنلاین");
                break;
            default:
                holder.txtPaymentType.setText(" - ");
                break;
        }
    }
    @Override
    public int getItemCount() {
        return payments.size();
    }
    public class PaymentViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtPaymentDate) TextView txtPaymentDate;
        @Bind(R.id.txtPaymentPrice) TextView txtPaymentPrice;
        @Bind(R.id.txtPaymentType) TextView txtPaymentType;
        public PaymentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void updateList(List<Payment> data){
        payments = data;
        notifyDataSetChanged();
        Logger.d("AdapterPayment : payments size is " + payments.size());
    }
}
