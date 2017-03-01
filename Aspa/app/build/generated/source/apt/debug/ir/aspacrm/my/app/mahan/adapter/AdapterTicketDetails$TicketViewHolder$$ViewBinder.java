// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterTicketDetails$TicketViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterTicketDetails.TicketViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624314, "field 'txtTicketDescription'");
    target.txtTicketDescription = finder.castView(view, 2131624314, "field 'txtTicketDescription'");
    view = finder.findRequiredView(source, 2131624315, "field 'txtTicketDate'");
    target.txtTicketDate = finder.castView(view, 2131624315, "field 'txtTicketDate'");
    view = finder.findRequiredView(source, 2131624316, "field 'txtTicketState'");
    target.txtTicketState = finder.castView(view, 2131624316, "field 'txtTicketState'");
  }

  @Override public void unbind(T target) {
    target.txtTicketDescription = null;
    target.txtTicketDate = null;
    target.txtTicketState = null;
  }
}
