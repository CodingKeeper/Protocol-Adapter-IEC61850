/**
 * Copyright 2014-2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.protocol.iec61850.infra.messaging.processors;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alliander.osgp.adapter.protocol.iec61850.infra.messaging.DeviceRequestMessageProcessor;
import com.alliander.osgp.adapter.protocol.iec61850.infra.messaging.DeviceRequestMessageType;
import com.alliander.osgp.shared.infra.jms.Constants;

/**
 * Class for processing common update firmware request messages
 */
@Component("iec61850CommonUpdateFirmwareRequestMessageProcessor")
public class CommonUpdateFirmwareRequestMessageProcessor extends DeviceRequestMessageProcessor {
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUpdateFirmwareRequestMessageProcessor.class);

    //
    // @Autowired
    // private DeviceService deviceService;
    //
    // @Autowired
    // private FirmwareLocation firmwareLocation;

    public CommonUpdateFirmwareRequestMessageProcessor() {
        super(DeviceRequestMessageType.UPDATE_FIRMWARE);
    }

    // IDEA: the FirmwareLocation class in domain and dto can/must be deleted!
    // Or, this
    // setup has to be changed in order to reuse the FirmwareLocation class in
    // the domain!!

    @Override
    public void processMessage(final ObjectMessage message) {
        LOGGER.debug("Processing common update firmware request message");

        String correlationUid = null;
        String domain = null;
        String domainVersion = null;
        String messageType = null;
        String organisationIdentification = null;
        String deviceIdentification = null;
        String ipAddress = null;
        Boolean isScheduled = null;
        int retryCount = 0;

        try {
            correlationUid = message.getJMSCorrelationID();
            domain = message.getStringProperty(Constants.DOMAIN);
            domainVersion = message.getStringProperty(Constants.DOMAIN_VERSION);
            messageType = message.getJMSType();
            organisationIdentification = message.getStringProperty(Constants.ORGANISATION_IDENTIFICATION);
            deviceIdentification = message.getStringProperty(Constants.DEVICE_IDENTIFICATION);
            ipAddress = message.getStringProperty(Constants.IP_ADDRESS);
            isScheduled = message.getBooleanProperty(Constants.IS_SCHEDULED);
            retryCount = message.getIntProperty(Constants.RETRY_COUNT);
        } catch (final JMSException e) {
            LOGGER.error("UNRECOVERABLE ERROR, unable to read ObjectMessage instance, giving up.", e);
            LOGGER.debug("correlationUid: {}", correlationUid);
            LOGGER.debug("domain: {}", domain);
            LOGGER.debug("domainVersion: {}", domainVersion);
            LOGGER.debug("messageType: {}", messageType);
            LOGGER.debug("organisationIdentification: {}", organisationIdentification);
            LOGGER.debug("deviceIdentification: {}", deviceIdentification);
            LOGGER.debug("ipAddress: {}", ipAddress);
            LOGGER.debug("scheduled: {}", isScheduled);
            return;
        }

        try {
            final String firmwareIdentification = (String) message.getObject();

            LOGGER.info("Calling DeviceService function: {} for domain: {} {}", messageType, domain, domainVersion);

            // final UpdateFirmwareDeviceRequest deviceRequest = new
            // UpdateFirmwareDeviceRequest(
            // organisationIdentification, deviceIdentification, correlationUid,
            // this.firmwareLocation.getDomain(),
            // this.firmwareLocation.getFullPath(firmwareIdentification),
            // domain, domainVersion, messageType, ipAddress, retryCount,
            // isScheduled);
            //
            // this.deviceService.updateFirmware(deviceRequest);
        } catch (final Exception e) {
            this.handleError(e, correlationUid, organisationIdentification, deviceIdentification, domain,
                    domainVersion, messageType, retryCount);
        }
    }

}
