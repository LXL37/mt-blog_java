import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Function;
import com.google.gson.Gson;
import com.meet.BlogApplication;
import com.meet.domain.entity.Menu;
import com.meet.domain.entity.User;
import com.meet.domain.vo.SysUserListVo;
import com.meet.mapper.MenuMapper;
import com.meet.mapper.RoleMapper;
import com.meet.mapper.UserMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import javafx.application.Application;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;


import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Author: alyosha
 * @Date: 2022/3/28 19:06
 */


@MapperScan("com.meet.mapper")

@SpringBootTest(classes = BlogApplication.class)

public class test1 {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMapper roleMapper;
    @Test
    public void Test1(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.matches("1234","$2a$10$SqdgfXVFUdk6ArSOxXmyV.lw.4TzK7SU742tu172xVPcm.MrmvIe2"));

    }
    @Before("")
    private void init() throws FileNotFoundException {
        File file = new File("UserMapper.xml");
        Reader reader=new FileReader(file);
        System.out.println(file);

  
    }
    @Test
    public void mybatisTest(){

        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper(new User());

        queryWrapper.select(tableFieldInfo ->(!"id".equals(tableFieldInfo.getColumn())));

        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users.size());
        System.out.println(JSON.toJSON(users));
        /*SimpleExecutor simpleExecutor=new SimpleExecutor()*/

    }
    @Test
    public void pageTest(){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.orderByDesc(User::getId);
        Page<User> page=new Page();
        page.setSize(10);
        page.setCurrent(3);

        List<User> records = userMapper.selectPage(page, queryWrapper).getRecords();




    }
    @Test
    public void menuTest(){
        Long[] ids={7l,9l,10l,11l};
        for (Long id : ids) {
            roleMapper.assignRolePerms(13l,id);
        }

    }
}
