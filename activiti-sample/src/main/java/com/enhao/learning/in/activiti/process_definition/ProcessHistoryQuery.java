package com.enhao.learning.in.activiti.process_definition;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;

import java.util.List;

/**
 * 历史信息查询
 *
 * @author enhao
 */
public class ProcessHistoryQuery {

    public static void main(String[] args) {
        // 1. 得到 ProcessEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 创建 HistoryService 对象
        HistoryService historyService = processEngine.getHistoryService();

        // 3. 得到查询对象
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        // 流程实例id
        String processInstanceId = "2501";
        List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceQuery.processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();

        historicActivityInstanceList.forEach(historicActivityInstance -> {
            System.out.println("流程定义id : " + historicActivityInstance.getProcessDefinitionId());
            System.out.println("流程实例id : " + historicActivityInstance.getProcessInstanceId());
            System.out.println("活动id : " + historicActivityInstance.getActivityId());
            System.out.println("活动名称 : " + historicActivityInstance.getActivityName());
            System.out.println("============");
        });
    }
}
