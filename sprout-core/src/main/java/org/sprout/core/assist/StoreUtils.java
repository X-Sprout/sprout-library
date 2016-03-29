/**
 * Created on 2016/2/25
 */
package org.sprout.core.assist;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import org.sprout.SproutLib;
import org.sprout.core.logging.Lc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储工具类
 * <p/>
 *
 * @author Wythe
 */
public final class StoreUtils {

    /**
     * 清空应用缓存
     *
     * @author Wythe
     */
    public static void clearAppCache() {
        StoreUtils.clearTargetFiles(StoreUtils.getInternalCacheDir(), StoreUtils.getExternalCacheDir());
    }

    /**
     * 清空应用数据
     *
     * @author Wythe
     */
    public static void clearAppFiles() {
        StoreUtils.clearTargetFiles(StoreUtils.getInternalFilesDir(), StoreUtils.getExternalFilesDir());
    }

    /**
     * 清空目标文件
     *
     * @param dirs 目标目录或文件
     * @author Wythe
     */
    public static void clearTargetFiles(final File... dirs) {
        if (!ArrayUtils.isEmpty(dirs)) {
            for (final File dir : dirs) {
                if (dir != null && dir.exists()) {
                    if (dir.isFile()) {
                        if (dir.delete() && Lc.D) {
                            Lc.t(SproutLib.name).d("Deleted File -> " + dir.getAbsolutePath());
                        }
                    } else {
                        StoreUtils.clearTargetFiles(dir.listFiles());
                    }
                }
            }
        }
    }

    /**
     * 清空目标文件
     *
     * @param paths 目标路径或文件路径
     * @author Wythe
     */
    public static void clearTargetFiles(final String... paths) {
        if (!ArrayUtils.isEmpty(paths)) {
            final List<File> dirs = new ArrayList<>();
            for (final String path : paths) {
                if (!StringUtils.isEmpty(path)) {
                    dirs.add(new File(path));
                }
            }
            StoreUtils.clearTargetFiles(dirs.toArray(new File[dirs.size()]));
        }
    }

    /**
     * 获得目标文件大小
     *
     * @param dirs 目标目录或文件
     * @return 目标文件大小
     * @author Wythe
     */
    public static long getTargetFilesSize(final File... dirs) {
        int size = 0;
        if (!ArrayUtils.isEmpty(dirs)) {
            for (final File dir : dirs) {
                if (dir != null && dir.exists()) {
                    if (dir.isFile()) {
                        size += dir.length();
                    } else {
                        size += StoreUtils.getTargetFilesSize(dir.listFiles());
                    }
                }
            }
        }
        return size;
    }

    /**
     * 获得目标文件大小
     *
     * @param paths 目标路径或文件路径
     * @return 目标文件大小
     * @author Wythe
     */
    public static long getTargetFilesSize(final String... paths) {
        int size = 0;
        if (!ArrayUtils.isEmpty(paths)) {
            final List<File> dirs = new ArrayList<>();
            for (final String path : paths) {
                if (!StringUtils.isEmpty(path)) {
                    dirs.add(new File(path));
                }
            }
            size += StoreUtils.getTargetFilesSize(dirs.toArray(new File[dirs.size()]));
        }
        return size;
    }

