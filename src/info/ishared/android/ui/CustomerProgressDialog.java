package info.ishared.android.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import info.ishared.android.R;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-28
 * Time: AM11:10
 */
public class CustomerProgressDialog extends ProgressDialog {

    private TextView mMessageView;
    private LayoutInflater inflater;
    private String message;

    public CustomerProgressDialog(Context context, int theme) {
        super(context, theme);
        inflater = getLayoutInflater();
    }

    public CustomerProgressDialog(Context context) {
        super(context);
        inflater = getLayoutInflater();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_progress_dialog);
        mMessageView = (TextView)this.findViewById(R.id.customer_progress_dialog_message);
        if(this.message!=null){
            mMessageView.setText(message);
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
