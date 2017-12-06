package vr.salton123.com.vrcomponent;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asha.vrlib.MDDirectorCamUpdate;
import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.texture.MD360BitmapTexture;
import com.salton123.base.BaseSupportFragment;

/**
 * User: newSalton@outlook.com
 * Date: 2017/12/5 21:41
 * ModifyTime: 21:41
 * Description:
 */
public class VrComponent extends BaseSupportFragment {
    private GLSurfaceView mGlSurfaceView;
    MDVRLibrary mVRLibrary;

    @Override
    public int GetLayout() {
        return R.layout.fm_vr;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        mGlSurfaceView = new GLSurfaceView(_mActivity);
        mVRLibrary = MDVRLibrary
                .with(_mActivity)
                .displayMode(MDVRLibrary.DISPLAY_MODE_NORMAL)
                .interactiveMode(MDVRLibrary.INTERACTIVE_MODE_MOTION_WITH_TOUCH).asBitmap(new MDVRLibrary.IBitmapProvider() {
                    @Override
                    public void onProvideBitmap(MD360BitmapTexture.Callback callback) {
                        callback.texture(BitmapFactory.decodeResource(getResources(), R.mipmap.star));
                    }
                }).pinchEnabled(true).build(mGlSurfaceView);
        ViewGroup rootView = f(R.id.glSurfaceView);
        rootView.addView(mGlSurfaceView);
    }

    boolean sensorOpen = false;
    boolean isNormal = false;
    TextView mode;

    @Override
    public void InitListener() {
        f(R.id.sensor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sensorOpen) {
                    sensorOpen = true;
                    mVRLibrary.switchInteractiveMode(_mActivity, MDVRLibrary.INTERACTIVE_MODE_MOTION_WITH_TOUCH);
//                sensor.setImageResource(R.drawable.sensor_icon)
                } else {
                    sensorOpen = false;
                    mVRLibrary.switchInteractiveMode(_mActivity, MDVRLibrary.INTERACTIVE_MODE_TOUCH);
//                sensor.setImageResource(R.drawable.unsensor_icon)
                }
            }
        });
        mode = f(R.id.mode);
        f(R.id.touchLayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNormal) {
                    normalMode();
                    mode.setText("鱼眼");
                } else {
                    fishEyesMode();
                    mode.setText("普通");
                }
            }
        });
    }

    private void normalMode() {
        isNormal = false;
        MDDirectorCamUpdate cameraUpdate = mVRLibrary.updateCamera();
        PropertyValuesHolder near = PropertyValuesHolder.ofFloat("near", cameraUpdate.getNearScale(), 0f);
        PropertyValuesHolder eyeZ = PropertyValuesHolder.ofFloat("eyeZ", cameraUpdate.getEyeZ(), 0f);
        PropertyValuesHolder pitch = PropertyValuesHolder.ofFloat("pitch", cameraUpdate.getPitch(), 0f);
        PropertyValuesHolder yaw = PropertyValuesHolder.ofFloat("yaw", cameraUpdate.getYaw(), 0f);
        PropertyValuesHolder roll = PropertyValuesHolder.ofFloat("roll", cameraUpdate.getRoll(), 0f);
        startCameraAnimation(cameraUpdate, near, eyeZ, pitch, yaw, roll);
    }


    private void fishEyesMode() {
        isNormal = true;
        MDDirectorCamUpdate cameraUpdate = mVRLibrary.updateCamera();
        PropertyValuesHolder near = PropertyValuesHolder.ofFloat("near", cameraUpdate.getNearScale(), -0.6f);
        PropertyValuesHolder eyeZ = PropertyValuesHolder.ofFloat("eyeZ", cameraUpdate.getEyeZ(), 18f);
        PropertyValuesHolder pitch = PropertyValuesHolder.ofFloat("pitch", cameraUpdate.getPitch(), 45f);
        PropertyValuesHolder yaw = PropertyValuesHolder.ofFloat("yaw", cameraUpdate.getYaw(), 45f);
        PropertyValuesHolder roll = PropertyValuesHolder.ofFloat("roll", cameraUpdate.getRoll(), 0f);
        startCameraAnimation(cameraUpdate, near, eyeZ, pitch, yaw, roll);
    }

    private ValueAnimator animator;

    private void startCameraAnimation(final MDDirectorCamUpdate cameraUpdate, PropertyValuesHolder... values) {
        if (animator != null) {
            animator.cancel();
        }

        animator = ValueAnimator.ofPropertyValuesHolder(values).setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float near = (float) animation.getAnimatedValue("near");
                float eyeZ = (float) animation.getAnimatedValue("eyeZ");
                float pitch = (float) animation.getAnimatedValue("pitch");
                float yaw = (float) animation.getAnimatedValue("yaw");
                float roll = (float) animation.getAnimatedValue("roll");
                cameraUpdate.setEyeZ(eyeZ).setNearScale(near).setPitch(pitch).setYaw(yaw).setRoll(roll);
            }
        });
        animator.start();
    }


    @Override
    public void onResume() {
        super.onResume();
        mVRLibrary.onResume(_mActivity);
    }

    @Override
    public void onPause() {
        super.onPause();
        mVRLibrary.onPause(_mActivity);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mVRLibrary.onDestroy();
    }


}
