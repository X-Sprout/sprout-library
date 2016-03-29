/**
 * Created on 2016/2/25
 */
package org.sprout.core.logging;

import org.sprout.core.assist.StringUtils;

/**
 * 日志操作类
 * <p/>
 *
 * @author Wythe
 */
public final class Lc {

    /**
     * 是否记录Verbose
     */
    public final static Boolean V = false;
    /**
     * 是否记录Debug
     */
    public final static Boolean D = false;
    /**
     * 是否记录Info
     */
    public final static Boolean I = false;
    /**
     * 是否记录Warn
     */
    public final static Boolean W = false;
    /**
     * 是否记录Error
     */
    public final static Boolean E = false;
    /**
     * 是否记录Assert
     */
    public final static Boolean A = false;
    /**
     * XML演示美化
     */
    public final static Lc.Formater XML = Lc.Formater.XML;
    /**
     * JSON演示美化
     */
    public final static Lc.Formater JSN = Lc.Formater.JSN;

    /**
     * 设置自定义TAG
     *
     * @param label 自定义TAG
     * @return 日志打印器
     * @author Wythe
     */
    public static Lc.Printer t(final String label) {
        return Lc.Printer.create().t(label);
    }

    /**
     * 设置输出文件路径
     *
     * @param local 输出文件路径
     * @return 日志打印器
     * @author Wythe
     */
    public static Lc.Printer o(final String local) {
        return Lc.Printer.create().o(local);
    }

    /**
     * 设置美化数据类型
     *
     * @param style 美化数据类型
     * @return 日志打印器
     * @author Wythe
     */
    public static Lc.Printer f(final Lc.Formater style) {
        return Lc.Printer.create().f(style);
    }

    /**
     * 记录Verbose等级日志
     *
     * @param print 打印信息
     * @author Wythe
     */
    public static void v(final String print) {
        Lc.v(print, null);
    }

    /**
     * 记录Verbose等级日志
     *
     * @param error 异常对象
     * @author Wythe
     */
    public static void v(final Throwable error) {
        Lc.v(null, error);
    }

    /**
     * 记录Verbose等级日志
     *
     * @param print 打印信息
     * @param error 异常对象
     * @author Wythe
     */
    public static void v(final String print, final Throwable error) {
        if (LogcatService.READY && Lc.V && (
                !StringUtils.isEmpty(print) || error != null
        )) {
            Lc.Printer.create().v(print, error);
        }
    }

    /**
     * 记录Debug等级日志
     *
     * @param print 打印信息
     * @author Wythe
     */
    public static void d(final String print) {
        Lc.d(print, null);
    }

    /**
     * 记录Debug等级日志
     *
     * @param error 异常对象
     * @author Wythe
     */
    public static void d(final Throwable error) {
        Lc.d(null, error);
    }

    /**
     * 记录Debug等级日志
     *
     * @param print 打印信息
     * @param error 异常对象
     * @author Wythe
     */
    public static void d(final String print, final Throwable error) {
        if (LogcatService.READY && Lc.D && (
                !StringUtils.isEmpty(print) || error != null
        )) {
            Lc.Printer.create().d(print, error);
        }
    }

    /**
     * 记录Info等级日志
     *
     * @param print 打印信息
     * @author Wythe
     */
    public static void i(final String print) {
        Lc.i(print, null);
    }

    /**
     * 记录Info等级日志
     *
     * @param error 异常对象
     * @author Wythe
     */
    public static void i(final Throwable error) {
        Lc.i(null, error);
    }

    /**
     * 记录Info等级日志
     *
     * @param print 打印信息
     * @param error 异常对象
     * @author Wythe
     */
    public static void i(final String print, final Throwable error) {
        if (LogcatService.READY && Lc.I && (
                !StringUtils.isEmpty(print) || error != null
        )) {
            Lc.Printer.create().i(print, error);
        }
    }

