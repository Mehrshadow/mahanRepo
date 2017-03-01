// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterConnection$FactorViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterConnection.FactorViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624277, "field 'txtConnectionStartDate'");
    target.txtConnectionStartDate = finder.castView(view, 2131624277, "field 'txtConnectionStartDate'");
    view = finder.findRequiredView(source, 2131624278, "field 'txtConnectionExpireDate'");
    target.txtConnectionExpireDate = finder.castView(view, 2131624278, "field 'txtConnectionExpireDate'");
    view = finder.findRequiredView(source, 2131624279, "field 'txtConnectionDuration'");
    target.txtConnectionDuration = finder.castView(view, 2131624279, "field 'txtConnectionDuration'");
    view = finder.findRequiredView(source, 2131624280, "field 'txtConnectionSend'");
    target.txtConnectionSend = finder.castView(view, 2131624280, "field 'txtConnectionSend'");
    view = finder.findRequiredView(source, 2131624281, "field 'txtConnectionReceive'");
    target.txtConnectionReceive = finder.castView(view, 2131624281, "field 'txtConnectionReceive'");
    view = finder.findRequiredView(source, 2131624282, "field 'txtConnectionTrafficUsed'");
    target.txtConnectionTrafficUsed = finder.castView(view, 2131624282, "field 'txtConnectionTrafficUsed'");
  }

  @Override public void unbind(T target) {
    target.txtConnectionStartDate = null;
    target.txtConnectionExpireDate = null;
    target.txtConnectionDuration = null;
    target.txtConnectionSend = null;
    target.txtConnectionReceive = null;
    target.txtConnectionTrafficUsed = null;
  }
}
