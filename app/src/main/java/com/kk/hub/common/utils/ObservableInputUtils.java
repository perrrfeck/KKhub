package com.kk.hub.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


/**
 * Create by liurp on 2019/12/2
 * 适配软键盘弹出时 标题向上顶起与手机状态栏重合的问题
 */
public final class ObservableInputUtils {

    private static boolean isHide;
    private static int changedHeight;

    private ObservableInputUtils() {
        throw new UnsupportedOperationException("can not be instance");
    }

    /**
     * @param onMoveOptions 接口，传入软键盘弹起时需要移动的ViewGroup
     * @param activity      懂的自然懂
     * @param editTexts     适配同一页面单个或多个EditText
     */
    public static void observerInput(final OnMoveOptions onMoveOptions, final Activity activity, EditText editTexts) {

        if (activity == null || activity.isFinishing()) return;
        getScreenHeight(activity);
        final View rootView = activity.findViewById(android.R.id.content);
        Log.d("kk", "ROOTheight: "+rootView.getHeight());
        KeyboardChangeUtils.create(activity).setKeyboardListener(
                (isShow, keyboardHeight) -> {
                    View targetMoveView = onMoveOptions.onMoveView();
                    if (isShow) {
                        View focusView =editTexts;
                        int focus_height = focusView.getHeight();
                        int[] et_location = new int[2];
                        focusView.getLocationOnScreen(et_location);
                        int rootHeight = rootView.getHeight();
                        int curBottom = et_location[1] + focus_height;
                        int offSetRoot = rootHeight - keyboardHeight;
                        int offSet = offSetRoot - curBottom;
                        Log.d("kk2", "rootHeight: " + rootHeight + "...keyboardHeight" + keyboardHeight);
                        Log.d("kk2", "et_location: " + et_location[1] + "..focus_height" + focus_height + "offSetRoot.." + offSetRoot);
                        Log.d("kk2", "offSet: " + offSet);
                        if (offSet < 0) {
                            Log.d("kk2", "遮住了 ");
                        } else {
                            Log.d("kk2", "没遮住 ");
                        }

//                        onTargetMove(targetMoveView, focusView, offSet, focus_height);

                    } else {
//                        if (isHide) {
//                            onHide(targetMoveView);
//                        }
                    }
                });
    }


    private static void getScreenHeight(Activity activity) {
        Rect outSize = new Rect();
        activity.getWindowManager().getDefaultDisplay().getRectSize(outSize);
        int left = outSize.left;
        int top = outSize.top;
        int right = outSize.right;
        int bottom = outSize.bottom;
        Log.d("kk2", "left = " + left + ",top = " + top + ",right = " + right + ",bottom = " + bottom);
    }

    private static void onTargetMove(View targetMoveView, View focusView, int offSet, int focus_height) {
        LinearLayout.LayoutParams moveParams = (LinearLayout.LayoutParams) targetMoveView.getLayoutParams();
        //部分
        if (offSet > 0) {
            changedHeight = offSet - focus_height;
            moveParams = (LinearLayout.LayoutParams) targetMoveView.getLayoutParams();
            moveParams.topMargin = changedHeight - focus_height;
            isHide = true;
        } else {
            //全部
            LinearLayout.LayoutParams marginParams = (LinearLayout.LayoutParams) focusView.getLayoutParams();
            changedHeight = -offSet + focus_height;
            moveParams.topMargin = -Math.abs(changedHeight) - (focus_height * 2) - marginParams.topMargin;
            isHide = true;
        }
        targetMoveView.setLayoutParams(moveParams);
    }


    private static void onHide(View targetMoveView) {
        if (targetMoveView != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) targetMoveView.getLayoutParams();
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;

            targetMoveView.setLayoutParams(layoutParams);
        }
        isHide = false;
        changedHeight = 0;
    }

    private static EditText findFocusView(EditText... editTexts) {
        for (EditText editText : editTexts) {
            boolean hasFocus = editText.hasFocus();
            if (hasFocus){
                return editText;
            }
        }
        return null;
    }


    public interface OnMoveOptions {
        //需要移动的View
        View onMoveView();
    }

}
