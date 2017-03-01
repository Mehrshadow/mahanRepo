// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityShowClubScores$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityShowClubScores> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624052, "field 'layBtnClose'");
    target.layBtnClose = finder.castView(view, 2131624052, "field 'layBtnClose'");
    view = finder.findRequiredView(source, 2131624055, "field 'layBtnBack'");
    target.layBtnBack = finder.castView(view, 2131624055, "field 'layBtnBack'");
    view = finder.findRequiredView(source, 2131624129, "field 'lstClubScore'");
    target.lstClubScore = finder.castView(view, 2131624129, "field 'lstClubScore'");
    view = finder.findRequiredView(source, 2131624127, "field 'txtTotalClubScore'");
    target.txtTotalClubScore = finder.castView(view, 2131624127, "field 'txtTotalClubScore'");
    view = finder.findRequiredView(source, 2131624126, "field 'layTotalClubScore'");
    target.layTotalClubScore = finder.castView(view, 2131624126, "field 'layTotalClubScore'");
    view = finder.findRequiredView(source, 2131624119, "field 'txtShowMessage'");
    target.txtShowMessage = finder.castView(view, 2131624119, "field 'txtShowMessage'");
    view = finder.findRequiredView(source, 2131624128, "field 'swipeRefreshLayout'");
    target.swipeRefreshLayout = finder.castView(view, 2131624128, "field 'swipeRefreshLayout'");
  }

  @Override public void unbind(T target) {
    target.layBtnClose = null;
    target.layBtnBack = null;
    target.lstClubScore = null;
    target.txtTotalClubScore = null;
    target.layTotalClubScore = null;
    target.txtShowMessage = null;
    target.swipeRefreshLayout = null;
  }
}
