// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityShowFactorDetail$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityShowFactorDetail> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624052, "field 'layBtnClose'");
    target.layBtnClose = finder.castView(view, 2131624052, "field 'layBtnClose'");
    view = finder.findRequiredView(source, 2131624141, "field 'lstFactorDetail'");
    target.lstFactorDetail = finder.castView(view, 2131624141, "field 'lstFactorDetail'");
    view = finder.findRequiredView(source, 2131624055, "field 'layBtnBack'");
    target.layBtnBack = finder.castView(view, 2131624055, "field 'layBtnBack'");
  }

  @Override public void unbind(T target) {
    target.layBtnClose = null;
    target.lstFactorDetail = null;
    target.layBtnBack = null;
  }
}
