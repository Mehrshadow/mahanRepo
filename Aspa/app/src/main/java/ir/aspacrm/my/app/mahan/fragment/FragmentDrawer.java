package ir.aspacrm.my.app.mahan.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.adapter.AdapterDrawerRecycler;

/**
 * Created by HaMiD on 1/22/2017.
 */

public class FragmentDrawer extends Fragment {

    private View view;
    private RecyclerView RecyDrawer;
    List<String> title;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        initView();
        return view;
    }

    private void initView() {
        title = new ArrayList<>();
        title.add(getString(R.string.profile));
        title.add(getString(R.string.connectionReport));
        title.add(getString(R.string.onlineSharj));
        title.add(getString(R.string.payReport));
        title.add(getString(R.string.consumechart));
        title.add(getString(R.string.speedTest));
        title.add(getString(R.string.tickets));
        title.add(getString(R.string.inviteFriends));
        title.add(getString(R.string.pointReports));
        title.add(getString(R.string.messages));
        title.add(getString(R.string.games));
        title.add(getString(R.string.spesioansuggestion));
        title.add(getString(R.string.exit));

        RecyDrawer = (RecyclerView)view.findViewById(R.id.RecyDrawer);
        RecyDrawer.setLayoutManager(new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL, false));
        RecyDrawer.setHasFixedSize(true);

        AdapterDrawerRecycler  adapterDrawerRecycler = new AdapterDrawerRecycler(title,G.context);
        RecyDrawer.setAdapter(adapterDrawerRecycler);


    }
}
