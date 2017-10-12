package com.spkj.supai.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.spkj.supai.R;
import com.spkj.supai.appconfig.UpdateManager;
import com.toocms.dink5.mylibrary.base.BaseFragment;

import org.xutils.view.annotation.Event;

/**
 * Created by ROYGEM-0830-1 on 2017/9/26.
 */

public class MineLjFrg extends BaseFragment {


//    @ViewInject(R.id.animation_view)
//    LottieAnimationView animationView;

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_ljmine;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }


    private String[] str = {
            "data.json",
            "Walkthrough/FullScreen.json"
            , "Walkthrough/Walkthrough.json"
            , "Tests/CheckSwitch.json"
            , "Tests/Fill.json"
            , "Tests/GradientFill.json"
            , "Tests/KeyframeTypes.json"
            , "Tests/Laugh4.json"
            , "Tests/LoopPlayOnce.json"
            , "Tests/Parenting.json"
            , "Tests/Precomps.json"
            , "Tests/Remap.json"
            , "Tests/Repeater.json"
            , "Tests/ShapeTypes.json"
            , "Tests/SplitDimensions.json"
            , "Tests/Stroke.json"
            , "Tests/TimeStretch.json"
            , "Tests/TrackMattes.json"
            , "Tests/TrimPaths.json"
//            , "Tests/WeAccept.json"
            , "Logo/LogoSmall.json"
            , "Mobilo/A.json"
            , "Mobilo/Apostrophe.json"
            , "Mobilo/B.json"
            , "Mobilo/BlinkingCursor.json"
            , "Mobilo/C.json"
            , "Mobilo/D.json"
            , "Mobilo/E.json"
            , "9squares-AlBoardman.json"
            , "City.json"
            , "EmptyState.json"
            , "HamburgerArrow.json"
            , "Hello World.json"
            , "lottiefiles.com - ATM.json"
            , "lottiefiles.com - Beating Heart.json"
            , "lottiefiles.com - Camera.json"
            , "lottiefiles.com - Countdown.json"
            , "lottiefiles.com - Curly Hair.json"
            , "lottiefiles.com - Day Night.json"
            , "lottiefiles.com - Emoji Shock.json"
            , "lottiefiles.com - Emoji Tongue.json"
            , "lottiefiles.com - Emoji Wink.json"
            , "lottiefiles.com - Favorite Star.json"
            , "lottiefiles.com - Gears.json"
            , "lottiefiles.com - Im Thirsty.json"
            , "lottiefiles.com - Loader 5.json"
            , "lottiefiles.com - Loading 1.json"
            , "lottiefiles.com - Loading 2.json"
            , "lottiefiles.com - Loading 3.json"
            , "lottiefiles.com - Loading 4.json"
            , "lottiefiles.com - Mail Sent.json"
            , "lottiefiles.com - Notifications.json"
            , "lottiefiles.com - Nudge.json"
            , "lottiefiles.com - Octopus.json"
            , "lottiefiles.com - Permission.json"
            , "lottiefiles.com - Play and Like It.json"
            , "lottiefiles.com - Postcard.json"
            , "lottiefiles.com - Preloader.json"
            , "lottiefiles.com - Progress Success.json"
            , "lottiefiles.com - React.json"
            , "lottiefiles.com - Retweet.json"
            , "lottiefiles.com - Retweet.json"
            , "lottiefiles.com - Star Wars Rey.json"
            , "lottiefiles.com - Tadah Image.json"
            , "lottiefiles.com - Tadah Video.json"
            , "lottiefiles.com - Touch ID.json"
            , "lottiefiles.com - Video Camera.json"
            , "lottiefiles.com - VR Sickness.json"
            , "lottiefiles.com - VR.json"
            , "LottieLogo1.json"
            , "LottieLogo2.json"
            , "lottifiles.com - QR Scan.json"
            , "MotionCorpse-Jrcanest.json"
            , "Name.json"
            , "PinJump.json"
            , "Spider Loader.json"
            , "TwitterHeart.json"
            , "WeAccept.json",};
    private int index = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
//
    }

    @Event(value = {R.id.btn})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
//                animationView.setAnimation(str[index++]);
//                animationView.loop(true);
//                animationView.playAnimation();
//                AppUpdateService service = new AppUpdateService();
//                getActivity().startService(new Intent(getActivity(), AppUpdateService.class));
//                UpdateManager.checkUpdate("", false);
                UpdateManager manager = new UpdateManager();
                manager.update("http://dakaapp.troila.com/download/daka.apk?v=3.0", UpdateManager.BackGroundUpdate);
//                manager.startDownload("http://dakaapp.troila.com/download/daka.apk?v=3.0");

                break;
        }
    }
}
