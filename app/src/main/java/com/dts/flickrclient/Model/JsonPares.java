package com.dts.flickrclient.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonPares {

   public static ArrayList<Photo> parseJson(String json){
       ArrayList<Photo> list = new ArrayList<>();
       try {
            JSONObject rootObj = new JSONObject(json);
            JSONObject photos = rootObj.getJSONObject("photos");
            JSONArray listPhoto = photos.getJSONArray("photo");
            for (int i = 0; i < listPhoto.length(); i++){
                JSONObject obj = (JSONObject) listPhoto.get(i);
                if (!obj.isNull("url_s")){
                    String id = obj.getString("id");
                    String title = obj.getString("title");
                    long ispublic = obj.getLong("ispublic");
                    long isfriend = obj.getLong("isfriend");
                    long isfamily = obj.getLong("isfamily");
                    String url_s = obj.getString("url_s");
                    String height_s = obj.getString("height_s");
                    String width_s = obj.getString("width_s");
                    if (url_s != null) list.add(new Photo(id, title, ispublic, isfriend, isfamily, url_s, height_s, width_s));
                }
         }
        } catch (JSONException e) {
           System.out.println(json);
            e.printStackTrace();
        }
        return list;
    }
}
