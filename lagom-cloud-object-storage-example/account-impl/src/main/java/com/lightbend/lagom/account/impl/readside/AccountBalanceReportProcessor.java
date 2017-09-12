package com.lightbend.lagom.account.impl.readside;

import akka.Done;
import akka.japi.Pair;
import akka.stream.javadsl.Flow;
import com.lightbend.lagom.account.impl.AccountEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.Offset;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AccountBalanceReportProcessor extends ReadSideProcessor<AccountEvent> {

  final private AccountReportRepositoryImpl repository;

  @Inject
  public AccountBalanceReportProcessor(AccountReportRepositoryImpl repository) {
    this.repository = repository;
  }


  @Override
  public PSequence<AggregateEventTag<AccountEvent>> aggregateTags() {
    return TreePVector.singleton(AccountEvent.TAG);
  }


  @Override
  public ReadSideHandler<AccountEvent> buildHandler() {

    return new ReadSideHandler<AccountEvent>() {


      @Override
      public CompletionStage<Done> globalPrepare() {
        // we don't save offset, repo is designed to consume from first event each time
        return CompletableFuture.completedFuture(Done.getInstance());
      }

      @Override
      public CompletionStage<Offset> prepare(AggregateEventTag<AccountEvent> tag) {
        // always start reading from first event, not offset storage
        return CompletableFuture.completedFuture(Offset.NONE);
      }

      @Override
      public Flow<Pair<AccountEvent, Offset>, Done, ?> handle() {
        return Flow.<Pair<AccountEvent, Offset>>create()
                .mapAsync(
                        1,
                        eventAndOffset -> repository.handleEvent(eventAndOffset.first(), eventAndOffset.second())
                );
      }
    };
  }


}
