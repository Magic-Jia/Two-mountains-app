package com.example.ble.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ble.R;
import com.example.ble.adapter.CommonAdapter;
import com.example.ble.adapter.ViewHolder;
import com.example.ble.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class HelpTopicsFragment extends BaseFragment {

    private class HelpTopic {
        public HelpTopic(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String title;
        public String content;
    }


    private RecyclerView recycler;
    private List<HelpTopic> helpTopics = new ArrayList<>();
    private CommonAdapter<HelpTopic> adapter = new CommonAdapter<HelpTopic>(R.layout.item_help_topics, helpTopics) {
        @Override
        public void bind(ViewHolder holder, HelpTopic helpTopic, int position) {
            holder.setText(R.id.txt_helpTitle, helpTopic.title);
            holder.setText(R.id.txt_helpContent, helpTopic.content);
        }
    };

    public HelpTopicsFragment() {
    }

    public static HelpTopicsFragment newInstance() {
        HelpTopicsFragment fragment = new HelpTopicsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

        helpTopics.add(new HelpTopic("Aenean imperdiet Etiam ultricies nisi vel augue", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo.\nLorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo."));
        helpTopics.add(new HelpTopic("Donec vitae sapien ut libero venenatis faucibus",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo.\nLorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo."));
        helpTopics.add(new HelpTopic("Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo."));
        helpTopics.add(new HelpTopic("Donec sodales sagittis magna", "Aenean commodo ligula eget dolor.Aenean massa.Cum sociis natoque penatibus etmagnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, utricies nec,pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pedejusto, fringilla vel, aliquet nec, vulputate eget, arcu. ln enim justo, rhoncus ut, imperdieta, venenatis vitae, justo." +
                "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.Donec quam felis, ultricies nec, pellentesque eu, pretium quis, serm. Nulla consequatmassa quis enim. Donec pede justo, fringila vel, aliquet nec, vulputate eget, arcu. lnenim justo, rhoncus ut, imperdiet a, venenatis vitae, justo.\n"));
        helpTopics.add(new HelpTopic("Cum sociis natoque penatibus et magnis", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis,sem.Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. ln enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo."));

        recycler.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void findViewsById(View view) {
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_help_topics;
    }
}