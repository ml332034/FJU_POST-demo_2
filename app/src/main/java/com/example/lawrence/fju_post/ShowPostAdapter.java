package com.example.lawrence.fju_post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPostAdapter extends BaseAdapter {
    Context c;
    String[] names={"[健康促進]","[國際交流]","[職涯發展]","[職涯發展]","[自我成長]","校内最新消息通知"};
    String[] price={"106學年度第2學期珍愛生命守門人培課課程","日本的語言藝術","暑假營隊幹部訓練",
            "校级活动，化工电影本周放啥？艺设妹子有什么动向？点我查看","職場美學工作坊","文學院教學助理培訓活動"};
    int[] images={R.drawable.ic_fiber_new_black_24dp,R.drawable.ic_fiber_new_black_24dp,
            R.drawable.ic_fiber_new_black_24dp,R.drawable.ic_fiber_new_black_24dp,R.drawable.ic_fiber_new_black_24dp,
            R.drawable.ic_fiber_new_black_24dp};


    public ShowPostAdapter(Context ctx)
    {
        this.c=ctx;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int pos) {
        return names[pos];
    }

    @Override
    public long getItemId(int pos) {
        return pos ;
    }

    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {
        if (convertview == null)
        {
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview=inflater.inflate(R.layout.tab_listview_item,null);
        }
        //get view
        TextView nametxt=(TextView)convertview.findViewById(R.id.name);
        TextView pricetxt=(TextView)convertview.findViewById(R.id.price);
        ImageView ing =(ImageView)convertview.findViewById(R.id.imgid);

        //set data
        nametxt.setText(names[pos]);
        pricetxt.setText(price[pos]);
        ing.setImageResource(images[pos]);


        return convertview;
    }
}
