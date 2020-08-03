package com.test.nals.domain;

import com.test.nals.entity.Work;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkResponse {

    List<Work> content;
//    Pageable pageable;
    int totalPages;
    int totalElements;
    boolean last;
    int size;
    int number;
    Sort sort;
    int numberOfElements;
    boolean first;
    boolean empty;

}
