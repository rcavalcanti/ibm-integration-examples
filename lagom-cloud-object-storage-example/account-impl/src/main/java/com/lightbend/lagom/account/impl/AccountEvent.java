/*
 * Copyright (C) 2016-2017 Lightbend Inc. <https://www.lightbend.com>
 */
package com.lightbend.lagom.account.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

import javax.annotation.concurrent.Immutable;
import java.time.OffsetDateTime;

/**
 * This interface defines all the events that the Account entity supports.
 * <p>
 * By convention, the events should be inner classes of the interface, which
 * makes it simple to get a complete picture of what events an entity has.
 */
public interface AccountEvent extends Jsonable, AggregateEvent<AccountEvent> {

  AggregateEventTag<AccountEvent> TAG = AggregateEventTag.of(AccountEvent.class);

  String getNumber();
  double getAmount();
  OffsetDateTime getDateTime();

  @Override
  default AggregateEventTag<AccountEvent> aggregateTag() {
    return TAG;
  }

  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class DepositExecuted implements AccountEvent {

    private final String number;
    private final double amount;
    private final OffsetDateTime dateTime;

    @Override
    public String getNumber() {
      return number;
    }

    @Override
    public double getAmount() {
      return amount;
    }

    @Override
    public OffsetDateTime getDateTime() {
      return dateTime;
    }


    @JsonCreator
    public DepositExecuted(double amount, String number, OffsetDateTime dateTime) {
      this.amount = amount;
      this.number = number;
      this.dateTime = dateTime;
    }

  }

  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class WithdrawExecuted implements AccountEvent {

    private final String number;
    private final double amount;
    private final OffsetDateTime dateTime;

    @Override
    public String getNumber() {
      return number;
    }

    @Override
    public double getAmount() {
      return amount;
    }

    @Override
    public OffsetDateTime getDateTime() {
      return dateTime;
    }

    @JsonCreator
    public WithdrawExecuted(double amount, String number, OffsetDateTime dateTime) {
      this.amount = amount;
      this.number = number;
      this.dateTime = dateTime;
    }
  }
}
