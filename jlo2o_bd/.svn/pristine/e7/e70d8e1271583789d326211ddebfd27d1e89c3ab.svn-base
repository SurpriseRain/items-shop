package com.jlsoft.o2o.product.service;

import java.util.Map;

public class ProduceLabel {

	public  String creheadDPLabel(String goodsName, String barcode,
			String batchNumber, String serialNumber, String spgl24,
			String spxh01, String companyName, String HYGLM) {
		String headDPLabel="new\r\n"
							+ "PP204,252:AN1\r\n"
							+ "FT \"MHeiGB18030C-Medium\",7,0,100\r\n"
							+ "NASC 8\r\n"
							+ "PT \""+goodsName+"\"\r\n"	//产品名称
							+ "PP204,228\r\n"
							+ "PT \"企业自编码：\"\r\n"
							+ "PP324,228\r\n"
							+ "PT \""+spgl24+"\"\r\n"			//企业自编码
							+ "PP204,204\r\n"
							+ "PT \"型      号：\"\r\n"
							+ "PP300,204\r\n"
							+ "PT \""+spxh01+"\"\r\n"		//商品型号
							+ "PP204,120\r\n"
							+ "PT \""+goodsName+"\"\r\n"			//产品名称
							+ "PP204,96\r\n"
							+ "PT \"条  码：\"\r\n"
							+ "PP276,96\r\n"
							+ "PT \""+barcode+"\"\r\n"			//商品条码
							+ "PP204,72\r\n"
							+ "PT \"批次号：\"\r\n"
							+ "PP284,72\r\n"
							+ "PT \""+batchNumber+"\"\r\n"				//批次号
							+ "PP204,48\r\n"
							+ "PT \"序列号：\"\r\n"
							+ "PP284,48\r\n"
							+ "PT \""+serialNumber+"\"\r\n"				//序列号
							+ "PP204,24\r\n"
							+ "PT \""+companyName+"\"\r\n"		//公司名称
							+ "PP204,0\r\n"
							+ "PT \"同质配件行业管理代码:"+HYGLM+"\"\r\n"	//同质配件行业管理代码
							+ "PP536,196\r\n"
							+ "BARSET \"QRCODE\",3,1,3,2,2\r\n"
							+ "PB \"http://www.baidu.com\"\r\n"
							+ "PP536,48\r\n"
							+ "BARSET \"QRCODE\",3,1,3,2,2\r\n"
							+ "PB \"http://www.baidu.com\"\r\n"
							+ "PF\r\n"
							+ "run\r\n\r\n";
		return headDPLabel;
	}

	public  String crebehindDPLabel(String headDPLabel, String cm) {
		String behindDPLabel=headDPLabel
							+ "PP536,196\r\n"
							+ "BARSET \"QRCODE\",3,1,3,2,2\r\n"
							+ "PB \""+cm+"\"\r\n"
							+ "PP536,48\r\n"
							+ "BARSET \"QRCODE\",3,1,3,2,2\r\n"
							+ "PB \""+cm+"\"\r\n"
							+ "PF\r\n"
							+ "run\r\n\r\n";
		
		return	behindDPLabel;
		
	}

