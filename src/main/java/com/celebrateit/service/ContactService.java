package com.celebrateit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.celebrateit.dto.ContactDTO;

@Service
public interface ContactService {

	ContactDTO createOrUpdateContact(ContactDTO contactDTO);

    List<ContactDTO> getAllContacts();

    ContactDTO getContactById(Integer id);

    void deleteContact(Integer id);
}