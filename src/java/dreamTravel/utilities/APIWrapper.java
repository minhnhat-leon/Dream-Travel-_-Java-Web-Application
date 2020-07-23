/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.utilities;

import dreamTravel.users.UsersDTO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author NhatBPM;
 */
public class APIWrapper {
    private final static String APP_ID = "919612915209110";
    private final static String APP_SECRET = "ce2c50a986e5e013e4abb800236d3fb8";
    private final static String REDIRECT_URL = "http://localhost:8084/DreamTravelLAB2/loginFacebook";
    private String accessToken;
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public static String getDiaLogLink() {
        String testUrl = "https://www.facebook.com/dialog/oauth?client_id=" + APP_ID + "&redirect_uri=" + REDIRECT_URL + "&scope=user_link%2Cemail%2Cpublic_profile";
        return testUrl;
    }
    
    public String getAccessToken(String code) {
        String accessTokenLink = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=%s"
                + "&client_secret=%s"
                + "&redirect_uri=%s"
                + "&code=%s";
        accessTokenLink = String.format(accessTokenLink, APP_ID, APP_SECRET, REDIRECT_URL, code);
        String result = NetUtils.GetResult(accessTokenLink);
        String token = result.substring(result.indexOf(":") + 2, result.indexOf(",") - 1);
        return token;
    }
    
    public UsersDTO getUserDTOInfor() throws MalformedURLException, IOException {
        String infoUrl = "https://graph.facebook.com/v7.0/me?fields=id%2Cname%2Clink%2Cemail&access_token=";
        infoUrl = infoUrl + this.accessToken;

        URL url = new URL(infoUrl);
        JSONTokener tokener = new JSONTokener(url.openStream());
        JSONObject obj = new JSONObject(tokener);
        String facebooId = obj.getString("id");
        String name = obj.getString("name");
        String facebookLink = obj.getString("link");
        UsersDTO user = new UsersDTO(facebooId, name, facebookLink, "user");
        return user;
    }
    
}
