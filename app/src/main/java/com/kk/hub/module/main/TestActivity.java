package com.kk.hub.module.main;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kk.hub.R;
import com.kk.hub.common.utils.ObservableInputUtils;

/**
 * Created by kk on 2019/12/2  11:50
 */
public class TestActivity extends AppCompatActivity {


    private EditText edit;
    private EditText edit2;
    private EditText edit3;
    private EditText edit4;
    private EditText et_first;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        edit = findViewById(R.id.edit);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        edit4 = findViewById(R.id.edit4);
        et_first = findViewById(R.id.et_first);
//, edit2, edit3, edit4
        ObservableInputUtils.observerInput(() -> et_first, this, edit);

    }


}
