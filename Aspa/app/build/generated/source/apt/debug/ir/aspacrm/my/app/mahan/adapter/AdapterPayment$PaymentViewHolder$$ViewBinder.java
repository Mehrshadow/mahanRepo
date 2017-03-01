// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterPayment$PaymentViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterPayment.PaymentViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624310, "field 'txtPaymentDate'");
    target.txtPaymentDate = finder.castView(view, 2131624310, "field 'txtPaymentDate'");
    view = finder.findRequiredView(source, 2131624311, "field 'txtPaymentPrice'");
    target.txtPaymentPrice = finder.castView(view, 2131624311, "field 'txtPaymentPrice'");
    view = finder.findRequiredView(source, 2131624312, "field 'txtPaymentType'");
    target.txtPaymentType = finder.castView(view, 2131624312, "field 'txtPaymentType'");
  }

  @Override public void unbind(T target) {
    target.txtPaymentDate = null;
    target.txtPaymentPrice = null;
    target.txtPaymentType = null;
  }
}
