package com.techelevator.tenmo.services;

public interface TransferTypeService {
    com.techelevator.tenmo.model.TransferType getTransferTypeById(int id);
    com.techelevator.tenmo.model.TransferType getTransferTypeFromDesc(String description);
}
