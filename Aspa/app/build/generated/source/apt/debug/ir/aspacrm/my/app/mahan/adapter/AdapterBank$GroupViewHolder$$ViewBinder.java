// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterBank$GroupViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterBank.GroupViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624263, "field 'txtBankName'");
    target.txtBankName = finder.castView(view, 2131624263, "field 'txtBankName'");
    view = finder.findRequiredView(source, 2131624262, "field 'imgBankLogo'");
    target.imgBankLogo = finder.castView(view, 2131624262, "field 'imgBankLogo'");
    view = finder.findRequiredView(source, 2131624261, "field 'layMainBank'");
    target.layMainBank = finder.castView(view, 2131624261, "field 'layMainBank'");
  }

  @Override public void unbind(T target) {
    target.txtBankName = null;
    target.imgBankLogo = null;
    target.layMainBank = null;
  }
}
