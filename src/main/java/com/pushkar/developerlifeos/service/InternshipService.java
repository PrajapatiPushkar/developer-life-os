package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.InternshipRequestDTO;
import com.pushkar.developerlifeos.dto.InternshipResponseDTO;
import com.pushkar.developerlifeos.entity.Internship;
import com.pushkar.developerlifeos.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final ModelMapper modelMapper;

    // Create Internship
    public Internship createInternship(InternshipRequestDTO dto) {

        Internship internship = modelMapper.map(dto, Internship.class);

        log.info("Creating Internship : {}", dto.getCompanyName());

        return internshipRepository.save(internship);
    }

    // Get All
    public List<InternshipResponseDTO> getAllInternships() {

        return internshipRepository.findAll()

                .stream()

                .map(internship ->

                        modelMapper.map(

                                internship,

                                InternshipResponseDTO.class))

                .toList();
    }

    // Get By Id
    public InternshipResponseDTO getInternshipById(Long id) {

        Internship internship = internshipRepository.findById(id)

                .orElseThrow(() ->

                        new RuntimeException("Internship not found"));

        return modelMapper.map(

                internship,

                InternshipResponseDTO.class);
    }

    // Update
    public Internship updateInternship(

            Long id,

            InternshipRequestDTO dto) {

        Internship internship = internshipRepository.findById(id)

                .orElseThrow(() ->

                        new RuntimeException("Internship not found"));

        internship.setCompanyName(dto.getCompanyName());

        internship.setRole(dto.getRole());

        internship.setLocation(dto.getLocation());

        internship.setApplicationDate(dto.getApplicationDate());

        internship.setDeadline(dto.getDeadline());

        internship.setStatus(dto.getStatus());

        internship.setJobLink(dto.getJobLink());

        internship.setSalary(dto.getSalary());

        internship.setNotes(dto.getNotes());

        log.info("Updating Internship : {}", id);

        return internshipRepository.save(internship);

    }

    // Delete
    public void deleteInternship(Long id) {

        Internship internship = internshipRepository.findById(id)

                .orElseThrow(() ->

                        new RuntimeException("Internship not found"));

        internshipRepository.delete(internship);

        log.info("Deleted Internship : {}", id);

    }

}