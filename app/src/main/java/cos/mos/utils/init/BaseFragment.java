package cos.mos.utils.init;

import cos.mos.library.init.KFragment;
import cos.mos.library.retrofit.HostWrapper;
import cos.mos.utils.mvp.contract.KContract;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @Description: base
 * @Author: Kosmos
 * @Date: 2018年08月02日 15:01
 * @Email: KosmoSakura@gmail.com
 */
public abstract class BaseFragment extends KFragment implements KContract {
    private RequestServes rs;

    protected RequestServes getServes() {
        if (rs == null) {
            rs = HostWrapper.with().create(RequestServes.class);
        }
        return rs;
    }

    @Override
    public void rxDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}