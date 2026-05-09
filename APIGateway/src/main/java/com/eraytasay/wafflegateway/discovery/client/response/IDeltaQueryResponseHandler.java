package com.eraytasay.wafflegateway.discovery.client.response;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDeltaQueryResponseHandler<T> {
    List<IDeltaCommand> getCommands(ResponseEntity<T> deltaQueryResponse);
    long getSnapshotTimestamp(ResponseEntity<T> deltaQueryResponse);
}
