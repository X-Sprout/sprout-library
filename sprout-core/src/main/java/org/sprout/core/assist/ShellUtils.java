/**
 * Created on 2016/8/11
 */
package org.sprout.core.assist;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 指令工具类
 * <p>
 *
 * @author Wythe
 */
public final class ShellUtils {

    private final static String SHELL_SU = "su";
    private final static String SHELL_SH = "sh";
    private final static String SHELL_EXIT = "exit\n";
    private final static String SHELL_LINE_END = "\n";

    /**
     * 执行结果
     */
    public final static class Result {
        // 结果码
        private final int code;
        // 输出信息
        private final String outInfo;
        // 错误信息
        private final String errInfo;

        public Result(final int code, final String outInfo, final String errInfo) {
            this.code = code;
            this.outInfo = outInfo;
            this.errInfo = errInfo;
        }

        public int getCode() {
            return this.code;
        }

        public String getErrInfo() {
            return this.errInfo;
        }

        public String getOutInfo() {
            return this.outInfo;
        }
    }

    /**
     * 执行脚本指令
     *
     * @param command 脚本指令
     * @return 执行结果
     * @author Wythe
     */
    public static Result execute(final String command) {
        return ShellUtils.execute(new String[]{command});
    }

    /**
     * 执行脚本指令
     *
     * @param command 脚本指令
     * @param isRoot  是否超管执行
     * @return 执行结果
     * @author Wythe
     */
    public static Result execute(final String command, final boolean isRoot) {
        return ShellUtils.execute(new String[]{command}, isRoot);
    }

    /**
     * 执行脚本指令
     *
     * @param command 脚本指令
     * @param isRoot  是否超管执行
     * @param isInfo  是否返回信息
     * @return 执行结果
     * @author Wythe
     */
    public static Result execute(final String command, final boolean isRoot, final boolean isInfo) {
        return ShellUtils.execute(new String[]{command}, isRoot, isInfo);
    }

    /**
     * 执行脚本指令
     *
     * @param commands 脚本数组
     * @return 执行结果
     * @author Wythe
     */
    public static Result execute(final String[] commands) {
        return ShellUtils.execute(commands, false);
    }

    /**
     * 执行脚本指令
     *
     * @param commands 脚本数组
     * @param isRoot   是否超管执行
     * @return 执行结果
     * @author Wythe
     */
    public static Result execute(final String[] commands, final boolean isRoot) {
        return ShellUtils.execute(commands, false, false);
    }

    /**
     * 执行脚本指令
     *
     * @param commands 脚本数组
     * @param isRoot   是否超管执行
     * @param isInfo   是否返回信息
     * @return 执行结果
     * @author Wythe
     */
    public static Result execute(final String[] commands, final boolean isRoot, final boolean isInfo) {
        int code = -1;
        if (ArrayUtils.isEmpty(commands)) {
            return new Result(code, null, null);
        }
        // 执行指令
        Process process = null;
        DataOutputStream dos = null;
        StringBuilder outInfo = null;
        StringBuilder errInfo = null;
        BufferedReader outResult = null;
        BufferedReader errResult = null;
        try {
            dos = new DataOutputStream((process = Runtime.getRuntime().exec(isRoot ? SHELL_SU : SHELL_SH)).getOutputStream());
            for (final String command : commands) {
                if (StringUtils.isEmpty(command)) {
                    continue;
                }
                dos.write(command.getBytes());
                dos.writeBytes(SHELL_LINE_END);
                dos.flush();
            }
            dos.writeBytes(SHELL_EXIT);
            dos.flush();
            // 处理结果
            code = process.waitFor();
            if (isInfo) {
                String str;
                outInfo = new StringBuilder(512);
                outResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((str = outResult.readLine()) != null) {
                    outInfo.append(str);
                }
                errInfo = new StringBuilder(512);
                errResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((str = errResult.readLine()) != null) {
                    errInfo.append(str);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outResult != null) {
                try {
                    outResult.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (errResult != null) {
                try {
                    errResult.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        return new Result(code, outInfo != null ? outInfo.toString() : null, errInfo != null ? errInfo.toString() : null);
    }

}
