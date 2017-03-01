// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FragmentSendTicket$$ViewBinder<T extends ir.aspacrm.my.app.mahan.fragment.FragmentSendTicket> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624124, "field 'spOlaviat'");
    target.spOlaviat = finder.castView(view, 2131624124, "field 'spOlaviat'");
    view = finder.findRequiredView(source, 2131624123, "field 'spVahed'");
    target.spVahed = finder.castView(view, 2131624123, "field 'spVahed'");
    view = finder.findRequiredView(source, 2131624125, "field 'layBtnSendTicket'");
    target.layBtnSendTicket = finder.castView(view, 2131624125, "field 'layBtnSendTicket'");
    view = finder.findRequiredView(source, 2131624121, "field 'edtTicketSubject'");
    target.edtTicketSubject = finder.castView(view, 2131624121, "field 'edtTicketSubject'");
    view = finder.findRequiredView(source, 2131624122, "field 'edtTicketDescription'");
    target.edtTicketDescription = finder.castView(view, 2131624122, "field 'edtTicketDescription'");
    view = finder.findRequiredView(source, 2131624320, "field 'txtBtnSendTicket'");
    target.txtBtnSendTicket = finder.castView(view, 2131624320, "field 'txtBtnSendTicket'");
    view = finder.findRequiredView(source, 2131624063, "field 'txtShowErrorMessage'");
    target.txtShowErrorMessage = finder.castView(view, 2131624063, "field 'txtShowErrorMessage'");
  }

  @Override public void unbind(T target) {
    target.spOlaviat = null;
    target.spVahed = null;
    target.layBtnSendTicket = null;
    target.edtTicketSubject = null;
    target.edtTicketDescription = null;
    target.txtBtnSendTicket = null;
    target.txtShowErrorMessage = null;
  }
}
