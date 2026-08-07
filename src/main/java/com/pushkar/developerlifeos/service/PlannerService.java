package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.PlannerRequestDTO;
import com.pushkar.developerlifeos.dto.PlannerResponseDTO;
import com.pushkar.developerlifeos.dto.PlannerStatisticsDTO;
import com.pushkar.developerlifeos.entity.Planner;
import com.pushkar.developerlifeos.entity.TimeSlot;
import com.pushkar.developerlifeos.entity.User;
import com.pushkar.developerlifeos.exception.TaskNotFoundException;
import com.pushkar.developerlifeos.repository.PlannerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;

    public PlannerService(
            PlannerRepository plannerRepository,
            ModelMapper modelMapper,
            CurrentUserService currentUserService) {

        this.plannerRepository = plannerRepository;
        this.modelMapper = modelMapper;
        this.currentUserService = currentUserService;
    }

    // Create Planner
    public Planner createPlanner(PlannerRequestDTO dto) {

        User currentUser = currentUserService.getCurrentUser();

        Planner planner = modelMapper.map(dto, Planner.class);

        planner.setCompleted(false);

        planner.setUser(currentUser);

        return plannerRepository.save(planner);
    }

    // Get All Planners
    public List<PlannerResponseDTO> getAllPlanners() {

        User currentUser = currentUserService.getCurrentUser();

        return plannerRepository.findByUser(currentUser)

                .stream()

                .map(planner -> modelMapper.map(
                        planner,
                        PlannerResponseDTO.class))

                .toList();
    }

    // Get Planner By Id
    public PlannerResponseDTO getPlannerById(Long id) {

        User currentUser = currentUserService.getCurrentUser();

        Planner planner = plannerRepository

                .findByIdAndUser(id, currentUser)

                .orElseThrow(() ->
                        new TaskNotFoundException("Planner Not Found"));

        return modelMapper.map(
                planner,
                PlannerResponseDTO.class);
    }

    // update planner
    public Planner updatePlanner(Long id, PlannerRequestDTO dto) {

        User currentUser = currentUserService.getCurrentUser();

        Planner planner = plannerRepository

                .findByIdAndUser(id, currentUser)

                .orElseThrow(() ->
                        new TaskNotFoundException("Planner not found"));

        planner.setTitle(dto.getTitle());
        planner.setDescription(dto.getDescription());
        planner.setTimeSlot(dto.getTimeSlot());
        planner.setPlannerDate(dto.getPlannerDate());

        return plannerRepository.save(planner);
    }

    // Delete Planner
    public void deletePlanner(Long id) {

        User currentUser = currentUserService.getCurrentUser();

        Planner planner = plannerRepository

                .findByIdAndUser(id, currentUser)

                .orElseThrow(() ->
                        new TaskNotFoundException("Planner not found"));

        plannerRepository.delete(planner);
    }

    // Toggle Complete
    public Planner toggleCompleted(Long id) {

        User currentUser = currentUserService.getCurrentUser();

        Planner planner = plannerRepository

                .findByIdAndUser(id, currentUser)

                .orElseThrow(() ->
                        new TaskNotFoundException("Planner not found"));

        planner.setCompleted(!planner.isCompleted());

        return plannerRepository.save(planner);
    }

    //  Filter by Date
    public List<PlannerResponseDTO> getPlannerByDate(LocalDate date) {

        User currentUser = currentUserService.getCurrentUser();

        return plannerRepository

                .findByUserAndPlannerDate(currentUser, date)

                .stream()

                .map(planner ->
                        modelMapper.map(
                                planner,
                                PlannerResponseDTO.class))

                .toList();
    }

    // Filter By Time Slot
    public List<PlannerResponseDTO> getPlannerByTimeSlot(TimeSlot slot) {

        User currentUser = currentUserService.getCurrentUser();

        return plannerRepository

                .findByUserAndTimeSlot(currentUser, slot)

                .stream()

                .map(planner ->
                        modelMapper.map(
                                planner,
                                PlannerResponseDTO.class))

                .toList();
    }

    // Focus method
    public List<PlannerResponseDTO> getTodayFocus() {

        User currentUser = currentUserService.getCurrentUser();

        return plannerRepository

                .findByUserAndPlannerDateAndCompletedFalse(
                        currentUser,
                        LocalDate.now()
                )

                .stream()

                .map(planner ->
                        modelMapper.map(
                                planner,
                                PlannerResponseDTO.class))

                .toList();
    }

    public PlannerStatisticsDTO getStatistics() {

        User currentUser = currentUserService.getCurrentUser();

        return PlannerStatisticsDTO.builder()

                .totalPlans(
                        plannerRepository.countByUser(currentUser)
                )

                .completedPlans(
                        plannerRepository.countByUserAndCompleted(
                                currentUser,
                                true
                        )
                )

                .pendingPlans(
                        plannerRepository.countByUserAndCompleted(
                                currentUser,
                                false
                        )
                )

                .morningPlans(
                        plannerRepository.countByUserAndTimeSlot(
                                currentUser,
                                TimeSlot.MORNING
                        )
                )

                .afternoonPlans(
                        plannerRepository.countByUserAndTimeSlot(
                                currentUser,
                                TimeSlot.AFTERNOON
                        )
                )

                .eveningPlans(
                        plannerRepository.countByUserAndTimeSlot(
                                currentUser,
                                TimeSlot.EVENING
                        )
                )

                .nightPlans(
                        plannerRepository.countByUserAndTimeSlot(
                                currentUser,
                                TimeSlot.NIGHT
                        )
                )

                .build();
    }

}