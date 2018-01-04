/**
 * Copyright 2014-2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.protocol.iec61850.infra.messaging;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;

import org.openmuc.openiec61850.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.support.JmsUtils;
import org.springframework.util.StringUtils;

import com.alliander.osgp.adapter.protocol.iec61850.device.DeviceResponse;
import com.alliander.osgp.adapter.protocol.iec61850.device.ssld.responses.EmptyDeviceResponse;
import com.alliander.osgp.adapter.protocol.iec61850.exceptions.ProtocolAdapterException;
import com.alliander.osgp.adapter.protocol.iec61850.infra.networking.helper.RequestMessageData;
import com.alliander.osgp.adapter.protocol.iec61850.infra.networking.services.Iec61850DeviceResponseHandler;
import com.alliander.osgp.adapter.protocol.iec61850.services.DeviceResponseService;
import com.alliander.osgp.shared.exceptionhandling.ComponentType;
import com.alliander.osgp.shared.exceptionhandling.OsgpException;
import com.alliander.osgp.shared.exceptionhandling.TechnicalException;
import com.alliander.osgp.shared.infra.jms.DeviceMessageMetadata;
import com.alliander.osgp.shared.infra.jms.MessageProcessor;
import com.alliander.osgp.shared.infra.jms.MessageProcessorMap;
import com.alliander.osgp.shared.infra.jms.ProtocolResponseMessage;
import com.alliander.osgp.shared.infra.jms.ResponseMessageResultType;
import com.alliander.osgp.shared.infra.jms.ResponseMessageSender;

public abstract class BaseMessageProcessor implements MessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMessageProcessor.class);

    private static final String NO_EXCEPTION_SPECIFIED = "no exception specified";

    @Autowired
    protected int maxRedeliveriesForIec61850Requests;

    @Autowired
    protected DeviceResponseMessageSender responseMessageSender;

    @Autowired
    protected DeviceResponseService deviceResponseService;

    @Autowired
    @Qualifier("iec61850DeviceRequestMessageProcessorMap")
    protected MessageProcessorMap iec61850RequestMessageProcessorMap;

    protected DeviceRequestMessageType deviceRequestMessageType;

    protected void printDomainInfo(final String messageType, final String domain, final String domainVersion) {
        LOGGER.info("Calling DeviceService function: {} for domain: {} {}", messageType, domain, domainVersion);
    }

    /**
     * Get the delivery count for a {@link Message} using 'JMSXDeliveryCount'
     * property.
     */
    public Integer getJmsXdeliveryCount(final Message message) {
        try {
            final int jmsXdeliveryCount = message.getIntProperty("JMSXDeliveryCount");
            LOGGER.info("jmsXdeliveryCount: {}", jmsXdeliveryCount);
            return jmsXdeliveryCount;
        } catch (final JMSException e) {
            LOGGER.error("JMSException while reading JMSXDeliveryCount", e);
            return null;
        }
    }

    /**
     * Use 'jmsxDeliveryCount' to determine if a request should be retried using
     * the re-delivery options. In case a JMSException is thrown, the request
     * will be rolled-back to the message-broker and will be re-delivered
     * according to the re-delivery policy set. If the maximum number of
     * re-deliveries have been executed, a protocol response message will be
     * sent to osgp-core.
     */
    public void checkForRedelivery(final DeviceMessageMetadata deviceMessageMetadata, final OsgpException e,
            final String domain, final String domainVersion, final int jmsxDeliveryCount) throws JMSException {
        final int jmsxRedeliveryCount = jmsxDeliveryCount - 1;
        LOGGER.info("jmsxDeliveryCount: {}, jmsxRedeliveryCount: {}, maxRedeliveriesForIec61850Requests: {}",
                jmsxDeliveryCount, jmsxRedeliveryCount, this.maxRedeliveriesForIec61850Requests);
        if (jmsxRedeliveryCount < this.maxRedeliveriesForIec61850Requests) {
            LOGGER.info(
                    "Redelivering message with messageType: {}, correlationUid: {}, for device: {} - jmsxRedeliveryCount: {} is less than maxRedeliveriesForIec61850Requests: {}",
                    deviceMessageMetadata.getMessageType(), deviceMessageMetadata.getCorrelationUid(),
                    deviceMessageMetadata.getDeviceIdentification(), jmsxRedeliveryCount,
                    this.maxRedeliveriesForIec61850Requests);
            final JMSException jmsException = new JMSException(
                    e == null ? "checkForRedelivery() unknown error: OsgpException e is null" : e.getMessage());
            throw JmsUtils.convertJmsAccessException(jmsException);
        } else {
            LOGGER.warn(
                    "All redelivery attempts failed for message with messageType: {}, correlationUid: {}, for device: {}",
                    deviceMessageMetadata.getMessageType(), deviceMessageMetadata.getCorrelationUid(),
                    deviceMessageMetadata.getDeviceIdentification());
            this.handleExpectedError(e, deviceMessageMetadata.getCorrelationUid(),
                    deviceMessageMetadata.getOrganisationIdentification(),
                    deviceMessageMetadata.getDeviceIdentification(), domain, domainVersion,
                    deviceMessageMetadata.getMessageType());
        }
    }

    public void handleDeviceResponse(final DeviceResponse deviceResponse,
            final ResponseMessageSender responseMessageSender, final String domain, final String domainVersion,
            final String messageType, final int retryCount) {
        final int messagePriority = 0;
        final Long scheduleTime = null;
        this.handleDeviceResponse(deviceResponse, responseMessageSender, domain, domainVersion, messageType, retryCount,
                messagePriority, scheduleTime);
    }

    /**
     * Handles {@link EmptyDeviceResponse} by default. MessageProcessor
     * implementations can override this function to handle responses containing
     * data.
     */
    public void handleDeviceResponse(final DeviceResponse deviceResponse,
            final ResponseMessageSender responseMessageSender, final String domain, final String domainVersion,
            final String messageType, final int retryCount, final int messagePriority, final Long scheduleTime) {

        ResponseMessageResultType result = ResponseMessageResultType.OK;
        OsgpException ex = null;

        try {
            final EmptyDeviceResponse response = (EmptyDeviceResponse) deviceResponse;
            this.deviceResponseService.handleDeviceMessageStatus(response.getStatus());
        } catch (final OsgpException e) {
            LOGGER.error("Device Response Exception", e);
            result = ResponseMessageResultType.NOT_OK;
            ex = e;
        }

        final DeviceMessageMetadata deviceMessageMetadata = new DeviceMessageMetadata(
                deviceResponse.getDeviceIdentification(), deviceResponse.getOrganisationIdentification(),
                deviceResponse.getCorrelationUid(), messageType, messagePriority, scheduleTime);
        final ProtocolResponseMessage protocolResponseMessage = new ProtocolResponseMessage.Builder().domain(domain)
                .domainVersion(domainVersion).deviceMessageMetadata(deviceMessageMetadata).result(result)
                .osgpException(ex).retryCount(retryCount).build();
        responseMessageSender.send(protocolResponseMessage);
    }

    public void handleUnExpectedError(final DeviceResponse deviceResponse, final Throwable t,
            final Serializable messageData, final String domain, final String domainVersion, final String messageType,
            final boolean isScheduled, final int retryCount) {

        final OsgpException ex = this.ensureOsgpException(t);

        final DeviceMessageMetadata deviceMessageMetadata = new DeviceMessageMetadata(
                deviceResponse.getDeviceIdentification(), deviceResponse.getOrganisationIdentification(),
                deviceResponse.getCorrelationUid(), messageType, 0);
        final ProtocolResponseMessage protocolResponseMessage = new ProtocolResponseMessage.Builder().domain(domain)
                .domainVersion(domainVersion).deviceMessageMetadata(deviceMessageMetadata)
                .result(ResponseMessageResultType.NOT_OK).osgpException(ex).retryCount(retryCount)
                .dataObject(messageData).scheduled(isScheduled).build();
        this.responseMessageSender.send(protocolResponseMessage);
    }

    protected OsgpException ensureOsgpException(final Throwable t) {
        if (t instanceof OsgpException && !(t instanceof ProtocolAdapterException)) {
            return (OsgpException) t;
        }

        if (t instanceof ServiceError) {
            String message;
            if (StringUtils.isEmpty(t.getMessage())) {
                message = "no specific service error code";
            } else if ("Error code=22".equals(t.getMessage())) {
                message = "Device communication failure";
            } else {
                message = t.getMessage();
            }
            return new TechnicalException(ComponentType.PROTOCOL_IEC61850, message);
        }

        return new TechnicalException(ComponentType.PROTOCOL_IEC61850,
                t == null ? NO_EXCEPTION_SPECIFIED : t.getMessage());
    }

    public void handleExpectedError(final OsgpException e, final String correlationUid,
            final String organisationIdentification, final String deviceIdentification, final String domain,
            final String domainVersion, final String messageType) {
        LOGGER.error("Expected error while processing message", e);

        final int retryCount = Integer.MAX_VALUE;

        final DeviceMessageMetadata deviceMessageMetadata = new DeviceMessageMetadata(deviceIdentification,
                organisationIdentification, correlationUid, messageType, 0);
        final ProtocolResponseMessage protocolResponseMessage = new ProtocolResponseMessage.Builder().domain(domain)
                .domainVersion(domainVersion).deviceMessageMetadata(deviceMessageMetadata)
                .result(ResponseMessageResultType.NOT_OK).osgpException(e).retryCount(retryCount).build();
        this.responseMessageSender.send(protocolResponseMessage);
    }

    protected Iec61850DeviceResponseHandler createIec61850DeviceResponseHandler(
            final RequestMessageData requestMessageData, final Message message) {
        final Integer jsmxDeliveryCount = this.getJmsXdeliveryCount(message);
        return new Iec61850DeviceResponseHandler(this, jsmxDeliveryCount, requestMessageData,
                this.responseMessageSender);
    }
}
