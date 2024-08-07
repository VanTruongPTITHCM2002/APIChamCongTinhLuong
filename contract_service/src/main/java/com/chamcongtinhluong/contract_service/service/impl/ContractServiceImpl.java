package com.chamcongtinhluong.contract_service.service.impl;

import com.chamcongtinhluong.contract_service.Enum.StatusContract;
import com.chamcongtinhluong.contract_service.commiunicate.ContractEmployeeService;
import com.chamcongtinhluong.contract_service.dto.request.ContractRequest;
import com.chamcongtinhluong.contract_service.dto.request.ContractUpdate;
import com.chamcongtinhluong.contract_service.dto.request.PayrollContractRequest;
import com.chamcongtinhluong.contract_service.dto.response.ApiResponse;
import com.chamcongtinhluong.contract_service.dto.response.ContractResponse;
import com.chamcongtinhluong.contract_service.dto.response.PayrollContractResponse;
import com.chamcongtinhluong.contract_service.entity.Contract;
import com.chamcongtinhluong.contract_service.repository.ContractRepository;
import com.chamcongtinhluong.contract_service.service.ContractService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractEmployeeService contractEmployeeService;

    @Override
    public ResponseEntity<?> getContracts() {
        List<ContractResponse> contractResponseList = contractRepository.findAll()
                .stream().map(contract ->
                        ContractResponse.builder()
                                .idemployee(contract.getIdemployee())
                                .basicsalary(contract.getBasicsalary())
                                .workingdays(contract.getWorkingdays())
                                .startdate(contract.getStartdate())
                                .endate(contract.getEndate())
                                .status(StatusContract.getStatusFromCode(contract.getStatus()))
                                .build()).toList();
        return ResponseEntity.ok()
                .body(
                        ApiResponse
                                .builder()
                                .status(HttpStatus.OK.value())
                                .message("Lấy thành công danh sách hợp đồng")
                                .data(contractResponseList)
                                .build()
                );
    }

    @Override
    public ResponseEntity<?> getContractByIdemployee(String idemployee) {
        List<ContractResponse> contractResponseList = contractRepository.findAll().stream().filter(
                contract -> contract.getIdemployee().equals(idemployee))
                .map(contract ->
                        ContractResponse.builder()
                                .idemployee(contract.getIdemployee())
                                .basicsalary(contract.getBasicsalary())
                                .workingdays(contract.getWorkingdays())
                                .startdate(contract.getStartdate())
                                .endate(contract.getEndate())
                                .status(StatusContract.getStatusFromCode(contract.getStatus()))
                                .build()
                ).toList();
        if(contractResponseList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Không tìm thấy hợp đồng của nhân viên này")
                            .data(contractResponseList)
                            .build()
            );
        }
         return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Hợp đồng của nhân viên " + idemployee)
                        .data(contractResponseList)
                        .build()
        );
    }
    @Transactional
    public String checkContractConflicts(String idemployee, Date startdate, Date endate) {
        // Kiểm tra hợp đồng có khoảng thời gian xung đột
        Contract conflictingContracts = contractRepository.findByIdemployeeAndStartdateBeforeAndEndateAfter(
                idemployee, endate, startdate);
        if (conflictingContracts != null) {
            return "Khoảng thời gian của hợp đồng mới bị xung đột với hợp đồng hiện có.";
        }

        // Kiểm tra hợp đồng chính xác dựa trên id nhân viên, ngày bắt đầu và ngày kết thúc
        Contract exactMatchContract = contractRepository.findByIdemployeeAndStartdateAndEndate(
                idemployee, startdate, endate);
        if (exactMatchContract != null) {
            return "Hợp đồng đã tồn tại với ngày bắt đầu và ngày kết thúc giống nhau.";
        }


        // Kiểm tra hợp đồng bắt đầu sau một ngày cụ thể
        Contract startDateAfterContracts = contractRepository.findByIdemployeeAndStartdateAfter(
                idemployee, startdate);
        if (startDateAfterContracts != null) {
            return "Hợp đồng đã tồn tại bắt đầu sau ngày bắt đầu của hợp đồng mới.";
        }

        // Kiểm tra hợp đồng kết thúc trước một ngày cụ thể
        Contract endDateBeforeContracts = contractRepository.findByIdemployeeAndEndateBefore(
                idemployee, endate);
        if (endDateBeforeContracts != null) {
            return "Hợp đồng đã tồn tại kết thúc trước ngày kết thúc của hợp đồng mới.";
        }

        return "Không có xung đột.";
    }

    @Override
    public ResponseEntity<?> addContract(ContractRequest contractRequest){
       String checkExist = checkContractConflicts(contractRequest.getIdemployee(),contractRequest.getStartdate(),contractRequest.getEndate());

        if (!checkExist.equals("Không có xung đột.") && !checkExist.equals("Hợp đồng đã tồn tại kết thúc trước ngày kết thúc của hợp đồng mới.")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse
                            .builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .data("")
                            .message(checkExist).build());

        }
        contractRepository.save(Contract.builder()
                .idemployee(contractRequest.getIdemployee())
                .basicsalary(contractRequest.getBasicsalary())
                .endate(contractRequest.getEndate())
                .startdate(contractRequest.getStartdate())
                .workingdays(contractRequest.getWorkingdays())
                        .status(StatusContract.getCodeFromStatus("Còn hợp đồng"))

                .build());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse
                        .builder()
                        .status(HttpStatus.CREATED.value())
                        .data("")
                        .message("Thêm hợp đồng thành công").build());

    }

    @Override
    public ResponseEntity<?> updateContract(ContractUpdate contractUpdate) {
        Contract contract = contractRepository.findByIdemployeeAndStartdateAndEndate(
                contractUpdate.getOldContractReqeust().getIdemployee(),
                contractUpdate.getOldContractReqeust().getStartdate(),
                contractUpdate.getOldContractReqeust().getEndate()
        );
        if(contract == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Không tìm thấy hợp đồng của nhân viên này")
                            .data("")
                            .build()
            );
        }

        contract.setIdemployee(contractUpdate.getNewContractRequest().getIdemployee());
        contract.setBasicsalary(contractUpdate.getNewContractRequest().getBasicsalary());
        contract.setWorkingdays(contractUpdate.getNewContractRequest().getWorkingdays());
        contract.setStartdate(contractUpdate.getNewContractRequest().getStartdate());
        contract.setEndate(contractUpdate.getNewContractRequest().getEndate());
        contractRepository.save(contract);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("thay đổi thành công")
                        .data("")
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> changeStatusContract(String idemployee, int month, int year) {
        Contract contract = contractRepository.findContractByIdemployeeAndMonthAndYear(idemployee,month,year);
        if(contract == null){
            return ResponseEntity.ok().body(ApiResponse.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Không tìm thấy hợp đồng")
                            .data("")
                    .build());
        }
        contract.setStatus(-1);
        contractRepository.save(contract);
        contractEmployeeService.updateStatusEmployee(idemployee);
        return ResponseEntity.ok().body(ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Thay đổi trạng thái thành công")
                        .data("")
                .build());
    }

    @Override
    public Boolean checkStatusContract(String idemployee,Date date) {
        Contract contract = contractRepository.findActiveContracts(idemployee,date);
        if(contract == null){
            return false;
        }
        return true;
    }

    @Override
    public PayrollContractResponse getBasicSalaryDayWork(PayrollContractRequest payrollContractRequest) {
        Contract contract = contractRepository.findContractByIdemployeeAndMonthAndYear(
                payrollContractRequest.getIdemployee(),payrollContractRequest.getMonth(),payrollContractRequest.getYear()
        );
        if(contract == null){
            return null;
        }

        return PayrollContractResponse.builder().idemployee(contract.getIdemployee()).basicsalary(contract.getBasicsalary()).dayworks(contract.getWorkingdays()). build();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?") // Chạy hàng ngày lúc 00:00
    public void updateContractStatus() {
        Date now = new Date();
        List<Contract> contracts = contractRepository.findAll();

        for (Contract contract : contracts) {
            // Lấy ngày kết thúc của hợp đồng
            Date endDate = contract.getEndate();

            // Kiểm tra nếu ngày hiện tại lớn hơn ngày kết thúc hợp đồng (có nghĩa là ngày tiếp theo hoặc sau đó)
            if (now.after(endDate)) {
                contract.setStatus(-1); // Hoặc trạng thái "Hết hạn"
                contractRepository.save(contract);
            }
        }
    }

    @Override
    public int countContracts(int month, int year) {
        int count = 0;
        try{
            count = contractRepository.countContractsByMonthAndYear(month,year);
        }catch(Exception e){

        }
        return count;
    }

    @Override
    public Boolean checkIdEmployeeInContract(String idemployee) {
        List <Contract> contracts = contractRepository.findByIdemployee(idemployee);
        return !contracts.isEmpty();
    }
}
