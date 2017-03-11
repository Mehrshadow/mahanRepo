package ir.aspacrm.my.app.mahan.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.ActivityShowSingleNews;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.component.PersianTextViewNormal;
import ir.aspacrm.my.app.mahan.component.PersianTextViewThin;
import ir.aspacrm.my.app.mahan.model.News;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.NewsViewHolder> {

    List<News> newses;

    public AdapterNews(List<News> newses) {
        this.newses = newses;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_news_item0, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final News news = newses.get(position);
        holder.txtNewsTitle.setText("" + news.title);
//        holder.txtNewsBodyText.setText("" + Html.fromHtml(news.bodyText));
        holder.txtNewsDate.setText("" + news.newsDate);
        if (!news.isSeen) {
//            holder.newsCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.circle_background_color));
            news.isSeen = true;
            news.save();
        } else {
//            holder.newsCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.dark_grey));
        }
//        holder.txtNewsImportant.setText("" + news.Title);
        holder.newsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showSingleNews = new Intent(G.currentActivity, ActivityShowSingleNews.class);
                showSingleNews.putExtra("title", "" + news.title);
                showSingleNews.putExtra("body", "" + news.bodyText);
                showSingleNews.putExtra("date", "" + news.newsDate);
                G.currentActivity.startActivity(showSingleNews);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtNewsTitle)
        PersianTextViewNormal txtNewsTitle;
        @Bind(R.id.txtNewsBodyText)
        PersianTextViewThin txtNewsBodyText;
        @Bind(R.id.txtNewsDate)
        PersianTextViewThin txtNewsDate;
        @Bind(R.id.newsCardView)
        CardView newsCardView;

        public NewsViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void updateList(List<News> data) {
        newses = data;
        notifyDataSetChanged();
    }
}