    /**
     * 获得内部缓存路径
     *
     * @return 内部缓存路径
     * @author Wythe
     */
    public static String getInternalCacheDir() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final File file = context.getCacheDir();
            if (file != null) {
                return file.getAbsolutePath() + File.separator;
            }
        }
        return null;
    }

    /**
     * 获得扩展缓存路径
     *
     * @return 扩展缓存路径
     * @author Wythe
     */
    public static String getExternalCacheDir() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final File file = context.getExternalCacheDir();
            if (file != null) {
                return file.getAbsolutePath() + File.separator;
            }
        }
        return null;
    }

    /**
     * 获得内部文件存储路径
     *
     * @return 内部文件存储路径
     * @author Wythe
     */
    public static String getInternalFilesDir() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final File file = context.getFilesDir();
            if (file != null) {
                return file.getAbsolutePath() + File.separator;
            }
        }
        return null;
    }

    /**
     * 获得扩展文件存储路径
     *
     * @return 扩展文件存储路径
     * @author Wythe
     */
    public static String getExternalFilesDir() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final File file = context.getExternalFilesDir(null);
            if (file != null) {
                return file.getAbsolutePath() + File.separator;
            }
        }
        return null;
    }

    /**
     * 获得设备存储路径
     *
     * @return 设备存储路径
     * @author Wythe
     */
    public static String getMEDiskPath() {
        final File file = Environment.getDataDirectory();
        if (file != null) {
            return file.getAbsolutePath() + File.separator;
        }
        return null;
    }

    /**
     * 获得设备存储大小
     *
     * @param available 是否可用
     * @return 设备存储大小
     * @author Wythe
     */
    public static long getMEDiskSize(final boolean available) {
        final File file = Environment.getDataDirectory();
        if (file != null) {
            final StatFs stat = new StatFs(file.getPath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                if (!available) {
                    return stat.getTotalBytes();
                } else {
                    return stat.getAvailableBytes();
                }
            } else {
                if (!available) {
                    return stat.getBlockCount() * stat.getBlockSize();
                } else {
                    return stat.getAvailableBlocks() * stat.getBlockSize();
                }
            }
        }
        return -1;
    }

    /**
     * 获得扩展存储路径
     *
     * @param useable 是否可用
     * @return 扩展存储路径
     * @author Wythe
     */
    public static String getSDCardPath(final boolean useable) {
        if (StoreUtils.isSDCardReady()) {
            final File file = Environment.getExternalStorageDirectory();
            if (file != null) {
                return file.getAbsolutePath() + File.separator;
            }
        } else {
            if (!useable) {
                final StoreUtils.SDCardInfo sdCardInfo = SDCardMountInfo.Instance.getSDCardInfo();
                if (sdCardInfo != null) {
                    final String sdCardPath = sdCardInfo.mount_path.trim();
                    if (!StringUtils.isEmpty(sdCardPath)) {
                        return sdCardPath + File.separator;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获得扩展存储大小
     *
     * @param available 是否可用
     * @return 扩展存储大小
     * @author Wythe
     */
    public static long getSDCardSize(final boolean available) {
        if (StoreUtils.isSDCardReady()) {
            final File file = Environment.getExternalStorageDirectory();
            if (file != null) {
                final StatFs stat = new StatFs(file.getPath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    if (!available) {
                        return stat.getTotalBytes();
                    } else {
                        return stat.getAvailableBytes();
                    }
                } else {
                    if (!available) {
                        return stat.getBlockCount() * stat.getBlockSize();
                    } else {
                        return stat.getAvailableBlocks() * stat.getBlockSize();
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 扩展存储是否共享
     *
     * @return 是否共享
     * @author Wythe
     */
    public static boolean isSDCardShare() {
        return Environment.MEDIA_SHARED.equals(Environment.getExternalStorageState());
    }

    /**
     * 扩展存储是否可用
     *
     * @return 是否可用
     * @author Wythe
     */
    public static boolean isSDCardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 扩展存储是否只读
     *
     * @return 是否只读
     * @author Wythe
     */
    public static boolean isSDCardReadonly() {
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }

    /**
     * 扩展存储是否可卸载
     *
     * @return 是否可卸载
     * @author Wythe
     */
    public static boolean isSDCardRemovable() {
        return Environment.isExternalStorageRemovable();
    }

    private final static class SDCardInfo {
        final String mount_label, mount_path, sysfs_path, mount_point;

        private SDCardInfo(final String mount_label, final String mount_path, final String sysfs_path, final String mount_point) {
            this.mount_label = mount_label;
            this.mount_path = mount_path;
            this.sysfs_path = sysfs_path;
            this.mount_point = mount_point;
        }
    }

    private enum SDCardMountInfo {

        Instance;

        private final static int MOUNT_LABEL = 1;

        private final static int MOUNT_PATH = 2;

        private final static int SYSFS_PATH = 4;

        private final static int MOUNT_POINT = 3;

        private final static String TYPE_MOUNT = "dev_mount";

        private final static String VOLD_FSTAB = Environment.getRootDirectory().getAbsoluteFile() + File.separator + "etc" + File.separator + "vold.fstab";

        final SDCardInfo getSDCardInfo() {
            final List<String> sdcards = this.readVoldFstab();
            if (!CollectionUtils.isEmpty(sdcards)) {
                final String[] sdinfo = sdcards.get(0).split(StringUtils.BLANK);
                if (!ArrayUtils.isEmpty(sdinfo)) {
                    return new SDCardInfo(sdinfo[MOUNT_LABEL], sdinfo[MOUNT_PATH], sdinfo[SYSFS_PATH], sdinfo[MOUNT_POINT]);
                }
            }
            return null;
        }

        final ArrayList<String> readVoldFstab() {
            if (VOLD_FSTAB != null) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(VOLD_FSTAB));
                } catch (Exception e) {
                    reader = null;
                } finally {
                    if (reader != null) {
                        final ArrayList<String> cacher = new ArrayList<>();
                        try {
                            String txt;
                            while ((txt = reader.readLine()) != null) {
                                if (txt.startsWith(TYPE_MOUNT)) {
                                    cacher.add(txt);
                                }
                            }
                        } catch (Exception e) {
                            cacher.clear();
                            if (Lc.E) {
                                Lc.t(SproutLib.name).e(e);
                            }
                        } finally {
                            cacher.trimToSize();
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return cacher;
                    }
                }
            }
            return null;
        }
    }

}
