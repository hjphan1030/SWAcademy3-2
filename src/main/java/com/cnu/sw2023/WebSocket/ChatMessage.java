package com.cnu.sw2023.WebSocket;

import com.cnu.sw2023.config.jwtconfig.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter @Setter
public class ChatMessage {
    private String token;
    private String msg;


    public static ChatMessage fromJson(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, ChatMessage.class);
    }

    public String toString(){
        String a = '"'+"name"+'"'+":" + '"' + JwtUtil.getUserName(token) + '"';
        String b = '"'+"msg"+'"'+":" + '"' + msg + '"';
        return '{' + a +"," + b + '}';
    }
}
