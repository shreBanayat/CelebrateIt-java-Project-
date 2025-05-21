package com.celebrateit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celebrateit.dao.ContactDao;
import com.celebrateit.dto.ContactDTO;
import com.celebrateit.pojo.Contact;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
    private ContactDao contactDao;
	
	// Convert DTO to Entity
    private Contact convertToEntity(ContactDTO dto) {
        return new Contact(dto.getContactId(), dto.getName(), dto.getEmail(),dto.getMobile(), dto.getMessage());
    }

    // Convert Entity to DTO
    private ContactDTO convertToDTO(Contact contact) {
        return new ContactDTO(contact.getContactId(), contact.getName(), contact.getEmail(),contact.getMobile(), contact.getMessage());
    }
	
	@Override
	public ContactDTO createOrUpdateContact(ContactDTO contactDTO) {
		Contact contact = convertToEntity(contactDTO);
        Contact savedContact = contactDao.save(contact);
        return convertToDTO(savedContact);
	}

	@Override
	public List<ContactDTO> getAllContacts() {
		return contactDao.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
	}

	@Override
	public ContactDTO getContactById(Integer id) {
		return contactDao.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
	}

	@Override
	public void deleteContact(Integer id) {
		contactDao.deleteById(id);
	}

}

