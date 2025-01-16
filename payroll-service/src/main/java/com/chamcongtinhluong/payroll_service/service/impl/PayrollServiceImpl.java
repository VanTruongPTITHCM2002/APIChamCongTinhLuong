package com.chamcongtinhluong.payroll_service.service.impl;

import com.chamcongtinhluong.payroll_service.Enum.StatusPayroll;
import com.chamcongtinhluong.payroll_service.commiunicate.*;
import com.chamcongtinhluong.payroll_service.dto.request.*;
import com.chamcongtinhluong.payroll_service.dto.response.*;
import com.chamcongtinhluong.payroll_service.entity.Payroll;
import com.chamcongtinhluong.payroll_service.repository.PayrollRepository;
import com.chamcongtinhluong.payroll_service.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final DetailSalaryServiceClient detailSalaryServiceClient;
    private final DayWorkServiceClient dayWorkServiceClient;
    private final IdEmployeeServiceClient idEmployeeServiceClient;
    private final PayrollContractClient payrollContractClient;
    private final RewardPunishPayrollClient rewardPunishPayrollClient;



    @Override
    public ResponseEntity<?> getListSalary() {
        try{
            List<PayrollResponse> responses= payrollRepository.findAll().stream().map(
                            e-> PayrollResponse.builder()
                                    .idEmployee(e.getIdemployee())
                                    .month(e.getMonth())
                                    .year(e.getYear())
                                    .reward(e.getReward())
                                    .punish(e.getPunish())
                                    .day_work(e.getDay_work())
                                    .createDate(e.getDatecreated())
                                    .totalPayment(e.getTotalpayment())
                                    .basicSalary(e.getBasicsalary())
                                    .status(StatusPayroll.getStatusFromCode(e.getStatus()))
                                    .build()
                    )
                    .toList();
            return ResponseEntity.ok().body(
                    ApiResponse.builder()
                            .status(HttpStatus.OK.value())
                            .message("Lấy thành công bảng lương")
                            .data(responses).build()
            );
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co so du lieu")
                            .build());
        }


    }

    @Override
    public ResponseEntity<ApiResponse> getListSalaryByMonthAndYear(int month, int year) {
        try{
            List<PayrollResponse> responses= payrollRepository.findAll().stream()
                    .filter(e->e.getMonth() == month && e.getYear() == year)
                    .map(
                            e-> PayrollResponse.builder()
                                    .idEmployee(e.getIdemployee())
                                    .month(e.getMonth())
                                    .year(e.getYear())
                                    .reward(e.getReward())
                                    .punish(e.getPunish())
                                    .day_work(e.getDay_work())
                                    .createDate(e.getDatecreated())
                                    .totalPayment(e.getTotalpayment())
                                    .status(StatusPayroll.getStatusFromCode(e.getStatus()))
                                    .build()
                    )
                    .toList();
            return ResponseEntity.ok().body(
                    ApiResponse.builder()
                            .status(HttpStatus.OK.value())
                            .message("Lấy thành công bảng lương")
                            .data(responses).build()
            );
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co so du lieu")
                            .build());
        }
    }

    @Override
    public ResponseEntity<?> getPayrollByIdEmployee(String idemployee) {
        if(idemployee.equals("all")){
            getListSalary();
        }
        List<PayrollResponse> payrollResponses = payrollRepository.findAll()
                .stream().filter(e->
                        e.getIdemployee().equals(idemployee))
                .map(e->
                        PayrollResponse.builder()
                                .idEmployee(e.getIdemployee())
                                .month(e.getMonth())
                                .year(e.getYear())
                                .basicSalary(e.getBasicsalary())
                                .reward(e.getReward())
                                .punish(e.getPunish())
                                .createDate(e.getDatecreated())
                                .day_work(e.getDay_work())
                                .totalPayment(e.getTotalpayment())
                                .status(StatusPayroll.getStatusFromCode(e.getStatus()))
                                .build()
                ).toList();
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Tìm kiếm thành công")
                        .data(payrollResponses).build()
        );
    }

    @Override
    public ResponseEntity<?> payrollEmployee(PayrollRequest payrollRequest) {

        Payroll payrollCheck = payrollRepository.findByIdemployeeAndMonthAndYear(payrollRequest.getIdEmployee(),payrollRequest.getMonth(),payrollRequest.getYear());

        if(payrollCheck != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Lương của nhân viên đã được tính")
                    .data("")
                    .build());
        }


        DayWorkResponse dayWorkResponse = new DayWorkResponse();
        ResponseEntity<?> response = null;
        PayrollContractResponse payrollContractResponse = payrollContractClient.getContractByIdMonthYear(
                PayrollContractRequest.builder().idemployee(payrollRequest.getIdEmployee()).month(payrollRequest.getMonth()).year(
                        payrollRequest.getYear()
                ).build()
        );
        if (payrollContractResponse == null) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Không thể tính lương do nhân viên " + payrollRequest.getIdEmployee() + " không có hợp đồng tháng " + payrollRequest.getMonth())
                    .data("")
                    .build());
        }

        List<RewardPunishPayrollResponse> rewardPunishPayrollResponseList = rewardPunishPayrollClient.getRewardPunishForCalSalary(
                RewardPunishPayrollRequest.builder()
                        .idemployee(payrollRequest.getIdEmployee())
                        .month(payrollRequest.getMonth())
                        .year(payrollRequest.getYear())
                        .build()
        );

        String name =  detailSalaryServiceClient.getDetailSalary(payrollRequest.getIdEmployee());

        DayWorkRequest dayWorkRequest = new DayWorkRequest();

        try{
            response = dayWorkServiceClient.getDayWork(DayWorkRequest.builder()
                    .idemployee(payrollRequest.getIdEmployee())
                    .month(payrollRequest.getMonth())
                    .year(payrollRequest.getYear())
                    .build());
        }catch(Exception e){
            String searchTerm = "\"message\":\"";
            int startIndex = e.getMessage().indexOf(searchTerm);
            startIndex += searchTerm.length();
            int endIndex = e.getMessage().indexOf("\"", startIndex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage().substring(startIndex, endIndex))
                    .build());
        }

        if(response.getStatusCode().is5xxServerError()){
            LinkedHashMap employeeResponse = (LinkedHashMap) response.getBody();
            String  res = (String) employeeResponse.get("message");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(res)
                    .build());
        }

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            LinkedHashMap employeeResponse = (LinkedHashMap) response.getBody();
            LinkedHashMap employee = (LinkedHashMap) employeeResponse.get("data");
            dayWorkResponse.setIdemployee(employee.get("idemployee").toString());
            dayWorkResponse.setMonth(Integer.parseInt( employee.get("month").toString()));
            dayWorkResponse.setYear(Integer.parseInt( employee.get("year").toString()));
            dayWorkResponse.setDay_work(Float.parseFloat(employee.get("day_work").toString()));
        }

        int rewardCash = rewardPunishPayrollResponseList.stream()
                .filter(r -> r.getType().equals("Thưởng"))
                .mapToInt(RewardPunishPayrollResponse::getCash)
                .sum();
        int punishCash = rewardPunishPayrollResponseList.stream()
                .filter(r -> r.getType().equals("Phạt"))
                .mapToInt(RewardPunishPayrollResponse::getCash)
                .sum();

        Payroll payroll = new Payroll();
        payroll.setIdemployee(payrollRequest.getIdEmployee());
        payroll.setMonth(payrollRequest.getMonth());
        payroll.setYear(payrollRequest.getYear());
        payroll.setReward(rewardCash);
        payroll.setPunish(punishCash);
