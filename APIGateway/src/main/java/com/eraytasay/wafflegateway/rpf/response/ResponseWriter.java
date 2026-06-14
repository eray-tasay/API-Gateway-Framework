package com.eraytasay.wafflegateway.rpf.response;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;

public class ResponseWriter {
    public void write(HttpServletResponse responseDest, Response responseSrc)
    {
        if (responseSrc == null || responseDest == null) {
            throw new IllegalArgumentException("Response and HttpServletResponse cannot be null");
        }

        responseDest.setStatus(responseSrc.getStatusCode().value());

        var headers = responseSrc.getHeaders();
            for (var headerName : headers.getNames()) {
                var values = headers.get(headerName);

                for (var value : values.getAll())
                    responseDest.addHeader(headerName, value);
        }

        var body = responseSrc.getBody();

        if (body != null && body.length > 0) {
            if (!responseDest.containsHeader("Content-Length"))
                responseDest.setContentLength(body.length);

            try (var outputStream = responseDest.getOutputStream()) {
                StreamUtils.copy(body, outputStream);
                outputStream.flush();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
