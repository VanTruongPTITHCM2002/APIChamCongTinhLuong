package com.chamcontinhluong.apigateway.config;

import com.chamcontinhluong.apigateway.ConstantsURL;
import com.chamcontinhluong.apigateway.secuirty.JwtAdminAuthenticationFilter;
import com.chamcontinhluong.apigateway.secuirty.JwtAuthenticationFilter;
import com.chamcontinhluong.apigateway.secuirty.JwtService;
import com.chamcontinhluong.apigateway.secuirty.JwtUserAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;


import java.util.Arrays;
import java.util.List;

@Configuration

public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account-service",r->r.path(ConstantsURL.AUTH_URL + "/login")
                        .uri("lb://account-service"))
                .route("auth-service",
                        r -> r.path("/api/v1/account/**")
                        .or().path("/api/v1/create_account")

                        .filters(f -> f.filter(jwtAuthenticationFilter)

                                )

                        .uri("lb://account-service"))
                .route("employee-service-admin", r -> r.path(
                                        "/api/v1/employee/**")
                                .and().method(HttpMethod.GET,HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE)
                        .filters(f->f.filter(JwtAdminAuthenticationFilter
                                .builder().checkpath("/api/v1/employee/**").build()))
                        .uri("lb://employee-service"))

                .route("employee-service", r -> r
                        .path("/api/v1/employee/amount")
                        .or().path("/api/v1/employee/detail-salary/{idemployee}")
                        .or().path("/api/v1/employee/list")
                        .or().path("/api/v1/employee/generateId")
                        .filters(f -> f.filter(
                                jwtAuthenticationFilter
                                ))
                        .uri("lb://employee-service"))
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
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8083"))
                .route("workrecord-service",r->r.path("/api/v1/workrecord")
                        .or().path("/api/v1/workrecord/getid")
                        .or().path("/api/v1/workrecord/getdaywork")
                        .or().path("/api/v1/workrecord/filter")

                        .filters(f->f.filter(jwtAuthenticationFilter)
                                )
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
                        .filters(f->f.filter(jwtAuthenticationFilter))
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
                                jwtAuthenticationFilter
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
                        .filters(f->f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8085"))

                .route("rewardpunish-service-admin",r->r.path("/api/v1/rewardpunish/**")
                        .and().method(HttpMethod.GET,HttpMethod.POST,HttpMethod.DELETE)
                        .filters(f->f.filter(JwtAdminAuthenticationFilter
                                .builder()
                                        .checkpath("/api/v1/rewardpunish/**")
                                .build()))
                        .uri("http://localhost:8086"))

                .route("rewardpunish-service",r->r.path("/api/v1/rewardpunish/countRewardPunish")
                        .or().path("/api/v1/rewardpunish/calsalary")
                        .filters(f->f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8086"))

                .route("contract-service-admin",r->r.path("/api/v1/contract/**")
                        .and().method(HttpMethod.POST,HttpMethod.GET,HttpMethod.PUT)
                        .filters(f->f.filter(JwtAdminAuthenticationFilter
                                .builder()
                                        .checkpath("/api/v1/contract/**")
                                .build()))
                        .uri("http://localhost:8087"))
                .route("contract-service",r->r.path("/api/v1/contract/checkcontract")
                        .or().path("/api/v1/contract/countContract")
                        .or().path("/api/v1/contract/checkemployee/**")
                        .or().path("/api/v1/contract/getcontract")
                        .or().path("/api/v1/contract/statusContract")
                        .filters(f-> f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8087"))
                .build();

    }
}
