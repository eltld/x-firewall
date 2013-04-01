package info.ishared.android.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import info.ishared.android.R;
import info.ishared.android.ui.CustomerProgressDialog;

/**
 * User: Lee
 * Date: 11-9-19
 * Time: 下午2:37
 */
public class AlertDialogUtils {
    public interface Executor {
        void execute();
    }

    /**
     * @param context
     * @param message
     * @param executor
     * @return
     */
    public static void showYesNoDiaLog(Context context, String message, final Executor executor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setCancelable(false).setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                executor.execute();
            }
        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }

    public static void showConfirmDiaLog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setCancelable(true).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }

    public static CustomerProgressDialog createProgressDialog(Context context){
        return  new CustomerProgressDialog(context);
    }

    public static void showInputDialog(Context context, String message, final Executor executor){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_input_layout, null);
        builder.setView(layout).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                executor.execute();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

//        Button button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
//        button.setBackgroundResource(R.drawable.button_background_enabled);
//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.button_background_enabled);

    }

}
