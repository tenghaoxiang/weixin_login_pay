package top.haibaraai.wx_login_pay;

import io.jsonwebtoken.Claims;
import org.junit.Test;
import top.haibaraai.wx_login_pay.domain.User;
import top.haibaraai.wx_login_pay.utils.JwtUtils;

public class CommonTest {

    @Test
    public void testGeneJwt() {

        User user = new User();
        user.setId(627);
        user.setName("滕浩翔");
        user.setHeadImg("www.haibaraai.top");
        String token = JwtUtils.geneJsonWebToken(user);
        Claims claims = JwtUtils.checkJWT(token);
        if (claims != null) {
            String name = (String) claims.get("name");
            Integer id = (Integer) claims.get("id");
            String headImg = (String) claims.get("headImg");
            System.out.println(name);
            System.out.println(id);
            System.out.println(headImg);
        } else {
            System.out.println("非法token");
        }

    }

}
