package com.asset.reservation.asset.exception;

import com.asset.reservation.asset.util.enums.BusinessExceptionLabels;

public class BusinessException extends RuntimeException {
  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(BusinessExceptionLabels labels) {
    super(labels.getValue());
  }
}
