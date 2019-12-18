package com.ls.realm.model.db.dao;

import android.support.annotation.NonNull;

import com.ls.realm.model.db.data.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class UserDao {

    private Realm mRealm;

    public UserDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final User user) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });
    }

    public void save(final List<User> userList) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(userList);
            }
        });
    }

    public RealmResults<User> loadAll() {
        return mRealm.where(User.class).findAll().sort("id");
    }

    public RealmResults<User> loadAllAsync() {
        return mRealm.where(User.class).findAllAsync().sort("id");
    }

    public RealmObject loadBy(long id) {
        return mRealm.where(User.class).equalTo("id", id).findFirst();
    }

    public void remove(@NonNull final RealmObject object) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                object.deleteFromRealm();
            }
        });
    }

    public void removeAll() {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                mRealm.clear(User.class);
                mRealm.delete(User.class);
            }
        });
    }

    public long count() {
        return mRealm.where(User.class).count();
    }
}
