// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityShowConnections$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityShowConnections> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624130, "field 'lstConnection'");
    target.lstConnection = finder.castView(view, 2131624130, "field 'lstConnection'");
    view = finder.findRequiredView(source, 2131624052, "field 'layBtnClose'");
    target.layBtnClose = finder.castView(view, 2131624052, "field 'layBtnClose'");
    view = finder.findRequiredView(source, 2131624055, "field 'layBtnBack'");
    target.layBtnBack = finder.castView(view, 2131624055, "field 'layBtnBack'");
    view = finder.findRequiredView(source, 2131624119, "field 'txtShowMessage'");
    target.txtShowMessage = finder.castView(view, 2131624119, "field 'txtShowMessage'");
    view = finder.findRequiredView(source, 2131624128, "field 'swipeRefreshLayout'");
    target.swipeRefreshLayout = finder.castView(view, 2131624128, "field 'swipeRefreshLayout'");
  }

  @Override public void unbind(T target) {
    target.lstConnection = null;
    target.layBtnClose = null;
    target.layBtnBack = null;
    target.txtShowMessage = null;
    target.swipeRefreshLayout = null;
  }
}