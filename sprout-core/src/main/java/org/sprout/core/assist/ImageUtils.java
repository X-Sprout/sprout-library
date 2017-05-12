/**
 * Created on 2016/2/25
 */
package org.sprout.core.assist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import org.sprout.SproutLib;

import java.io.ByteArrayOutputStream;

/**
 * 图片工具类
 * <p/>
 *
 * @author Wythe
 */
public final class ImageUtils {

    /**
     * 字节流转化为图片对象
     *
     * @param bytes 字节流
     * @return 图片对象
     * @author Wythe
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        if (!ArrayUtils.isEmpty(bytes)) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return null;
    }

    /**
     * 图片对象转化为字节流
     * (默认高品质)
     *
     * @param bitmap 图片对象
     * @param format 图片格式
     * @return 字节流
     * @author Wythe
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        return ImageUtils.bitmap2Bytes(bitmap, format, 100);
    }

    /**
     * 图片对象转化为字节流
     *
     * @param bitmap  图片对象
     * @param format  图片格式
     * @param quality 图片品质(0-100)
     * @return 字节流
     * @author Wythe
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format, final int quality) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap != null && format != null && quality >= 0) {
            bitmap.compress(format, quality, baos);
        }
        return baos.toByteArray();
    }

    /**
     * 图片对象转化为Base64编码
     * (默认高品质)
     *
     * @param bitmap 图片对象
     * @param format 图片格式
     * @return Base64编码
     * @author Wythe
     */
    public static String bitmap2Base64(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        return ImageUtils.bitmap2Base64(bitmap, format, 100);
    }

    /**
     * 图片对象转化为Base64编码
     *
     * @param bitmap  图片对象
     * @param format  图片格式
     * @param quality 图片品质(0-100)
     * @return Base64编码
     * @author Wythe
     */
    public static String bitmap2Base64(final Bitmap bitmap, final Bitmap.CompressFormat format, final int quality) {
        return ImageUtils.bitmap2Base64(bitmap, format, quality, Base64.DEFAULT);
    }

    /**
     * 图片对象转化为Base64编码
     *
     * @param bitmap  图片对象
     * @param format  图片格式
     * @param quality 图片品质(0-100)
     * @param enctype 编码方式
     * @return Base64编码
     * @author Wythe
     */
    public static String bitmap2Base64(final Bitmap bitmap, final Bitmap.CompressFormat format, final int quality, final int enctype) {
        final byte[] bytes = ImageUtils.bitmap2Bytes(bitmap, format, quality);
        if (ArrayUtils.isEmpty(bytes)) {
            return StringUtils.EMPTY;
        } else {
            return Base64.encodeToString(bytes, enctype);
        }
    }

    /**
     * 图片对象转化为绘制对象
     *
     * @param bitmap 图片对象
     * @return 绘制对象
     * @author Wythe
     */
    public static Drawable bitmap2Drawable(final Bitmap bitmap) {
        if (bitmap != null) {
            final Context context = SproutLib.getContext();
            if (context != null) {
                return new BitmapDrawable(context.getResources(), bitmap);
            }
        }
        return null;
    }

    /**
     * 绘制对象转化为图片对象
     *
     * @param drawable 绘制对象
     * @return 图片对象
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable != null) {
            final int w = drawable.getIntrinsicWidth(), h = drawable.getIntrinsicHeight();
            if (w > 0 && h > 0) {
                final Bitmap bitmap = Bitmap.createBitmap(w, h, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                if (bitmap != null) {
                    final Canvas canvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, w, h);
                    drawable.draw(canvas);
                    return bitmap;
                }
            }
        }
        return null;
    }

}
