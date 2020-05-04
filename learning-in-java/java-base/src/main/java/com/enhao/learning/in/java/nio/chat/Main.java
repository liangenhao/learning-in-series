package com.enhao.learning.in.java.nio.chat;

import java.util.Scanner;

/**
 * 聊天程序Demo
 *
 * @author enhao
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ChatClient chatClient = new ChatClient();

        new Thread(() -> {
            while (true) {
                try {
                    chatClient.receiveMsg();
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }
}
