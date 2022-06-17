package com.example.sen_touroperator.config.security.jwt;

import com.example.sen_touroperator.models.DAO.User;
import com.example.sen_touroperator.repositroy.UserRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;
@Component
public class UserTokenIdManager {
    @Autowired
    UserRepository userRepository;

    public int getIdFromToken(String token) {
        String bodyEncoded = token.split("\\.")[1];
        String payloadAsString = new String(Base64.getUrlDecoder().decode(bodyEncoded));

        Map<String, Object> payload = null;
        try {
            payload = new JSONParser(payloadAsString).parseObject();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String id = (String) payload.get("id");

        return Integer.parseInt(id);

    }

    public String getRoleFromToken(String token){
        String bodyEncoded = token.split("\\.")[1];
        String payloadAsString = new String(Base64.getUrlDecoder().decode(bodyEncoded));

        Map<String, Object> payload = null;
        try {
            payload = new JSONParser(payloadAsString).parseObject();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (String) payload.get("role");

    }
}
