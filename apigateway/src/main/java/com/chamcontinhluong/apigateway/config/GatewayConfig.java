package com.chamcontinhluong.apigateway.config;

import com.chamcontinhluong.apigateway.secuirty.JwtAdminAuthenticationFilter;
import com.chamcontinhluong.apigateway.secuirty.JwtAuthenticationFilter;
import com.chamcontinhluong.apigateway.secuirty.JwtUserAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;


import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor

public class GatewayConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account-service",r->r.path("/api/v1/auth/login")
                        .uri("http://localhost:8082"))
                .route("auth-service",
                        r -> r.path("/api/v1/account/**")
                        .or().path("/api/v1/create_account")
                        .or().path("/api/v1/change_password")
                        .filters(f -> f.filter(JwtAuthenticationFilter.builder()

                                .userPaths(Arrays.asList( "/api/v1/change_password"))
                                .adminPaths(Arrays.asList("/api/v1/account","api/v1/account/**","/api/v1/create_account," +
                                        "/api/v1/change_password")).build()))
                        .uri("http://localhost:8082"))
                .route("employee-service-admin", r -> r.path(
                                        "/api/v1/employee/**")
                                .and().method(HttpMethod.GET,HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE)
                        .filters(f->f.filter(JwtAdminAuthenticationFilter
                                .builder().checkpath("/api/v1/employee/**").build()))
                        .uri("http://localhost:8080"))

                .route("employee-service", r -> r
                        .path("/api/v1/employee/amount")
                        .or().path("/api/v1/employee/detail-salary/{idemployee}")
                        .or().path("/api/v1/employee/list")
                        .or().path("/api/v1/employee/generateId")
                        .filters(f -> f.filter(JwtAuthenticationFilter.builder()
                                        .adminPaths(Arrays.asList( "/api/v1/employee","api/v1/employee/amount"

                                        ,"/api/v1/employee/detail-salary/{idemployee}"
                                        ,"/api/v1/employee/list"
                                        ,"/api/v1/employee/generateId"))
                                        .userPaths(Arrays.asList("/sdfs"))
                                .build()))
                        .uri("http://localhost:8080"))
                .route("attendance-service-admin", r -> r.path("/api/v1/attendance")
                        .and().method(HttpMethod.GET)
                        .filters(f -> f.filter(JwtAdminAuthenticationFilter.builder()
                                .checkpath("/api/v1/attendance")
                                .build()))
                        .uri("http://localhost:8083")
                )
                .route("attendance-service-user", r -> r.path("/api/v1/attendance")
                        .and().method(HttpMethod.POST, HttpMethod.PUT)
                        .filters(f -> f.filter(JwtUserAuthenticationFilter.builder()
                                .checkpath("/api/v1/attendance")
                                .build()))
                        .uri("http://localhost:8083")
                )

                .route("attendance-service", r -> r.
                       path("/api/v1/attendance/{idemployee}")
                        .or().path("/api/v1/attendance/countDate")
                        .or().path("/api/v1/attendance/admin/**")
                        .filters(f -> f.filter(JwtAuthenticationFilter.builder()
                                        .adminPaths(Arrays.asList("/api/v1/attendance/countDate","/api/v1/attendance/admin/**"))
                                        .userPaths(List.of("/api/v1/attendance/{idemployee}"))
                                .build()))
                        .uri("http://localhost:8083"))
                .route("workrecord-service",r->r.path("/api/v1/workrecord")
                        .or().path("/api/v1/workrecord/getid")
                        .or().path("/api/v1/workrecord/getdaywork")
                        .or().path("/api/v1/workrecord/filter")

                        .filters(f->f.filter(JwtAuthenticationFilter.builder()
                                .adminPaths(Arrays.asList("/api/v1/work/record",
                                        "/api/v1/workrecord/getid",
                                        "/api/v1/workrecord/getdaywork",
                                        "/api/v1/workrecord/filter"))
                                .userPaths(Arrays.asList("/api/v1/attendance/{idemployee}"))
                                .build()))
                        .uri("http://localhost:8083"))
                .route("attendance_explain-service-admin",r->r.path("/api/v1/attendance_explain")
                        .and().method(HttpMethod.PUT,HttpMethod.GET)
                        .filters(f->f.filter(JwtAdminAuthenticationFilter
                                .builder()
                                .checkpath("/api/v1/attendance_explain")
                                .build()))
                        .uri("http://localhost:8083")
                )
                .route("attendance_explain-service-user",r->r.path("/api/v1/attendance_explain")
                        .and().method(HttpMethod.POST)
                        .filters(f->f.filter(JwtUserAuthenticationFilter
                                .builder()
                                .checkpath("/api/v1/attendance_explain")
                                .build()))
                        .uri("http://localhost:8083")
                )
                .route("attendance_explain-service",r->r.path("/api/v1/attendance_explain/filter")
                        .or().path("/api/v1/attendance_explain/{idemployee}")
                        .filters(f->f.filter(JwtAuthenticationFilter
                                .builder()
                                .adminPaths(List.of("/api/v1/attendance_explain/filter"))
                                .userPaths(List.of("/api/v1/attendance_explain/{idemployee}"))
                                .build()))
                        .uri("http://localhost:8083")
                )
                .route("workschedule-service-admin",r -> r.path("/api/v1/workschedule")
                        .and().method(HttpMethod.POST,HttpMethod.GET,HttpMethod.DELETE)
                        .filters(f -> f.filter(JwtAdminAuthenticationFilter.builder()
                                        .checkpath("/api/v1/workschedule")
                                .build()))
                        .uri("http://localhost:8084"))
                .route("workschedule-service",r->r.path(
                        "/api/v1/workschedule/getidemp"
                ).or().path("/api/v1/workschedule/workdate")
                        .or().path("/api/v1/workschedule/{idemployee}")
                  .or().path("/api/v1/workschedule/workschedulemployee")

                        .filters(f->f.filter(
                                JwtAuthenticationFilter.builder()
                                        .adminPaths(Arrays.asList("/api/v1/workschedule/getidemp"
                                        ,"/api/v1/workschedule/workdate"
                                        ,"/api/v1/workschedule/workschedulemployee"
                                                ,"/api/v1/workschedule/{idemployee}"
                                                ))
                                        .userPaths(List.of("asd"))
                                .build()
                        ))
                        .uri("http://localhost:8084"))

                .route("workscheduledetail-service",r->r.path("/api/v1/workscheduledetail/**")
                        .filters(f->f.filter(JwtAdminAuthenticationFilter
                                .builder().checkpath("/api/v1/workscheduledetail/**").build()))
                        .uri("http://localhost:8084"))

                .route("payroll-service-admin",r->r.path("/api/v1/payroll/**")
                        .and().method(HttpMethod.GET,HttpMethod.PUT,HttpMethod.POST)
                        .filters(f->f.filter(JwtAdminAuthenticationFilter
                                .builder().checkpath("/api/v1/payroll/**").
                                build()))
                        .uri("http://localhost:8085")
                )
                .route("payroll-service",r->r.path("/api/v1/payroll/getidemployee")
                        .or().path("/api/v1/payroll/totalPayment")
                        .or().path("/api/v1/payroll/getMonthlyEmployee")
                        .filters(f->f.filter(JwtAuthenticationFilter
                                .builder().
                                adminPaths(Arrays.asList("/api/v1/payroll/getidemployee"
                                ,"/api/v1/payroll/totalPayment"
                                ,"/api/v1/payroll/getMonthlyEmployee"))
                                        .userPaths(List.of("/asda"))
                                .build()))
                        .uri("http://localhost:8085"))
                .build();
    }
}
