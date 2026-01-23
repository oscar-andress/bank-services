package bank.report_service.service.impl;

import org.springframework.stereotype.Service;

import bank.common_lib.event.dto.movement.MovementCreateEvent;
import bank.common_lib.event.dto.movement.MovementDeleteEvent;
import bank.report_service.entity.MovementReport;
import bank.report_service.mapper.ClientReportMapper;
import bank.report_service.repository.MovementReportRepository;
import bank.report_service.service.MovementReportEventService;

@Service
public class MovementReportEventServiceImpl implements MovementReportEventService {

    private final MovementReportRepository movementReportRepository;
    private final ClientReportMapper clientReportMapper;

    MovementReportEventServiceImpl(MovementReportRepository movementReportRepository,
                                   ClientReportMapper clientReportMapper
    ){
        this.movementReportRepository = movementReportRepository;
        this.clientReportMapper = clientReportMapper;
    }

    @Override
    public void handleMovementCreated(MovementCreateEvent movementCreateEvent) {

        if(movementExists(movementCreateEvent.getMovementId())) return;

        MovementReport movementReport = clientReportMapper.movementCreateEventToEntity(movementCreateEvent);

        movementReportRepository.save(movementReport);
    }

    private final boolean movementExists(long movementId){
        return movementReportRepository.findByMovementId(movementId)
                .isPresent();
    }

    @Override
    public void handleMovementDeleted(MovementDeleteEvent movementDeleteEvent) {

        if(!movementExists(movementDeleteEvent.getMovementId())) return;

        MovementReport movementReport = clientReportMapper.movementDeleteEventToEntity(movementDeleteEvent);

        movementReportRepository.save(movementReport);
    }
}
