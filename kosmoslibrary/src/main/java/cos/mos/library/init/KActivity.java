package cos.mos.library.init;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @Description: <p>
 * @Author: Kosmos
 * @Date: 2018年08月02日 13:17
 * @Email: KosmoSakura@gmail.com
 */
public abstract class KActivity extends AppCompatActivity {
    protected Context context;
    protected CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        int layoutId = layout();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
        init();
        logic();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}