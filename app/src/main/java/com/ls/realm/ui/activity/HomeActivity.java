package com.ls.realm.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ls.realm.R;
import com.ls.realm.model.db.RealmManager;
import com.ls.realm.model.db.data.User;
import com.ls.realm.model.db.utils.DataGenerator;
import com.ls.realm.ui.adapter.RealmAdapter;

import java.util.List;

import io.realm.RealmResults;

public class HomeActivity extends AppCompatActivity implements RealmAdapter.OnActionClickListner {

    private RealmAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home);
        RealmManager.open();

        initViews();
        saveUserList();
        loadUserListAsync();
    }

    @Override
    protected void onDestroy() {
        RealmManager.close();
        super.onDestroy();
    }

    private void initViews() {
        mAdapter = new RealmAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnActionClickListner(this);
        recyclerView.setAdapter(mAdapter);
    }

    private void saveUserList() {
        RealmManager.createUserDao().save(DataGenerator.generateUserList());
    }

    private void loadUserListAsync() {
       final RealmResults<User> dataList = RealmManager.createUserDao().loadAllAsync();
        updateRecyclerView(dataList);
        Log.v("d top-->","-"+dataList.size());
//        dataList.addChangeListener(new RealmChangeListener<RealmResults<User>>() {
//            @Override
//            public void onChange(RealmResults<User> users) {
//                Log.v("d-->","-"+dataList.size());
//                updateRecyclerView(users);
//            }
//        });
//        dataList.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<User>>() {
//            @Override
//            public void onChange(RealmResults<User> users, OrderedCollectionChangeSet changeSet) {
//                Log.v("d-->","-"+users.size());
//                if (changeSet == null) {
//                    // The first time async returns with an null changeSet.
//                } else {
//                    // Called on every update.
//                    updateRecyclerView(users);
//                }
//            }
//        });

//        dataList.addChangeListener(new RealmChangeListener() {
//            @Override
//            public void onChange(RealmResults<User> result) {
//
//            }
////
////            @Override
////            public void onChange(RealmResults<User> result) {
////                updateRecyclerView(dataList);
////            }
//        });
    }

    private void updateRecyclerView(List<User> userList) {
        if (mAdapter != null && userList != null) {
            mAdapter.setData(userList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDeleteUser(User user) {
        RealmManager.createUserDao().remove(user);
        loadUserListAsync();
    }
}
