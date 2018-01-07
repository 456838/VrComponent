package vr.salton123.com.vrcomponent;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.util.SimpleArrayMap;

import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.texture.MD360BitmapTexture;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by salton on 2018/1/7.
 */

public class ImageLoadProvider implements MDVRLibrary.IImageLoadProvider{

    private SimpleArrayMap<Uri,Target> targetMap = new SimpleArrayMap<>();

    @Override
    public void onProvideBitmap(final Uri uri, final MD360BitmapTexture.Callback callback) {

        final Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // texture
                callback.texture(bitmap);
                targetMap.remove(uri);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                targetMap.remove(uri);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        targetMap.put(uri, target);
        Picasso.with(SaltonApplication.getInstance()).load(uri).resize(callback.getMaxTextureSize(),callback.getMaxTextureSize()).onlyScaleDown().centerInside().memoryPolicy(NO_CACHE, NO_STORE).into(target);
    }
}