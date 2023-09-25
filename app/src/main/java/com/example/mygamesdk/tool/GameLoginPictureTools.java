package com.example.mygamesdk.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
public class GameLoginPictureTools {
	public static Drawable loadImageFromAsserts(final Context ctx, String fileName) {
        try {
            String name = "gamesdk/images/";
            InputStream is = ctx.getResources().getAssets().open(name+fileName);
            return Drawable.createFromStream(is, null);
        } catch (IOException e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
