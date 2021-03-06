package com.example.zzt.whyfi.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.zzt.whyfi.R;
import com.example.zzt.whyfi.common.net.wifi.WiFiDirectBroadcastReceiver;
import com.example.zzt.whyfi.common.net.wifi.WifiActivity;
import com.example.zzt.whyfi.common.net.wifi.WifiMsg;
import com.example.zzt.whyfi.databinding.ActivityDrawerBinding;
import com.example.zzt.whyfi.model.Device;
import com.example.zzt.whyfi.vm.AvatarBindingAdapters;
import com.example.zzt.whyfi.vm.MsgHistory;
import com.example.zzt.whyfi.vm.MsgRecyclerAdapter;

public class Drawer extends WifiActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WiFiDirectBroadcastReceiver mReceiver;
    private android.content.IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDrawerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_drawer);
        binding.setThisDevice(Device.now);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToEdit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        setMsgList();
        setTabHost();


        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);
        if (!WifiMsg.setUpWifi(this)) {
            Toast.makeText(this, R.string.net_not_enabled, Toast.LENGTH_SHORT).show();
        }
    }



    private void setTabHost() {
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        assert host != null;
        host.setup();
        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec(getString(R.string.sent_tab));
        spec.setContent(R.id.sentTab);
        spec.setIndicator(getString(R.string.sent_tab));
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec(getString(R.string.received_tab));
        spec.setContent(R.id.receivedTab);
        spec.setIndicator(getString(R.string.received_tab));
        host.addTab(spec);
    }

    private void setMsgList() {
        setRecycler(R.id.sentList, new MsgRecyclerAdapter(MsgHistory.getSent()));
        setRecycler(R.id.receivedList, new MsgRecyclerAdapter(MsgHistory.getReceived()));
    }

    private void setRecycler(int id, MsgRecyclerAdapter adapter) {
        RecyclerView sentRecycler = (RecyclerView) findViewById(id);
        assert sentRecycler != null;
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        sentRecycler.setHasFixedSize(true);

        // use a linear layout manager
        sentRecycler.setLayoutManager(new LinearLayoutManager(this));

        sentRecycler.setAdapter(adapter);
    }

    private void jumpToEdit() {
        Intent intent = new Intent(this, EditMsgActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, DeviceSettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onClickNavHeader(View view) {
        Intent intent = new Intent(this, DeviceInfoActivity.class);
        startActivity(intent);
    }

    public void onClickMsgAvatar(View view) {
        Intent intent = new Intent(this, OthersInfoActivity.class);
        Device device = AvatarBindingAdapters.getDevice((ImageView) view);
        device.addToIntent(intent);
        startActivity(intent);
    }


    @Override
    public void setReceiver(WiFiDirectBroadcastReceiver receiver) {
        mReceiver = receiver;
    }

    @Override
    public WiFiDirectBroadcastReceiver getReceiver() {
        return mReceiver;
    }
}
