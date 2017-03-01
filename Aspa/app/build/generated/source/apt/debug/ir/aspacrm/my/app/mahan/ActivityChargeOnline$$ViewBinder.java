// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityChargeOnline$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityChargeOnline> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624055, "field 'layBtnBack'");
    target.layBtnBack = finder.castView(view, 2131624055, "field 'layBtnBack'");
    view = finder.findRequiredView(source, 2131624053, "field 'layFragment'");
    target.layFragment = finder.castView(view, 2131624053, "field 'layFragment'");
    view = finder.findRequiredView(source, 2131624054, "field 'layLoading'");
    target.layLoading = finder.castView(view, 2131624054, "field 'layLoading'");
  }

  @Override public void unbind(T target) {
    target.layBtnBack = null;
    target.layFragment = null;
    target.layLoading = null;
  }
}
