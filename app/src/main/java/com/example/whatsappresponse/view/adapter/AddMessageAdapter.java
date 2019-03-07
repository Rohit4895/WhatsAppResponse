package com.example.whatsappresponse.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.whatsappresponse.R;
import com.example.whatsappresponse.service.model.EnumViewTypes;
import com.example.whatsappresponse.service.model.MultiView;
import com.example.whatsappresponse.view.ui.AddKeyword;

import java.util.List;

public class AddMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<MultiView> multiViewsList;

    public AddMessageAdapter(Context context, List<MultiView> multiViewsList) {
        this.context = context;
        this.multiViewsList = multiViewsList;
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
        switch (viewHolder.getItemViewType()){
            case 0:
                ViewHolderReceived viewHolderReceived = (ViewHolderReceived) viewHolder;
                break;
            case 1:
                ViewHolderReplyAdd viewHolderReplyAdd = (ViewHolderReplyAdd) viewHolder;
                viewHolderReplyAdd.imageViewReplyAdd.setTag(position);
                viewHolderReplyAdd.imageViewReplyAdd.setOnClickListener(this);
                break;
            case 2:
                ViewHolderReplyDelete viewHolderReplyDelete = (ViewHolderReplyDelete) viewHolder;
                viewHolderReplyDelete.imageViewReplyDelete.setTag(position);
                viewHolderReplyDelete.imageViewReplyDelete.setOnClickListener(this);
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


    public static class ViewHolderReceived extends RecyclerView.ViewHolder {


        public ViewHolderReceived(@NonNull View itemView) {
            super(itemView);
        }

    }

    public static class ViewHolderReplyAdd extends RecyclerView.ViewHolder {
        private ImageView imageViewReplyAdd;


        public ViewHolderReplyAdd(@NonNull View itemView) {
            super(itemView);
            imageViewReplyAdd = (ImageView) itemView.findViewById(R.id.addReplyIcon);
        }
    }

    public static class ViewHolderReplyDelete extends RecyclerView.ViewHolder {
        private ImageView imageViewReplyDelete;

        public ViewHolderReplyDelete(@NonNull View itemView) {
            super(itemView);
            imageViewReplyDelete = (ImageView)itemView.findViewById(R.id.deleteIcon);
        }
    }
}
