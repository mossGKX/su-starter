package com.cntest.su.process.activiti.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.process.activiti.converter.ActivitiDefinitionConverter;
import com.cntest.su.process.activiti.event.listener.ActivitiTaskListener;
import com.cntest.su.process.model.definition.ExtFlowElement;
import com.cntest.su.process.model.definition.ExtProcess;
import com.cntest.su.process.service.ProcessDefinitionService;

/**
 * 流程定义服务接口实现
 * 
 * @author caining
 *
 */
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

  /**
   * activiti流程元素定义对象转换器
   */
  private ActivitiDefinitionConverter activitiFlowElementConverter =
      new ActivitiDefinitionConverter();

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private ActivitiTaskListener activitiTaskListener;

  @Override
  public String deployProcess(ExtProcess extProcess) {

    /**
     * 构建activiti流程定义对象
     */
    Process process = this.activitiFlowElementConverter.converto(extProcess);

    List<ExtFlowElement> extFlowElements = extProcess.getFlowElementList();
    ExtFlowElement extFlowElement = null;
    for (int i = 0; i < extFlowElements.size(); ++i) {
      extFlowElement = extFlowElements.get(i);
      FlowElement flowElement = activitiFlowElementConverter.convertTo(extFlowElement);
      process.addFlowElement(flowElement);
      /**
       * 添加事件
       */
      List<ActivitiListener> aes = new ArrayList<ActivitiListener>();
      flowElement.setExecutionListeners(aes);
      // 开始事件
      ActivitiListener ae = new ActivitiListener();
      aes.add(ae);
      ae.setEvent("start");
      ae.setImplementation("#{activitiExecutionListener}");
      ae.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
      // 结束事件
      ActivitiListener ae1 = new ActivitiListener();
      aes.add(ae1);
      ae1.setEvent("end");
      ae1.setImplementation("#{activitiExecutionListener}");
      ae1.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);

    }

    /**
     * 发布流程定义对象
     */
    String processId = process.getId();
    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // 2.生成BPMN自动布局
    new BpmnAutoLayout(model).execute();

    // 3. 部署这个BPMN模型
    Deployment deployment = this.repositoryService.createDeployment()
        .addBpmnModel(processId + ".bpmn", model).name(processId + "_deployment").deploy();


    return deployment.getId();
  }

  @Override
  public String saveProcessImage(String deploymentId, String processId, String processImgPath)
      throws IOException {
    // 保存bpmn流程图
    InputStream processDiagram =
        repositoryService.getResourceAsStream(deploymentId, processId + "." + processId + ".png");
    String filePath = processImgPath + processId + ".png";
    FileUtils.copyInputStreamToFile(processDiagram, new File(filePath));
    return filePath;
  }


}
