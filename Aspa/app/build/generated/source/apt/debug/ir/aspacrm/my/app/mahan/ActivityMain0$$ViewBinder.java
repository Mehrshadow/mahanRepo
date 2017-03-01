// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityMain0$$ViewBinder<T extends ir.aspacrm.my.app.mahan.ActivityMain0> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624098, "field 'mask'");
    target.mask = finder.castView(view, 2131624098, "field 'mask'");
    view = finder.findRequiredView(source, 2131624099, "field 'bgMain'");
    target.bgMain = finder.castView(view, 2131624099, "field 'bgMain'");
  }

  @Override public void unbind(T target) {
    target.mask = null;
    target.bgMain = null;
  }
}
