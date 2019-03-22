package com.enhao.learning.in.java.design.pattern.chain_of_responsibility;

/**
 * 抽象处理者
 *
 * @author enhao
 */
public abstract class Handler {

    private Handler nextHandler;

    public final Response handleMessage(Request request) {
        Response response = null;
        if (this.getHandlerLevel().equals(request.getRequestLevel())) {
            // 是自己的处理级别
            response = this.echo(request);
        } else {
            // 不是自己的处理级别，给责任链的下一个处理者
            if (this.nextHandler != null) {
                response = this.nextHandler.handleMessage(request);
            } else {
                // 没有处理者
                System.out.println("没有处理者");
            }
        }

        return response;
    }

    /**
     * 设置责任链的下一个处理者
     * @param handler
     */
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    /**
     * 责任链上的每个处理者都有处理级别
     * @return
     */
    protected abstract Level getHandlerLevel();

    /**
     * 处理任务
     * @param request
     * @return
     */
    protected abstract Response echo(Request request);

}
