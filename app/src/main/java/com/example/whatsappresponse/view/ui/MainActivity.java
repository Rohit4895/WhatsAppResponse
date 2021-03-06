package com.example.whatsappresponse.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappresponse.R;
import com.example.whatsappresponse.service.model.MessageResponse;
import com.example.whatsappresponse.service.utils.InterfaceRepositories;
import com.example.whatsappresponse.view.adapter.MessageAdapter;
import com.example.whatsappresponse.viewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InterfaceRepositories.OnClickDelete {
    private RecyclerView recyclerView;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.mainRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        final MessageAdapter messageAdapter = new MessageAdapter(this, new ArrayList<MessageResponse>());
        recyclerView.setAdapter(messageAdapter);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getList().observe(this, new Observer<List<MessageResponse>>() {
            @Override
            public void onChanged(@Nullable List<MessageResponse> list) {
                messageAdapter.addList(list);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddKeyword.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mainActivityViewModel.getList().observe(this, new Observer<List<MessageResponse>>() {
            @Override
            public void onChanged(@Nullable List<MessageResponse> list) {

            }
        });
    }

    @Override
    public void deleteSelectedMessage(final int messageId) {
        deleteAlertDialogue(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainActivityViewModel.deleteMessage(messageId);
                Log.d("roh","deleted");
            }
        });

    }

    public void deleteAlertDialogue(DialogInterface.OnClickListener dialogInterface){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to delete this message..???");
        builder.setPositiveButton("Yes", dialogInterface);
        builder.setNegativeButton("Cancel",null);
        builder.create();
        builder.show();
    }
}
