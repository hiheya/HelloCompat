## 实现
1. 在activity_main.xml文件中实现布局
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/hello_textview"
        android:text="Hello World!"
        android:textStyle="bold"
        android:textAlignment="center"
        android:textSize="100sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:gravity="center_horizontal" />

    <Button
        android:id="@+id/color_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="changeColor"
        android:text="change color"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        tools:ignore="MissingConstraints,OnClick" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TextView"
        android:textSize="30sp"
        app:layout_constraintBottom_toTopOf="@+id/color_button"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/hello_textview" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
2. 在MainActivity.java中实现主活动。
```java
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
```
3. 在colors.xml文件中添加颜色资源。
```xml
<color name="red">#F44336</color>
    <color name="pink">#E91E63</color>
    <color name="purple">#9C27B0</color>
    <color name="deep_purple">#673AB7</color>
    <color name="indigo">#3F51B5</color>
    <color name="blue">#2196F3</color>
    <color name="light_blue">#03A9F4</color>
    <color name="cyan">#00BCD4</color>
    <color name="teal">#009688</color>
    <color name="green">#4CAF50</color>
    <color name="light_green">#8BC34A</color>
    <color name="lime">#CDDC39</color>
    <color name="yellow">#FFEB3B</color>
    <color name="amber">#FFC107</color>
    <color name="orange">#FF9800</color>
    <color name="deep_orange">#FF5722</color>
    <color name="brown">#795548</color>
    <color name="grey">#9E9E9E</color>
    <color name="blue_grey">#607D8B</color>
```
## 代码解析
1. 先通过Random类生成一个0-20的随机数，然后通过这个数来获取colorName并保存起来。
```java
Random random = new Random();
// 获取一个0-20的随机数，获取到color name 存放在colorName 字符串当中。
String colorName = mColorArray[random.nextInt(20)];
```
2. 拿到colorName之后去包里面获取其ResourceID；
```java
// 通过colorName来从当前包中获取color名为 colorName的ResourceID；
int colorResourceName = getResources().getIdentifier(colorName,"color", getApplicationContext().getPackageName());
```
3. 最后通过 ResourceID get到颜色资源的色值（16进制）
```java
// 拿到ResourceID后即可get到具体的色值（十六进制）；
int colorRes = ContextCompat.getColor(this, colorResourceName);
text = text.concat(Integer.toHexString(colorRes));
```
4. 将获取到的值给textView
```java
mTextView.setText(text);
mTextView.setTextColor(colorRes);
mHelloTextView.setTextColor(colorRes);
```