package com.chamcongtinhluong.leaverequest_service.mapper;

import com.chamcongtinhluong.leaverequest_service.Enum.LeaveType;
import com.chamcongtinhluong.leaverequest_service.Enum.Status;
import com.chamcongtinhluong.leaverequest_service.dto.request.LeaveRequest_Request;
import com.chamcongtinhluong.leaverequest_service.dto.response.LeaveRequest_Response;
import com.chamcongtinhluong.leaverequest_service.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface LeaveRequestMapper {
    LeaveRequestMapper INSTANCE = Mappers.getMapper(LeaveRequestMapper.class);

    @Mapping(source = "idEmployee",target = "idEmployee")
    @Mapping(source = "leaveType",target = "leaveType",qualifiedByName = "stringToEnum")
    @Mapping(source = "startDate",target = "startDate")
    @Mapping(source = "endate",target = "endate")
    @Mapping(source = "reason",target = "reason")
    @Mapping(source = "createAt",target = "createAt",qualifiedByName = "stringToDate")
    @Mapping(source = "status",target = "status",qualifiedByName = "StringEnum")
    @Mapping(source = "approveBy",target = "approveBy")
    @Mapping(source = "approveAt",target = "approveAt",qualifiedByName = "stringToDate")
    LeaveRequest toEntity(LeaveRequest_Request leaveRequest_request);

    @Mapping(source = "idEmployee",target = "idEmployee")
    @Mapping(source = "leaveType",target = "leaveType",qualifiedByName = "enumToString")
    @Mapping(source = "startDate",target = "startDate")
    @Mapping(source = "endate",target = "endate")
    @Mapping(source = "reason",target = "reason")
    @Mapping(source = "createAt",target = "createAt",qualifiedByName = "dateToString")
    @Mapping(source = "status",target = "status",qualifiedByName = "EnumString")
    @Mapping(source = "approveBy",target = "approveBy")
    @Mapping(source = "approveAt",target = "approveAt",qualifiedByName = "dateToString")
    LeaveRequest_Response toResponse(LeaveRequest leaveRequest);

    @Named("stringToEnum")
    default LeaveType stringToEnum(String value) {
        return LeaveType.convertStringToEnum(value);
    }

    @Named("enumToString")
    default String enumToString(LeaveType type) {
        return type.getDisplayName();
    }

    @Named("StringEnum")
    default Status StringEnum(String value){
        return Status.convertStringToEnum(value);
    }

    @Named("EnumString")
    default String EnumString(Status status){
        return status.getDisplayName();
    }


    @Named("dateToString")
    default String dateToString(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

    @Named("stringToDate")
    default Date stringToDate(String dateStr) {
        if (dateStr == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
