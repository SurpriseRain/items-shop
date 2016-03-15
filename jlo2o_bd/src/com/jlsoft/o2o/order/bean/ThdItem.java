package com.jlsoft.o2o.order.bean;

import java.io.Serializable;

public class ThdItem implements Serializable {
	private int SPXX01 = 0;
	private String XSDD01 = null;
	private String THDH = null;
	private String TPMC = null;
	private String SPXX02 = null;
	private String SPXX04 = null;
	private String SPTP02 = null;
	public String getTPMC() {
		return TPMC;
	}

	public String getSPTP02() {
		return SPTP02;
	}

	public void setSPTP02(String sPTP02) {
		SPTP02 = sPTP02;
	}

	public void setTPMC(String tPMC) {
		TPMC = tPMC;
	}

	public String getTHDH() {
		return THDH;
	}

	public void setTHDH(String tHDH) {
		THDH = tHDH;
	}

	public String getSPXX04() {
		return SPXX04;
	}

	public void setSPXX04(String sPXX04) {
		SPXX04 = sPXX04;
	}

	public String getSPXX02() {
		return SPXX02;
	}

	public void setSPXX02(String sPXX02) {
		SPXX02 = sPXX02;
	}

	public void setSPXX01(int sPXX01) {
		SPXX01 = sPXX01;
	}

	public int getSPXX01() {
		return SPXX01;
	}

	public void setXSDD01(String xSDD01) {
		XSDD01 = xSDD01;
	}

	public String getXSDD01() {
		return XSDD01;
	}
}
