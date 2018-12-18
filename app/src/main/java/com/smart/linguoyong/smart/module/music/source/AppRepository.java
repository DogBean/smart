package com.smart.linguoyong.smart.module.music.source;


import com.smart.linguoyong.smart.module.music.model.Folder;
import com.smart.linguoyong.smart.module.music.model.PlayList;
import com.smart.linguoyong.smart.module.music.model.Song;
import com.smart.linguoyong.smart.module.music.source.db.LiteOrmHelper;
import com.smart.linguoyong.smart.utils.Injection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import rx.functions.Action1;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/10/16
 * Time: 4:17 PM
 * Desc: AppRepository
 */
public class AppRepository implements AppContract {

    private static volatile AppRepository sInstance;

    private AppLocalDataSource mLocalDataSource;

    private List<PlayList> mCachedPlayLists;

    private AppRepository() {
        mLocalDataSource = new AppLocalDataSource(Injection.provideContext(), LiteOrmHelper.getInstance());
    }

    public static AppRepository getInstance() {
        if (sInstance == null) {
            synchronized (AppRepository.class) {
                if (sInstance == null) {
                    sInstance = new AppRepository();
                }
            }
        }
        return sInstance;
    }

    // Play List

    @Override
    public Observable<List<PlayList>> playLists() {
        return mLocalDataSource.playLists()
                .doOnNext(playLists -> mCachedPlayLists = playLists);


    }

    @Override
    public List<PlayList> cachedPlayLists() {
        if (mCachedPlayLists == null) {
            return new ArrayList<>(0);
        }
        return mCachedPlayLists;
    }

    @Override
    public Observable<PlayList> create(PlayList playList) {
        return mLocalDataSource.create(playList);
    }

    @Override
    public Observable<PlayList> update(PlayList playList) {
        return mLocalDataSource.update(playList);
    }

    @Override
    public Observable<PlayList> delete(PlayList playList) {
        return mLocalDataSource.delete(playList);
    }

    // Folders

    @Override
    public Observable<List<Folder>> folders() {
        return mLocalDataSource.folders();
    }

    @Override
    public Observable<Folder> create(Folder folder) {
        return mLocalDataSource.create(folder);
    }

    @Override
    public Observable<List<Folder>> create(List<Folder> folders) {
        return mLocalDataSource.create(folders);
    }

    @Override
    public Observable<Folder> update(Folder folder) {
        return mLocalDataSource.update(folder);
    }

    @Override
    public Observable<Folder> delete(Folder folder) {
        return mLocalDataSource.delete(folder);
    }

    @Override
    public Observable<List<Song>> insert(List<Song> songs) {
        return mLocalDataSource.insert(songs);
    }

    @Override
    public Observable<Song> update(Song song) {
        return mLocalDataSource.update(song);
    }

    @Override
    public Observable<Song> setSongAsFavorite(Song song, boolean favorite) {
        return mLocalDataSource.setSongAsFavorite(song, favorite);
    }
}