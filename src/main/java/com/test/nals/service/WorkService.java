package com.test.nals.service;

import com.test.nals.domain.WorkRequest;
import com.test.nals.entity.Work;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WorkService {

    void createWork(WorkRequest workRequest);

    void editWork(String idWork, WorkRequest workRequest);

    void removeWork(String workName);

    Page<Work> getListWork(int page, int size, String sortType, String sortField);
}
