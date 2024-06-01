package com.example.twoMountains.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twoMountains.R;
import com.example.twoMountains.adapter.CommonAdapter;
import com.example.twoMountains.adapter.ViewHolder;
import com.example.twoMountains.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class FAQFragment extends BaseFragment {

    private class FAQ {
        public FAQ(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String title;
        public String content;
    }


    private RecyclerView recycler;
    private List<FAQ> fAQs = new ArrayList<>();
    private CommonAdapter<FAQ> adapter = new CommonAdapter<FAQ>(R.layout.item_faqs, fAQs) {
        @Override
        public void bind(ViewHolder holder, FAQ fAQ, int position) {
            holder.setText(R.id.txt_FAQSTitle, fAQ.title);
            holder.setText(R.id.txt_FAQSContent, fAQ.content);
        }
    };

    public FAQFragment() {
    }

    public static FAQFragment newInstance() {
        FAQFragment fragment = new FAQFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

        fAQs.add(new FAQ("Aenean imperdiet Etiam ultricies nisi vel augue", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo.\nLorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo."));
        fAQs.add(new FAQ("Donec vitae sapien ut libero venenatis faucibus",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo.\nLorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo."));
        fAQs.add(new FAQ("Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo."));
        fAQs.add(new FAQ("Donec sodales sagittis magna", "Aenean commodo ligula eget dolor.Aenean massa.Cum sociis natoque penatibus etmagnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, utricies nec,pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pedejusto, fringilla vel, aliquet nec, vulputate eget, arcu. ln enim justo, rhoncus ut, imperdieta, venenatis vitae, justo." +
                "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.Donec quam felis, ultricies nec, pellentesque eu, pretium quis, serm. Nulla consequatmassa quis enim. Donec pede justo, fringila vel, aliquet nec, vulputate eget, arcu. lnenim justo, rhoncus ut, imperdiet a, venenatis vitae, justo.\n"));
        fAQs.add(new FAQ("Cum sociis natoque penatibus et magnis", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis,sem.Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. ln enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo."));

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
        return R.layout.fragment_faq;
    }
}