package info.ishared.android;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import info.ishared.android.bean.ContactsInfo;
import info.ishared.android.bean.NumberType;
import info.ishared.android.util.LogUtils;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Seven
 * Date: 13-3-28
 * Time: PM4:24
 */
public class MainController {




    private MainActivity mainActivity;

    public MainController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


}
