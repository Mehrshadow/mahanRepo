// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FragmentChargeOnlineGroupPackage$$ViewBinder<T extends ir.aspacrm.my.app.mahan.fragment.FragmentChargeOnlineGroupPackage> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624225, "field 'lstPackage'");
    target.lstPackage = finder.castView(view, 2131624225, "field 'lstPackage'");
    view = finder.findRequiredView(source, 2131624128, "field 'swipeRefreshLayout'");
    target.swipeRefreshLayout = finder.castView(view, 2131624128, "field 'swipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131624119, "field 'txtShowMessage'");
    target.txtShowMessage = finder.castView(view, 2131624119, "field 'txtShowMessage'");
    view = finder.findRequiredView(source, 2131624054, "field 'layLoading'");
    target.layLoading = finder.castView(view, 2131624054, "field 'layLoading'");
    view = finder.findRequiredView(source, 2131624052, "field 'layBtnClose'");
    target.layBtnClose = finder.castView(view, 2131624052, "field 'layBtnClose'");
    view = finder.findRequiredView(source, 2131624222, "field 'txtPageTitle'");
    target.txtPageTitle = finder.castView(view, 2131624222, "field 'txtPageTitle'");
  }

  @Override public void unbind(T target) {
    target.lstPackage = null;
    target.swipeRefreshLayout = null;
    target.txtShowMessage = null;
    target.layLoading = null;
    target.layBtnClose = null;
    target.txtPageTitle = null;
  }
}
