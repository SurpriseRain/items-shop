package com.jlsoft.utils.rcl;

public class ThreadRclTestBySync {

	public void test() {
        ThreadRclWorkService trws=new ThreadRclWorkService();
        trws.setDebug(true);
		String sGSAry[];
		sGSAry = new String[] { "0101", "0102", "0103", "0105", "0106", "014A",
				"0150", "0151", "01XJ", "0201", "0301","0302","0303"};
		trws.setParams(sGSAry, null, "EXEC_RKD_TEST", 4);//4最好对应CPU总数量,如果是16C的机器，可填16
		trws.doParallelWorkBySync();
	    
		System.out.println("其它工作开始执行");
	    System.out.println("其它工作执行完毕");
	    System.out.println("所有工作全部执行完毕");
	}
	
	
    public static void main(String[] args)
    {
    	new ThreadRclTestBySync().test();
    }
}
