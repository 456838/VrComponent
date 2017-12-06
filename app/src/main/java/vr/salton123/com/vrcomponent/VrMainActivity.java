package vr.salton123.com.vrcomponent;

import android.os.Bundle;

import com.salton123.base.BaseSupportActivity;
import com.salton123.base.BaseSupportFragment;

public class VrMainActivity extends BaseSupportActivity {

    @Override
    public int GetLayout() {
        return R.layout.activity_vr_main;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        loadRootFragment(R.id.mainLayer, BaseSupportFragment.newInstance(VrComponent.class));
    }

    @Override
    public void InitListener() {

    }
}
