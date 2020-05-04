package com.enhao.learning.in.activiti.holiday_process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;

/**
 * 任务处理
 *
 * @author enhao
 */
public class ActivitiCompleteTask {

    public static void main(String[] args) {
        // 1. 得到 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 得到 TaskService 对象
        TaskService taskService = processEngine.getTaskService();
        // 3. 处理任务，结合当前用户任务列表的查询，可以得到任务的id: taskId
        // 该 taskId 根据 ActivitiTaskQuery 中查询任务列表中获得。
        String taskId = "2505";
        taskService.complete(taskId);
    }
}
