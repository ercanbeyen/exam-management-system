package com.ercanbeyen.servicecommon.client.contract;

import java.util.List;

public record SchoolDto(int id, String name, String location, String owner, List<String> classroomIds) {

}
