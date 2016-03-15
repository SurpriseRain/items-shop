<%@ page contentType="text/html; charset=UTF-8" %>
<link type="text/css" rel="StyleSheet" href="<%=request.getContextPath()%>/css/motai.css"/>
<link type="text/css" rel="StyleSheet" href="<%=request.getContextPath()%>/css/readExcel.css"/>
<!--导入 add lx 20130919-->
<form id="form2" name="form2" action="<%=request.getContextPath()%>/ImportAction.do"  method="post" enctype="multipart/form-data"  style="display:block">
<table id="readimport" style="display:none">
  <tr>
    <td>
      <div class="readimport_mwindow" style="padding-bottom:0px">
        <div class="readimport_mwindowstitle" id="readimport_mt_win_title" style="padding:2px 0 2px 2px;">
          <table width="100%" style="border:0px;">
            <tr>
              <td width="90%" align="left" style="border:0px;text-align:left;">导入EXCEL</td>
              <td width="10%" style="border:0px;cursor:pointer;" onclick="closeReadimportExcel()">×&nbsp;</td>
            </tr>
          </table>
        </div>
        <div class="readimport_mwindowsmsg" id="mt_win_msg" style="padding:0px 0 0px 0px;">
          <table width="100%">
            <tr>
              <td width="30%" style="text-align:right;padding-right:2px;height:30px;">导入EXCEL</td>
              <td width="70%" style="text-align:left;padding-left:2px;height:30px;">
                <input type="file" id="FileUploadExcel" name="file" value=""/>
                <input type="hidden" id="fileLX" name="fileLX" value="IMPORT_GDJJITEM" />
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <img style="cursor:hand;" alt="确定" src="<%=request.getContextPath()%>/img/save.gif" onclick="importUploadExcel()"/>
                <!--<input type="submit" value="提交" onclick="importUploadExcel()" />-->
              </td>
            </tr>
          </table>
        </div>
      </div>
    </td>
  </tr>
</table>
</form>
<!--导入EXCEL结束-->

<script type="text/javascript">
function showReadimportExcel()
{
  document.getElementById("div_readExcel_Bg").style.display="block";
  document.getElementById("readimport").style.display = "block";
  document.getElementById("form2").style.display="block";
}

function closeReadimportExcel()
{
  document.getElementById("processingTable").style.display = "none";
  document.getElementById("div_readExcel_Bg").style.display="none";
  document.getElementById("readimport").style.display = "none";
  document.getElementById("form2").style.display="none";
}


//上传EXCEL
function importUploadExcel(){
  var excelApp;
  var excelWorkBook;
  var excelSheet;
  var file = document.getElementById("FileUploadExcel").value;
  if (file == "") {
    alert("请选择导入EXCEL文件");
    return false;
  }else{
    var stuff = file.split(".")[1];
    if (stuff != "xls" && stuff != "xlsx"){
      alert("请选择正确的EXCEL文件导入");
      return false;
    }
    form2.submit();
  }
}
</script>
