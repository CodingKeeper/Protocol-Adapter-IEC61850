/**
 * Copyright 2014-2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.protocol.iec61850.infra.messaging;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alliander.osgp.adapter.protocol.iec61850.device.DeviceResponse;
import com.alliander.osgp.adapter.protocol.iec61850.device.ssld.SsldDeviceService;
import com.alliander.osgp.adapter.protocol.iec61850.device.ssld.responses.GetStatusDeviceResponse;
import com.alliander.osgp.dto.valueobjects.DeviceStatusDto;
import com.alliander.osgp.shared.infra.jms.DeviceMessageMetadata;
import com.alliander.osgp.shared.infra.jms.ProtocolResponseMessage;
import com.alliander.osgp.shared.infra.jms.ResponseMessageResultType;
import com.alliander.osgp.shared.infra.jms.ResponseMessageSender;

/**
 * Base class for MessageProcessor implementations. Each MessageProcessor
 * implementation should be annotated with @Component. Further the MessageType
 * the MessageProcessor implementation can process should be passed in at
 * construction. The Singleton instance is added to the HashMap of
 * MessageProcessors after dependency injection has completed.
 */
public abstract class SsldDeviceRequestMessageProcessor extends BaseMessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SsldDeviceRequestMessageProcessor.class);

    @Autowired
    protected SsldDeviceService deviceService;

    /**
     * Each MessageProcessor should register it's MessageType at construction.
     *
     * @param deviceRequestMessageType
     *            The MessageType the MessageProcessor implementation can
     *            process.
     */
    protected SsldDeviceRequestMessageProcessor(final DeviceRequestMessageType deviceRequestMessageType) {
        this.deviceRequestMessageType = deviceRequestMessageType;
    }

    /**
     * Initialization function executed after dependency injection has finished.
     * The MessageProcessor Singleton is added to the HashMap of
     * MessageProcessors. The key for the HashMap is the integer value of the
     * enumeration member.
     */
    @PostConstruct
    public void init() {
        this.iec61850RequestMessageProcessorMap.addMessageProcessor(this.deviceRequestMessageType.ordinal(),
                this.deviceRequestMessageType.name(), this);
    }

    // This function is used in 3 domains.
    protected void handleGetStatusDeviceResponse(final DeviceResponse deviceResponse,
            final ResponseMessageSender responseMessageSender, final String domain, final String domainVersion,
            final String messageType, final int retryCount) {
        LOGGER.info("Handling getStatusDeviceResponse for device: {}", deviceResponse.getDeviceIdentification());
        if (StringUtils.isEmpty(deviceResponse.getCorrelationUid())) {
            LOGGER.warn(
                    "CorrelationUID is null or empty, not sending GetStatusResponse message for GetStatusRequest message for device: {}",
                    deviceResponse.getDeviceIdentification());
            return;
        }

        final GetStatusDeviceResponse response = (GetStatusDeviceResponse) deviceResponse;
        final DeviceStatusDto status = response.getDeviceStatus();

        final DeviceMessageMetadata deviceMessageMetadata = new DeviceMessageMetadata(
                deviceResponse.getDeviceIdentification(), deviceResponse.getOrganisationIdentification(),
                deviceResponse.getCorrelationUid(), messageType, 0);
        final ProtocolResponseMessage protocolResponseMessage = new ProtocolResponseMessage.Builder().domain(domain)
                .domainVersion(domainVersion).deviceMessageMetadata(deviceMessageMetadata)
                .result(ResponseMessageResultType.OK).osgpException(null).retryCount(retryCount).dataObject(status)
                .build();
        responseMessageSender.send(protocolResponseMessage);
    }
}
