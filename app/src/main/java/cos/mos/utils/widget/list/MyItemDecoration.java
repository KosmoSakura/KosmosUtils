package cos.mos.utils.widget.list;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private int left, top, right, bottom;

    public MyItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(left, top, right, bottom);
    }
}