package top.haibaraai.wx_login_pay.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import top.haibaraai.wx_login_pay.domain.User;

public interface UserMapper {

    /**
     * 根据主键id查找用户
     * @param id
     * @return
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(int id);

    /**
     * 根据openid查找用户
     * @param openid
     * @return
     */
    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User findByOpenId(String openid);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @Insert("INSERT INTO `wechat_pay`.`user`" +
            "(`openid`, `name`, `head_img`, `phone`, `sign`, `sex`, `city`, `create_time`) " +
            "VALUES (#{openid}, #{name}, #{headImg}, #{phone}, #{sign}, #{sex}, #{city}, #{createTime});")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int save(User user);

}
