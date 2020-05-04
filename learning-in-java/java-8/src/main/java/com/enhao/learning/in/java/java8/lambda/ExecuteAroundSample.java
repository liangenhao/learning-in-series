package com.enhao.learning.in.java.java8.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * com 应用实例：环绕执行模式
 * <p>
 * 资源处理(例如处理文件或数据库)时一个常见的模式就是打开一个资源，做一些处理，然后关闭资源。
 * 这个设置和清理阶段总是很类似，并且会围绕着执行处理的那些重要代码。
 * 这就是所谓的环绕执行(execute around)模式
 *
 * @author enhao
 */
public class ExecuteAroundSample {
    public static void main(String[] args) throws IOException {
        // 读一行
        String oneLine = processFile((BufferedReader reader) -> reader.readLine());
        System.out.println(oneLine);
        System.out.println("===================");
        // 读两行
        String twoLine = processFile((BufferedReader reader) -> reader.readLine() + reader.readLine());
        System.out.println(twoLine);

    }

    public static String processFile(BufferedReaderProcessor processor) throws IOException {
        BufferedReader reader = null;
        try {
            String path = ExecuteAroundSample.class.getClassLoader().getResource("data.txt").getPath();
            reader = new BufferedReader(new FileReader(path));

            return processor.process(reader);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }


    @FunctionalInterface
    public interface BufferedReaderProcessor {

        String process(BufferedReader reader) throws IOException;
    }
}
