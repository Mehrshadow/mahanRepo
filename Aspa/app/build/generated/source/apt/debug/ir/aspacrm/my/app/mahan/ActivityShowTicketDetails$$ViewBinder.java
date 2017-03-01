// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityShowTicketDetails$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityShowTicketDetails> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624153, "field 'lstTicketDetail'");
    target.lstTicketDetail = finder.castView(view, 2131624153, "field 'lstTicketDetail'");
    view = finder.findRequiredView(source, 2131624052, "field 'layBtnClose'");
    target.layBtnClose = finder.castView(view, 2131624052, "field 'layBtnClose'");
    view = finder.findRequiredView(source, 2131624055, "field 'layBtnBack'");
    target.layBtnBack = finder.castView(view, 2131624055, "field 'layBtnBack'");
    view = finder.findRequiredView(source, 2131624119, "field 'txtShowMessage'");
    target.txtShowMessage = finder.castView(view, 2131624119, "field 'txtShowMessage'");
    view = finder.findRequiredView(source, 2131624128, "field 'swipeRefreshLayout'");
    target.swipeRefreshLayout = finder.castView(view, 2131624128, "field 'swipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131624155, "field 'laySendChatMessage'");
    target.laySendChatMessage = finder.castView(view, 2131624155, "field 'laySendChatMessage'");
    view = finder.findRequiredView(source, 2131624154, "field 'edtTicketReplay'");
    target.edtTicketReplay = finder.castView(view, 2131624154, "field 'edtTicketReplay'");
  }

  @Override public void unbind(T target) {
    target.lstTicketDetail = null;
    target.layBtnClose = null;
    target.layBtnBack = null;
    target.txtShowMessage = null;
    target.swipeRefreshLayout = null;
    target.laySendChatMessage = null;
    target.edtTicketReplay = null;
  }
}
