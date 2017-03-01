// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterISP$ISPViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterISP.ISPViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624058, "field 'txtIspName'");
    target.txtIspName = finder.castView(view, 2131624058, "field 'txtIspName'");
    view = finder.findRequiredView(source, 2131624303, "field 'layMainIspItem'");
    target.layMainIspItem = finder.castView(view, 2131624303, "field 'layMainIspItem'");
  }

  @Override public void unbind(T target) {
    target.txtIspName = null;
    target.layMainIspItem = null;
  }
}
