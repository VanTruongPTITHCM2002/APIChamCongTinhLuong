package com.chamcongtinhluong.account_service.mapper;

import com.chamcongtinhluong.account_service.dto.request.RoleRequest;
import com.chamcongtinhluong.account_service.dto.response.RoleResponse;
import com.chamcongtinhluong.account_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ap.shaded.freemarker.core.ParseException;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(source = "rolename",target = "rolename")
    @Mapping(source = "roleDescription", target = "roleDescription")
    @Mapping(source = "createAt", target = "createAt",qualifiedByName = "dateToString")
    @Mapping(source = "updateAt", target = "updateAt",qualifiedByName = "dateToString")
    @Mapping(source = "scope", target = "scope")
    @Mapping(source = "isActive",target = "isActive")
    RoleResponse toResponse(Role role);

    @Mapping(source = "rolename",target = "rolename")
    @Mapping(source = "roleDescription", target = "roleDescription")
    @Mapping(source = "createAt", target = "createAt")
    @Mapping(source = "updateAt", target = "updateAt")
    @Mapping(source = "scope", target = "scope")
    @Mapping(source = "isActive",target = "isActive")
    Role toEntity(RoleRequest roleRequest);

    @Named("dateToString")
    default String dateToString(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

    @Named("stringToDate")
    default Date stringToDate(String dateStr) {
        if (dateStr == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        try {
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
