package bank.report_service.service;

import bank.common_lib.event.dto.client.ClientCreateEvent;

public interface ClientReportEventService {
    void handleClientCreated(ClientCreateEvent clientEvent);
}
