package org.vanguardmatrix.engine.sidemenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class NavigationStyle {

    String _FONTNAME = "carbonatedgothic.TTF";
    String _COLOR = "#F0F0F0";
    String _TYPEFACE = "ITALIC";

    public JSONObject getAttributes() {

        JSONObject x = new JSONObject();

        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                System.out.println(field.getName() + " - " + field.getType()
                        + " - " + field.get(this));

                x.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return x;
    }

}