	public String creZXMLabel(Map tempMap) {
		//产品名称
		String spmc=DBisNotEmpty((String) tempMap.get("spxx04"));
		//装箱时间
		String zxsj=DBisNotEmpty((String) tempMap.get("DRRQ"));
		//装箱码
		String pCode=DBisNotEmpty((String) tempMap.get("pCode"));
		//数量
		String SPLength=DBisNotEmpty((String) tempMap.get("DRSL"));
		//ppb02 品牌名称
		String ppb02=DBisNotEmpty((String) tempMap.get("ppb02"));
		//barcode 商品条码
		String barcode=DBisNotEmpty((String) tempMap.get("barcode"));
		//规格型号
		String ggxh=DBisNotEmpty((String) tempMap.get("ggxh"));
		//HYGLM 行业管理码
		String HYGLM=DBisNotEmpty((String) tempMap.get("HYGLM"));
		//zcxx02 公司名称
		String zcxx02=DBisNotEmpty((String) tempMap.get("zcxx02"));
		
		if(ppb02.length()>12){
			String tempppb02 = cutInfo(ppb02, 11);		//7号字60X40标签12-2总长度
			ppb02=tempppb02+"...";
		}
		if(ggxh.length()>12){
			String tempggxh = cutInfo(ggxh, 11);		//7号字60X40标签12-2总长度
			ggxh=tempggxh+"...";
		}
		String spmc1="";
		String spmc2="";
		if(spmc.length()>13){
			String[] spmcs = cutSPMCInfo(spmc, 12);			//9号字60X40标签13-2总长度
			spmc1=spmcs[0];
			spmc2=spmcs[1];
		}else {
			spmc1=spmc;
		}

	String[] pCodes = pCode.split("\\(10\\)",2);
	String newpCode1 = pCodes[0].concat("(10)");
	String newpCode2=pCodes[1];
	
	String QYMC1="";
	String QYMC2="";
	if(zcxx02.length()<=16){
		 QYMC1=zcxx02;
	}else if(zcxx02.length()>32){
		QYMC1=zcxx02.substring(0, 16);
		QYMC2=zcxx02.substring(16, 31)+"...";
	}else{
		QYMC1=zcxx02.substring(0, 16);
		QYMC2=zcxx02.substring(16, zcxx02.length());
	}
	
	if(SPLength.length()>6){
		SPLength=SPLength.substring(0, 5)+"...";
	}
	if(barcode.length()>20){
		barcode=barcode.substring(0, 19)+"...";
	}
	
	String ZXMLabel="NEW\r\n"
					+ "PP204,270:AN1\r\n"
					+ "FT \"MHeiGB18030C-Medium\",9,0,100\r\n"
					+ "NASC 8\r\n"
					+ "PT \""+spmc1+"\"\r\n"			//商品名称
					+ "PP204,240\r\n"
					+ "PT \""+spmc2+"\"\r\n"
					+ "FT \"MHeiGB18030C-Medium\",7,0,100\r\n"
					+ "NASC 8\r\n"
					+ "PP204,216\r\n"
					+ "PT \"品  牌：\"\r\n"
					+ "PP276,216\r\n"
					+ "PT \""+ppb02+"\"\r\n"			//品牌
					+ "PP204,192\r\n"
					+ "PT \"规  格：\"\r\n"
					+ "PP276,192\r\n"
					+ "PT \""+ggxh+"\"\r\n"			//规格
					+ "PP204,168\r\n"
					+ "PT \"条  码：\"\r\n"				//条码
					+ "PP276,168\r\n"
					+ "PT \""+barcode+"\"\r\n"
					+ "PP204,120\r\n"
					+ "PT \"装箱码：\"\r\n"
					+ "PP284,120\r\n"
					+ "PT \""+newpCode1+"\"\r\n"		//装箱码1
					+ "PP284,96\r\n"
					+ "PT \""+newpCode2+"\"\r\n"		//装箱码2
					+ "PP204,72\r\n"
					+ "PT \"装箱时间：\"\r\n"
					+ "PP304,72\r\n"
					+ "PT \""+zxsj+"\"\r\n"					//装箱时间
					+ "PP524,72\r\n"
					+ "PT \"数量：\"\r\n"
					+ "PP584,72\r\n"
					+ "PT \""+SPLength+"\"\r\n"					//数量
					+ "PP204,48\r\n"
					+ "PT \"同质配件行业管理代码：\"\r\n"
					+ "PP428,48\r\n"
					+ "PT \""+HYGLM+"\"\r\n"					//同质配件行业管理代码
					+ "PP204,24\r\n"
					+ "PT \"企业名称：\"\r\n"
					+ "PP304,24\r\n"
					+ "PT \""+QYMC1+"\"\r\n"			//企业名称1
					+ "PP304,0\r\n"
					+ "PT \""+QYMC2+"\"\r\n"			//企业名称2
					+ "PP532,172\r\n"
					+ "BARSET \"QRCODE\",3,1,4,2,2\r\n"
					+ "PB \""+pCode+"\"\r\n"
					+ "PF\r\n"
					+ "RUN\r\n";
	
		return ZXMLabel;
	}
	
	//验证DB中传输字符串是否为空
	private String DBisNotEmpty(Object o){
		String s=(String) o;
		if(s==null){
			s="";
		}
		return s;
	}
	
	//标签单行信息超出截断
	private String cutInfo(String str,Integer requireSize){
		Float tempSize=0.0F;
		StringBuffer sb=new StringBuffer();

		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			
			if(Character.isDigit(charArray[i])){   //确定指定字符是否为数字
				tempSize+=0.548F;
			}else if (Character.isLowerCase(charArray[i])) {   //确定指定字符是否为小写字母
				tempSize+=0.47F;
			}else if (Character.isUpperCase(charArray[i])) {   //确定指定字符是否为大写字母。
				tempSize+=0.677F;
			}else {
				tempSize+=1.0F;
			}
			sb.append(charArray[i]);
			
			if(tempSize>=requireSize-1){
				return sb.toString();
			}
		}
		
		return sb.toString();
	}
	
	//商品名称信息超出截断
	private String[] cutSPMCInfo(String str,Integer requireSize){
		String oneSPMCInfo = cutInfo(str, requireSize+1);   //第一行信息
		String twoSPMCInfo="";
		if(str.equals(oneSPMCInfo)){						//如果为true,只有一行信息
			
		}else {
			String tempstr=str.substring(oneSPMCInfo.length());
			twoSPMCInfo =cutInfo(tempstr, requireSize);
			 if(!str.equals(oneSPMCInfo+twoSPMCInfo)){
				 twoSPMCInfo+="...";
			 }
		}
		return new String[] {oneSPMCInfo,twoSPMCInfo};
	}

}
