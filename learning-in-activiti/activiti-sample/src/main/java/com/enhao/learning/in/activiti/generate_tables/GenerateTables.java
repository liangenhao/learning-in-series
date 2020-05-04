package com.enhao.learning.in.activiti.generate_tables;

import org.activiti.engine.ProcessEngineConfiguration;

/**
 * 用来生成 activiti 的25张表结构
 *
 * 通过获取 ProcessEngine 对象，就会自动创建25张表
 *
 * @author enhao
 */
public class GenerateTables {

    public static void main(String[] args) {
        // 1. 创建 ProcessEngineConfiguration 对象
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        // ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml", "实际的ProcessEngineConfiguration bean的名称");

        // 2. 创建 ProcessEngine 对象
        configuration.buildProcessEngine();
    }
}
