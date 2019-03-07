package com.example.whatsappresponse.view.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

public class AddKeyword extends AppCompatActivity implements InterfaceRepositories.OnClickAddReplyMessage {
    private EditText receiveText, sentText;
    private Button addKey;
    private AddKeywordViewModel addKeywordViewModel;
    private RecyclerView recyclerView;
    private int size;
    private AddMessageAdapter addMessageAdapter;
    private List<MultiView> multiViewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);
        size=1;

        receiveText = findViewById(R.id.receiveEditText);
        sentText = findViewById(R.id.replyEditText);
        addKey = findViewById(R.id.addKey);
        recyclerView = findViewById(R.id.addMessageRecycler);

        multiViewsList = new ArrayList<MultiView>();
        multiViewsList.add(new MultiView("hi",1, EnumViewTypes.RECEIVED));
        multiViewsList.add(new MultiView("hello", 2,EnumViewTypes.REPLYADD));
       // multiViewsList.add(new MultiView("bye",2,EnumViewTypes.REPLYDELETE));

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        addMessageAdapter = new AddMessageAdapter(this, multiViewsList);
        recyclerView.setAdapter(addMessageAdapter);

        addKeywordViewModel = ViewModelProviders.of(this).get(AddKeywordViewModel.class);

        addKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receive = receiveText.getText().toString();
                String sent = sentText.getText().toString();

                addKeywordViewModel.insertMessages(receive,sent).observe(AddKeyword.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean success) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                });
            }
        });

    }


    @Override
    public void addReplyMessage() {
        multiViewsList.add(new MultiView("addedd",3,EnumViewTypes.REPLYDELETE));
        addMessageAdapter.addList(multiViewsList);
    }

    @Override
    public void deleteReplyMessage(int id) {
        multiViewsList.remove(id);
        addMessageAdapter.addList(multiViewsList);
    }
}