//        payroll.setName(name);
        payroll.setStatus(StatusPayroll.getCodeFromStatus(payrollRequest.getStatus()));
        payroll.setDatecreated(payrollRequest.getDatecreated());
        payroll.setDay_work(dayWorkResponse.getDay_work());
        payroll.setBasicsalary(payrollContractResponse.getBasicsalary());
        payroll.setTotalpayment(((dayWorkResponse.getDay_work() * payrollContractResponse.getBasicsalary())/ payrollContractResponse.getDayworks() )+ rewardCash - punishCash);
        payrollRepository.save(payroll);

        payrollContractClient.changeStatusContract(payrollRequest.getIdEmployee(),payrollRequest.getMonth(),payrollRequest.getYear());
      return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
              .status(HttpStatus.CREATED.value())
              .message("Tính lương thành công")
              .data(        PayrollResponse
          .builder().idEmployee(payroll.getIdemployee()).month(payroll.getMonth()).year(payroll.getYear())
               .reward(rewardCash).punish(punishCash).status(StatusPayroll.getStatusFromCode(payroll.getStatus()))
               .createDate(payrollRequest.getDatecreated())
               .day_work(payroll.getDay_work())
               .basicSalary(payroll.getBasicsalary())
              .totalPayment(payroll.getTotalpayment()).build())

              .build());
    }

    @Override
    public ResponseEntity<ApiResponse> payrollManyEmployee( PayrollRes payrollRes) {
        for(String idEmployee: payrollRes.getListEmployee()){
            try{
            Payroll payrollCheck = payrollRepository.findByIdemployeeAndMonthAndYear(idEmployee,payrollRes.getMonth(),payrollRes.getYear());
            DayWorkResponse dayWorkResponse = dayWorkServiceClient.getDayWorkEmployee(DayWorkRequest.builder()
                    .idemployee(idEmployee)
                    .month(payrollRes.getMonth())
                    .year(payrollRes.getYear())
                    .build());
            PayrollContractResponse payrollContractResponse = payrollContractClient.getContractByIdMonthYear(
                    PayrollContractRequest.builder().idemployee(idEmployee).month(payrollRes.getMonth()).year(
                            payrollRes.getYear()
                    ).build()
            );
            if(payrollCheck == null && dayWorkResponse != null && payrollContractResponse != null){


                List<RewardPunishPayrollResponse> rewardPunishPayrollResponseList = rewardPunishPayrollClient.getRewardPunishForCalSalary(
                        RewardPunishPayrollRequest.builder()
                                .idemployee(idEmployee)
                                .month(payrollRes.getMonth())
                                .year(payrollRes.getYear())
                                .build()
                );
                int rewardCash = rewardPunishPayrollResponseList == null ? 0 : rewardPunishPayrollResponseList.stream()
                        .filter(r -> r.getType().equals("Thưởng"))
                        .mapToInt(RewardPunishPayrollResponse::getCash)
                        .sum();
                int punishCash = rewardPunishPayrollResponseList == null ? 0 : rewardPunishPayrollResponseList.stream()
                        .filter(r -> r.getType().equals("Phạt"))
                        .mapToInt(RewardPunishPayrollResponse::getCash)
                        .sum();
                Payroll payroll = new Payroll();
                payroll.setIdemployee(idEmployee);
                payroll.setMonth(payrollRes.getMonth());
                payroll.setYear(payrollRes.getYear());
                payroll.setReward(rewardCash);
                payroll.setPunish(punishCash);
//        payroll.setName(name);
                payroll.setStatus(StatusPayroll.getCodeFromStatus(payrollRes.getStatus()));
                payroll.setDatecreated(payrollRes.getDatecreated());
                payroll.setDay_work(dayWorkResponse.getDay_work());
                payroll.setBasicsalary(payrollContractResponse.getBasicsalary());
                int number = idEmployeeServiceClient.getNumberSalary(idEmployee);
                payroll.setTotalpayment(((dayWorkResponse.getDay_work() * payrollContractResponse.getBasicsalary())/ payrollContractResponse.getDayworks() )+ number + rewardCash - punishCash);
                payrollRepository.save(payroll);

                payrollContractClient.changeStatusContract(idEmployee,payrollRes.getMonth(),payrollRes.getYear());
            }}catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Tính lương thành công")
                .data("")
                .build());
    }

    @Override
    public ResponseEntity<?> getIdEmployee() {
        ResponseEntity<?> response = idEmployeeServiceClient.getEmployees();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            LinkedHashMap employeeResponse = (LinkedHashMap) response.getBody();
            List employee = (List) employeeResponse.get("data");
            List<IdEmployeeResponse>idEmployeeClientList = new ArrayList<>();
            for(int i = 0 ; i < employee.size();i++){
                LinkedHashMap linkedHashMap = (LinkedHashMap) employee.get(i);
                IdEmployeeResponse idEmployeeClient = new IdEmployeeResponse();
                idEmployeeClient.setIdemployee(linkedHashMap.get("idEmployee").toString());
                idEmployeeClientList.add(idEmployeeClient);
            }
            idEmployeeClientList = idEmployeeClientList.stream().filter(
                    e -> payrollContractClient.checkContractById(e.getIdemployee(), new Date())
            ).toList();
            return ResponseEntity.ok().body(idEmployeeClientList);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> updatePayroll(PayrollRequest payrollRequest) {
        Payroll payrollCheck = payrollRepository.findByIdemployeeAndMonthAndYear(payrollRequest.getIdEmployee(),payrollRequest.getMonth(),payrollRequest.getYear());
        if (payrollCheck == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value())
                            .data("")
                            .build()
            );
        }
        payrollCheck.setStatus(StatusPayroll.getCodeFromStatus(payrollRequest.getStatus()));
        payrollRepository.save(payrollCheck);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Thanh toán thành công")
                        .data("").build()
        );
    }

    @Override
    public int totalPaymentInMonth(int month,int year) {
        int sum = 0;
        try{
             sum = payrollRepository.getTotalPaymentByMonthAndYear(month,year);
        }catch (Exception e){

        }
        return sum;
    }

    @Override
    public List<Integer> getMonthlyTotals(String idemployee, int year) {
        List<Payroll> payrollList = payrollRepository.findByYearAndEmployee(year, idemployee);

        // Initialize the list with zeros for 12 months
        List<Integer> monthlyTotals = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            monthlyTotals.add(0);
        }

        for (Payroll payroll : payrollList) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(payroll.getDatecreated()); // Assuming 'datecreated' is used to get the month
            int month = cal.get(Calendar.MONTH); // Months are 0-indexed (January is 0, February is 1, etc.)
            int currentTotal = monthlyTotals.get(month);
            monthlyTotals.set(month, (int) (currentTotal + payroll.getTotalpayment()));
        }

        return monthlyTotals;
    }

    @Override
    public ResponseEntity<ApiResponse> getSalary() {
        try{
            List<SalaryResponse> salaryResponseList = payrollRepository.getMonthlySalarySummary();
            return ResponseEntity.ok().body(ApiResponse.builder()
                            .status(HttpStatus.OK.value())
                            .message("Lay thanh cong bang luong")
                            .data(salaryResponseList)
                    .build()
            );
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi co du lieu")
                            .build());
        }

    }

    @Override
    public ResponseEntity<?> getDetailSalary(int month, int year) {
        try{
            List<PayrollResponse> payrollResponseList = payrollRepository
                    .findAll().stream().filter(payroll -> payroll.getMonth() == month &&
                            payroll.getYear() == year).map(e->
                            PayrollResponse.builder()
                                    .idEmployee(e.getIdemployee())
                                    .month(e.getMonth())
                                    .year(e.getYear())
                                    .basicSalary(e.getBasicsalary())
                                    .reward(e.getReward())
                                    .punish(e.getPunish())
                                    .createDate(e.getDatecreated())
                                    .day_work(e.getDay_work())
                                    .totalPayment(e.getTotalpayment())
                                    .status(StatusPayroll.getStatusFromCode(e.getStatus()))
                                    .build()
                    ).toList();
            return ResponseEntity.ok().body(
                    ApiResponse.builder()
                            .status(HttpStatus.OK.value())
                            .message("Lay thanh cong chi tiet luong")
                            .data(payrollResponseList)
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi co du lieu")
                            .build());
        }
    }
}
