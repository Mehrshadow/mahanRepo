package ir.aspacrm.my.app.mahan.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.List;

import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.component.PersianTextViewNormal;
import ir.aspacrm.my.app.mahan.component.PersianTextViewThin;
import ir.aspacrm.my.app.mahan.model.News;

public class AdapterSingleNews extends RecyclerView.Adapter<AdapterSingleNews.NewsViewHolder> {

    List<News> newses;

    public AdapterSingleNews(List<News> newses) {
        this.newses = newses;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.currentActivity).inflate(R.layout.l_news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final News news = newses.get(position);
        holder.txtNewsTitle.setText("" + news.title);
        holder.webView.loadDataWithBaseURL("", news.bodyText, "text/html", "utf-8", "");
        holder.txtNewsDate.setText("" + news.newsDate);
        if (!news.isSeen) {
//            holder.newsCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.circle_background_color));
            news.isSeen = true;
            news.save();
        } else {
//            holder.newsCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.dark_grey));
        }
//        holder.txtNewsImportant.setText("" + news.Title);
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        //        @Bind(R.id.txtNewsTitle)
        PersianTextViewNormal txtNewsTitle;
        //        @Bind(R.id.txtNewsDate)
        PersianTextViewThin txtNewsDate;
        //        @Bind(R.id.newsCardView)
        CardView newsCardView;
        //        @Bind(R.id.web)
        WebView webView;

        //        @Bind(R.id.txtNewsImportant) PersianTextViewThin txtNewsImportant;
        public NewsViewHolder(final View itemView) {
            super(itemView);
//            ButterKnife.bind(G.currentActivity, itemView);

            txtNewsTitle = (PersianTextViewNormal)itemView.findViewById(R.id.txtNewsTitle);
            txtNewsDate = (PersianTextViewThin) itemView.findViewById(R.id.txtNewsDate);
            newsCardView = (CardView) itemView.findViewById(R.id.newsCardView);
            webView = (WebView) itemView.findViewById(R.id.web);

            webView.getSettings().setJavaScriptEnabled(true);
        }
    }

    public void updateList(List<News> data) {
        newses = data;
        notifyDataSetChanged();
    }
}