    /**
     * 记录Warn等级日志
     *
     * @param print 打印信息
     * @author Wythe
     */
    public static void w(final String print) {
        Lc.w(print, null);
    }

    /**
     * 记录Warn等级日志
     *
     * @param error 异常对象
     * @author Wythe
     */
    public static void w(final Throwable error) {
        Lc.w(null, error);
    }

    /**
     * 记录Warn等级日志
     *
     * @param print 打印信息
     * @param error 异常对象
     * @author Wythe
     */
    public static void w(final String print, final Throwable error) {
        if (LogcatService.READY && Lc.W && (
                !StringUtils.isEmpty(print) || error != null
        )) {
            Lc.Printer.create().w(print, error);
        }
    }

    /**
     * 记录Error等级日志
     *
     * @param print 打印信息
     * @author Wythe
     */
    public static void e(final String print) {
        Lc.e(print, null);
    }

    /**
     * 记录Error等级日志
     *
     * @param error 异常对象
     * @author Wythe
     */
    public static void e(final Throwable error) {
        Lc.e(null, error);
    }

    /**
     * 记录Error等级日志
     *
     * @param print 打印信息
     * @param error 异常对象
     * @author Wythe
     */
    public static void e(final String print, final Throwable error) {
        if (LogcatService.READY && Lc.E && (
                !StringUtils.isEmpty(print) || error != null
        )) {
            Lc.Printer.create().e(print, error);
        }
    }

    /**
     * 记录Assert等级日志
     *
     * @param print 打印信息
     * @author Wythe
     */
    public static void a(final String print) {
        Lc.a(print, null);
    }

    /**
     * 记录Assert等级日志
     *
     * @param error 异常对象
     * @author Wythe
     */
    public static void a(final Throwable error) {
        Lc.a(null, error);
    }

    /**
     * 记录Assert等级日志
     *
     * @param print 打印信息
     * @param error 异常对象
     * @author Wythe
     */
    public static void a(final String print, final Throwable error) {
        if (LogcatService.READY && Lc.A && (
                !StringUtils.isEmpty(print) || error != null
        )) {
            Lc.Printer.create().a(print, error);
        }
    }

    /**
     * 日志格式类
     * <p/>
     *
     * @author Wythe
     */
    public enum Formater {
        JSN, XML
    }

    /**
     * 日志打印类
     * <p/>
     *
     * @author Wythe
     */
    public final static class Printer {

        private String label;

        private String local;

        private Lc.Formater style;

        private Printer(final String label) {
            this.label = label;
        }

        private static Lc.Printer create() {
            return new Lc.Printer(LogcatService.TAG);
        }

        private boolean review(final String print, final Throwable error) {
            return LogcatService.READY && !StringUtils.isEmpty(this.label) && (
                    !StringUtils.isEmpty(print) || error != null
            );
        }

        /**
         * 设置自定义TAG
         *
         * @param label 定义TAG
         * @return 日志打印器
         * @author Wythe
         */
        public Lc.Printer t(final String label) {
            if (!StringUtils.isEmpty(label)) {
                this.label = label;
            }
            return this;
        }

        /**
         * 设置输出文件路径
         *
         * @param local 输出文件路径
         * @return 日志打印器
         * @author Wythe
         */
        public Lc.Printer o(final String local) {
            if (!StringUtils.isEmpty(local)) {
                this.local = local;
            }
            return this;
        }

        /**
         * 设置美化数据类型
         *
         * @param style 美化数据类型
         * @return 日志打印器
         * @author Wythe
         */
        public Lc.Printer f(final Lc.Formater style) {
            if (style != null) {
                this.style = style;
            }
            return this;
        }

        /**
         * 记录Verbose等级日志
         *
         * @param print 打印信息
         * @author Wythe
         */
        public void v(final String print) {
            this.v(print, null);
        }

