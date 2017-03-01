// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityShowFeshfeshe$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityShowFeshfeshe> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624055, "field 'layBtnBack'");
    target.layBtnBack = finder.castView(view, 2131624055, "field 'layBtnBack'");
    view = finder.findRequiredView(source, 2131624148, "field 'lstFeshfeshe'");
    target.lstFeshfeshe = finder.castView(view, 2131624148, "field 'lstFeshfeshe'");
    view = finder.findRequiredView(source, 2131624128, "field 'swipeRefreshLayout'");
    target.swipeRefreshLayout = finder.castView(view, 2131624128, "field 'swipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131624119, "field 'txtShowMessage'");
    target.txtShowMessage = finder.castView(view, 2131624119, "field 'txtShowMessage'");
    view = finder.findRequiredView(source, 2131624146, "field 'txtCurrentFeshfesheExpireDate'");
    target.txtCurrentFeshfesheExpireDate = finder.castView(view, 2131624146, "field 'txtCurrentFeshfesheExpireDate'");
    view = finder.findRequiredView(source, 2131624147, "field 'txtCurrentFeshfesheTraffic'");
    target.txtCurrentFeshfesheTraffic = finder.castView(view, 2131624147, "field 'txtCurrentFeshfesheTraffic'");
    view = finder.findRequiredView(source, 2131624143, "field 'layCurrentFeshfeshe'");
    target.layCurrentFeshfeshe = finder.castView(view, 2131624143, "field 'layCurrentFeshfeshe'");
    view = finder.findRequiredView(source, 2131624144, "field 'imgEndCurrentFeshfesheRequest'");
    target.imgEndCurrentFeshfesheRequest = finder.castView(view, 2131624144, "field 'imgEndCurrentFeshfesheRequest'");
  }

  @Override public void unbind(T target) {
    target.layBtnBack = null;
    target.lstFeshfeshe = null;
    target.swipeRefreshLayout = null;
    target.txtShowMessage = null;
    target.txtCurrentFeshfesheExpireDate = null;
    target.txtCurrentFeshfesheTraffic = null;
    target.layCurrentFeshfeshe = null;
    target.imgEndCurrentFeshfesheRequest = null;
  }
}
