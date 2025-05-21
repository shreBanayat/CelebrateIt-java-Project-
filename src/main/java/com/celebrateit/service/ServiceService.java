package com.celebrateit.service;


import java.util.List;

import com.celebrateit.dto.ApiResponse;
import com.celebrateit.dto.ServiceRequestDTO;
import com.celebrateit.dto.ServiceResponseDTO;
import com.celebrateit.dto.ServiceResponseFormCategoryDTO;
import com.celebrateit.pojo.Services;

public interface ServiceService {

	ServiceResponseDTO addService(ServiceRequestDTO serviceRequestDTO);

	

	ApiResponse deleteServiceDetails(int serviceId);


	ServiceResponseDTO getServiceDetailsById(Integer serviceId);


	//ApiResponse updateServiceDetailsById(Integer serviceId, Services services);
	
	List<ServiceResponseDTO> getServicesByCategoryId(Integer categoryId);

	//update
	ServiceResponseDTO updateServiceDetailsById(Integer serviceId, ServiceRequestDTO serviceRequestDTO);


	

	

}
