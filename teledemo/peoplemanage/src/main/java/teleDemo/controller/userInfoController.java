package teleDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import teleDemo.entities.GetVo;
import teleDemo.entities.tbuser;
import teleDemo.service.UserInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
public class userInfoController {
    @Resource
    teleDemo.mapper.userInfoMapper userInfoMapper;

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/v1/userInfo")
    public GetVo gettbUSer(HttpServletRequest request){
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        int size = userInfoMapper.getAlltbUser().size();
        List<tbuser> tbInfos = userInfoService.gettbUSertbInfos(limit,page);

        List<Integer> tmp = getEffected();
        for(Integer integer : tmp){
            System.out.println(integer);
        }
        tmp = getCloseContacts();
        for(Integer integer : tmp){
            System.out.println(integer);
        }

        return new GetVo<>(0,"获取数据成功！",size,tbInfos);
    }

    @GetMapping("/v1/userInfo/check")
    public GetVo checktbUSer(HttpServletRequest request){
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = Integer.valueOf(request.getParameter("page"));
        int userStatus = Integer.valueOf(request.getParameter("keyword"));
        int size = userInfoMapper.checktbUser(userStatus).size();
        List<tbuser> tbInfos = userInfoMapper.checktbUserByPage((page-1)*limit,limit,userStatus);
        if(tbInfos.size() == 0){
            page = 1;
            tbInfos = userInfoMapper.checktbUserByPage((page-1)*limit,limit,userStatus);
        }
        GetVo<tbuser> getVo = new GetVo<>(0,"获取数据成功！",size,tbInfos);
        return  getVo;
    }


    public List<Integer> getEffected(){
        return userInfoMapper.getAllEffected();
    }

    public List<Integer> getCloseContacts(){
        return userInfoMapper.getAllCloseContacts();
    }
}
