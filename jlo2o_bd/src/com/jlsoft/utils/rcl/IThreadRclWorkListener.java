package com.jlsoft.utils.rcl;

public interface IThreadRclWorkListener {
  public void onAllThreadWorkDone(String sProcName,java.sql.Date dRclRQ,String[] sGSIDAry);
}
