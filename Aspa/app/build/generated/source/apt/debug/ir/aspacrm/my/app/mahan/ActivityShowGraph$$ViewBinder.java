// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityShowGraph$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityShowGraph> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624149, "field 'mChart'");
    target.mChart = finder.castView(view, 2131624149, "field 'mChart'");
    view = finder.findRequiredView(source, 2131624052, "field 'layBtnClose'");
    target.layBtnClose = finder.castView(view, 2131624052, "field 'layBtnClose'");
    view = finder.findRequiredView(source, 2131624055, "field 'layBtnBack'");
    target.layBtnBack = finder.castView(view, 2131624055, "field 'layBtnBack'");
    view = finder.findRequiredView(source, 2131624119, "field 'txtShowMessage'");
    target.txtShowMessage = finder.castView(view, 2131624119, "field 'txtShowMessage'");
    view = finder.findRequiredView(source, 2131624054, "field 'layLoading'");
    target.layLoading = finder.castView(view, 2131624054, "field 'layLoading'");
  }

  @Override public void unbind(T target) {
    target.mChart = null;
    target.layBtnClose = null;
    target.layBtnBack = null;
    target.txtShowMessage = null;
    target.layLoading = null;
  }
}
