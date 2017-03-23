package utils;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import view.CusBoardView;

/**
 * Created by zchao on 2017/3/2.
 * desc:
 * version:
 */

public class KeyUtil {
    private Context ctx;
    private Activity act;
    private CusBoardView keyboardView;

    private EditText ed;

    public KeyUtil(Activity act, Context ctx, EditText edit, CusBoardView keyboardView) {
        this.act = act;
        this.ctx = ctx;
        this.ed = edit;
        this.keyboardView = keyboardView;
        keyboardView.setEnabled(true);
        keyboardView.setOnKeyboardActionListener(listener);
    }

    private CusBoardView.OnKeyboardActionListener listener = new CusBoardView.OnKeyboardActionListener() {
        @Override
        public void onKey(int primaryCode) {
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                hideKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            }else if (primaryCode == 4896) {// 清除
                editable.clear();
            }else if (primaryCode == 57419) { // go left
                if (start > 0) {
                    ed.setSelection(start - 1);
                }
            } else if (primaryCode == 57421) { // go right
                if (start < ed.length()) {
                    ed.setSelection(start + 1);
                }
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }
    };

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isword(String str){
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase())>-1) {
            return true;
        }
        return false;
    }
}
