$(function() {
    mapChart('mapChart')
})

let mapStack = []
let parentId = null
let parentName = null

let chinaId = 100000
let chinaName = 'china'
let chinaJson = null

let myChart = null

let province_list = []
let city_list = []
let district_list = []

function initEData() {
    $.ajax({
        url:"/map/test",
        dataType:'List',
        success: function(data){
            province_list = data[0];
            city_list = data[1];
            district_list = data[2];
        }
    })
}

province_list = ["北京","北京","北京","北京","北京","北京","北京","河北","北京","北京","北京","北京","北京","北京","北京","河北","河北","河北","河北"];
city_list = ["北京市","北京市","北京市","北京市","北京市","北京市","北京市","沧州市","北京市","北京市","北京市","北京市","北京市","北京市","北京市","沧州市","沧州市","沧州市","沧州市"];
district_list = ["海淀区","海淀区","海淀区","海淀区","海淀区","海淀区","海淀区","任丘市","丰台区","海淀区","海淀区","海淀区","海淀区","大兴区","海淀区","任丘市","任丘市","任丘市","任丘市"];

function initMapData(mapJson) {
    let mapData = [];
    let tmp;
    let isRestricted = 0;
    for (let i = 0; i < mapJson.features.length; i++) {
        tmp = mapJson.features[i].properties.name;
        if (province_list.includes(tmp) || city_list.includes(tmp) || district_list.includes(tmp)){
            isRestricted = 1;
        }
        else {
            isRestricted = 0;
        }
        mapData.push({
            name: mapJson.features[i].properties.name,
            value: isRestricted
        })
    }
    return mapData;
}

function back(){
    if (mapStack.length !== 0){
        let map = mapStack.pop()
        $.get('../json/map/' + map.mapId + '.json', function(mapJson){
            registerAndSetOption(myChart, map.mapId, map.mapName, mapJson, false);
            parentId = map.mapId;
            parentName = map.mapName;
        })
    }
}

function registerAndSetOption(myChart, id, name, mapJson, flag){
    echarts.registerMap(name, mapJson);
    myChart.setOption({
        series: [{
            type: 'map',
            map: name,
            data: initMapData(mapJson)
        }],
        visualMap: {
            min: 0,
            max: 1,
            text: ['High', 'Low'],
            realtime: false,
            calculable: true,
            inRange: {
                color: ['lightskyblue', 'yellow', 'orangered']
            }
        },
    });

    if(flag){
        mapStack.push({
            mapId: parentId,
            mapName: parentName
        })
        parentId = id;
        parentName = name;
    }
}

function mapChart(div){
    //initEData();
    $.get('../json/map/' + chinaId + '.json', function(mapJson){
        chinaJson = mapJson;
        myChart = echarts.init(document.getElementById(div));
        registerAndSetOption(myChart, chinaId, chinaName, mapJson, false)

        parentId = chinaId;
        parentName = chinaName;

        myChart.on('click', function(param){
            let cityId = cityMap[param.name]
            if(cityId){
                $.get('../json/map/' + cityId + '.json', function(mapJson){
                    registerAndSetOption(myChart, cityId, param.name, mapJson, true);
                })
            } else {
                registerAndSetOption(myChart, chinaId, chinaName, chinaJson, false);
                mapStack = [];
                parentId = chinaId;
                parentName = chinaName;
            }
        })
    })
}