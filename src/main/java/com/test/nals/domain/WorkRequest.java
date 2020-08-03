package com.test.nals.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkRequest {

    String idWork;

    @NotNull(message = "WorkName must not empty or null")
    String workName;

    @NotNull(message = "Starting Date must not empty or null")
    LocalDate startingDate;

    @NotNull(message = "Ending Date must not empty or null")
    LocalDate endingDate;

    @NotNull(message = "Status must not empty or null")
    String status;
}
