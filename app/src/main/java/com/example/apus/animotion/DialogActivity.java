package com.example.apus.animotion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apus.animotion.animationview.ListItemView;

public class DialogActivity extends AppCompatActivity {
    private ListItemView listItemView;
    private Button know_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        init();
        listItemView.startAnimator();
    }

    //控件初始化
    public void init(){
        listItemView=(ListItemView)findViewById(R.id.list_item_view);
        know_btn=(Button)findViewById(R.id.know_btn);
        know_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
    }

    @Override
    public void onBackPressed() {
        listItemView.stopAnim();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        listItemView.stopAnim();
        finish();
    }
}
