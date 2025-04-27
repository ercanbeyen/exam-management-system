package com.ercanbeyen.schoolservice.validator;

import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.servicecommon.client.exception.ResourceConflictException;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class SchoolValidator {
    public void checkClassrooms(SchoolDto request) {
        Set<String> names = new HashSet<>();

        request.classrooms()
                .forEach(classroom -> {
                    String name = classroom.getName();

                    if (names.contains(name)) {
                        throw new ResourceConflictException("Classroom names must be unique");
                    }

                    names.add(name);
                });
    }
}
