package com.len.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * wangshaolei
 * 自动补全email
 */
public class AutoFillEmailEditText extends AutoCompleteTextView {

    private static String DOMAINS = null;
    private static int DEFAULT_DROP_DOWN_KEY_COLOR = Color.parseColor("#7281a3");
    private static int DEFAULT_DROP_DOWN_BG = 0;
    private static boolean DEFAULT_DROP_DOWN_DIVIDER = true;

    private static String[] emails = null;

    public AutoFillEmailEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStyle(context, attrs);
        initWidget(context);
    }

    public AutoFillEmailEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initStyle(context, attrs);
        initWidget(context);
    }

    private void initStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoFillEmailEditText, 0, 0);
        DOMAINS = typedArray.getString(R.styleable.AutoFillEmailEditText_AutoFillEmailEditText_domains);
        DEFAULT_DROP_DOWN_KEY_COLOR = typedArray.getColor(R.styleable.AutoFillEmailEditText_AutoFillEmailEditText_default_drop_down_key_color, DEFAULT_DROP_DOWN_KEY_COLOR);
        DEFAULT_DROP_DOWN_BG = typedArray.getResourceId(R.styleable.AutoFillEmailEditText_AutoFillEmailEditText_default_drop_down_bg, 0);
        DEFAULT_DROP_DOWN_DIVIDER = typedArray.getBoolean(R.styleable.AutoFillEmailEditText_AutoFillEmailEditText_default_drop_down_divider, true);
        typedArray.recycle();
    }

    private void initWidget(final Context context) {
        if (!TextUtils.isEmpty(DOMAINS) && DOMAINS.contains(",") && DOMAINS.contains("@")) {
            DOMAINS = DOMAINS.replace("/", "");
            emails = DOMAINS.split(",");
        }
        if (DEFAULT_DROP_DOWN_BG != 0) {
            setDropDownBackgroundResource(DEFAULT_DROP_DOWN_BG);
        }
        if (emails == null || emails.length == 0) {
            return;
        }
        setAdapter(new EmailAutoCompleteAdapter(context, R.layout.item_register_email, emails));
        //使得在输入1个字符之后便开启自动完成
        setThreshold(1);
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String text = getText().toString();
                    //当该文本域重新获得焦点后，重启自动完成
                    if (!"".equals(text)) {
                        performFiltering(text, 0);
                    }
                } else {
                    //当文本域丢失焦点后，检查输入email地址的格式
                    AutoFillEmailEditText ev = (AutoFillEmailEditText) v;
                    String text = ev.getText().toString();
                    //这里正则写的有点粗暴:)
                    if (!text.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")) {
                        Toast.makeText(context, "邮件地址格式不正确", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void replaceText(CharSequence text) {
        String t = this.getText().toString();
        int index = t.indexOf("@");
        if (index != -1) {
            t = t.substring(0, index);
        }
        super.replaceText(t + text);
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        String t = text.toString();
        int index = t.indexOf("@");
        if (index == -1) {
            if (t.matches("^[a-zA-Z0-9_]+$")) {
                super.performFiltering("@", keyCode);
            } else {
                dismissDropDown();
            }
        } else {
            super.performFiltering(t.substring(index), keyCode);
        }
    }

    private class EmailAutoCompleteAdapter extends ArrayAdapter<String> {
        @Override
        public String getItem(int position) {
            return emails[position];
        }

        public EmailAutoCompleteAdapter(Context context, int textViewResourceId, String[] email_s) {
            super(context, textViewResourceId, email_s);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_register_email, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_email);
            View divider = convertView.findViewById(R.id.divider);
            if (DEFAULT_DROP_DOWN_DIVIDER) {
                divider.setVisibility(VISIBLE);
            } else {
                divider.setVisibility(GONE);
            }
            if (DEFAULT_DROP_DOWN_KEY_COLOR != 0) {
                tv.setTextColor(DEFAULT_DROP_DOWN_KEY_COLOR);
            }
            String t = getText().toString();
            int index = t.indexOf("@");
            if (index != -1) {
                t = t.substring(0, index);
            }
            tv.setText(t + getItem(position));
            return convertView;
        }
    }
}