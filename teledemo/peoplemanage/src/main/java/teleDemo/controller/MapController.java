package teleDemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengs
 */

@Controller
@ResponseBody
public class MapController {
    @Resource
    teleinfoController teleinfoController;

    @RequestMapping("/map/test")
    public String toMap(Model model){
        List<List<String>> result;
        List<String> tmp = teleinfoController.getLatLon(teleinfoController.getEffectedLonLat());

        result = getProvinceCityDistrict(tmp);

        model.addAttribute("data", result);



        return "map";
    }

    private List<List<String>> getProvinceCityDistrict(List<String> latLonS){
        List<List<String>> result = new ArrayList<>();
        List<String> provinceResult = new ArrayList<>();
        List<String> cityResult = new ArrayList<>();
        List<String> districtResult = new ArrayList<>();
        String urlString = "https://api.map.baidu.com/reverse_geocoding/v3/?ak=mkK6K43xFtmqZG4om5HQwttuqyIboZu0&output=json&coordtype=wgs84ll&location=";
        for(String latLon : latLonS){
            String tmpUrlString = urlString + latLon;
            StringBuilder tmpResult = new StringBuilder();
            try{
                URL url = new URL(tmpUrlString);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();

                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                java.io.BufferedReader in = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                );

                String line;
                while((line = in.readLine()) != null){
                    tmpResult.append(line).append("\n");
                }
                in.close();
            } catch (Exception e){
                e.printStackTrace();
            }
            String tmp = tmpResult.toString();
            JSONObject object = JSON.parseObject(tmp);
            JSONObject tmpObject = object.getJSONObject("result").getJSONObject("addressComponent");

            provinceResult.add(tmpObject.getString("province"));
            cityResult.add(tmpObject.getString("city"));
            districtResult.add(tmpObject.getString("district"));

        }

        result.add(provinceResult);
        result.add(cityResult);
        result.add(districtResult);

        return result;
    }

}
