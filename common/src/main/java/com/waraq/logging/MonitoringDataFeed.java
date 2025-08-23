package com.waraq.logging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitoringDataFeed implements DataFeed {

    @Override
    public void log(Event event) {
        try {
            log.info(event.toString());
        } catch (Exception e) {
            log.error("Error While Logging Controller : ", e);
        }
    }
}
