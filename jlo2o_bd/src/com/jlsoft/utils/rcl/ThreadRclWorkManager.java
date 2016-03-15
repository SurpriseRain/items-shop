package com.jlsoft.utils.rcl;

public class ThreadRclWorkManager implements Runnable {
	private String sGSID;
	private java.sql.Date dat;
	private String sProcName;
	private ThreadRclWorkService nbjsmw;

	public ThreadRclWorkManager(String sGSID, java.sql.Date dat,String sProcName,ThreadRclWorkService nbjsmw) {
		this.sGSID = sGSID;
		this.dat = dat;
		this.sProcName=sProcName;
		this.nbjsmw=nbjsmw;
	}

	public void run() {
		if (nbjsmw.getDebug())
		{	
		  System.out.println("开始执行"+sProcName+":" + sGSID + "子线程");
		}
		try {
			  if (nbjsmw.getDebug())
			  {
				  Thread.sleep(10000);
				  
			  }
			  else
			  {
			    new ThreadRclWorkNode().doWork(sGSID,dat,sProcName);//调用具体工作节点，处理核心业务
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (nbjsmw.getDebug())
		{
		  System.out.println("执行完毕" +sProcName+":" + sGSID + "子线程");
		}
		nbjsmw.getLog_parallel_degree().poll();//执行完毕之后退出并行度
		nbjsmw.getCdl().countDown();//倒计数锁存器减一操作
	}

}
