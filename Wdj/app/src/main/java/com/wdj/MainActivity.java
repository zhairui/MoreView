package com.wdj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MoreView.OnMoreClicklistener{
    private MoreView moreView;
    String message="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moreView= (MoreView) findViewById(R.id.moreview);
        moreView.setOnMoreClicklistener(this);
        message="《最后一张签证》是花箐、牛牛执导的战争剧，由王雷、陈宝国、张静静领衔主演[1]" +
                "该剧改编自中国驻奥地利外交官何凤山及其同事无私帮助犹太人逃亡的真实故事，" +
                "讲述了1938年中国驻维也纳领事馆以普济州为首的外交官们，顶住重重压力，冒着巨大风险，为犹太难民办理签证的故事...";
        moreView.setData(message);
    }

    @Override
    public void onClick() {
        Toast.makeText(this,"点击了更多",Toast.LENGTH_SHORT).show();
    }
}
