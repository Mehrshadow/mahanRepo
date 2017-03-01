// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterChargeOnlineGroupPackage$PackageViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterChargeOnlineGroupPackage.PackageViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624266, "field 'txtPackageName'");
    target.txtPackageName = finder.castView(view, 2131624266, "field 'txtPackageName'");
    view = finder.findRequiredView(source, 2131624267, "field 'txtPackageScore'");
    target.txtPackageScore = finder.castView(view, 2131624267, "field 'txtPackageScore'");
    view = finder.findRequiredView(source, 2131624145, "field 'txtPackagePrice'");
    target.txtPackagePrice = finder.castView(view, 2131624145, "field 'txtPackagePrice'");
    view = finder.findRequiredView(source, 2131624269, "field 'txtPackagePreScore'");
    target.txtPackagePreScore = finder.castView(view, 2131624269, "field 'txtPackagePreScore'");
    view = finder.findRequiredView(source, 2131624270, "field 'txtPackageTakhfif'");
    target.txtPackageTakhfif = finder.castView(view, 2131624270, "field 'txtPackageTakhfif'");
    view = finder.findRequiredView(source, 2131624271, "field 'layTakhfif'");
    target.layTakhfif = finder.castView(view, 2131624271, "field 'layTakhfif'");
    view = finder.findRequiredView(source, 2131624268, "field 'layPreScore'");
    target.layPreScore = finder.castView(view, 2131624268, "field 'layPreScore'");
    view = finder.findRequiredView(source, 2131624272, "field 'layBtnBuy'");
    target.layBtnBuy = finder.castView(view, 2131624272, "field 'layBtnBuy'");
  }

  @Override public void unbind(T target) {
    target.txtPackageName = null;
    target.txtPackageScore = null;
    target.txtPackagePrice = null;
    target.txtPackagePreScore = null;
    target.txtPackageTakhfif = null;
    target.layTakhfif = null;
    target.layPreScore = null;
    target.layBtnBuy = null;
  }
}
