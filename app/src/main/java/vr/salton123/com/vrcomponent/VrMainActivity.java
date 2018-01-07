package vr.salton123.com.vrcomponent;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.salton123.base.BaseSupportActivity;
import com.salton123.base.BaseSupportFragment;
import com.salton123.mvp.util.RxUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class VrMainActivity extends BaseSupportActivity {
    android.support.v7.app.AlertDialog mAlertDialog;
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
        if (!new RxPermissions(this).isGranted(Manifest.permission.INTERNET) || !new RxPermissions(this).isGranted(Manifest.permission.ACCESS_NETWORK_STATE)) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setTitle("授权通知")
                    .setMessage("授予网络访问权限")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            requestPermission().compose(RxUtil.<Boolean>rxSchedulerHelper()).subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    mAlertDialog.dismiss();
                                    if (!aBoolean) {
                                        Toast.makeText(getApplicationContext(), "授权失败，请重试!", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        showData();
                                    }
                                }

                            });
                        }
                    }).create();
            mAlertDialog.show();
        } else {
            showData();
        }
    }

    @Override
    public void InitListener() {

    }


    private void showData() {
    }

    private Observable<Boolean> requestPermission() {
        // final String[] arr = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET};
        final String[] arr = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};
        return new RxPermissions(VrMainActivity.this).request(arr).all(new Predicate<Boolean>() {
            @Override
            public boolean test(@NonNull Boolean aBoolean) throws Exception {
                return aBoolean;
            }
        }).flatMapObservable(new Function<Boolean, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                return Observable.just(aBoolean);
            }
        });
    }
}
