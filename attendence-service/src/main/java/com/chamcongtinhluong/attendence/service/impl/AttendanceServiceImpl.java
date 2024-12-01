package com.chamcongtinhluong.attendence.service.impl;

import com.chamcongtinhluong.attendence.Enum.StatusAttendance;
import com.chamcongtinhluong.attendence.communicate.WorkScheduleServiceClient;
import com.chamcongtinhluong.attendence.dto.request.AttendanceRequest;
import com.chamcongtinhluong.attendence.dto.request.IdEmployeeRequest;
import com.chamcongtinhluong.attendence.dto.response.ApiResponse;
import com.chamcongtinhluong.attendence.dto.response.AttendanceResponse;
import com.chamcongtinhluong.attendence.entity.Attendance;
import com.chamcongtinhluong.attendence.entity.AttendanceStatus;
import com.chamcongtinhluong.attendence.entity.WorkRecord;
import com.chamcongtinhluong.attendence.repository.AttendanceRepository;
import com.chamcongtinhluong.attendence.repository.AttendanceStatusRepository;
import com.chamcongtinhluong.attendence.repository.WorkRecordRepository;
import com.chamcongtinhluong.attendence.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceStatusRepository attendanceStatusRepository;
    private final WorkRecordRepository workRecordRepository;
    private final WorkScheduleServiceClient workScheduleServiceClient;

    public static String formatTime(Time time) {
        if(time == null){
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(time);
    }

    public static Time parseTime(String timeString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long ms = sdf.parse(timeString).getTime();
        return new Time(ms);
    }
    @Override
    public ResponseEntity<?> getAttendance() {
        List<AttendanceResponse> attendanceResponseList =
                attendanceRepository.findAll().stream().map(
                        e->new AttendanceResponse(
                                e.getIdemployee(),
                                e.getDateattendance(),
                                formatTime(e.getCheckintime()),
                                formatTime(e.getCheckouttime()),
                                e.getAttendanceStatus().getAttendanceStatusName(),
                                e.getAttendanceStatus().getDescription(),
                                e.getNumberwork()
                        )
                ).toList();
        return ResponseEntity.ok().body(ApiResponse.builder()
                .status(HttpStatus.OK.value()).message("Lấy dữ liệu chấm công thành công")
                .data(attendanceResponseList).build());
    }

    @Override
    public ResponseEntity<?> getFilterAttendance(IdEmployeeRequest idEmployeeRequest) {
        if(idEmployeeRequest.getIdemployee().equals("all")){
            return getAttendance();
        }
        List<Attendance> attendanceList =
                attendanceRepository.filterAttendance(idEmployeeRequest.getIdemployee());

        List<AttendanceResponse> attendanceResponseList =
                attendanceList.stream().map(
                        e->new AttendanceResponse(
                                e.getIdemployee(),
                                e.getDateattendance(),
                                formatTime(e.getCheckintime()),
                                formatTime(e.getCheckouttime()),
                                e.getAttendanceStatus().getAttendanceStatusName(),
                                e.getAttendanceStatus().getDescription(),
                                e.getNumberwork()
                        )
                ).toList();
        return ResponseEntity.ok().body(ApiResponse.builder().status(HttpStatus.OK.value()).
                message("Lấy dữ liệu chấm công thành công").
                data(attendanceResponseList).build());
    }

    @Override
    public ResponseEntity<?> addAttendance(AttendanceRequest attendanceRequest) throws ParseException {
        Boolean isCheck = workScheduleServiceClient.getWorkScheduleDetailByIdAndDate(attendanceRequest.getIdemployee(),attendanceRequest.getDateattendance());
        if(!isCheck){
            return ResponseEntity.ok().body(ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Không thể thực hiện chấm công ngày hôm nay do không có lịch lảm việc")
                    .data("")
                    .build());
        }
        Attendance attendance = new Attendance();
        attendance.setIdemployee(attendanceRequest.getIdemployee());
        attendance.setDateattendance(attendanceRequest.getDateattendance());
        attendance.setCheckintime(parseTime(attendanceRequest.getCheckintime()));
//        attendance.setCheckouttime(parseTime(attendanceRequest.getCheckouttime()));
        AttendanceStatus attendanceStatus =attendanceStatusRepository.findById(StatusAttendance.getCodeFromStatus(attendanceRequest.getStatus())).orElse(null);
        attendance.setAttendanceStatus(attendanceStatus);
        attendance.setNumberwork(attendanceRequest.getNumberwork());
        WorkRecord workRecord = workRecordRepository.findByIdemployeeAndMonthAndYear(attendanceRequest.getIdemployee(),
                attendanceRequest.getDateattendance().getMonth() + 1,
                attendanceRequest.getDateattendance().getYear() + 1900
        );
        attendance.setWorkRecord(workRecord);
        attendanceRepository.save(attendance);
//        workRecord.setDay_work(workRecord.getDay_work() + attendanceRequest.getNumberwork());
//        workRecordRepository.save(workRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Chấm công vào thành công")
                .data("")
                .build());
    }

    @Override
    public ResponseEntity<?> updateAttendance(AttendanceRequest attendanceRequest) throws ParseException {
        Boolean isCheck = workScheduleServiceClient.getWorkScheduleDetailByIdAndDate(attendanceRequest.getIdemployee(),attendanceRequest.getDateattendance());
        if(!isCheck){
            return ResponseEntity.ok().body(ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Không thể thực hiện chấm công ngày hôm nay do không có lịch lảm việc")
                    .data("")
                    .build());
        }
        Attendance attendance = attendanceRepository.findByIdemployeeAndDateattendance(attendanceRequest.getIdemployee(), attendanceRequest.getDateattendance());
        attendance.setIdemployee(attendanceRequest.getIdemployee());
        attendance.setDateattendance(attendanceRequest.getDateattendance());
        attendance.setCheckintime(parseTime(attendanceRequest.getCheckintime()));
        attendance.setCheckouttime(parseTime(attendanceRequest.getCheckouttime()));
        AttendanceStatus attendanceStatus =attendanceStatusRepository.findById(StatusAttendance.getCodeFromStatus(attendanceRequest.getStatus())).orElse(null);
        attendance.setAttendanceStatus(attendanceStatus);
        WorkRecord workRecord = workRecordRepository.findByIdemployeeAndMonthAndYear(
                attendanceRequest.getIdemployee(),
                attendanceRequest.getDateattendance().getMonth() + 1,
                attendanceRequest.getDateattendance().getYear() + 1900
        );

        attendance.setNumberwork(attendanceRequest.getNumberwork());
        workRecord.setDay_work(workRecord.getDay_work() + attendanceRequest.getNumberwork());
        attendanceRepository.save(attendance);
        workRecordRepository.save(workRecord);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Chấm công ra thành công")
                        .data("")
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> getAttendanceById(String idemployee) {
        List<AttendanceResponse> attendanceResponseList = attendanceRepository
                .findAll().stream().filter(attendance -> attendance.getIdemployee()
                        .equals(idemployee))
                .map(
                        e->new AttendanceResponse(
                                e.getIdemployee(),
                                e.getDateattendance(),
                                formatTime(e.getCheckintime()),
                                formatTime(e.getCheckouttime()),
                                e.getAttendanceStatus().getAttendanceStatusName(),
                                e.getAttendanceStatus().getDescription(),
                                e.getNumberwork()
                        )
                ).toList();
        if(attendanceResponseList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Không tìm thấy danh sách chấm công của nhân viên " + idemployee)
                            .data(attendanceResponseList)
                            .build()
            );
        }
       return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy thành công danh sách chấm công của nhân viên " + idemployee)
                        .data(attendanceResponseList)
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> updateAttendanceByAdmin(AttendanceRequest attendanceRequest) throws ParseException {
        Attendance attendance = attendanceRepository.findByIdemployeeAndDateattendance(attendanceRequest.getIdemployee(), attendanceRequest.getDateattendance());

        attendance.setIdemployee(attendanceRequest.getIdemployee());
        attendance.setDateattendance(attendanceRequest.getDateattendance());
        attendance.setCheckintime(parseTime(attendanceRequest.getCheckintime()));
        attendance.setCheckouttime(parseTime(attendanceRequest.getCheckouttime()));
        AttendanceStatus attendanceStatus =attendanceStatusRepository.findById(StatusAttendance.getCodeFromStatus(attendanceRequest.getStatus())).orElse(null);
        attendance.setAttendanceStatus(attendanceStatus);
        WorkRecord workRecord = workRecordRepository.findByIdemployeeAndMonthAndYear(
                attendanceRequest.getIdemployee(),
                attendanceRequest.getDateattendance().getMonth() + 1,
                attendanceRequest.getDateattendance().getYear() + 1900
        );
        if(attendanceRequest.getNumberwork() > attendance.getNumberwork()){
            workRecord.setDay_work(workRecord.getDay_work() + (attendanceRequest.getNumberwork() - attendance.getNumberwork()));
        }else{
            workRecord.setDay_work(workRecord.getDay_work() - (  attendance.getNumberwork() - attendanceRequest.getNumberwork()));
        }

        attendance.setNumberwork(attendanceRequest.getNumberwork());
        attendanceRepository.save(attendance);
        workRecordRepository.save(workRecord);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Chỉnh sửa chấm công thành công")
                        .data("")
                        .build()
        );

    }

    @Override
    public int countAttendanceByDate(Date date) {
        int count = 0;
        try{
            count = attendanceRepository.countByDateattendance(date);
        }catch (Exception e){

        }
        return count;
    }

    @Override
    public ResponseEntity<?> addAttendanceByAdmin(AttendanceRequest attendanceRequest) throws ParseException {
        Boolean isCheck = workScheduleServiceClient.getWorkScheduleDetailByIdAndDate(attendanceRequest.getIdemployee(),attendanceRequest.getDateattendance());
        if(!isCheck){
            return ResponseEntity.ok().body(ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Không thể thực hiện chấm công ngày hôm nay do không có lịch lảm việc")
                    .data("")
                    .build());
        }

        Attendance attendanceCheck = attendanceRepository.findByIdemployeeAndDateattendance(attendanceRequest.getIdemployee(),attendanceRequest.getDateattendance());
        if(attendanceCheck != null){
            return ResponseEntity.ok().body(ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Nhân viên đã chấm công rồi quản lý không thể thêm giúp")
                    .data("")
                    .build());
        }

        Attendance attendance = new Attendance();
        attendance.setIdemployee(attendanceRequest.getIdemployee());
        attendance.setDateattendance(attendanceRequest.getDateattendance());
        attendance.setCheckintime(parseTime(attendanceRequest.getCheckintime()));
        attendance.setCheckouttime(parseTime(attendanceRequest.getCheckouttime()));
        AttendanceStatus attendanceStatus =attendanceStatusRepository.findById(StatusAttendance.getCodeFromStatus(attendanceRequest.getStatus())).orElse(null);
        attendance.setAttendanceStatus(attendanceStatus);
        attendance.setNumberwork(attendanceRequest.getNumberwork());
        WorkRecord workRecord = workRecordRepository.findByIdemployeeAndMonthAndYear(
                attendanceRequest.getIdemployee(),
                attendanceRequest.getDateattendance().getMonth() + 1,
                attendanceRequest.getDateattendance().getYear() + 1900
        );
        attendance.setWorkRecord(workRecord);
        workRecord.setDay_work(workRecord.getDay_work() + attendanceRequest.getNumberwork());
        attendanceRepository.save(attendance);

        workRecordRepository.save(workRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Thực hiện chấm công thành công")
                .data("")
                .build());
    }
}
