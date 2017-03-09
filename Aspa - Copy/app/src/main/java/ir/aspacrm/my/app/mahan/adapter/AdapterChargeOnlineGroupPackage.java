package ir.aspacrm.my.app.mahan.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.events.EventOnClickedChargeOnlineGroupPackage;
import ir.aspacrm.my.app.mahan.gson.ChargeOnlineGroupPackage;

public class AdapterChargeOnlineGroupPackage extends RecyclerView.Adapter<AdapterChargeOnlineGroupPackage.PackageViewHolder> {

    List<ChargeOnlineGroupPackage> packages;
    int isClub;
    public AdapterChargeOnlineGroupPackage(List<ChargeOnlineGroupPackage> packages, int isClub) {
        this.packages = packages;
        this.isClub = isClub;
    }
    @Override
    public PackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_charge_online_package_item, parent, false);
        return new PackageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(PackageViewHolder holder, int position) {
        final ChargeOnlineGroupPackage pack = packages.get(position);
        holder.txtPackageName.setText("" + pack.name);
        holder.txtPackageScore.setText(" " + pack.score);
        holder.txtPackagePrice.setText("" + G.formatterPrice.format(pack.price) + " تومان");
        if(isClub == 1){
            holder.txtPackageTakhfif.setText("" + G.formatterPrice.format(pack.discount) + " تومان");
            holder.txtPackagePreScore.setText("" + pack.preScore);
        }else{
            holder.layPreScore.setVisibility(View.GONE);
            holder.layTakhfif.setVisibility(View.GONE);
        }
        holder.layBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedChargeOnlineGroupPackage(pack.code));
            }
        });
    }
    @Override
    public int getItemCount() {
        return packages.size();
    }
    public class PackageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtPackageName) TextView txtPackageName;
        @Bind(R.id.txtPackageScore) TextView txtPackageScore;
        @Bind(R.id.txtPackagePrice) TextView txtPackagePrice;
        @Bind(R.id.txtPackagePreScore) TextView txtPackagePreScore;
        @Bind(R.id.txtPackageTakhfif) TextView txtPackageTakhfif;
        @Bind(R.id.layTakhfif) LinearLayout layTakhfif;
        @Bind(R.id.layPreScore) LinearLayout layPreScore;
        @Bind(R.id.layBtnBuy) CardView layBtnBuy;
        public PackageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void updateList(List<ChargeOnlineGroupPackage> data) {
        packages = data;
        notifyDataSetChanged();
    }
}
