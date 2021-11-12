package com.example.notes_app;

public class Note {

    private String header;
    private String text;

    void setHeader(String str){
        header = str;
    }

    void setText(String str){
        text = str;
    }

    String getHeader(){
        return header;
    }

    String getText(){
        return text;
    }
}
