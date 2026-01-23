package bank.account_movement.service;

import bank.common_lib.event.dto.client.ClientCreateEvent;

public interface AccountEventService {
    void handleClientCreated(ClientCreateEvent clientEvent);
}
