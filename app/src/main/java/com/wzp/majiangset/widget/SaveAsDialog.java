package com.wzp.majiangset.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wzp.majiangset.R;
import com.wzp.majiangset.constant.ProjectConstants;

/**
 * Created by wzp on 2017/9/21.
 */

public class SaveAsDialog extends Dialog {

    private EditText edtFilename;
    private Button btnConfirm;


    public SaveAsDialog(@NonNull Context context) {
        super(context, R.style.MyDialogBaseTheme);
        init(context);
    }

    public SaveAsDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected SaveAsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_save_as, null);

        edtFilename = (EditText) view.findViewById(R.id.edt_filename);
        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);

        setContentView(view);
    }

    public void setPositiveButton(android.view.View.OnClickListener listener) {
        btnConfirm.setOnClickListener(listener);
    }

    public String getFilename() {
        return edtFilename.getEditableText().toString().trim();
    }

    /**
     * 对输入的文件名进行正则表达式校验
     *
     * @return
     */
    public boolean isFilenameValidate() {
        String filename = getFilename();
        return filename.matches(ProjectConstants.lettNumbUnde);
    }
}
