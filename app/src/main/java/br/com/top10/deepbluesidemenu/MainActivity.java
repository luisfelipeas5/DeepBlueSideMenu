package br.com.top10.deepbluesidemenu;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.top10.deepbluesidemenu.databinding.ActivityMainBinding;
import br.com.top10.deepbluesidemenu.databinding.LayoutContentBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LayoutContentBinding contentBinding = mBinding.includeLayoutContent;
        contentBinding.imgSideMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_side_menu:
                mBinding.deepBlueLayout.switchMode();
                break;
        }
    }
}
