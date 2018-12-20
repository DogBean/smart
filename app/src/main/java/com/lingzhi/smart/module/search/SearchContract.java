package com.lingzhi.smart.module.search;

import com.smart.linguoyong.data.bean.SearchResultBean;
import com.lingzhi.smart.base.BaseView;
import com.lingzhi.smart.base.MvpPresenter;
import com.smart.linguoyong.data.bean.SearchTagBean;
import java.util.List;

public interface SearchContract {
    interface View extends BaseView<Presenter> {
        void showLoading(String msg);
        void dissmissLoading();
        /**
         * 显示热门标签
         *
         * @param tags
         */
        void showSearchHotTag(List<SearchTagBean> tags);

        /**
         * 显示最近标签
         *
         * @param tags
         */
        void showSearchNearlyTag(List<String> tags);
        /**
         * 清除标签
         */
        void clearTagView();

        void startSearch(String key);
        void showSearchResult(SearchResultBean resultBean);
    }

    interface Presenter extends MvpPresenter {
        /**
         * 搜索
         */
        void search(String key);
        /**
         * 获取热门标签
         */
        void getSearchHotTag(String key);

        /**
         * 插入热门标签
         * @param tag
         */
        void insertSearchHotTag(String tag);

        /**
         * 获取最近标签
         */
        void getSearchNearlyTag();

        /**
         * 插入最近标签
         * @param tag
         */
        void insertSearchNearlyTag(String tag);


        /**
         * 清除最近搜索标签
         */
        void clearSearchNearlyTag();
    }
}
