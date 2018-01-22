package cn.wangliang.foodsafe.base.mvp;

public interface MvpPresenter<T extends MvpView> {
    void attachView(T view);
    void detachView();
}