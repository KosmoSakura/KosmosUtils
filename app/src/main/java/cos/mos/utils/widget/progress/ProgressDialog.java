package cos.mos.utils.widget.progress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.TextView;

import cos.mos.utils.R;
import cos.mos.utils.from_blankj.ULogBj;

/**
 * @Description: 进度弹窗
 * @Author: Kosmos
 * @Date: 2018.11.13 21:43
 * @Email: KosmoSakura@gmail.com
 */
public class ProgressDialog {
    private volatile static ProgressDialog instance;
    private MyDialog myDialog;

    private ProgressDialog() {
    }

    public static ProgressDialog instance() {
        if (instance == null) {
            synchronized (ProgressDialog.class) {
                if (instance == null) {
                    instance = new ProgressDialog();
                }
            }
        }
        return instance;
    }

    public void stopProgressDialog() {
        if (myDialog != null) {
            if (myDialog.isShowing()) {
                try {
                    myDialog.dismiss();
                } catch (Exception e) {
                    ULogBj.d(e.toString());
                }
            }
            myDialog = null;
        }
    }

    /**
     * @param text 修改加载文字
     */
    public void setProgressText(Context context, String text) {
        if (myDialog != null && myDialog.isShowing()) {
            myDialog.setMsg(text);
        } else {
            startProgressDialog(context, text);
        }
    }

    public void startProgressDialog(Context context) {
        this.startProgressDialog(context, "Loading...");
    }

    public void startProgressDialog(Context context, String content) {
        if (myDialog != null) {
            stopProgressDialog();
        }
        myDialog = new MyDialog(context);
        myDialog.setMsg(content);
        if (!((Activity) context).isFinishing()) {
            try {
                myDialog.show();
            } catch (Exception e) {
                ULogBj.d(e.toString());
            }
        }
    }

    private class MyDialog extends Dialog {
        private TextView tv;

        MyDialog(@NonNull Context context) {
            super(context, R.style.SakuraDialog);
            setContentView(R.layout.dia_progress);
            setCancelable(false);
            tv = findViewById(R.id.tv);
        }

        void setMsg(String content) {
            tv.setText(content);
        }

        @Override
        public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    dismiss();
                    return true;
            }
            return super.onKeyDown(keyCode, event);
        }
    }
}
