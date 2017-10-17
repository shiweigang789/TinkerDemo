package com.swg.tinkerdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.swg.tinkerdemo.tinker.Utils;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载补丁包
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
    }

    public void show(View view){
        Toast.makeText(this, "show_tinker", Toast.LENGTH_SHORT).show();
    }

    //Tinker相关配置
    protected void onResume() {
        super.onResume();
        Utils.setBackground(false);

    }

    //Tinker相关配置
    @Override
    protected void onPause() {
        super.onPause();
        Utils.setBackground(true);
    }

}
