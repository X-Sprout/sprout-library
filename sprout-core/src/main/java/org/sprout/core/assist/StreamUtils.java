/**
 * Created on 2016/2/29
 */
package org.sprout.core.assist;

import org.sprout.SproutLib;
import org.sprout.core.logging.Lc;

import java.util.ArrayList;
import java.util.List;

/**
 * 流处理工具类
 * <p/>
 *
 * @author Wythe
 */
public final class StreamUtils {

    // 255
    private final static int MAX = 0xFF;

    /**
     * 格式化十六进制（字节）
     *
     * @param single 十六进制
     * @return 字节
     * @author Wythe
     */
    public static byte hex2Byte(final String single) {
        if (!StringUtils.isEmpty(single)) {
            try {
                return (byte) Integer.parseInt(single, 16);
            } catch (NumberFormatException e) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
            }
        }
        return ObjectUtils.defaultByte();
    }

    /**
     * 格式化字节（十六进制）
     * <p/>
     *
     * @param single 字节
     * @return 十六进制字符串
     * @author Wythe
     */
    public static String byte2Hex(final byte single) {
        return String.format("%02x", single & StreamUtils.MAX);
    }

    /**
     * 格式化字节（十六进制）
     * <p/>
     *
     * @param single 字节
     * @return 十六进制字符串
     * @author Wythe
     */
    public static String byte2Hex(final Byte single) {
        return StreamUtils.byte2Hex(single == null ? ObjectUtils.defaultByte() : single.byteValue());
    }

    /**
     * 格式化十六进制（字节流）
     * <p/>
     *
     * @param hexstr 十六进制
     * @return 字节流
     * @author Wythe
     */
    public static byte[] hex2Bytes(final String hexstr) {
        return StreamUtils.hex2Bytes(hexstr, null);
    }

    /**
     * 格式化字节流（十六进制）
     * <p/>
     *
     * @param stream 字节流
     * @return 十六进制字符串
     * @author Wythe
     */
    public static String bytes2Hex(final byte[] stream) {
        return StreamUtils.bytes2Hex(stream, null);
    }

    /**
     * 格式化字节流（十六进制）
     * <p/>
     *
     * @param stream 字节流
     * @return 十六进制字符串
     * @author Wythe
     */
    public static String bytes2Hex(final Byte[] stream) {
        return StreamUtils.bytes2Hex(stream, null);
    }

    /**
     * 格式化十六进制（字节流）
     * <p/>
     *
     * @param hexstr 十六进制
     * @param splice 连接符
     * @return 字节流
     * @author Wythe
     */
    public static byte[] hex2Bytes(final String hexstr, final String splice) {
        if (!StringUtils.isEmpty(hexstr)) {
            if (!StringUtils.isEmpty(splice)) {
                final String[] arr = StringUtils.split(hexstr, splice);
                if (arr != null) {
                    final int l = arr.length;
                    if (l > 0) {
                        final byte[] bytes = new byte[l];
                        for (int i = 0; i < l; i++) {
                            bytes[i] = StreamUtils.hex2Byte(arr[i]);
                        }
                        return bytes;
                    }
                }
            } else {
                final int l = hexstr.length();
                if (l % 2 == 0) {
                    final byte[] bytes = new byte[l / 2];
                    final char[] chars = hexstr.toCharArray();
                    for (int i = 0, p = 0, s = chars.length; i < s; i += 2, p++) {
                        bytes[p] = StreamUtils.hex2Byte(String.copyValueOf(chars, i, 2));
                    }
                    return bytes;
                }
            }
        }
        return ArrayUtils.EMPTY_byte_ARRAY;
    }

    /**
     * 格式化字节流（十六进制）
     *
     * @param stream 字节流
     * @param splice 连接符
     * @return Wythe
     */
    public static String bytes2Hex(final byte[] stream, final String splice) {
        final List<String> hex = new ArrayList<>();
        for (final byte b : stream) {
            hex.add(StreamUtils.byte2Hex(b));
        }
        return StringUtils.join(hex, splice == null ? StringUtils.COMMA : splice);
    }

    /**
     * 格式化字节流（十六进制）
     *
     * @param stream 字节流
     * @param splice 连接符
     * @return Wythe
     */
    public static String bytes2Hex(final Byte[] stream, final String splice) {
        final List<String> hex = new ArrayList<>();
        for (final Byte b : stream) {
            hex.add(StreamUtils.byte2Hex(b));
        }
        return StringUtils.join(hex, splice == null ? StringUtils.COMMA : splice);
    }

}
