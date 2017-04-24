package com.example.administrator.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapplication.bean.User;
import com.example.administrator.myapplication.utils.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    List<User> list;
    ArrayAdapter<User> arrayAdapter;
    List<User> mList;
    DBManager dbManager;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        iv = (ImageView) findViewById(R.id.iv);
        init();
        //今天是12：01
    }

    //初始化数据
    private void init() {
        list = new ArrayList<>();
        mList = new ArrayList<>();
        dbManager = DBManager.getInstance(MainActivity.this);
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("name=" + i);
            user.setAge(i);
            list.add(user);
        }
        arrayAdapter = new ArrayAdapter<User>(MainActivity.this, android.R.layout.simple_list_item_1, mList);
        lv.setAdapter(arrayAdapter);

    }

    public void showToast(String content) {
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
    }


    public void btn(View view) {
        switch (view.getId()) {
            case R.id.btn_query://查询
                showToast("查询");
                showList();
                break;
            case R.id.btn_add://添加
                showToast("添加");
                dbManager.insertUserList(list);
                showList();

                break;
            case R.id.btn_delete://删除
                showToast("删除");
                if (mList == null || mList.size() <= 0) return;
                User user = mList.get(0);
                dbManager.deleteUser(user);
                showList();

                break;
            case R.id.btn_jietu://截图
                Bitmap bitmap = shotActivity(MainActivity.this);
                if (bitmap != null)

                    iv.setImageBitmap(bitmap);

                break;

            case R.id.iv://图片
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                iv.setLayoutParams(params);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
        }
    }

    /**
     * shot the current screen ,with the status but the status is trans *
     *
     * @param ctx current activity
     */
    public static Bitmap shotActivity(Activity ctx) {

        View view = ctx.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(),
                view.getMeasuredHeight());

        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return bp;
    }

    private void showList() {
        List<User> querylist = dbManager.queryUserList();
        if (querylist != null && querylist.size() > 0) {
            Log.e("123", "querylist=" + querylist.toString());
            mList.clear();
            mList.addAll(querylist);
            arrayAdapter.notifyDataSetInvalidated();
        }

    }
}
