package ir.aspacrm.my.app.mahan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ActivitySplash extends AppCompatActivity {

    ImageView img_mahan,img_rss, img_wifi, img_mobile, img_lte, img_computer, img_ngn, img_cloud,img_4g,img_adsl,img_voip,img_wireless;

    Animation Animation, Animation1,
            Animation2, Animation3, Animation4,
            Animation5, Animation6, Animation7,
            Animation8, Animation9,Animation10,
            Animation11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        G.context = this;
        initView();
    }

    private void initView() {
        loadAnim();
        img_wifi = (ImageView)findViewById(R.id.img_wifi);
        img_mahan = (ImageView)findViewById(R.id.img_mahan);
        img_mobile = (ImageView)findViewById(R.id.img_mobile);
        img_lte = (ImageView)findViewById(R.id.img_lte);
        img_computer = (ImageView)findViewById(R.id.img_computer);
        img_ngn = (ImageView)findViewById(R.id.img_ngn);
        img_cloud = (ImageView)findViewById(R.id.img_cloud);
        img_4g = (ImageView)findViewById(R.id.img_4g);
        img_adsl = (ImageView)findViewById(R.id.img_adsl);
        img_voip = (ImageView)findViewById(R.id.img_voip);
        img_wireless = (ImageView)findViewById(R.id.img_wireless);
        img_rss = (ImageView)findViewById(R.id.img_rss);


        img_wifi.startAnimation(Animation);
        img_ngn.startAnimation(Animation1);
        img_mobile.startAnimation(Animation2);
        img_lte.startAnimation(Animation3);
        img_computer.startAnimation(Animation4);
        img_cloud.startAnimation(Animation5);
        img_computer.startAnimation(Animation6);
        img_4g.startAnimation(Animation7);
        img_adsl.startAnimation(Animation8);
        img_voip.startAnimation(Animation9);
        img_wireless.startAnimation(Animation10);
        img_rss.startAnimation(Animation11);

    }

    private void loadAnim() {
        Animation = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein);
        Animation1 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein1);
        Animation2 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein2);
        Animation3 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein3);
        Animation4 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein4);
        Animation5 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein5);
        Animation6 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein6);
        Animation7 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein7);
        Animation8 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein8);
        Animation9 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein9);
        Animation10 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein10);
        Animation11 = AnimationUtils.loadAnimation(G.context, R.anim.anim_fadein11);
        Animation11.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(G.context,ActivityStarter.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
