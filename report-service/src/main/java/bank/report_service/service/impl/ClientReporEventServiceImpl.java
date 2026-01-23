package bank.report_service.service.impl;

import org.springframework.stereotype.Service;

import bank.common_lib.event.dto.client.ClientCreateEvent;
import bank.report_service.entity.ClientReport;
import bank.report_service.mapper.ClientReportMapper;
import bank.report_service.repository.ClientReportRepository;
import bank.report_service.service.ClientReportEventService;

@Service
public class ClientReporEventServiceImpl implements ClientReportEventService {

    private final ClientReportRepository clientReportRepository;
    private final ClientReportMapper clientReportMapper;

    ClientReporEventServiceImpl(ClientReportRepository clientReportRepository,
                                ClientReportMapper clientReportMapper
    ){
        this.clientReportRepository = clientReportRepository;
        this.clientReportMapper = clientReportMapper;
    }
    @Override
    public void handleClientCreated(ClientCreateEvent clientEvent) {

        if(hasClient(clientEvent.getClientId())) return;

        ClientReport clientReport = clientReportMapper.clientEventToEntity(clientEvent);

        clientReportRepository.save(clientReport);
    }

    private boolean hasClient(String clientId){
        return clientReportRepository.findByClientId(clientId).isPresent();
    }
    
}
