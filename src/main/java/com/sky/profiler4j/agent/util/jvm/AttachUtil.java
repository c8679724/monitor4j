package com.sky.profiler4j.agent.util.jvm;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

public class AttachUtil {

    public static void main(String[] args) throws MonitorException, URISyntaxException {
        
        List<MonitoredVm> monitoredVms = listLocalVM();

        // 遍历集合，输出PID和进程名
        for (MonitoredVm vm : monitoredVms) {

            // 获取类名
            String jvmFlags = MonitoredVmUtil.jvmFlags(vm);
            String mainArgs = MonitoredVmUtil.mainArgs(vm);
            String jvmArgs = MonitoredVmUtil.jvmArgs(vm);
            String vmVersion = MonitoredVmUtil.vmVersion(vm);
            boolean isAttachable = MonitoredVmUtil.isAttachable(vm);
            String commandLine = MonitoredVmUtil.commandLine(vm);
            String mainClass = MonitoredVmUtil.mainClass(vm, true);
            System.out.println("isAttachable:" + isAttachable + "---" + "vmVersion:" + vmVersion + "---" + "jvmFlags:" + jvmFlags
                    + " ------> " + mainClass + "---->" + "mainArgs:" + mainArgs + "---" + commandLine + "--->" + jvmArgs);
        }
    }

    public static List<MonitoredVm> listLocalVM() throws MonitorException, URISyntaxException {
        List<MonitoredVm> monitoredVms = new ArrayList<>();

        // 获取监控主机
        MonitoredHost monitoredHost = MonitoredHost.getMonitoredHost("localhost");
        // 取得所有在活动的虚拟机集合
        Set<?> vmlist = new HashSet<Object>(monitoredHost.activeVms());
        for (Object process : vmlist) {
            MonitoredVm vm = monitoredHost.getMonitoredVm(new VmIdentifier("//" + process));
            monitoredVms.add(vm);
        }
        return monitoredVms;
    }
}
