package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferStatus;

public interface TransferStatusService {
    TransferStatus getTransferStatusById(int id);
    TransferStatus getTransferStatus(String description);
}
