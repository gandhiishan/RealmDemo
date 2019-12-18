package com.ls.realm.model.db.schema;

import com.ls.realm.model.db.data.User;
import com.ls.realm.model.db.utils.RealmString;

import io.realm.annotations.RealmModule;

@RealmModule(classes = { User.class, RealmString.class  })
public class SimpleRealmModule {
}
