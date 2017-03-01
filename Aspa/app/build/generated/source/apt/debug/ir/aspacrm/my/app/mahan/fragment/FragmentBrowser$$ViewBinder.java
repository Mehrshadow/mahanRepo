// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FragmentBrowser$$ViewBinder<T extends ir.aspacrm.my.app.mahan.fragment.FragmentBrowser> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624223, "field 'webView'");
    target.webView = finder.castView(view, 2131624223, "field 'webView'");
    view = finder.findRequiredView(source, 2131624054, "field 'layLoading'");
    target.layLoading = finder.castView(view, 2131624054, "field 'layLoading'");
    view = finder.findRequiredView(source, 2131624052, "field 'layBtnClose'");
    target.layBtnClose = finder.castView(view, 2131624052, "field 'layBtnClose'");
  }

  @Override public void unbind(T target) {
    target.webView = null;
    target.layLoading = null;
    target.layBtnClose = null;
  }
}
