// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityShowCurrentService$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityShowCurrentService> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624132, "field 'tvPackageName'");
    target.tvPackageName = finder.castView(view, 2131624132, "field 'tvPackageName'");
    view = finder.findRequiredView(source, 2131624137, "field 'layTempConnection'");
    target.layTempConnection = finder.castView(view, 2131624137, "field 'layTempConnection'");
    view = finder.findRequiredView(source, 2131624324, "field 'btnTempConnection'");
    target.btnTempConnection = finder.castView(view, 2131624324, "field 'btnTempConnection'");
    view = finder.findRequiredView(source, 2131624136, "field 'layExpired'");
    target.layExpired = finder.castView(view, 2131624136, "field 'layExpired'");
    view = finder.findRequiredView(source, 2131624133, "field 'lay_remaining_days'");
    target.lay_remaining_days = finder.castView(view, 2131624133, "field 'lay_remaining_days'");
    view = finder.findRequiredView(source, 2131624135, "field 'tvRemainingDays'");
    target.tvRemainingDays = finder.castView(view, 2131624135, "field 'tvRemainingDays'");
    view = finder.findRequiredView(source, 2131624134, "field 'lblRemainingDays'");
    target.lblRemainingDays = finder.castView(view, 2131624134, "field 'lblRemainingDays'");
    view = finder.findRequiredView(source, 2131624138, "field 'tvTarazeMali'");
    target.tvTarazeMali = finder.castView(view, 2131624138, "field 'tvTarazeMali'");
    view = finder.findRequiredView(source, 2131624139, "field 'tvConnectionStatus'");
    target.tvConnectionStatus = finder.castView(view, 2131624139, "field 'tvConnectionStatus'");
    view = finder.findRequiredView(source, 2131624140, "field 'btnEnter'");
    target.btnEnter = finder.castView(view, 2131624140, "field 'btnEnter'");
    view = finder.findRequiredView(source, 2131624054, "field 'layLoading'");
    target.layLoading = finder.castView(view, 2131624054, "field 'layLoading'");
  }

  @Override public void unbind(T target) {
    target.tvPackageName = null;
    target.layTempConnection = null;
    target.btnTempConnection = null;
    target.layExpired = null;
    target.lay_remaining_days = null;
    target.tvRemainingDays = null;
    target.lblRemainingDays = null;
    target.tvTarazeMali = null;
    target.tvConnectionStatus = null;
    target.btnEnter = null;
    target.layLoading = null;
  }
}
