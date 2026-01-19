package bank.account_movement.service;

import bank.account_movement.dto.message.event.ClientEvent;

public interface AccountEventService {
    void handleClientCreated(ClientEvent clientEvent);
}
