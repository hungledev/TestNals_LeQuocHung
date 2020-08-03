package com.test.nals;

import com.google.gson.Gson;
import com.test.nals.domain.WorkRequest;
import com.test.nals.domain.WorkResponse;
import com.test.nals.entity.Work;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	WorkRequest getWorkRequestForCreate() {
		WorkRequest workRequest = new WorkRequest();
		workRequest.setIdWork("bb1");
		workRequest.setWorkName("meeting");
		workRequest.setStartingDate(LocalDate.parse("2020-04-05"));
		workRequest.setEndingDate(LocalDate.parse("2020-04-07"));
		workRequest.setStatus("Planning");
		return workRequest;
	}

	WorkRequest getWorkRequestForUpdate() {
		WorkRequest workRequest = new WorkRequest();
		workRequest.setIdWork("bb1");
		workRequest.setWorkName("Demo application");
		workRequest.setStartingDate(LocalDate.parse("2020-04-05"));
		workRequest.setEndingDate(LocalDate.parse("2020-04-07"));
		workRequest.setStatus("Planning");
		return workRequest;
	}

	/**
	 * Test for create work function
	* */
	@Test
	public void createWork() {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8888/works";
		WorkRequest workRequest = getWorkRequestForCreate();
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<WorkRequest> request = new HttpEntity<>(workRequest, httpHeaders);
		ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, request, String.class);
		Assert.assertEquals(201, result.getStatusCodeValue());
	}

	@Test
	public void updateWork() {
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		final String baseUrl = "http://localhost:8888/works/bb1";
		WorkRequest workRequest = getWorkRequestForUpdate();
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<WorkRequest> request = new HttpEntity<>(workRequest, httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.PATCH, request, String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	public void deleteWork() {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8888/works/bb1";
		HttpHeaders httpHeaders = new HttpHeaders();
		Work work = new Work();
		HttpEntity<Work> request = new HttpEntity<>(work, httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, request, String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	public void getWorks() {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8888/works";
		HttpHeaders headers = new HttpHeaders();
		headers.add("page", "0");
		headers.add("size", "5");
		headers.add("sortType", "ASC");
		headers.add("sortField", "workName");
		HttpEntity request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, (HttpEntity<?>) request, String.class, headers);

		WorkResponse workResponse = new Gson().fromJson(response.getBody(), WorkResponse.class);
		Assert.assertEquals(5, workResponse.getContent().size());
	}


}
