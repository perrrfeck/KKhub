package com.kk.hub.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

/**
 * simple and powerful Keyboard show/hidden listener,view {@android.R.id.content} and {@ViewTreeObserver.OnGlobalLayoutListener}
 * Created by yes.cpu@gmail.com 2016/7/13.
 */
public class KeyboardChangeUtils implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final int MIN_KEYBOARD_HEIGHT = 300;
    private KeyboardListener mKeyboardListener;
    private boolean mShowFlag = false;
    private Window mWindow;
    private View mContentView;

    public interface KeyboardListener {
        /**
         * call back
         *
         * @param isShow         true is show else hidden
         * @param keyboardHeight keyboard height
         */
        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }

    public void setKeyboardListener(KeyboardListener keyboardListener) {
        this.mKeyboardListener = keyboardListener;
    }

    public static KeyboardChangeUtils create(Activity activity) {
        return new KeyboardChangeUtils(activity);
    }

    public static KeyboardChangeUtils create(Dialog dialog) {
        return new KeyboardChangeUtils(dialog);
    }

    private KeyboardChangeUtils(Object contextObj) {
        if (contextObj == null) {
            return;
        }
        if (contextObj instanceof Activity) {
            mContentView = findContentView((Activity) contextObj);
            mWindow = ((Activity) contextObj).getWindow();
        } else if (contextObj instanceof Dialog) {
            mContentView = findContentView((Dialog) contextObj);
            mWindow = ((Dialog) contextObj).getWindow();
        }
        addListener();
    }

    public void addListener() {
        if (mContentView != null && mWindow != null) {
            addContentTreeObserver();
        }
    }

    private View findContentView(Activity contextObj) {
        return contextObj.findViewById(android.R.id.content);
    }

    private View findContentView(Dialog contextObj) {
        return contextObj.findViewById(android.R.id.content);
    }

    public void addContentTreeObserver() {
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @Override
    public void onGlobalLayout() {
        if (mContentView == null || mWindow == null) {
            return;
        }
        int currentViewHeight = mContentView.getHeight();
        if (currentViewHeight == 0) {
            return;
        }
        int screenHeight = getScreenHeight();
        int windowBottom;
        int keyboardHeight;

        Rect rect = new Rect();
        mWindow.getDecorView().getWindowVisibleDisplayFrame(rect);
        windowBottom = rect.bottom;

        keyboardHeight = screenHeight - windowBottom;

        if (mKeyboardListener != null) {
            boolean currentShow = keyboardHeight > MIN_KEYBOARD_HEIGHT;
            if (mShowFlag != currentShow) {
                mShowFlag = currentShow;
                mKeyboardListener.onKeyboardChange(currentShow, keyboardHeight);
            }
        }
    }

    private int getScreenHeight() {
        Display defaultDisplay = mWindow.getWindowManager().getDefaultDisplay();
        int screenHeight = 0;
        Point point = new Point();
        defaultDisplay.getSize(point);
        screenHeight = point.y;
        return screenHeight;
    }


    public void destroy() {
        if (mContentView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        }
    }
}