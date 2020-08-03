package com.test.nals.service.impl;

import com.test.nals.domain.WorkRequest;
import com.test.nals.entity.Work;
import com.test.nals.exception.BadRequestException;
import com.test.nals.exception.ErrorCode;
import com.test.nals.exception.ResourceNotFoundException;
import com.test.nals.repository.WorkRepository;
import com.test.nals.service.WorkService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WorkServiceImpl implements WorkService {

    WorkRepository workRepository;

    public WorkServiceImpl(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    public void createWork(WorkRequest workRequest) {
        log.info("Create new work {} with status {}", workRequest.getWorkName(), workRequest.getStatus());
        if (workRepository.findById(workRequest.getIdWork()).isPresent()) {
            throw new BadRequestException(ErrorCode.WORK_EXISTED);
        }
        workRepository.save(createOrUpdateWork(workRequest));
        log.info("The work {} has just created", workRequest.getWorkName());
    }

    @Override
    public void editWork(String idWork, WorkRequest workRequest) {
        log.info("Update work ()", idWork);
        Work work = workRepository.findById(idWork).orElseThrow(
                () -> new ResourceNotFoundException("Work", "idWork", idWork));
        log.info("Delete work {}", idWork);
        workRepository.delete(work);
        workRequest.setIdWork(idWork);
        workRepository.save(createOrUpdateWork(workRequest));
        log.info("Work {} is updated", idWork);
    }

    @Override
    public void removeWork(String idWork) {
        log.info("Delete work {}", idWork);
        Work work = workRepository.findById(idWork).orElseThrow(
                () -> new ResourceNotFoundException("Work", "idWork", idWork));
        workRepository.delete(work);
        log.info("Work {} is deleted", idWork);
    }

    @Override
    public Page<Work> getListWork(int page, int size, String sortType, String sortField) {
        Sort sortable = null;
        if ("ASC".equals(sortType)) {
            sortable = Sort.by(sortField).ascending();
        }
        if ("DESC".equals(sortType)) {
            sortable = Sort.by(sortField).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);

        return workRepository.findAll(pageable);
    }

    private boolean isValidTime(LocalDate startingDate, LocalDate endingDate) {
        if (startingDate.isAfter(endingDate)) {
            return false;
        }
        return true;
    }

    private Work createOrUpdateWork(WorkRequest workRequest) {
        Work work = new Work();
        work.setId(workRequest.getIdWork());
        work.setWorkName(workRequest.getWorkName());
        if (!isValidTime(workRequest.getStartingDate(), workRequest.getEndingDate())) {
            throw new BadRequestException(ErrorCode.TIME_INVALID);
        }
        work.setStartingDate(workRequest.getStartingDate());
        work.setEndingDate(workRequest.getEndingDate());
        work.setStatus(workRequest.getStatus());
        return work;
    }
}
