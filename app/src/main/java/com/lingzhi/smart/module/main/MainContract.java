/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lingzhi.smart.module.main;


import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.bean.IconLink;
import com.lingzhi.smart.data.bean.ResourceGroup;
import com.lingzhi.smart.data.bean.ResourceList;
import com.lingzhi.smart.data.bean.Song;
import com.lingzhi.smart.data.source.Banner;
import com.lingzhi.smart.data.source.RecommendBean;
import com.lingzhi.smart.base.BaseView;
import com.lingzhi.smart.base.MvpPresenter;
import com.lingzhi.smart.data.source.remote.Resp;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {

        void banner(List<Banner.BannerEntity> bannerEntities);

        void category(DatedLinkGroup groud);

        void requisite(ResourceGroup<Song> requisite);

        void recommend(ResourceGroup<IconLink> recommends);

        void finishTask();


    }

    interface Presenter extends MvpPresenter {


    }
}
