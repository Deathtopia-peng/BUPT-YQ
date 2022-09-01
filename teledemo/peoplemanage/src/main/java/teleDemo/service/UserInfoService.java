package teleDemo.service;

import org.springframework.stereotype.Service;
import teleDemo.entities.GetVo;
import teleDemo.entities.tbuser;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoService {
    @Resource
    teleDemo.mapper.userInfoMapper userInfoMapper;

    public List<tbuser> gettbUSertbInfos(int limit, int page) {
        List<tbuser> tbInfos = userInfoMapper.gettbUserByPage((page-1)*limit,limit);
        return tbInfos;
    }
    public List<Integer> getEffected(){
        return userInfoMapper.getAllEffected();
    }

    public List<Integer> getCloseContacts(){
        return userInfoMapper.getAllCloseContacts();
    }
}
