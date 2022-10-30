package cn_java_mapper;

import cn_java_PO.User;
import cn_java_PO.UserExample;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User row);

    int insertSelective(User row);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") User row, @Param("example") UserExample example);

    int updateByExample(@Param("row") User row, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);

    /**
     * 判断用户传过来的账号和密码在数据库中是否存在
     * @param account:账号
     * @param password:密码
     * @return
     */
    @Select("select count(*) as result from user where account=#{0} and password=#{1}")
    int userIsExists(String account, String password);

    /**
     * 根据账号得到头像
     * @param account
     * @return
     */
    @Select("select avatar from user where account=#{0}")
    String getAvatar(String account);

    /**
     * 判断用户注册时输入的账号是否已经存在
     * @param account
     * @return
     */
    @Select("select count(*) as result from user where account=#{0}")
    int accountIsExists(String account);

    /**
     * 根据用户的账号来设置头像
     * @param account
     * @param avatarName
     * @return
     */
    @Update("update user set avatar=#{1} where account=#{0}")
    int setAvatar(String account, String avatarName);

    /**
     * 根据账号得到用户的基本信息
     * @param account
     * @return
     */
    @Select("select avatar,nickname,introduction,gender,address,birthday from user where account=#{0}")
    Map<String, Object> getAllInfoByAccount(String account);

    /**
     * 根据账号更新用户的基本信息
     * @param user
     * @return
     */
    @Update("update user set nickname=#{nickname},introduction=#{introduction},gender=#{gender},birthday=#{birthday},address=#{address} where account=#{account}")
    int UpdateUserInfoByAccount(User user);

    /**
     * 根据账号得到用户的id,从而将博客的user_id关联到相关的用户
     * @param account
     * @return
     */
    @Select("select id from user where account=#{0}")
    Map<String, Object> getIdByAccount(String account);

    @Select("select avatar,nickname from user where id=#{0}")
    Map<String, Object> getNickname(Long id);
}