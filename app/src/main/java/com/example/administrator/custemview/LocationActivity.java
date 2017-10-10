package com.example.administrator.custemview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.LocationUtil;
import view.BreakViewGroup;

/**
 * Created by zchao on 2017/9/15.
 * desc:
 * version:
 */

public class LocationActivity extends BaseActivity {

    @Bind(R.id.location_net)
    Button locationNet;
    @Bind(R.id.location_gps)
    Button locationGps;
    @Bind(R.id.location_lati)
    TextView locationLati;
    @Bind(R.id.location_long)
    TextView locationLong;
    @Bind(R.id.bre)
    BreakViewGroup mBre;
    private boolean flag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        ButterKnife.bind(this);
        String[] aaa = {"的单独","的单独","的单独","的单独的单独的单独的单独的单独","的单独","的单独","的单独的单独的单独","的单独","的单独","的单独的单独","的单独","的单独","的单独的单独","的单独","的单独","的单独",};
        mBre.removeAllViews();
        for (int i = 0; i < aaa.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(aaa[i]);
            mBre.addView(textView);
        }
    }
    /**
     * 最好定位
     */
    @OnClick(R.id.location_best)
    public void locationByBest() {
        if (flag) {
            getBestLocation();
        } else {
            Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 网络定位
     */
    @OnClick(R.id.location_net)
    public void locationByNet() {
        if (flag) {
            getNetworkLocation();
        } else {
            Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * gps定位
     */
    @OnClick(R.id.location_gps)
    public void locationByGps() {
        if (flag) {
            getGPSLocation();
        } else {
            Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            flag = grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }
    }

    /**
     * 通过GPS获取定位信息
     */
    public void getGPSLocation() {
        Location gps = LocationUtil.getGPSLocation(this);
        if (gps == null) {
            //设置定位监听，因为GPS定位，第一次进来可能获取不到，通过设置监听，可以在有效的时间范围内获取定位信息
            LocationUtil.addLocationListener(this, LocationManager.GPS_PROVIDER, new LocationUtil.ILocationListener() {
                @Override
                public void onSuccessLocation(Location location) {
                    if (location != null) {
                        Toast.makeText(LocationActivity.this, "gps onSuccessLocation location:  lat==" + location.getLatitude() + "     lng==" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        locationLati.setText(String.valueOf(location.getLatitude()));
                        locationLong.setText(String.valueOf(location.getLongitude()));
                    } else {
                        Toast.makeText(LocationActivity.this, "gps location is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "gps location: lat==" + gps.getLatitude() + "  lng==" + gps.getLongitude(), Toast.LENGTH_SHORT).show();
            locationLati.setText(String.valueOf(gps.getLatitude()));
            locationLong.setText(String.valueOf(gps.getLongitude()));
        }
    }

    /**
     * 通过网络等获取定位信息
     */
    private void getNetworkLocation() {
        Location net = LocationUtil.getNetWorkLocation(this);
        if (net == null) {
            Toast.makeText(this, "net location is null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "network location: lat==" + net.getLatitude() + "  lng==" + net.getLongitude(), Toast.LENGTH_SHORT).show();
            locationLati.setText(String.valueOf(net.getLatitude()));
            locationLong.setText(String.valueOf(net.getLongitude()));
        }
    }

    /**
     * 采用最好的方式获取定位信息
     */
    private void getBestLocation() {
        Criteria c = new Criteria();//Criteria类是设置定位的标准信息（系统会根据你的要求，匹配最适合你的定位供应商），一个定位的辅助信息的类
        c.setPowerRequirement(Criteria.POWER_LOW);//设置低耗电
        c.setAltitudeRequired(false);//设置需要海拔
        c.setBearingAccuracy(Criteria.ACCURACY_COARSE);//设置COARSE精度标准
        c.setAccuracy(Criteria.ACCURACY_LOW);//设置低精度
        c.setCostAllowed(true);
        //... Criteria 还有其他属性，就不一一介绍了
        Location best = LocationUtil.getBestLocation(this, c);
        if (best == null) {
            Toast.makeText(this, " best location is null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "best location: lat==" + best.getLatitude() + " lng==" + best.getLongitude(), Toast.LENGTH_SHORT).show();
            locationLati.setText(String.valueOf(best.getLatitude()));
            locationLong.setText(String.valueOf(best.getLongitude()));
        }
    }
}
