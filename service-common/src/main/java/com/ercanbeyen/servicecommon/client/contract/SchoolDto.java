package com.ercanbeyen.servicecommon.client.contract;

import java.util.List;

public record SchoolDto(String id, String name, String location, String owner, List<ClassroomDto> classroomDtos) {

}
