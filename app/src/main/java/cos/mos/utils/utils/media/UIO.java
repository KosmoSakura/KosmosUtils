package cos.mos.utils.utils.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cos.mos.utils.utils.java.UText;

/**
 * @Description: <p>
 * @Author: Kosmos
 * @Date: 2018.10.30 20:38
 * @Email: KosmoSakura@gmail.com
 */
public class UIO {
    private static String SD_PATH;

    private static String getSdPath() {
        if (UText.isEmpty(SD_PATH)) {
            SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "QR_Code";
        }
        File fileDr = new File(SD_PATH);
        if (!fileDr.exists()) {
            fileDr.mkdir();
        }
        return SD_PATH;
    }

    /**
     * @param bmp 保存bmp到sd卡
     */
    public static void saveBitmap(Bitmap bmp) {
        File fileDr = new File(getSdPath(), System.currentTimeMillis() + ".png");
        try {
            FileOutputStream out = new FileOutputStream(fileDr);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存目录
     */
    public static String getExternalCacheDir(Context context) {
        File externalFilesDir = context.getExternalCacheDir();
        if (externalFilesDir != null) {
            return externalFilesDir.getPath();
        }
        return "";
    }


    /**
     * @return 为空文件夹
     */
    public static boolean isEmptyDir(File file) {
        return file.exists() && file.isDirectory() && file.listFiles().length <= 0;
    }

    /**
     * @return 为空文件
     */
    public static boolean isEmptyFile(File file) {
        return file.exists() && !file.isDirectory() && file.length() <= 0;
    }

    /**
     * @param name 文件名
     * @return 后缀名
     */
    public static String getSuffix(String name) {
        return name.substring(name.indexOf(".") + 1).toLowerCase();
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? System.currentTimeMillis() + ".apk" : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * @return SD卡总大小
     */
    public static long getSDSize() {
        return getFileSize(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    /**
     * @return SD卡剩余大小
     */
    public static long getSDAvailable() {
        return getFileAvailable(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    /**
     * @param dir 路径
     * @return 返回路径大小
     */
    public static long getFileSize(String dir) {
        return new File(dir).getTotalSpace();//总空间
    }

    /**
     * @param dir 路径
     * @return 返回路径大小
     */
    public static long getFileAvailable(String dir) {
        return new File(dir).getUsableSpace();//剩余空间
    }

    /**
     * @param dir 路径
     * @return 返回路径大小
     */
    public static long getBolockSize(String dir) {
        StatFs fs = new StatFs(dir);
        long totalBolocks;//总的block数量
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            totalBolocks = fs.getBlockCount();
        } else {
            totalBolocks = fs.getBlockCountLong();
        }
        long blockSize = fs.getBlockSize(); //单个block的大小
        return totalBolocks * blockSize;//总空间
    }

    /**
     * @param dir 路径
     * @return 返回路径剩余空间大小
     */
    public static long getBolockAvailable(String dir) {
        StatFs fs = new StatFs(dir);
        long availableBolocks; //可用的blocks的数量
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBolocks = fs.getAvailableBlocks();
        } else {
            availableBolocks = fs.getAvailableBlocksLong();
        }

        long blockSize = fs.getBlockSize(); //单个block的大小
        return availableBolocks * blockSize;//剩余空间
    }
}
