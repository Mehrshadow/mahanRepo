// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterFactor$FactorViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterFactor.FactorViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624295, "field 'txtFactorAmount'");
    target.txtFactorAmount = finder.castView(view, 2131624295, "field 'txtFactorAmount'");
    view = finder.findRequiredView(source, 2131624250, "field 'txtFactorCode'");
    target.txtFactorCode = finder.castView(view, 2131624250, "field 'txtFactorCode'");
    view = finder.findRequiredView(source, 2131624249, "field 'txtFactorDes'");
    target.txtFactorDes = finder.castView(view, 2131624249, "field 'txtFactorDes'");
    view = finder.findRequiredView(source, 2131624293, "field 'txtFactorDiscount'");
    target.txtFactorDiscount = finder.castView(view, 2131624293, "field 'txtFactorDiscount'");
    view = finder.findRequiredView(source, 2131624291, "field 'txtFactorEndDate'");
    target.txtFactorEndDate = finder.castView(view, 2131624291, "field 'txtFactorEndDate'");
    view = finder.findRequiredView(source, 2131624251, "field 'txtFactorStartDate'");
    target.txtFactorStartDate = finder.castView(view, 2131624251, "field 'txtFactorStartDate'");
    view = finder.findRequiredView(source, 2131624292, "field 'txtFactorPrice'");
    target.txtFactorPrice = finder.castView(view, 2131624292, "field 'txtFactorPrice'");
    view = finder.findRequiredView(source, 2131624294, "field 'txtFactorTax'");
    target.txtFactorTax = finder.castView(view, 2131624294, "field 'txtFactorTax'");
    view = finder.findRequiredView(source, 2131624290, "field 'txtFactorDate'");
    target.txtFactorDate = finder.castView(view, 2131624290, "field 'txtFactorDate'");
    view = finder.findRequiredView(source, 2131624297, "field 'layMoreDetail'");
    target.layMoreDetail = finder.castView(view, 2131624297, "field 'layMoreDetail'");
    view = finder.findRequiredView(source, 2131624296, "field 'layFactorDescription'");
    target.layFactorDescription = finder.castView(view, 2131624296, "field 'layFactorDescription'");
    view = finder.findRequiredView(source, 2131624209, "field 'layBtnStartFactor'");
    target.layBtnStartFactor = finder.castView(view, 2131624209, "field 'layBtnStartFactor'");
  }

  @Override public void unbind(T target) {
    target.txtFactorAmount = null;
    target.txtFactorCode = null;
    target.txtFactorDes = null;
    target.txtFactorDiscount = null;
    target.txtFactorEndDate = null;
    target.txtFactorStartDate = null;
    target.txtFactorPrice = null;
    target.txtFactorTax = null;
    target.txtFactorDate = null;
    target.layMoreDetail = null;
    target.layFactorDescription = null;
    target.layBtnStartFactor = null;
  }
}
