// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterNotify$NewsViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterNotify.NewsViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624309, "field 'txtNotifyMessage'");
    target.txtNotifyMessage = finder.castView(view, 2131624309, "field 'txtNotifyMessage'");
    view = finder.findRequiredView(source, 2131624308, "field 'notifyCardView'");
    target.notifyCardView = finder.castView(view, 2131624308, "field 'notifyCardView'");
  }

  @Override public void unbind(T target) {
    target.txtNotifyMessage = null;
    target.notifyCardView = null;
  }
}
