// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterClubScore$ClubScoreViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterClubScore.ClubScoreViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624273, "field 'txtClubScoreTitle'");
    target.txtClubScoreTitle = finder.castView(view, 2131624273, "field 'txtClubScoreTitle'");
    view = finder.findRequiredView(source, 2131624274, "field 'txtClubScore'");
    target.txtClubScore = finder.castView(view, 2131624274, "field 'txtClubScore'");
    view = finder.findRequiredView(source, 2131624276, "field 'txtClubScoreDescription'");
    target.txtClubScoreDescription = finder.castView(view, 2131624276, "field 'txtClubScoreDescription'");
    view = finder.findRequiredView(source, 2131624275, "field 'layDescription'");
    target.layDescription = finder.castView(view, 2131624275, "field 'layDescription'");
  }

  @Override public void unbind(T target) {
    target.txtClubScoreTitle = null;
    target.txtClubScore = null;
    target.txtClubScoreDescription = null;
    target.layDescription = null;
  }
}
