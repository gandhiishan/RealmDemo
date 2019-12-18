package com.ls.realm.app;

import android.app.Application;

import com.ls.realm.model.db.schema.SimpleRealmModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder()
                        .name("realm-sample.realm")
                        .modules(new SimpleRealmModule())
                        .schemaVersion(1)
                        .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
