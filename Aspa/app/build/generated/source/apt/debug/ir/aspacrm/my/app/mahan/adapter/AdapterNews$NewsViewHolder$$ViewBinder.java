// Generated code from Butter Knife. Do not modify!
package ir.aspacrm.my.app.mahan.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AdapterNews$NewsViewHolder$$ViewBinder<T extends ir.aspacrm.my.app.mahan.adapter.AdapterNews.NewsViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624305, "field 'txtNewsTitle'");
    target.txtNewsTitle = finder.castView(view, 2131624305, "field 'txtNewsTitle'");
    view = finder.findRequiredView(source, 2131624306, "field 'txtNewsBodyText'");
    target.txtNewsBodyText = finder.castView(view, 2131624306, "field 'txtNewsBodyText'");
    view = finder.findRequiredView(source, 2131624307, "field 'txtNewsDate'");
    target.txtNewsDate = finder.castView(view, 2131624307, "field 'txtNewsDate'");
    view = finder.findRequiredView(source, 2131624304, "field 'newsCardView'");
    target.newsCardView = finder.castView(view, 2131624304, "field 'newsCardView'");
  }

  @Override public void unbind(T target) {
    target.txtNewsTitle = null;
    target.txtNewsBodyText = null;
    target.txtNewsDate = null;
    target.newsCardView = null;
  }
}
