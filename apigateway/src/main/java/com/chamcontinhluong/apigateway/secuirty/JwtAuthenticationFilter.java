package com.chamcontinhluong.apigateway.secuirty;

import com.chamcontinhluong.apigateway.config.ListURL;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  implements GatewayFilter  {

    private final JwtService jwtService;
    private final ListURL listURL;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        HttpMethod method = exchange.getRequest().getMethod();


        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        if(path.contains("NV")){
            return chain.filter(exchange);
        }
       // String requiredRoles = exchange.getRequest().getHeaders().getFirst("X-Required-Roles");
        String role =jwtService.extractRoleFromToken(token);
        if (role != null) {
            if ("ADMIN".equals(role)) {
                return chain.filter(exchange);
            }



            // Kiểm tra quyền từ token
            List<String> permissions = jwtService.extractPermissionsFromToken(token);
            Boolean checkPermissions = listURL.isPermissionsUrl(permissions,path);
            // Kiểm tra quyền của phương thức và path
//            if (!permissions.contains(requiredPermission)) {
//                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                return exchange.getResponse().setComplete();
//            }
        if(!checkPermissions){
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }



//            if (userPaths.stream().anyMatch(p-> p.startsWith(path)) && !role.equals("USER")) {
//                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                return exchange.getResponse().setComplete();
//            }

//            if (adminPaths.stream().anyMatch(path::startsWith) && !role.equals("ADMIN")) {
//                if(path.contains("change_password")){
//                    return chain.filter(exchange);
//                }
//                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                return exchange.getResponse().setComplete();
//            }

        } else {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> handleForbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }


}
