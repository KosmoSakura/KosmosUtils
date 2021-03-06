package cos.mos.toolkit.init;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @Description: <p>
 * @Author: Kosmos
 * @Date: 2018年08月02日 13:17
 * @Email: KosmoSakura@gmail.com
 */
public abstract class KActivity extends AppCompatActivity {
    protected Context context;
    protected CompositeDisposable compositeDisposable;
    /**
     * 在哪里接收,在哪里注册
     */
    protected boolean initEventBus;//是否注册EventBus

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initEventBus = false;
        int layoutId = layout();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
        init();
        logic();
        if (initEventBus) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * @return 返回布局
     */
    protected abstract int layout();

    /**
     * 初始化基础信息
     */
    protected abstract void init();

    /**
     * 填充逻辑部分 include
     */
    protected abstract void logic();

    private BitmapFactory.Options opt;

    /**
     * @param iBg 大图控件
     * @param id  图片id
     */
    protected void setBigImage(ImageView iBg, int id) {
        if (opt == null) {
            opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        iBg.setImageBitmap(BitmapFactory.decodeStream(getResources().openRawResource(id), null, opt));
    }

    /**
     * @param colorTop    顶部颜色
     * @param colorBottom 底部颜色
     * @apiNote 设置顶部状态栏、底部导航栏颜色(只能在Activity内部调用)
     */
    protected void setBarColor(int colorTop, int colorBottom) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorTop);//顶部状态栏
                window.setNavigationBarColor(colorBottom);//底部导航栏
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void rxJava(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        if (initEventBus) {
            EventBus.getDefault().register(this);
        }
    }
}
