package com.app.sucrates.cymatria.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Stephen on 7/11/2017.
 */
public class TextResourceReader {

    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder text = new StringBuilder();
        try {
            InputStream stream = context.getResources().openRawResource(resourceId);
            InputStreamReader streamReader = new InputStreamReader(stream);

            BufferedReader bf = new BufferedReader(streamReader);

            String nextLine;

            while((nextLine = bf.readLine()) != null) {
                text.append(nextLine);
                text.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("Error opening resource ID: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource Not Found: " + resourceId, nfe);
        }

        return text.toString();
    }
}
