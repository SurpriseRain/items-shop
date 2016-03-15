var mapObj;
var marker = new Array();
function mapInit(id,pannel){
	var wrap = null;
	$(document.body).append("<div id=\"resultDiv\" style=\"display:none;\"><div>");
	if(!id){
		wrap = pannel ? $(pannel) :$(document.body);
		wrap.append("<div id=\"iCenter\" style=\"display:none;\"></div>");
		id='iCenter';
	}
	mapObj = new AMap.Map(id, {
		resizeEnable: false
	});
    var clickEventListener = mapObj.on( 'click', function(e) {
        lngX=e.lnglat.getLng();
        latY=e.lnglat.getLat();
        mapObj.clearMap();
    	//id.setMap(null);
        //alert(1);
        addMarker(lngX,latY);
        getAddress(lngX,latY);
        //document.getElementById("lngX").value = e.lnglat.getLng();
        //document.getElementById("latY").value = e.lnglat.getLat();
    });
}

//cityCode 城市代码  address
function searchPostion(cityCode, address,flag){
	if(undefined != address && "" != address){
		AMap.service(["AMap.PlaceSearch"], function() {
	    var placeSearch;
	    if(null != cityCode && "" != cityCode){
	    	placeSearch = new AMap.PlaceSearch({ //构造地点查询类
	        pageSize: 5,
	        pageIndex: 1,
	        city: cityCode, //城市
	        map: mapObj,
	        panel: "resultDiv"
	   	 });
	    }else{
	    	placeSearch = new AMap.PlaceSearch({ //构造地点查询类
	        pageSize: 5,
	        pageIndex: 1,
	        map: mapObj,
	        panel: "resultDiv"
	   	 });
	    }
	    //关键字查询
	    placeSearch.search(address, function(status, result) {
	    	if(result.info == "OK" && null != result.poiList && result.poiList.count > 0 ){
	    		var obj = (result.poiList.pois)[0];
	    		var locat = obj.location;
	    		//returnVal = locat.lng + "," + locat.lat;
				if(typeof(setLngLat)=="function"){
				  setLngLat(locat.lng,locat.lat,null);
				}
	    		mapObj.clearMap();
	    		if(flag=="show"){
	    			addMarker(locat.lng, locat.lat);
	    		}
	    	}
	    });
		});
	}
}

function addMarker(lngX, latY){
  var markerOption = {  
      map:mapObj,
      icon:"http://webapi.amap.com/images/marker_sprite.png",    
      position:new AMap.LngLat(lngX, latY)  
  };
  var mar = new AMap.Marker(markerOption);
  marker.push(new AMap.LngLat(lngX, latY));
}

function getAddress(lngX, latY)
{
	//setLngLat(locat.lng,locat.lat,null);
	AMap.service('AMap.Geocoder',function(){//回调函数
        //实例化Geocoder
        geocoder = new AMap.Geocoder({
            city: "010"//城市，默认：“全国”
        });
        var lnglatXY=[lngX, latY];//地图上所标点的坐标
        geocoder.getAddress(lnglatXY, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
               //获得了有效的地址信息:
               //即，result.regeocode.formattedAddress
          	  //alert(result.regeocode.formattedAddress);
            	var add=result.regeocode.formattedAddress;
            	if(typeof(setLngLat)=="function"){
            		setLngLat(lngX, latY,add);
  				}
            }else{
               //获取地址失败
            }
        });
        //TODO: 使用geocoder 对象完成相关功能
    })
}