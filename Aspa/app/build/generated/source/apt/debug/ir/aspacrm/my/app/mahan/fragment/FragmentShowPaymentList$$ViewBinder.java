// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FragmentShowPaymentList$$ViewBinder<T extends ir.aspacrm.my.app.mahan.fragment.FragmentShowPaymentList> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624259, "field 'lstPayment'");
    target.lstPayment = finder.castView(view, 2131624259, "field 'lstPayment'");
    view = finder.findRequiredView(source, 2131624052, "field 'layBtnClose'");
    target.layBtnClose = finder.castView(view, 2131624052, "field 'layBtnClose'");
    view = finder.findRequiredView(source, 2131624055, "field 'layBtnBack'");
    target.layBtnBack = finder.castView(view, 2131624055, "field 'layBtnBack'");
    view = finder.findRequiredView(source, 2131624054, "field 'layLoading'");
    target.layLoading = finder.castView(view, 2131624054, "field 'layLoading'");
    view = finder.findRequiredView(source, 2131624119, "field 'txtShowMessage'");
    target.txtShowMessage = finder.castView(view, 2131624119, "field 'txtShowMessage'");
    view = finder.findRequiredView(source, 2131624063, "field 'txtShowErrorMessage'");
    target.txtShowErrorMessage = finder.castView(view, 2131624063, "field 'txtShowErrorMessage'");
    view = finder.findRequiredView(source, 2131624128, "field 'swipeRefreshLayout'");
    target.swipeRefreshLayout = finder.castView(view, 2131624128, "field 'swipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131624260, "field 'actionBtnPayment'");
    target.actionBtnPayment = finder.castView(view, 2131624260, "field 'actionBtnPayment'");
    view = finder.findRequiredView(source, 2131624152, "field 'layExpandPayment'");
    target.layExpandPayment = finder.castView(view, 2131624152, "field 'layExpandPayment'");
    view = finder.findRequiredView(source, 2131624326, "field 'edtPayment'");
    target.edtPayment = finder.castView(view, 2131624326, "field 'edtPayment'");
    view = finder.findRequiredView(source, 2131624325, "field 'layBtnPay'");
    target.layBtnPay = finder.castView(view, 2131624325, "field 'layBtnPay'");
  }

  @Override public void unbind(T target) {
    target.lstPayment = null;
    target.layBtnClose = null;
    target.layBtnBack = null;
    target.layLoading = null;
    target.txtShowMessage = null;
    target.txtShowErrorMessage = null;
    target.swipeRefreshLayout = null;
    target.actionBtnPayment = null;
    target.layExpandPayment = null;
    target.edtPayment = null;
    target.layBtnPay = null;
  }
}
