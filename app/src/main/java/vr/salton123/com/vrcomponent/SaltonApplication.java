package vr.salton123.com.vrcomponent;

import android.app.Application;

import com.salton123.base.ApplicationBase;
import com.salton123.common.image.FrescoImageLoader;

/**
 * User: newSalton@outlook.com
 * Date: 2017/12/6 10:16
 * ModifyTime: 10:16
 * Description:
 */
public class SaltonApplication extends ApplicationBase {

    @Override
    public void onCreate() {
        super.onCreate();
        FrescoImageLoader.Init(this);
    }
}
