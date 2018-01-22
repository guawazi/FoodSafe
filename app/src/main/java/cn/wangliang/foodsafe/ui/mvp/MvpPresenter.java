package cn.wangliang.foodsafe.ui.mvp;

public interface MvpPresenter<T extends MvpView> {
    void attachView(T view);
    void detachView();
}