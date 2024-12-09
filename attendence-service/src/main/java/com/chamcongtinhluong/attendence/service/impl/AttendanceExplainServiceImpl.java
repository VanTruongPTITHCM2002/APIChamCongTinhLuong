package com.chamcongtinhluong.attendence.service.impl;

import com.chamcongtinhluong.attendence.Enum.StatusAttedanceExplain;
import com.chamcongtinhluong.attendence.dto.request.AttendanceExplainRequest;
import com.chamcongtinhluong.attendence.dto.request.IdEmployeeRequest;
import com.chamcongtinhluong.attendence.dto.response.ApiResponse;
import com.chamcongtinhluong.attendence.dto.response.AttedanceExplainResponse;
import com.chamcongtinhluong.attendence.entity.Attendance;
import com.chamcongtinhluong.attendence.entity.AttendanceExplain;
import com.chamcongtinhluong.attendence.repository.AttendanceExplainRepository;
import com.chamcongtinhluong.attendence.repository.AttendanceRepository;
import com.chamcongtinhluong.attendence.service.AttendanceExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceExplainServiceImpl implements AttendanceExplainService {

    @Autowired
    AttendanceExplainRepository attendanceExplainRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Override
    public ResponseEntity<?> getAttedanceExplain() {
        List<AttedanceExplainResponse> attedanceExplainResponses = attendanceExplainRepository.findAll()
                .stream().map(e->
                        new AttedanceExplainResponse(
                                e.getAttendance().getWorkRecord().getIdemployee(),
                                e.getAttendance().getDateattendance(),
                                e.getAttendance().getCheckintime(),
                                e.getAttendance().getCheckouttime(),
                                e.getExplaination(),
                                StatusAttedanceExplain.getStatusFromCode(e.getStatus())
                        )).toList();
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy thành công bảng giải trình")
                        .data(attedanceExplainResponses)
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> addAttendanceExplain(AttendanceExplainRequest attendanceExplainRequest) {
        Attendance attendance = attendanceRepository.findByWorkRecord_IdemployeeAndDateattendance(attendanceExplainRequest.getIdemployee(),attendanceExplainRequest.getDate());
        if(attendance == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Không tìm thấy bảng chấm công ngày này")
                            .data("")
                            .build()
            );
        }

        attendanceExplainRepository.save(
                AttendanceExplain.builder()
                        .attendance(attendance)
                        .explaination(attendanceExplainRequest.getReason())
                        .status(0)
                        .build()
        );


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.builder()
                                .status(HttpStatus.CREATED.value())
                                .message("Thêm thành công bảng giải trình")
                                .data("")
                                .build()
                );
    }

    @Override
    public ResponseEntity<?> getAttendanceExplainById(IdEmployeeRequest idEmployeeRequest) {
        if(idEmployeeRequest.getIdemployee().equals("")){
            return getAttedanceExplain();
        }
        List<AttendanceExplain> attendanceExplainList =
                attendanceExplainRepository.filterAttendanceExplain(idEmployeeRequest.getIdemployee());

        List<AttedanceExplainResponse> attedanceExplainResponses =
                attendanceExplainList.stream().map(
                        e->new AttedanceExplainResponse(
                                e.getAttendance().getWorkRecord().getIdemployee(),
                                e.getAttendance().getDateattendance(),
                                e.getAttendance().getCheckintime(),
                                e.getAttendance().getCheckouttime(),
                                e.getExplaination(),
                                StatusAttedanceExplain.getStatusFromCode(e.getStatus())
                        )
                ).toList();
        return ResponseEntity.ok().body(ApiResponse.builder().status(HttpStatus.OK.value()).
                message("Lấy dữ liệu bảng giải trình thành công của nhân viên").
                data(attedanceExplainResponses).build());
    }

    @Override
    public ResponseEntity<?> updateAttendanceExplain(AttedanceExplainResponse attedanceExplainResponse) {
        Attendance attendance = attendanceRepository.findByWorkRecord_IdemployeeAndDateattendance(attedanceExplainResponse.getIdemployee(),attedanceExplainResponse.getDate());

        AttendanceExplain attendanceExplain = attendanceExplainRepository.findByAttendance(attendance);
        attendanceExplain.setStatus(StatusAttedanceExplain.getCodeFromStatus(attedanceExplainResponse.getStatus()));
        attendanceExplainRepository.save(attendanceExplain);
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .status(HttpStatus.OK.value())
                                .message("Cập nhật trạng thái đơn duyệt thành công")
                                .data("")
                                .build()
                );
    }
}