        /**
         * 记录Verbose等级日志
         *
         * @param error 异常对象
         * @author Wythe
         */
        public void v(final Throwable error) {
            this.v(null, error);
        }

        /**
         * 记录Verbose等级日志
         *
         * @param print 打印信息
         * @param error 异常对象
         * @author Wythe
         */
        public void v(final String print, final Throwable error) {
            if (this.review(print, error)) {
                LogcatService.worker.write(LogcatLevel.VERBOSE, this.label, this.local, print, error, this.style);
            }
        }

        /**
         * 记录Debug等级日志
         *
         * @param print 打印信息
         * @author Wythe
         */
        public void d(final String print) {
            this.d(print, null);
        }

        /**
         * 记录Debug等级日志
         *
         * @param error 异常对象
         * @author Wythe
         */
        public void d(final Throwable error) {
            this.d(null, error);
        }

        /**
         * 记录Debug等级日志
         *
         * @param print 打印信息
         * @param error 异常对象
         * @author Wythe
         */
        public void d(final String print, final Throwable error) {
            if (this.review(print, error)) {
                LogcatService.worker.write(LogcatLevel.DEBUG, this.label, this.local, print, error, this.style);
            }
        }

        /**
         * 记录Info等级日志
         *
         * @param print 打印信息
         * @author Wythe
         */
        public void i(final String print) {
            this.i(print, null);
        }

        /**
         * 记录Info等级日志
         *
         * @param error 异常对象
         * @author Wythe
         */
        public void i(final Throwable error) {
            this.i(null, error);
        }

        /**
         * 记录Info等级日志
         *
         * @param print 打印信息
         * @param error 异常对象
         * @author Wythe
         */
        public void i(final String print, final Throwable error) {
            if (this.review(print, error)) {
                LogcatService.worker.write(LogcatLevel.INFO, this.label, this.local, print, error, this.style);
            }
        }

        /**
         * 记录Warn等级日志
         *
         * @param print 打印信息
         * @author Wythe
         */
        public void w(final String print) {
            this.w(print, null);
        }

        /**
         * 记录Warn等级日志
         *
         * @param error 异常对象
         * @author Wythe
         */
        public void w(final Throwable error) {
            this.w(null, error);
        }

        /**
         * 记录Warn等级日志
         *
         * @param print 打印信息
         * @param error 异常对象
         * @author Wythe
         */
        public void w(final String print, final Throwable error) {
            if (this.review(print, error)) {
                LogcatService.worker.write(LogcatLevel.WARN, this.label, this.local, print, error, this.style);
            }
        }

        /**
         * 记录Error等级日志
         *
         * @param print 打印信息
         * @author Wythe
         */
        public void e(final String print) {
            this.e(print, null);
        }

        /**
         * 记录Error等级日志
         *
         * @param error 异常对象
         * @author Wythe
         */
        public void e(final Throwable error) {
            this.e(null, error);
        }

        /**
         * 记录Error等级日志
         *
         * @param print 打印信息
         * @param error 异常对象
         * @author Wythe
         */
        public void e(final String print, final Throwable error) {
            if (this.review(print, error)) {
                LogcatService.worker.write(LogcatLevel.ERROR, this.label, this.local, print, error, this.style);
            }
        }

        /**
         * 记录Assert等级日志
         *
         * @param print 打印信息
         * @author Wythe
         */
        public void a(final String print) {
            this.a(print, null);
        }

        /**
         * 记录Assert等级日志
         *
         * @param error 异常对象
         * @author Wythe
         */
        public void a(final Throwable error) {
            this.a(null, error);
        }

        /**
         * 记录Assert等级日志
         *
         * @param print 打印信息
         * @param error 异常对象
         * @author Wythe
         */
        public void a(final String print, final Throwable error) {
            if (this.review(print, error)) {
                LogcatService.worker.write(LogcatLevel.ASSERT, this.label, this.local, print, error, this.style);
            }
        }

    }

}
