package bank.account_movement.service.impl;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import bank.account_movement.domain.movement.factory.MovementOperationFactory;
import bank.account_movement.domain.movement.operation.MovementOperation;
import bank.account_movement.dto.request.MovementDeleteRequest;
import bank.account_movement.dto.request.MovementRegisterRequest;
import bank.account_movement.dto.response.MovementRegisterResponse;
import bank.account_movement.dto.response.MovementResponse;
import bank.account_movement.dto.response.PageResponse;
import bank.account_movement.entity.Account;
import bank.account_movement.entity.Movement;
import bank.account_movement.kafka.producer.MovementEventProducer;
import bank.account_movement.mapper.MovementMapper;
import bank.account_movement.repository.AccountRespository;
import bank.account_movement.repository.MovementRepository;
import bank.account_movement.service.MovementService;
import bank.common_lib.event.dto.movement.MovementCreateEvent;
import bank.common_lib.event.dto.movement.MovementDeleteEvent;

@Service
public class MovementServiceImpl implements MovementService{

    private final MovementRepository movementRepository;
    private final AccountRespository accountRespository;
    private final MovementMapper movementMapper;
    private final MovementOperationFactory movementOperationFactory;
    private final MovementEventProducer movementEventProducer;

    MovementServiceImpl(MovementRepository movementRepository,
                        AccountRespository accountRespository,
                        MovementMapper movementMapper,
                        MovementOperationFactory movementOperationFactory,
                        MovementEventProducer movementEventProducer){
        this.movementRepository = movementRepository;
        this.accountRespository = accountRespository;
        this.movementMapper = movementMapper;
        this.movementOperationFactory = movementOperationFactory;
        this.movementEventProducer = movementEventProducer;
    }

    @Override
    @Transactional
    public MovementRegisterResponse registerMovement(MovementRegisterRequest request) {
        
        Account account = findAccountOrElseThrow(request.getAccountId());
        
        Movement movement = movementMapper.toEntity(request);

        MovementOperation movementOperation = movementOperationFactory.getOperation(request.getMovementType());
        
        BigDecimal balance = movementOperation.processMovement(account.getInitialBalance(), request.getValue());
        // processMovement(request.getMovementType(), 
        //                                      account.getInitialBalance(), 
        //                                      request.getValue());
        
        account.setInitialBalance(balance);
        accountRespository.save(account);

        movement.setBalance(balance);
        Movement savedMovement =movementRepository.save(movement);

        MovementCreateEvent movementEvent = movementMapper.toMovementCreateEvent(savedMovement);

        movementEventProducer.produceCreateMovement(movementEvent);
    
        return movementMapper.toMovementRegisterResponse(movement);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MovementResponse> getMovements(long accountId, int pageNumber, int pageSize) {

        Page<Movement> pageMovement = 
            movementRepository
                .findByAccountIdOrderByMovementDateDescMovementHourDesc(
                    accountId, PageRequest.of(pageNumber, pageSize));
        
        return new PageResponse<>(
            pageMovement.getContent()
                .stream()
                .map(movementMapper :: toMovementResponse)
                .toList(), 
            pageMovement.getNumber(), 
            pageMovement.getSize(), 
            pageMovement.getTotalElements(), 
            pageMovement.getTotalPages());
    }

    @Override
    @Transactional
    public void deleteMovement(MovementDeleteRequest request) {

        Movement movement = findMovementOrElseThrow(request.getMovementId());

        Account account = findAccountOrElseThrow(movement.getAccountId());

        MovementOperation movementOperation = movementOperationFactory.getOperation(movement.getMovementType());
        BigDecimal balance = movementOperation.reverseMovement(account.getInitialBalance(), movement.getValue());
        
        // BigDecimal balance = processMovementReverse(movement.getMovementType(), 
        //                                             account.getInitialBalance(), 
        //                                             movement.getValue());

        account.setInitialBalance(balance);
        accountRespository.save(account);

        movementRepository.deleteById(request.getMovementId());

        MovementDeleteEvent movementDeleteEvent = movementMapper.toMovementDeleteEvent(movement, balance);

        movementEventProducer.produceDeleteMovement(movementDeleteEvent);
    }

    // private BigDecimal processMovement(MovementType movementType, BigDecimal initialBalance, BigDecimal value) {
    //     return switch (movementType) {
    //         case DEPOSIT -> accountOperationService.processDeposit(initialBalance, value);
    //         case WITHDRAWAL -> accountOperationService.processWithdrawal(initialBalance, value);
    //         default -> throw new ResponseStatusException(
    //             HttpStatus.CONFLICT, "Unsupported movement type: " + movementType
    //         );
    //     };
    // }

    // private BigDecimal processMovementReverse(MovementType movementType, BigDecimal initialBalance, BigDecimal value) {
    //     return switch (movementType) {
    //         case DEPOSIT -> accountOperationService.reverseDeposit(initialBalance, value);
    //         case WITHDRAWAL -> accountOperationService.reverseWithdrawal(initialBalance, value);
    //         default -> throw new ResponseStatusException(
    //             HttpStatus.CONFLICT, "Unsupported movement reverse type: " + movementType
    //         );
    //     };
    // }

    private Account findAccountOrElseThrow(long accountId){
        return accountRespository.findById(accountId)
            .orElseThrow( () -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, "Accound not found"));
    }

    private Movement findMovementOrElseThrow(long movementId){
        return movementRepository.findById(movementId)
            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, "Movement not found"));
    }
    
}
