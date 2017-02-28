package ir.aspacrm.my.app.mahan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.gson.FactorDetailResponse;

import java.util.ArrayList;

public class AdapterFactorDetail extends RecyclerView.Adapter<AdapterFactorDetail.FactorViewHolder> {

    ArrayList<FactorDetailResponse> factorDetails;
    public AdapterFactorDetail(ArrayList<FactorDetailResponse> factorDetails) {
        this.factorDetails = factorDetails;
    }
    @Override
    public FactorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_factor_detail_item, parent, false);
        return new FactorViewHolder(view);
    }
    @Override
    public void onBindViewHolder(FactorViewHolder holder, int position) {
        final FactorDetailResponse factor = factorDetails.get(position);
        String name = factor.Name.length() == 0 ? "-" : "" + factor.Name;
        holder.txtFactorDetailTitle.setText(name);
        String price = factor.Price.length() == 0 ? "-" :"" + factor.Price + " تومان";
        holder.txtFactorDetailPrice.setText(price);
        if (factor.Des.length() != 0){
            holder.layDescription.setVisibility(View.VISIBLE);
            holder.txtFactorDetailDes.setText("" + factor.Des);
        }else{
            holder.layDescription.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return factorDetails.size();
    }

    public class FactorViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtFactorDetailTitle) TextView txtFactorDetailTitle;
        @Bind(R.id.txtFactorDetailPrice) TextView txtFactorDetailPrice;
        @Bind(R.id.txtFactorDetailDes) TextView txtFactorDetailDes;
        @Bind(R.id.layDescription) LinearLayout layDescription;
        public FactorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(ArrayList<FactorDetailResponse> data) {
        factorDetails = data;
        notifyDataSetChanged();
    }
}
