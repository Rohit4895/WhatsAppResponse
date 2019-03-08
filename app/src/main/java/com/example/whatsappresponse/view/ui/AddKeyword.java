package com.example.whatsappresponse.view.ui;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsappresponse.R;
import com.example.whatsappresponse.service.model.EnumViewTypes;
import com.example.whatsappresponse.service.model.MultiView;
import com.example.whatsappresponse.service.utils.InterfaceRepositories;
import com.example.whatsappresponse.view.adapter.AddMessageAdapter;
import com.example.whatsappresponse.viewModel.AddKeywordViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddKeyword extends AppCompatActivity implements InterfaceRepositories.OnClickAddReplyMessage, View.OnClickListener {

    private Button addKey;
    private AddKeywordViewModel addKeywordViewModel;
    private RecyclerView recyclerView;
    private AddMessageAdapter addMessageAdapter;
    private List<MultiView> multiViewsList;
    private int multiViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);

        multiViewId=0;

        addKey = findViewById(R.id.addKey);
        recyclerView = findViewById(R.id.addMessageRecycler);

        multiViewsList = new ArrayList<MultiView>();
        multiViewsList.add(new MultiView("",10000, EnumViewTypes.RECEIVED));
        multiViewsList.add(new MultiView("", multiViewId,EnumViewTypes.REPLYADD));

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        addMessageAdapter = new AddMessageAdapter(this, multiViewsList);
        recyclerView.setAdapter(addMessageAdapter);

        addKeywordViewModel = ViewModelProviders.of(this).get(AddKeywordViewModel.class);

        addKey.setOnClickListener(this);

    }


    @Override
    public void addReplyMessage() {
        multiViewId++;
        Log.d("rohit","multiViewId Id: "+multiViewId);
        multiViewsList.add(new MultiView("",multiViewId,EnumViewTypes.REPLYDELETE));

        Collections.sort(multiViewsList, new Comparator<MultiView>() {
            @Override
            public int compare(MultiView right, MultiView left) {

                if (left.getId() > right.getId()) {

                    return 1;
                } else if (left.getId() < right.getId()) {

                    return -1;
                }
                return 0;
            }
        });

        for(MultiView multiView: multiViewsList){
            Log.d("roh"," Id: "+multiView.getId());
        }
        addMessageAdapter.addList(multiViewsList);

    }

    @Override
    public void deleteReplyMessage(int id) {
        multiViewsList.remove(id);
        addMessageAdapter.addList(multiViewsList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addKey:
                LiveData<Boolean> insertStatus = null;
                    String receive = multiViewsList.get(0).getMessage();
                    for (int i=1; i<multiViewsList.size();i++) {
                        String reply = multiViewsList.get(i).getMessage();

                       insertStatus = addKeywordViewModel.insertMessages(receive, reply);
                    }
                    insertStatus.observe(AddKeyword.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean success) {
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    });
                    break;
        }
    }
}
