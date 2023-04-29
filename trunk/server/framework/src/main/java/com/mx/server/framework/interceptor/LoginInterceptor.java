// package com.mx.server.framework.interceptor;
//
// import cn.dev33.satoken.stp.StpUtil;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.HandlerInterceptor;
//
// /**
//  * @author Ck
//  */
// @Slf4j
// @Component
// public class LoginInterceptor implements HandlerInterceptor {
//     @Value("${sa-token.timeout}")
//     private Integer renewTimeout;
//
//
//     private final UserService userService;
//
//     public LoginInterceptor(UserService userService) {
//         this.userService = userService;
//     }
//
//     @Override
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//         log.debug("request url: {}", request.getRequestURL());
//         log.debug("Token:" + StpUtil.getTokenInfo());
//         try {
//             // 若在长期有效期内，则自动续签临时和长久有效期
//             if (StpUtil.getTokenTimeout() > 0) {
//                 // 长时间无操作，视为重新登录
//                 if (StpUtil.getTokenActivityTimeout() < 0) {
//                     log.debug("Request re login, Log login");
//                     int id = Integer.parseInt(String.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue())));
//                     userService.logLogin(id);
//                 }
//                 // 续签临时有效期
//                 StpUtil.updateLastActivityToNow();
//                 // 续签长时有效期
//                 StpUtil.renewTimeout(renewTimeout);
//                 log.debug("Request is Login, refresh activity token");
//             }
//         } catch (Exception ignore) {
//         }
//         return true;
//     }
// }
