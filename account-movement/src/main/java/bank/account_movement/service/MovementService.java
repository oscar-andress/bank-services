package bank.account_movement.service;

import bank.account_movement.dto.request.MovementDeleteRequest;
import bank.account_movement.dto.request.MovementRegisterRequest;
import bank.account_movement.dto.response.MovementRegisterResponse;
import bank.account_movement.dto.response.MovementResponse;
import bank.account_movement.dto.response.PageResponse;

public interface MovementService {
    MovementRegisterResponse registerMovement(MovementRegisterRequest request);
    PageResponse<MovementResponse> getMovements(long accountId, int pageNumber, int pageSize);
    void deleteMovement(MovementDeleteRequest request);
}
