package cos.mos.utils.ui.sideslip;


import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cos.mos.library.Utils.ULog;
import cos.mos.library.Utils.UText;
import cos.mos.library.Utils.UUnit;
import cos.mos.library.init.KFragment;
import cos.mos.library.listener.KOnFreshListener;
import cos.mos.utils.dao.DbHelper;
import cos.mos.utils.dao.UserBean;
import cos.mos.utils.widget.list.MyDividerDecoration;

/**
 * @Description: 侧滑删除
 * @Author: Kosmos
 * @Date: 2018.11.20 15:43
 * @Email: KosmoSakura@gmail.com
 */
public class SideLipFragment extends KFragment {
    private SpringView spv;
    private RecyclerView rv;
    private SideLipAdapter adapter;
    private ArrayList<UserBean> list;

    @Override
    protected int layout() {
        return R.layout.frag_side_lip;
    }

    @Override
    protected void init() {
        spv = findViewById(R.id.qr_hty_spv);
        rv = findViewById(R.id.qr_hty_rv);

        spv.setHeader(new DefaultHeader(context));
        list = new ArrayList<>();
        adapter = new SideLipAdapter(list);
        adapter.openLoadAnimation();
        rv.setLayoutManager(new LinearLayoutManager(context));
        adapter.setEmptyView(R.layout.lay_empty, rv);
        rv.addItemDecoration(new MyDividerDecoration(ContextCompat.getColor(context, R.color.white_bg),
            UUnit.dp2px(10), UUnit.dp2px(10), UUnit.dp2px(10)));
        rv.setAdapter(adapter);
    }

    @Override
    protected void logic() {
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (position < 0 || position > list.size()) {
                return;
            }
            UserBean bean = list.get(position);
            switch (view.getId()) {
                case R.id.item_qr_right:
                    list.remove(position);
                    DbHelper.deleteByBean(context, bean);
                    adapter.notifyItemRemoved(position);
                    break;
            }
        });
        spv.setListener(new KOnFreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                spv.onFinishFreshAndLoadDelay();
            }
        });
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        ULog.commonD("onResume");
    }

    private void refresh() {
        list.clear();
        List<UserBean> userBeans = DbHelper.SearchAll(context);
        if (!UText.isEmpty(userBeans)) {
            ULog.commonD("-->" + userBeans.size());
            list.addAll(userBeans);
        }
        adapter.notifyDataSetChanged();
    }
}