// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityShowTickets$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityShowTickets> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624158, "field 'lstTicket'");
    target.lstTicket = finder.castView(view, 2131624158, "field 'lstTicket'");
    view = finder.findRequiredView(source, 2131624052, "field 'layBtnClose'");
    target.layBtnClose = finder.castView(view, 2131624052, "field 'layBtnClose'");
    view = finder.findRequiredView(source, 2131624119, "field 'txtShowMessage'");
    target.txtShowMessage = finder.castView(view, 2131624119, "field 'txtShowMessage'");
    view = finder.findRequiredView(source, 2131624063, "field 'txtShowErrorMessage'");
    target.txtShowErrorMessage = finder.castView(view, 2131624063, "field 'txtShowErrorMessage'");
    view = finder.findRequiredView(source, 2131624128, "field 'swipeRefreshLayout'");
    target.swipeRefreshLayout = finder.castView(view, 2131624128, "field 'swipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131624159, "field 'actionBtnAddTicket'");
    target.actionBtnAddTicket = finder.castView(view, 2131624159, "field 'actionBtnAddTicket'");
    view = finder.findRequiredView(source, 2131624156, "field 'layExpandTicket'");
    target.layExpandTicket = finder.castView(view, 2131624156, "field 'layExpandTicket'");
    view = finder.findRequiredView(source, 2131624055, "field 'layBtnBack'");
    target.layBtnBack = finder.castView(view, 2131624055, "field 'layBtnBack'");
    view = finder.findRequiredView(source, 2131624157, "field 'layBtnNewTicket'");
    target.layBtnNewTicket = finder.castView(view, 2131624157, "field 'layBtnNewTicket'");
  }

  @Override public void unbind(T target) {
    target.lstTicket = null;
    target.layBtnClose = null;
    target.txtShowMessage = null;
    target.txtShowErrorMessage = null;
    target.swipeRefreshLayout = null;
    target.actionBtnAddTicket = null;
    target.layExpandTicket = null;
    target.layBtnBack = null;
    target.layBtnNewTicket = null;
  }
}
