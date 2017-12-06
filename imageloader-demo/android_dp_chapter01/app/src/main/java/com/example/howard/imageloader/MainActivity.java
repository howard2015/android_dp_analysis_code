package com.example.howard.imageloader;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.howard.imageloader.tools.s02.ImageLoader;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;
    private String url="http://yingxiao.baidu.com/new/Public/static/Home/img/marketing-logo.png";
    private String url1="https://bdyingxiaocms.cdn.bcebos.com/2017-11-14/5a0aa16f84d38.jpg";
    private String url2="https://bdyingxiaocms.cdn.bcebos.com/2017-11-28/5a1d4f2eaa49f.jpg";
    private String url3="https://bdyingxiaocms.cdn.bcebos.com/2017-11-21/5a13a2e87bd57.jpg";
    private String url4="https://bdyingxiaocms.cdn.bcebos.com/2017-11-22/5a151b8ad180a.png";
    private String url5="https://bdyingxiaocms.cdn.bcebos.com/2017-11-09/5a0479893edf5.jpg";
    private String url6="https://bdyingxiaocms.cdn.bcebos.com/2017-10-24/59eeb75403203.jpg";
    private String url7="https://bdyingxiaocms.cdn.bcebos.com/2017-11-09/5a040802604fc.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iv=(ImageView)findViewById(R.id.iv_load);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageLoader imageLoader=new ImageLoader();
        imageLoader.useDoubleCache(true);
        imageLoader.displayImage(url7,iv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
