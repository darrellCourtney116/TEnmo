package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.util.List;

public interface TransferService {
    Transfer[] getAllTransfers();
    Transfer[] getTransfersByUserId(int userId);

    Transfer getTransferFromId(int id);

    Transfer createTransfer(int transferId, int id, int accountTo, int typeId, BigDecimal amount);


    //TransferStatus getTransferStatusById(int id);
    //TransferStatus getTransferStatus(String description);
    //TransferStatus isTransferApproved(boolean isApproved);

    //TransferType getTransferTypeById(int id);
    //TransferType getTransferType(String description);
}
