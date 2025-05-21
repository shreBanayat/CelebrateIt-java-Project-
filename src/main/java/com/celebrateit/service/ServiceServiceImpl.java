package com.celebrateit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celebrateit.custom_exceptions.ResourceNotFoundException;
import com.celebrateit.dao.CategoryDao;
import com.celebrateit.dao.ServiceDao;
import com.celebrateit.dto.ApiResponse;
import com.celebrateit.dto.ContactDTO;
import com.celebrateit.dto.ServiceRequestDTO;
import com.celebrateit.dto.ServiceResponseDTO;
import com.celebrateit.dto.ServiceResponseFormCategoryDTO;
import com.celebrateit.pojo.Categories;
import com.celebrateit.pojo.Services;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {
	@Autowired
	ServiceDao serviceDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	ModelMapper mapper;

	//add
//	@Override
//	public ServiceResponseDTO addService(ServiceRequestDTO serviceRequestDTO) {
//		// TODO Auto-generated method stub
//		int categoryId= serviceRequestDTO.getCategoryId();
//		Categories category = categoryDao.findById(categoryId).orElseThrow();
//		
//		Services service = mapper.map(serviceRequestDTO, Services.class);
//		service.setCategories(category);
//		category.getServices().add(service);
//		Services serviceResponse = serviceDao.save(service);
//		
//		ServiceResponseDTO resdto = mapper.map(serviceResponse, ServiceResponseDTO.class);
//		
//		return resdto;
//	}

	//delete
//	@Override
//	public ApiResponse deleteServiceDetails(int serviceId) {
//		// TODO Auto-generated method stub
//		if(serviceDao.existsById(serviceId)) {
//			
//			serviceDao.deleteById(serviceId);
//			return new ApiResponse("Deleted service details"+ serviceId +" !");
//		}
//		throw new ResourceNotFoundException("Invalid service ID !!!!!");
//	}

//	@Override
//	public ServiceResponseDTO getServiceDetailsById(Integer serviceId) {
//		Services serviceEntity=serviceDao.findById(serviceId)
//				.orElseThrow(() -> new ResourceNotFoundException("Invalid Category ID!!!!"));
//				//public D map(T src, Class<D> class)
//				return mapper.map(serviceEntity,ServiceResponseDTO.class);
//	}

//	@Override
//	public ApiResponse updateServiceDetailsById(Integer serviceId, Services services) {
//		if(serviceDao.existsById(serviceId)) {
//			// => category exists
//			//category - DETACHED
//			Services services2 = serviceDao.save(services);//upon commit -> update
//			//category2 - PERSISTENT
//			return new ApiResponse("Updated Category Details !!!");
//			
//		}
//		return new ApiResponse("Invalid Category ID!!!");
//	}

	// Add service
    @Override
    public ServiceResponseDTO addService(ServiceRequestDTO serviceRequestDTO) {
        int categoryId = serviceRequestDTO.getCategoryId();
        Categories category = categoryDao.findById(categoryId).orElseThrow();
        
        Services service = mapper.map(serviceRequestDTO, Services.class);
        service.setCategories(category);
        category.getServices().add(service);
        Services serviceResponse = serviceDao.save(service);
        
        return mapper.map(serviceResponse, ServiceResponseDTO.class);
    }

    // Delete service
    @Override
    public ApiResponse deleteServiceDetails(int serviceId) {
        try {
            if (!serviceDao.existsById(serviceId)) {
                return new ApiResponse("Service ID " + serviceId + " not found!");
            }
            serviceDao.deleteById(serviceId);
            return new ApiResponse("Deleted service successfully: " + serviceId);
        } catch (Exception e) {
            return new ApiResponse("Error while deleting service: " + e.getMessage());
        }
    }

    @Override
    public ServiceResponseDTO getServiceDetailsById(Integer serviceId) {
        Services serviceEntity = serviceDao.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Service ID!!!!"));
        ServiceResponseDTO dto= mapper.map(serviceEntity, ServiceResponseDTO.class);
        dto.setServiceid(serviceEntity.getServiceid());
        dto.setCategorieid(serviceEntity.getCategories().getCategorieid());
        return dto;
        
    }

//    @Override
//    public ApiResponse updateServiceDetailsById(Integer serviceId, Services services) {
//        if (serviceDao.existsById(serviceId)) {
//            Services updatedService = serviceDao.save(services);
//            return new ApiResponse("Updated Service Details!!!");
//        }
//        return new ApiResponse("Invalid Service ID!!!");
//    }
//
    // Get services by category ID
    @Override
    public List<ServiceResponseDTO> getServicesByCategoryId(Integer categoryId) {
        Categories category = categoryDao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Category ID"));

        return category.getServices().stream()
                .map(service -> mapper.map(service, ServiceResponseDTO.class))
                .collect(Collectors.toList());
    }

	//update 
    @Override
    public ServiceResponseDTO updateServiceDetailsById(Integer serviceId, ServiceRequestDTO serviceRequestDTO) {
        Services existingService = serviceDao.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service ID " + serviceId + " not found!"));

        // Update only non-null values
        if (serviceRequestDTO.getTitle() != null) existingService.setTitle(serviceRequestDTO.getTitle());
        if (serviceRequestDTO.getDescription() != null) existingService.setDescription(serviceRequestDTO.getDescription());
        if (serviceRequestDTO.getImage() != null) existingService.setImage(serviceRequestDTO.getImage());
        if (serviceRequestDTO.getBasePrice() != null) existingService.setBasePrice(serviceRequestDTO.getBasePrice());
        if (serviceRequestDTO.getDiscount() != null) existingService.setDiscount(serviceRequestDTO.getDiscount());
        if (serviceRequestDTO.getRating() != null) existingService.setRating(serviceRequestDTO.getRating());

        Services savedService = serviceDao.save(existingService);

        return mapper.map(savedService, ServiceResponseDTO.class);
    }

	



	

	
	

}
