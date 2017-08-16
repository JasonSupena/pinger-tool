package com.tool;

import java.io.IOException;
import java.net.InetAddress;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.util.IPAddressUtil;

public class PingExecutor implements Job
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PingExecutor.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        String ipAddress = System.getProperty("com.tool.ping.host");
        try
        {
            InetAddress inet = InetAddress.getByAddress(IPAddressUtil.textToNumericFormatV4(ipAddress));
            LOGGER.info(inet.getHostAddress());
            LOGGER.info(inet.isReachable(5000) ? "Host is Reachable" : "Host is not Reachable");
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage());
        }

    }
}
