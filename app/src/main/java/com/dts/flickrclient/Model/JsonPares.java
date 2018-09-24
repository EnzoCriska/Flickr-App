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
                list.add(new Photo(obj.getString("id"),obj.getString("owner"),
                        obj.getString("secret"), obj.getString("server"),
                        obj.getLong("farm"), obj.getString("title"), obj.getLong("ispublic"),
                        obj.getLong("isfriend"), obj.getLong("isfamily"), obj.getString("url_s"),
                        obj.getString("height_s"), obj.getString("width_s")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
