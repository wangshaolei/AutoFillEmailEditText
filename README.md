# AutoFillEmailEditText
Auto fill the custom email to EditText

##Step1

```xml
<declare-styleable name="AutoFillEmailEditText">
    <attr name="AutoFillEmailEditText_domains" format="string"/> //email's domain, split ','(en) and first char must be with '/'
    <attr name="AutoFillEmailEditText_default_drop_down_key_color" format="color"/>//drop down word color
    <attr name="AutoFillEmailEditText_default_drop_down_bg" format="reference"/>//drop down bg
    <attr name="AutoFillEmailEditText_default_drop_down_divider" format="boolean"/>//drop down divider
</declare-styleable>
```

##Step2
```xml
<com.len.library.AutoFillEmailEditText
  android:id="@+id/et_mail"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:layout_marginLeft="20dp"
  android:layout_marginRight="20dp"
  android:layout_marginTop="10dp"
  android:drawablePadding="10dp"
  android:hint="邮箱地址"
  android:singleLine="true"
  android:textSize="15sp"
  app:AutoFillEmailEditText_default_drop_down_divider="true"
  app:AutoFillEmailEditText_domains="/@qq.com,@gmail.com,@126.com,@163.com" //notice
/>
```

![](https://github.com/wangshaolei/AutoFillEmailEditText/blob/master/img/1.png)

