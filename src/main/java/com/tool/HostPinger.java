package com.tool;

import java.io.IOException;
import java.util.Properties;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class HostPinger
{
    public static void main(String[] args) throws IOException, SchedulerException
    {
        Properties props = new Properties();
        props.setProperty("org.quartz.scheduler.skipUpdateCheck", "true");
        props.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        props.setProperty("org.quartz.threadPool.threadCount", "1");
        String ipAddress = args[0];
        String interval = args[1];
        System.setProperty("com.tool.ping.host", ipAddress);

        JobDetail job = JobBuilder.newJob(PingExecutor.class).withIdentity("vpnKeepAliveJob", "vpnKeepAliveGroup").build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("vpnKeepAliveTrigger", "vpnKeepAliveGroup")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(Integer.parseInt(interval)).repeatForever())
                .build();
        Scheduler scheduler = new StdSchedulerFactory(props).getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

}
