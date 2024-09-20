package com.example.phonestore.OBJ;

public class Message
{
    public String idsend, idreceiver, mess, time;

    public Message() {
    }

    public Message(String idsend, String idreceiver, String mess, String time)
    {
        this.idsend = idsend;
        this.idreceiver = idreceiver;
        this.mess = mess;
        this.time = time;
    }

    public String getIdsend() {
        return idsend;
    }

    public void setIdsend(String idsend) {
        this.idsend = idsend;
    }

    public String getIdreceiver() {
        return idreceiver;
    }

    public void setIdreceiver(String idreceiver) {
        this.idreceiver = idreceiver;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
