package com.chamcontinhluong.apigateway.secuirty;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;

@Builder
public class JwtAuthenticationFilter  implements GatewayFilter  {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer ";
    private static final String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"; // Thay đổi bằng khóa bí mật của bạn



    private List<String> userPaths;
    private List<String> adminPaths;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        HttpMethod method = exchange.getRequest().getMethod();
//            if (path.startsWith(excludedPaths)) {
//                return chain.filter(exchange); // Bỏ qua bộ lọc cho các đường dẫn không cần xác thực
//            }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN_TYPE)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        if(!JwtService.builder().build().validateToken(token)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        if(path.contains("NV")){
            return chain.filter(exchange);
        }
       // String requiredRoles = exchange.getRequest().getHeaders().getFirst("X-Required-Roles");
        String role = extractRoleFromToken(token);
        if (role != null) {

            System.out.println(userPaths);
            if (userPaths.stream().anyMatch(p-> p.startsWith(path)) && !role.equals("USER")) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);

                return exchange.getResponse().setComplete();
            }


            if (adminPaths.stream().anyMatch(path::startsWith) && !role.equals("ADMIN")) {
                if(path.contains("change_password")){
                    return chain.filter(exchange);
                }
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);

                return exchange.getResponse().setComplete();
            }

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
    private String extractRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("role", String.class); // Assuming role is stored in the "role" claim
        } catch (Exception e) {
            return null;
        }
    }

}
