package com.example.whatsappresponse.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsappresponse.R;
import com.example.whatsappresponse.service.model.EnumViewTypes;
import com.example.whatsappresponse.service.model.MultiView;
import com.example.whatsappresponse.service.utils.EditTextOnFocused;
import com.example.whatsappresponse.service.utils.EditTextWatcher;
import com.example.whatsappresponse.service.utils.InterfaceRepositories;
import com.example.whatsappresponse.view.ui.AddKeyword;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener, InterfaceRepositories.CallBackTextWatcher {
    private Context context;
    private List<MultiView> multiViewsList;
    private EditTextWatcher editTextWatcher;
    private EditTextOnFocused editTextOnFocused;

    public AddMessageAdapter(Context context, List<MultiView> multiViewsList) {
        this.context = context;
        this.multiViewsList = multiViewsList;
        this.editTextWatcher = new EditTextWatcher(this);
        this.editTextOnFocused = new EditTextOnFocused(editTextWatcher);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

        switch (EnumViewTypes.values()[viewType]){
            case RECEIVED:
                view = LayoutInflater.from(context).inflate(R.layout.received,viewGroup,false);
                return new ViewHolderReceived(view);

            case REPLYADD:
                view = LayoutInflater.from(context).inflate(R.layout.reply_add, viewGroup,false);
                return new ViewHolderReplyAdd(view);

            case REPLYDELETE:
                view = LayoutInflater.from(context).inflate(R.layout.reply_delete, viewGroup, false);
                return new ViewHolderReplyDelete(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
       switch (multiViewsList.get(position).getEnumViewTypes()){
           case RECEIVED:
                   return EnumViewTypes.RECEIVED.ordinal();
           case REPLYADD:
                   return EnumViewTypes.REPLYADD.ordinal();
           case REPLYDELETE:
                   return EnumViewTypes.REPLYDELETE.ordinal();
               default:
                   return -1;
           }
    }

    public void addList(List<MultiView> multiViewsList){
        this.multiViewsList = multiViewsList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        MultiView multiView = multiViewsList.get(position);
        switch (EnumViewTypes.values()[viewHolder.getItemViewType()]){
            case RECEIVED:
                final ViewHolderReceived viewHolderReceived = (ViewHolderReceived) viewHolder;
                viewHolderReceived.imageViewReplyAdd.setTag(position);
                viewHolderReceived.imageViewReplyAdd.setOnClickListener(this);
                viewHolderReceived.editText.setTag(position);
                viewHolderReceived.editText.setOnFocusChangeListener(editTextOnFocused);
                viewHolderReceived.editText.setText(multiView.getMessage());
                break;
            case REPLYADD:
                final ViewHolderReplyAdd viewHolderReplyAdd = (ViewHolderReplyAdd) viewHolder;
                viewHolderReplyAdd.editText.setTag(position);
                viewHolderReplyAdd.editText.setOnFocusChangeListener(editTextOnFocused);
                viewHolderReplyAdd.editText.setText(multiView.getMessage());
                break;
            case REPLYDELETE:
                final ViewHolderReplyDelete viewHolderReplyDelete = (ViewHolderReplyDelete) viewHolder;
                viewHolderReplyDelete.imageViewReplyDelete.setTag(position);
                viewHolderReplyDelete.imageViewReplyDelete.setOnClickListener(this);
                viewHolderReplyDelete.editText.setTag(position);
                viewHolderReplyDelete.editText.setOnFocusChangeListener(editTextOnFocused);
                viewHolderReplyDelete.editText.setText(multiView.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return multiViewsList.size();
    }

    @Override
    public void onClick(View v) {

        int position = Integer.parseInt(String.valueOf(v.getTag()));
        switch (v.getId()){
            case R.id.addReplyIcon:
                ((AddKeyword)context).addReplyMessage();
                break;

            case R.id.deleteIcon:
                ((AddKeyword)context).deleteReplyMessage(position);
                break;
        }

    }

    @Override
    public void callback(int position, String message) {
        MultiView multiView = multiViewsList.get(position);
        multiView.setMessage(message);
    }

    public static class ViewHolderReceived extends RecyclerView.ViewHolder {
        private ImageView imageViewReplyAdd;
        private EditText editText;

        public ViewHolderReceived(@NonNull View itemView) {
            super(itemView);
            this.editText = (EditText) itemView.findViewById(R.id.receiveEditText);
            this.imageViewReplyAdd = (ImageView) itemView.findViewById(R.id.addReplyIcon);
        }

    }

    public static class ViewHolderReplyAdd extends RecyclerView.ViewHolder {

        private EditText editText;


        public ViewHolderReplyAdd(@NonNull View itemView) {
            super(itemView);
            this.editText = (EditText) itemView.findViewById(R.id.replyEditTextAdd);
        }
    }

    public static class ViewHolderReplyDelete extends RecyclerView.ViewHolder {
        private ImageView imageViewReplyDelete;
        private EditText editText;

        public ViewHolderReplyDelete(@NonNull View itemView) {
            super(itemView);
            this.imageViewReplyDelete = (ImageView)itemView.findViewById(R.id.deleteIcon);
            this.editText = (EditText) itemView.findViewById(R.id.replyEditTextDelete);
        }
    }
}
