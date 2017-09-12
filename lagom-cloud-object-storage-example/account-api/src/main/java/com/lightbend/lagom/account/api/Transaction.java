package com.lightbend.lagom.account.api;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Transaction {

  final public Double amount;

  public Transaction(Double amount) {
    // transactions must be positive numbers.
    // Deposit and withdrawn are handled via API contract.
    assert amount > 0;
    this.amount = amount;
  }
}
