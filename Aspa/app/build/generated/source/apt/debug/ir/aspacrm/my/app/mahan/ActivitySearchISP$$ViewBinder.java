// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivitySearchISP$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivitySearchISP> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624117, "field 'lstISP'");
    target.lstISP = finder.castView(view, 2131624117, "field 'lstISP'");
    view = finder.findRequiredView(source, 2131624116, "field 'edtSearchISP'");
    target.edtSearchISP = finder.castView(view, 2131624116, "field 'edtSearchISP'");
    view = finder.findRequiredView(source, 2131624114, "field 'layBtnSearch'");
    target.layBtnSearch = finder.castView(view, 2131624114, "field 'layBtnSearch'");
    view = finder.findRequiredView(source, 2131624118, "field 'progressWaiting'");
    target.progressWaiting = finder.castView(view, 2131624118, "field 'progressWaiting'");
    view = finder.findRequiredView(source, 2131624119, "field 'txtShowMessage'");
    target.txtShowMessage = finder.castView(view, 2131624119, "field 'txtShowMessage'");
    view = finder.findRequiredView(source, 2131624063, "field 'txtShowErrorMessage'");
    target.txtShowErrorMessage = finder.castView(view, 2131624063, "field 'txtShowErrorMessage'");
  }

  @Override public void unbind(T target) {
    target.lstISP = null;
    target.edtSearchISP = null;
    target.layBtnSearch = null;
    target.progressWaiting = null;
    target.txtShowMessage = null;
    target.txtShowErrorMessage = null;
  }
}
