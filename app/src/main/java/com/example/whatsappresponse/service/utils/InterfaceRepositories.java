package com.example.whatsappresponse.service.utils;

import com.example.whatsappresponse.service.model.MessageResponse;

import java.util.List;

public class InterfaceRepositories {
    public interface CallBackToGetMessagList{
        void getMessageList(List<MessageResponse> list);
    }

    public interface CallBackForInsertion{
        void insertionCompleted(long id);
    }

    public interface CallBackForDeletion{
        void deletionCompleted(int id);
    }

    public interface OnClickDelete{
        void deleteSelectedMessage(int id);
    }

    public interface OnClickAddReplyMessage{
        void addReplyMessage();
        void deleteReplyMessage(int id);
    }

    public interface CallBackTextWatcher {

        void callback(int position, String message);
    }

}
