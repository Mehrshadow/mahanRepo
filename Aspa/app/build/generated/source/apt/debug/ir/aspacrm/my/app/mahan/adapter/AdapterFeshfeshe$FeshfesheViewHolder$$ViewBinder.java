// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterFeshfeshe$FeshfesheViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterFeshfeshe.FeshfesheViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624299, "field 'txtFeshfesheName'");
    target.txtFeshfesheName = finder.castView(view, 2131624299, "field 'txtFeshfesheName'");
    view = finder.findRequiredView(source, 2131624300, "field 'layEndFeshfeshe'");
    target.layEndFeshfeshe = finder.castView(view, 2131624300, "field 'layEndFeshfeshe'");
    view = finder.findRequiredView(source, 2131624301, "field 'layStartFeshfeshe'");
    target.layStartFeshfeshe = finder.castView(view, 2131624301, "field 'layStartFeshfeshe'");
  }

  @Override public void unbind(T target) {
    target.txtFeshfesheName = null;
    target.layEndFeshfeshe = null;
    target.layStartFeshfeshe = null;
  }
}
