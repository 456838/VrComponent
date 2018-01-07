package vr.salton123.com.vrcomponent;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.base.BaseSupportFragment;
import com.salton123.util.ViewUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

/**
 * Created by salton on 2018/1/7.
 */

public class VideoPlayFragment extends DialogFragment {
    private StandardGSYVideoPlayer videoView;
    private View mContentView;


    public static VideoPlayFragment newInstance(){
        VideoPlayFragment videoPlayFragment = new VideoPlayFragment();
        return videoPlayFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.cp_video_play,null);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoView = f(R.id.videoView);
        //是否可以滑动调整
        videoView.setIsTouchWiget(true);
        videoView.setUp("http://ws.streamhls.huya.com/hqlive/94525224-2460685313-10568562945082523648-2789274524-10057-A-1512526024-1_1200/playlist.m3u8", false, "周星驰电影");
        videoView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //释放所有
                videoView.setStandardVideoAllCallBack(null);
                GSYVideoPlayer.releaseAllVideos();
                dismiss();
            }
        });
        videoView.getTitleTextView().setVisibility(View.GONE);
        videoView.getBackButton().setVisibility(View.GONE);
      //videoView.startWindowFullscreen(getActivity(),false,false);
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT f(@IdRes int id) {
        return ViewUtils.f(mContentView, id);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
    }

    public boolean onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }
}
