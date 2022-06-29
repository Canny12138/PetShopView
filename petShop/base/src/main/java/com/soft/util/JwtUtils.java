package com.soft.util;

import com.soft.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/22 21:46
 **/
public class JwtUtils {
    private  final static long TIME = 1000*60*60*24;
    private  final static String SECRET = "test";//签名(解密信息)
    //获取Token
    public static String getToken(User user){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("username",user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+TIME))
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256,SECRET)
                //拼接
                .compact();
        return jwtToken;
    }
    //校验Token
    public static Result<String> validateToken(String token){
        Result<String> result = new Result<>();
        try {
            if(token!=null){
                Map<String, Object> body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody();
                String username = (String)(body.get("username"));
                if(username==null||username.isEmpty()){
                    result.fail("未找到用户名");
                }else {
                    result.setData(username);
                    result.success("ok");
                }
            }else {
                result.fail("未找到Token");
            }
        }catch (Exception e){
            result.fail(e.toString());
        }
        return result;
    }
//    //获取nickname
//    public static String getNicknameByToken(String token){
//        Map<String, Object> body = Jwts.parser()
//                .setSigningKey(SECRET)
//                .parseClaimsJws(token)
//                .getBody();
//        return (String) (body.get("nickname"));
//    }
}
