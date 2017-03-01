// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterChargeOnlineGroup$GroupViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterChargeOnlineGroup.GroupViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624265, "field 'txtGroupName'");
    target.txtGroupName = finder.castView(view, 2131624265, "field 'txtGroupName'");
    view = finder.findRequiredView(source, 2131624264, "field 'layMainGroup'");
    target.layMainGroup = finder.castView(view, 2131624264, "field 'layMainGroup'");
  }

  @Override public void unbind(T target) {
    target.txtGroupName = null;
    target.layMainGroup = null;
  }
}
