package com.qwh.findtutor.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPickerView;

public class Utils {

    /**
     * 创建目录
     *
     * @param path
     */
    public static void createDirs(File path) {
        if (path != null && !path.exists()) {
            path.mkdirs();
        }
    }

    /**
     * 弹出提示对话框
     *
     * @param tel
     */
    public static void ShowCallPhoneDialog(final Context context, final String tel) {
        new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题
                .setMessage("电话:" + tel)//设置显示的内容
                .setPositiveButton("拨打", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                        Intent phoneIntent = new Intent("android.intent.action.CALL",
                                Uri.parse("tel:" + tel));
                        //启动
                        context.startActivity(phoneIntent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件
                // TODO Auto-generated method stub
                Log.i("alertdialog", " 请保存数据！");
            }
        }).show();//在按键响应事件中显示此对话框
    }

    /**
     * 文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExist(File file) {
        if (file != null && file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * �?��sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }


    /**
     * 验证手机号格式是否正�?
     *
     * @param mobileNumber
     * @return
     */
    public static boolean validateMobileNumber(String mobileNumber) {
        if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", mobileNumber)) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符�?是否适合某种格式
     *
     * @param expression 正则表达�?
     * @param text       操作的字符串
     * @return
     */
    private static boolean matchingText(String expression, String text) {
        Pattern p = Pattern.compile(expression); // 正则表达�?
        Matcher m = p.matcher(text); // 操作的字符串
        boolean b = m.matches();
        return b;
    }

    /**
     * 判断网络状态
     */
    public static boolean hasNetwork(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }
        return false;
    }

    /**
     * 查看网络状态
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); //获取系统网络连接管理�?
        if (connectivity == null) { //如果网络管理器为null
            return false; //返回false表明网络无法连接
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo(); //获取�?��的网络连接对�?
            if (info != null) { //网络信息不为null�?
                for (int i = 0; i < info.length; i++) { //遍历网路连接对象
                    if (info[i].isConnected()) { //当有�?��网络连接对象连接上网络时
                        return true; //返回true表明网络连接正常
                    }
                }
            }
        }
        return false;
    }

    public static boolean isMobileNetworkAvailable(Context context) {
        //获取应用上下文
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取系统的连接服器
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        //获取网络的连接情况
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            //判断3G网络
            return true;
        }
        return false;
    }

    /**
     * 版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String name = appInfo.metaData.getString("version_name");
            if (name != null) {
                return name;
            }
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 渠道
     *
     * @param context
     * @param metaName
     * @return
     */
    public static int getChannel(Context context, String metaName) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getInt(metaName);

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 字符串转成int
     *
     * @param str
     * @return
     */
    public static int parseStr2Int(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 字符串转成int
     *
     * @param str
     * @return
     */
    public static float parseStr2Float(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 判断字符串是否是合法16进制
     *
     * @param str
     * @return
     * @author: Xue Wenchao
     * @return: boolean
     * @date: 2014-1-21 上午10:13:23
     */
    public static boolean isHexString(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.matches("^[0-9a-fA-F]++$", str);
    }

    /**
     * 字符串转成Long
     *
     * @param str
     * @return
     */
    public static long parseStr2Long(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 隐藏输入键盘
     *
     * @param view
     * @param context
     */
    public static void hideSoftInput(EditText view, Context context) {
        InputMethodManager inputMeMana = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMeMana.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMeMana = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMeMana.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 城市选择
     *
     * @param context 上下文
     * @param view    当前view
     */
    public static void showCityPick(Context context, final Button view) {
        CityPickerView cityPickerView = new CityPickerView(context);
        cityPickerView.show();
        cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                if (!citySelected[1].equals("市辖区") && !citySelected[1].equals("县")) {
                    view.setText(citySelected[1] + citySelected[2]);
                } else {
                    view.setText(citySelected[0] + citySelected[2]);
                }
                view.setTag(citySelected[3]);//邮编
                Log.i("citypick", "showCityPick: " + citySelected[3]);
            }
        });
    }

    /**
     * 城市选择
     *
     * @param context 上下文
     * @param view    当前view
     */
    public static void setChooseCity(Context context, TextView view) {
        setChooseCity(context, view, false);
    }

    /**
     * 城市选择
     *
     * @param context 上下文
     * @param view    当前view
     */
    public static void setChooseCity(final Context context, final TextView view, final boolean isChange) {
        final CityPickerView cityPickerView = new CityPickerView(context);
        cityPickerView.show();
        cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                if (!citySelected[1].equals("市辖区") && !citySelected[1].equals("县")) {
                    view.setText(citySelected[1] + citySelected[2]);
                } else {
                    view.setText(citySelected[0] + citySelected[2]);
                }
                view.setTag(citySelected[3]);//邮编
                Log.i("citypick", "showCityPick: " + citySelected[3]);
                if (isChange) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.setText("福州");
                                        Toast.makeText(context, "目前仅支持福州市区...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * 计算字符个数，一个汉字算两个
     *
     * @param s
     * @return
     */
    public static int countWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length(), a = 0, b = 0;
        int len = 0;
        char c;
        for (int i = 0; i < n; i++) {
            c = s.charAt(i);
            if (Character.isSpaceChar(c)) {
                ++b;
            } else if (isAscii(c)) {
                ++a;
            } else {
                ++len;
            }
        }
        return len + (int) Math.ceil((a + b) / 2.0);
    }

    public static boolean isAscii(char c) {
        return c <= 0x7f;
    }

    /**
     * 验证邮箱地址是否合法
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * MD5字符串加密
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().toLowerCase();
    }


    /**
     * 获取当前时间戳
     */
    public static String getCurrentTimeMillis() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return System.currentTimeMillis() + "";
    }


    /**
     * 过滤文本中的html脚本信息
     *
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_html1;
        Matcher m_html1;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{�?script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{�?style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符�?
    }


    /**
     * 拷贝�?
     *
     * @param is
     * @param os
     */
    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        if (is == null || os == null) {
            return;
        }
        BufferedInputStream bufIs;
        boolean shouldClose = false;
        if (is instanceof BufferedInputStream) {
            bufIs = (BufferedInputStream) is;
        } else {
            bufIs = new BufferedInputStream(is);
            shouldClose = true;
        }

        int bufLen = 102400;
        byte[] buf = new byte[bufLen];
        int len;
        while (true) {
            len = bufIs.read(buf);
            if (len < 0) {
                break;
            }
            os.write(buf, 0, len);
        }
        if (shouldClose) {
            bufIs.close();
        }
    }

    /**
     * 得到屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getWinWidth(Activity context) {
        // TODO Auto-generated constructor stub
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 得到屏幕高度
     *
     * @param context
     * @return
     */
    public static int getWinHight(Activity context) {
        // TODO Auto-generated constructor stub
        return context.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static int calculateCharLength(String src) {
        int counter = -1;
        if (src != null) {
            counter = 0;
            final int len = src.length();
            for (int i = 0; i < len; i++) {
                char sigleItem = src.charAt(i);
                if (isAlphanumeric(sigleItem)) {
                    counter++;
                } else if (Character.isLetter(sigleItem)) {
                    counter = counter + 2;
                } else {
                    counter++;
                }
            }
        } else {
            counter = -1;
        }

        return counter;
    }

    /**
     * 判断字符是否为英文字母或者阿拉伯数字.
     *
     * @param ch char字符
     * @return true or false
     */
    public static boolean isAlphanumeric(char ch) {
        // 常量定义
        final int DIGITAL_ZERO = 0;
        final int DIGITAL_NINE = 9;
        final char MIN_LOWERCASE = 'a';
        final char MAX_LOWERCASE = 'z';
        final char MIN_UPPERCASE = 'A';
        final char MAX_UPPERCASE = 'Z';

        if ((ch >= DIGITAL_ZERO && ch <= DIGITAL_NINE) || (ch >= MIN_LOWERCASE && ch <= MAX_LOWERCASE)
                || (ch >= MIN_UPPERCASE && ch <= MAX_UPPERCASE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * decode js用escape编码的字符串
     *
     * @param src
     * @return
     * @method: unEscape
     * @description: TODO
     * @author: DongFuhai
     * @return: String
     * @date: 2013-10-14 下午5:57:56
     */
    public static String unEscape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }


    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    public static boolean judgePhoneNums(String phoneNums) {
//        if (isMatchLength(phoneNums, 11)) {
//            return true;
//        }
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、7,8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

}
