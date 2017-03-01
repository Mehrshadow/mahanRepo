// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterTicket$TicketViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterTicket.TicketViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624317, "field 'txtTicketTitle'");
    target.txtTicketTitle = finder.castView(view, 2131624317, "field 'txtTicketTitle'");
    view = finder.findRequiredView(source, 2131624315, "field 'txtTicketDate'");
    target.txtTicketDate = finder.castView(view, 2131624315, "field 'txtTicketDate'");
    view = finder.findRequiredView(source, 2131624318, "field 'txtTicketStatus'");
    target.txtTicketStatus = finder.castView(view, 2131624318, "field 'txtTicketStatus'");
    view = finder.findRequiredView(source, 2131624319, "field 'txtTicketPriority'");
    target.txtTicketPriority = finder.castView(view, 2131624319, "field 'txtTicketPriority'");
    view = finder.findRequiredView(source, 2131624313, "field 'cardTicketMain'");
    target.cardTicketMain = finder.castView(view, 2131624313, "field 'cardTicketMain'");
    view = finder.findRequiredView(source, 2131624297, "field 'layMoreDetail'");
    target.layMoreDetail = finder.castView(view, 2131624297, "field 'layMoreDetail'");
  }

  @Override public void unbind(T target) {
    target.txtTicketTitle = null;
    target.txtTicketDate = null;
    target.txtTicketStatus = null;
    target.txtTicketPriority = null;
    target.cardTicketMain = null;
    target.layMoreDetail = null;
  }
}
