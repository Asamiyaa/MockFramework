package com.xmlPlatform;

import java.io.*;

/**
 * @author YangWenjun
 * @date 2019/12/14 12:35
 * @project MockFramework
 * @title: CallSysShell
 * @description:
 */
public final class CallSysShell {

    public static void main(String[] args) throws Exception {
        //TODO : 将这里改成 mvn的工具类型  mvn compile 执行是否可以自动再对应目录下生成
        String result = execCmd("java -version", null);
        System.out.println(result);
    }

    public static String execCmd(String cmd, File dir) {

        StringBuilder result = new StringBuilder();

        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;

        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmd, null, dir);

            // 方法阻塞, 等待命令执行完成（成功会返回0）
            process.waitFor();

            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

            // 读取输出
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(bufrIn);
            closeStream(bufrError);

            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }

        // 返回执行结果
        return result.toString();
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                // nothing
            }
        }
    }

}


