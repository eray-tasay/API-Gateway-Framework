package com.eraytasay.wafflegateway.discovery.waffle.response.handler;

import com.eraytasay.wafflegateway.discovery.client.response.IDeltaCommand;
import com.eraytasay.wafflegateway.discovery.client.response.IDeltaQueryResponseHandler;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleDeltaResponse;
import com.eraytasay.wafflegateway.discovery.waffle.response.body.WaffleUpdate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class WaffleDeltaQueryResponseHandler implements IDeltaQueryResponseHandler<WaffleDeltaResponse> {
    @Override
    public List<IDeltaCommand> getCommands(ResponseEntity<WaffleDeltaResponse> deltaQueryResponse)
    {
        var updates = deltaQueryResponse.getBody().getData().getUpdates();
        var res = new ArrayList<IDeltaCommand>(updates.size());

        updates.forEach(u -> res.add(createCommand(u)));

        return res;
    }

    @Override
    public long getSnapshotTimestamp(ResponseEntity<WaffleDeltaResponse> deltaQueryResponse)
    {
        return deltaQueryResponse.getBody().getData().getSnapshotTimestamp();
    }

    private static IDeltaCommand createCommand(WaffleUpdate update)
    {
        return switch (update.getType()) {
            case REGISTER -> createRegisterCommand(update);
            case DEAD, UNREGISTER -> createDeleteCommand(update);
        };
    }

    private static IDeltaCommand createDeleteCommand(WaffleUpdate update)
    {
        return (mutableDataSource) -> mutableDataSource.delete(update.getServiceInstance());
    }

    private static IDeltaCommand createRegisterCommand(WaffleUpdate update)
    {
        return (mutableDataSource) -> mutableDataSource.add(update.getServiceInstance());
    }
}
