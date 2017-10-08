package com.xinchen.ssh.demo.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskOneJob extends QuartzJobBean {
    private static final Logger LOGGER = Logger
            .getLogger(TaskOneJob.class);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        LOGGER.info("---------TaskOneJob----------->>>>" + df.format(new Date(System.currentTimeMillis())));
//        System.out.println("---------TaskOneJob----------->>>>" + Calendar.getInstance().getTimeInMillis());

    }
}
