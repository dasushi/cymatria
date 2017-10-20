package com.app.sucrates.cymatria.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;

/**
 * Created by Stephen on 7/17/2017.
 */
public class TextureHelper {

    private static String TAG = "TextureHelper";

    public static int loadTexture(Context context, int resourceId) {
        final int[] textureObjectIds = new int[1];
        glGenTextures(1, textureObjectIds, 0);

        if(textureObjectIds[0] == 0) {
            if(LoggerConfig.ENABLED) {
                Log.w(TAG, "Error loading new OpenGL texture");
            }
            return 0;
        }
        final BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = false;

        //fetch bitmap from resources
        final Resources res = context.getResources();
        final Bitmap bm = BitmapFactory.decodeResource(res, resourceId, opt);

        //error handling for bitmap load
        if(bm == null) {
            if(LoggerConfig.ENABLED) {
                Log.w(TAG, "ResourceID " + resourceId + " failed decoding");
            }
            glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }

        //bind texture after successful load
        glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);

        //set texture filtering parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        //load texture into openGL
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bm, 0);

        //generate all mipmap levels
        glGenerateMipmap(GL_TEXTURE_2D);

        //cleanup
        bm.recycle();
        //unbind texture
        glBindTexture(GL_TEXTURE_2D, 0);

        return textureObjectIds[0];
    }
}
