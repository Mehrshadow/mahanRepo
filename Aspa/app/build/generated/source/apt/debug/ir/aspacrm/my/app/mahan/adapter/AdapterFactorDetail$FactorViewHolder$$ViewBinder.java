// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterFactorDetail$FactorViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterFactorDetail.FactorViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624286, "field 'txtFactorDetailTitle'");
    target.txtFactorDetailTitle = finder.castView(view, 2131624286, "field 'txtFactorDetailTitle'");
    view = finder.findRequiredView(source, 2131624289, "field 'txtFactorDetailPrice'");
    target.txtFactorDetailPrice = finder.castView(view, 2131624289, "field 'txtFactorDetailPrice'");
    view = finder.findRequiredView(source, 2131624288, "field 'txtFactorDetailDes'");
    target.txtFactorDetailDes = finder.castView(view, 2131624288, "field 'txtFactorDetailDes'");
    view = finder.findRequiredView(source, 2131624275, "field 'layDescription'");
    target.layDescription = finder.castView(view, 2131624275, "field 'layDescription'");
  }

  @Override public void unbind(T target) {
    target.txtFactorDetailTitle = null;
    target.txtFactorDetailPrice = null;
    target.txtFactorDetailDes = null;
    target.layDescription = null;
  }
}
