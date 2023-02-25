package work.icu007.hellocompat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mHelloTextView;
    private TextView mTextView;
    private String[] mColorArray = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloTextView = findViewById(R.id.hello_textview);
        mTextView = findViewById(R.id.textView);
        if (savedInstanceState != null){
            mHelloTextView.setTextColor(savedInstanceState.getInt("color"));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save the color;
        outState.putInt("color",mHelloTextView.getCurrentTextColor());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void changeColor(View view) {
        String text = " ";
        Random random = new Random();
        // 获取一个0-20的随机数，获取到color name 存放在colorName 字符串当中。
        String colorName = mColorArray[random.nextInt(20)];
        text = text.concat("color name: " + colorName  + "\n ResourceID:");
        // 通过colorName来从当前包中获取color名为 colorName的ResourceID；
        int colorResourceName = getResources().getIdentifier(colorName,"color", getApplicationContext().getPackageName());

        text = text.concat(Integer.toString(colorResourceName) + "\n colorRes:");

//        int colorRes = getResources().getColor(colorResourceName,this.getTheme());

        // 拿到ResourceID后即可get到具体的色值（十六进制）；
        int colorRes = ContextCompat.getColor(this, colorResourceName);
        text = text.concat(Integer.toHexString(colorRes));

        mTextView.setText(text);
        mTextView.setTextColor(colorRes);
        mHelloTextView.setTextColor(colorRes);
    }
}