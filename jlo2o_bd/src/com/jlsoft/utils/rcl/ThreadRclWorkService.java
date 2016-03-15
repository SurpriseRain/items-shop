package com.jlsoft.utils.rcl;

import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.*;
/*
 * 调用并行服务接口 调用模型:
 * Client->ThreadRclWorkService->ThreadRclWorkManager->ThreadRclWorkNode
 * 调用方式:同步调用,调用ThreadRclWorkService.doParallelWorkBySync()
 * 异步调用,调用ThreadRclWorkService.doParallelWorkByAsync(),处理完毕之后，会回调IThreadRclWorkListener接口
 */

public class ThreadRclWorkService implements Runnable {

    private Stack<String> stack = new Stack<String>();// 处理公司栈
    private int iGSTotalCount;// 公司总数
    private java.sql.Date dRclRQ;// 日结日期
    private String sProcName;//执行的过程名
    /*
     * 设置允许公司并行度 同时进行线程数量
     */
    private ArrayBlockingQueue<String> log_parallel_degree;
    /*
     * 倒计数锁存器,当所有的子公司全部执行完毕，则返回主线程继续操作
     */
    private CountDownLatch cdl;
    private boolean bWorking;//工作中标志位
    private boolean debug = false;//是否在DEBUG状态
    private IThreadRclWorkListener callBack;
    private String[] sGSIDAry;

    public ArrayBlockingQueue<String> getLog_parallel_degree() {
        return log_parallel_degree;
    }

    public CountDownLatch getCdl() {
        return cdl;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean getDebug() {
        return debug;
    }

    /*
     * 设置工作参数
     */
    public void setParams(String[] sGSIDAry, java.sql.Date dRclRQ, String sProcName, int iLog_parallel_degreeCount) {
        if (!bWorking) {
            stack.clear();
            stack.addAll(Arrays.asList(sGSIDAry));
            this.iGSTotalCount = stack.size();
            this.dRclRQ = dRclRQ;
            this.sProcName = sProcName;
            this.log_parallel_degree = new ArrayBlockingQueue<String>(iLog_parallel_degreeCount);
            this.sGSIDAry = sGSIDAry;
        }
    }

    public ThreadRclWorkService()// 初始化构造函数
    {
        super();
        bWorking = false;
    }

    public void run() {
        doParallelWorkBySync();
    }

    public void doParallelWorkByAsync(IThreadRclWorkListener callBack)//异步回调入口(可与外部线程并行)
    {
        this.callBack = callBack;
        Thread t = new Thread(this);
        t.start();
    }
    /*
     * 工作主入口
     */

    public void doParallelWorkBySync() { //同步调用入口
        bWorking = true;
        if (debug) {
            System.out.println(sProcName + "开始做公司分支工作!");
        }
        cdl = new CountDownLatch(iGSTotalCount);//设置倒计数锁存器，锁存公司总数.
        while (!stack.isEmpty()) {
            try {
                log_parallel_degree.put("");// 加入并行度控制
                Thread t = new Thread(new ThreadRclWorkManager(stack.pop(),
                        dRclRQ, sProcName, this));
                t.start();// 开始执行独立子线程,进行对公司单独处理
                Thread.sleep(100);//排序正确
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        try {
            cdl.await();// 倒计数锁存器进入等待状态，等待所有分支全部完成
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (debug) {
            System.out.println(sProcName + "所有公司分支工作,全部执行完毕!");
        }
        if (callBack != null) {
            callBack.onAllThreadWorkDone(sProcName, dRclRQ, sGSIDAry);
        }
        bWorking = false;
        callBack = null;
    }
}
