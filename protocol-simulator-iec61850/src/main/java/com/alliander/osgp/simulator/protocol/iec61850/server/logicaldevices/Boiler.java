/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.simulator.protocol.iec61850.server.logicaldevices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openmuc.openiec61850.BasicDataAttribute;
import org.openmuc.openiec61850.BdaType;
import org.openmuc.openiec61850.Fc;
import org.openmuc.openiec61850.ServerModel;

import com.alliander.osgp.simulator.protocol.iec61850.server.QualityType;

public class Boiler extends LogicalDevice {

    private static final Map<Node, Fc> FC_BY_NODE;
    static {
        final Map<Node, Fc> fcByNode = new TreeMap<>();

        fcByNode.put(Node.LLN0_HEALTH_STVAL, Fc.ST);
        fcByNode.put(Node.LLN0_HEALTH_Q, Fc.ST);
        fcByNode.put(Node.LLN0_HEALTH_T, Fc.ST);
        fcByNode.put(Node.LLN0_BEH_STVAL, Fc.ST);
        fcByNode.put(Node.LLN0_BEH_Q, Fc.ST);
        fcByNode.put(Node.LLN0_BEH_T, Fc.ST);
        fcByNode.put(Node.LLN0_MOD_STVAL, Fc.ST);
        fcByNode.put(Node.LLN0_MOD_Q, Fc.ST);
        fcByNode.put(Node.LLN0_MOD_T, Fc.ST);

        fcByNode.put(Node.GGIO1_ALM1_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM1_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM1_T, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM2_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM2_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM2_T, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM3_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM3_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM3_T, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM4_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM4_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_ALM4_T, Fc.ST);
        fcByNode.put(Node.GGIO1_INTIN1_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_INTIN1_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_INTIN1_T, Fc.ST);

        fcByNode.put(Node.GGIO1_WRN1_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN1_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN1_T, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN2_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN2_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN2_T, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN3_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN3_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN3_T, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN4_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN4_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_WRN4_T, Fc.ST);
        fcByNode.put(Node.GGIO1_INTIN2_STVAL, Fc.ST);
        fcByNode.put(Node.GGIO1_INTIN2_Q, Fc.ST);
        fcByNode.put(Node.GGIO1_INTIN2_T, Fc.ST);

        fcByNode.put(Node.DSCH1_SCHDID_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDTYP_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDCAT_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDABSTM_VAL_0, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDABSTM_TIME_0, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDABSTM_VAL_1, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDABSTM_TIME_1, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDABSTM_VAL_2, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDABSTM_TIME_2, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDABSTM_VAL_3, Fc.SP);
        fcByNode.put(Node.DSCH1_SCHDABSTM_TIME_3, Fc.SP);

        fcByNode.put(Node.DSCH2_SCHDID_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDTYP_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDCAT_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDABSTM_VAL_0, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDABSTM_TIME_0, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDABSTM_VAL_1, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDABSTM_TIME_1, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDABSTM_VAL_2, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDABSTM_TIME_2, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDABSTM_VAL_3, Fc.SP);
        fcByNode.put(Node.DSCH2_SCHDABSTM_TIME_3, Fc.SP);

        fcByNode.put(Node.DSCH3_SCHDID_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDTYP_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDCAT_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDABSTM_VAL_0, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDABSTM_TIME_0, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDABSTM_VAL_1, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDABSTM_TIME_1, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDABSTM_VAL_2, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDABSTM_TIME_2, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDABSTM_VAL_3, Fc.SP);
        fcByNode.put(Node.DSCH3_SCHDABSTM_TIME_3, Fc.SP);

        fcByNode.put(Node.DSCH4_SCHDID_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDTYP_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDCAT_SETVAL, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDABSTM_VAL_0, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDABSTM_TIME_0, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDABSTM_VAL_1, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDABSTM_TIME_1, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDABSTM_VAL_2, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDABSTM_TIME_2, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDABSTM_VAL_3, Fc.SP);
        fcByNode.put(Node.DSCH4_SCHDABSTM_TIME_3, Fc.SP);

        fcByNode.put(Node.MMXU1_TOTW_MAG_F, Fc.MX);
        fcByNode.put(Node.MMXU1_TOTW_Q, Fc.MX);
        fcByNode.put(Node.MMXU1_TOTW_T, Fc.MX);
        fcByNode.put(Node.MMXU1_MINWPHS_MAG_F, Fc.MX);
        fcByNode.put(Node.MMXU1_MINWPHS_Q, Fc.MX);
        fcByNode.put(Node.MMXU1_MINWPHS_T, Fc.MX);
        fcByNode.put(Node.MMXU1_MAXWPHS_MAG_F, Fc.MX);
        fcByNode.put(Node.MMXU1_MAXWPHS_Q, Fc.MX);
        fcByNode.put(Node.MMXU1_MAXWPHS_T, Fc.MX);

        fcByNode.put(Node.DRCC1_OUTWSET_SUBVAL_F, Fc.SV);
        fcByNode.put(Node.DRCC1_OUTWSET_SUBQ, Fc.SV);

        fcByNode.put(Node.DGEN1_TOTWH_MAG_F, Fc.MX);
        fcByNode.put(Node.DGEN1_TOTWH_Q, Fc.MX);
        fcByNode.put(Node.DGEN1_TOTWH_T, Fc.MX);
        fcByNode.put(Node.DGEN1_GNOPST_STVAL, Fc.ST);
        fcByNode.put(Node.DGEN1_GNOPST_Q, Fc.ST);
        fcByNode.put(Node.DGEN1_GNOPST_T, Fc.ST);
        fcByNode.put(Node.DGEN1_OPTMSRS_STVAL, Fc.ST);
        fcByNode.put(Node.DGEN1_OPTMSRS_Q, Fc.ST);
        fcByNode.put(Node.DGEN1_OPTMSRS_T, Fc.ST);

        fcByNode.put(Node.TTMP1_TMPSV_INSTMAG_F, Fc.MX);
        fcByNode.put(Node.TTMP1_TMPSV_Q, Fc.MX);
        fcByNode.put(Node.TTMP1_TMPSV_T, Fc.MX);
        fcByNode.put(Node.TTMP2_TMPSV_INSTMAG_F, Fc.MX);
        fcByNode.put(Node.TTMP2_TMPSV_Q, Fc.MX);
        fcByNode.put(Node.TTMP2_TMPSV_T, Fc.MX);
        fcByNode.put(Node.TTMP3_TMPSV_INSTMAG_F, Fc.MX);
        fcByNode.put(Node.TTMP3_TMPSV_Q, Fc.MX);
        fcByNode.put(Node.TTMP3_TMPSV_T, Fc.MX);
        fcByNode.put(Node.TTMP4_TMPSV_INSTMAG_F, Fc.MX);
        fcByNode.put(Node.TTMP4_TMPSV_Q, Fc.MX);
        fcByNode.put(Node.TTMP4_TMPSV_T, Fc.MX);

        fcByNode.put(Node.MFLW1_FLWRTE_MAG_F, Fc.MX);
        fcByNode.put(Node.MFLW1_FLWRTE_Q, Fc.MX);
        fcByNode.put(Node.MFLW1_FLWRTE_T, Fc.MX);

        fcByNode.put(Node.KTNK1_VLMCAP_SETMAG_F, Fc.SP);

        FC_BY_NODE = Collections.unmodifiableMap(fcByNode);
    }

    public Boiler(final String physicalDeviceName, final String logicalDeviceName, final ServerModel serverModel) {
        super(physicalDeviceName, logicalDeviceName, serverModel);
    }

    @Override
    public List<BasicDataAttribute> getAttributesAndSetValues(final Date timestamp) {

        final List<BasicDataAttribute> values = new ArrayList<>();

        values.add(this.setRandomByte(Node.LLN0_HEALTH_STVAL, Fc.ST, 1, 2));
        values.add(this.setQuality(Node.LLN0_HEALTH_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.LLN0_HEALTH_T, Fc.ST, timestamp));
        values.add(this.setRandomByte(Node.LLN0_BEH_STVAL, Fc.ST, 1, 2));
        values.add(this.setQuality(Node.LLN0_BEH_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.LLN0_BEH_T, Fc.ST, timestamp));
        values.add(this.setRandomByte(Node.LLN0_MOD_STVAL, Fc.ST, 1, 2));
        values.add(this.setQuality(Node.LLN0_MOD_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.LLN0_MOD_T, Fc.ST, timestamp));

        values.add(this.setBoolean(Node.GGIO1_ALM1_STVAL, Fc.ST, false));
        values.add(this.setQuality(Node.GGIO1_ALM1_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_ALM1_T, Fc.ST, timestamp));
        values.add(this.setBoolean(Node.GGIO1_ALM2_STVAL, Fc.ST, false));
        values.add(this.setQuality(Node.GGIO1_ALM2_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_ALM2_T, Fc.ST, timestamp));
        values.add(this.setBoolean(Node.GGIO1_ALM3_STVAL, Fc.ST, false));
        values.add(this.setQuality(Node.GGIO1_ALM3_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_ALM3_T, Fc.ST, timestamp));
        values.add(this.setBoolean(Node.GGIO1_ALM4_STVAL, Fc.ST, false));
        values.add(this.setQuality(Node.GGIO1_ALM4_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_ALM4_T, Fc.ST, timestamp));
        values.add(this.setRandomInt(Node.GGIO1_INTIN1_STVAL, Fc.ST, 1, 100));
        values.add(this.setQuality(Node.GGIO1_INTIN1_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_INTIN1_T, Fc.ST, timestamp));

        values.add(this.setBoolean(Node.GGIO1_WRN1_STVAL, Fc.ST, false));
        values.add(this.setQuality(Node.GGIO1_WRN1_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_WRN1_T, Fc.ST, timestamp));
        values.add(this.setBoolean(Node.GGIO1_WRN2_STVAL, Fc.ST, false));
        values.add(this.setQuality(Node.GGIO1_WRN2_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_WRN2_T, Fc.ST, timestamp));
        values.add(this.setBoolean(Node.GGIO1_WRN3_STVAL, Fc.ST, false));
        values.add(this.setQuality(Node.GGIO1_WRN3_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_WRN3_T, Fc.ST, timestamp));
        values.add(this.setBoolean(Node.GGIO1_WRN4_STVAL, Fc.ST, false));
        values.add(this.setQuality(Node.GGIO1_WRN4_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_WRN4_T, Fc.ST, timestamp));
        values.add(this.setRandomInt(Node.GGIO1_INTIN2_STVAL, Fc.ST, 1, 100));
        values.add(this.setQuality(Node.GGIO1_INTIN2_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.GGIO1_INTIN2_T, Fc.ST, timestamp));

        values.add(this.setRandomInt(Node.DSCH1_SCHDID_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomInt(Node.DSCH1_SCHDTYP_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomInt(Node.DSCH1_SCHDCAT_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomFloat(Node.DSCH1_SCHDABSTM_VAL_0, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH1_SCHDABSTM_TIME_0, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH1_SCHDABSTM_VAL_1, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH1_SCHDABSTM_TIME_1, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH1_SCHDABSTM_VAL_2, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH1_SCHDABSTM_TIME_2, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH1_SCHDABSTM_VAL_3, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH1_SCHDABSTM_TIME_3, Fc.SP, timestamp));

        values.add(this.setRandomInt(Node.DSCH2_SCHDID_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomInt(Node.DSCH2_SCHDTYP_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomInt(Node.DSCH2_SCHDCAT_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomFloat(Node.DSCH2_SCHDABSTM_VAL_0, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH2_SCHDABSTM_TIME_0, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH2_SCHDABSTM_VAL_1, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH2_SCHDABSTM_TIME_1, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH2_SCHDABSTM_VAL_2, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH2_SCHDABSTM_TIME_2, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH2_SCHDABSTM_VAL_3, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH2_SCHDABSTM_TIME_3, Fc.SP, timestamp));

        values.add(this.setRandomInt(Node.DSCH3_SCHDID_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomInt(Node.DSCH3_SCHDTYP_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomInt(Node.DSCH3_SCHDCAT_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomFloat(Node.DSCH3_SCHDABSTM_VAL_0, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH3_SCHDABSTM_TIME_0, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH3_SCHDABSTM_VAL_1, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH3_SCHDABSTM_TIME_1, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH3_SCHDABSTM_VAL_2, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH3_SCHDABSTM_TIME_2, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH3_SCHDABSTM_VAL_3, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH3_SCHDABSTM_TIME_3, Fc.SP, timestamp));

        values.add(this.setRandomInt(Node.DSCH4_SCHDID_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomInt(Node.DSCH4_SCHDTYP_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomInt(Node.DSCH4_SCHDCAT_SETVAL, Fc.SP, 1, 100));
        values.add(this.setRandomFloat(Node.DSCH4_SCHDABSTM_VAL_0, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH4_SCHDABSTM_TIME_0, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH4_SCHDABSTM_VAL_1, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH4_SCHDABSTM_TIME_1, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH4_SCHDABSTM_VAL_2, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH4_SCHDABSTM_TIME_2, Fc.SP, timestamp));
        values.add(this.setRandomFloat(Node.DSCH4_SCHDABSTM_VAL_3, Fc.SP, 0, 1000));
        values.add(this.setTime(Node.DSCH4_SCHDABSTM_TIME_3, Fc.SP, timestamp));

        values.add(this.setRandomFloat(Node.MMXU1_TOTW_MAG_F, Fc.MX, 0, 1000));
        values.add(this.setQuality(Node.MMXU1_TOTW_Q, Fc.MX, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.MMXU1_TOTW_T, Fc.MX, timestamp));
        values.add(this.setRandomFloat(Node.MMXU1_MINWPHS_MAG_F, Fc.MX, 0, 500));
        values.add(this.setQuality(Node.MMXU1_MINWPHS_Q, Fc.MX, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.MMXU1_MINWPHS_T, Fc.MX, timestamp));
        values.add(this.setRandomFloat(Node.MMXU1_MAXWPHS_MAG_F, Fc.MX, 500, 1000));
        values.add(this.setQuality(Node.MMXU1_MAXWPHS_Q, Fc.MX, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.MMXU1_MAXWPHS_T, Fc.MX, timestamp));

        values.add(this.setRandomFloat(Node.DRCC1_OUTWSET_SUBVAL_F, Fc.SV, 0, 1000));
        values.add(this.setQuality(Node.DRCC1_OUTWSET_SUBQ, Fc.SV, QualityType.VALIDITY_GOOD));

        values.add(this.setRandomFloat(Node.DGEN1_TOTWH_MAG_F, Fc.MX, 0, 1000));
        values.add(this.setQuality(Node.DGEN1_TOTWH_Q, Fc.MX, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.DGEN1_TOTWH_T, Fc.MX, timestamp));
        values.add(this.setRandomByte(Node.DGEN1_GNOPST_STVAL, Fc.ST, 1, 2));
        values.add(this.setQuality(Node.DGEN1_GNOPST_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.DGEN1_GNOPST_T, Fc.ST, timestamp));
        values.add(this.incrementInt(Node.DGEN1_OPTMSRS_STVAL, Fc.ST));
        values.add(this.setQuality(Node.DGEN1_OPTMSRS_Q, Fc.ST, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.DGEN1_OPTMSRS_T, Fc.ST, timestamp));

        values.add(this.setFixedFloat(Node.TTMP1_TMPSV_INSTMAG_F, Fc.MX, 314));
        values.add(this.setQuality(Node.TTMP1_TMPSV_Q, Fc.MX, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.TTMP1_TMPSV_T, Fc.MX, timestamp));
        values.add(this.setFixedFloat(Node.TTMP2_TMPSV_INSTMAG_F, Fc.MX, 324));
        values.add(this.setQuality(Node.TTMP2_TMPSV_Q, Fc.MX, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.TTMP2_TMPSV_T, Fc.MX, timestamp));
        values.add(this.setFixedFloat(Node.TTMP3_TMPSV_INSTMAG_F, Fc.MX, 334));
        values.add(this.setQuality(Node.TTMP3_TMPSV_Q, Fc.MX, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.TTMP3_TMPSV_T, Fc.MX, timestamp));
        values.add(this.setFixedFloat(Node.TTMP4_TMPSV_INSTMAG_F, Fc.MX, 344));
        values.add(this.setQuality(Node.TTMP4_TMPSV_Q, Fc.MX, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.TTMP4_TMPSV_T, Fc.MX, timestamp));

        values.add(this.setFixedFloat(Node.MFLW1_FLWRTE_MAG_F, Fc.MX, 314));
        values.add(this.setQuality(Node.MFLW1_FLWRTE_Q, Fc.MX, QualityType.VALIDITY_GOOD));
        values.add(this.setTime(Node.MFLW1_FLWRTE_T, Fc.MX, timestamp));

        values.add(this.setFixedFloat(Node.KTNK1_VLMCAP_SETMAG_F, Fc.SP, 1313));

        return values;
    }

    @Override
    public BasicDataAttribute getAttributeAndSetValue(final Node node, final String value) {
        final Fc fc = this.getFunctionalConstraint(node);
        if (fc == null) {
            throw this.illegalNodeException(node);
        }

        if (node.getType().equals(BdaType.BOOLEAN)) {
            return this.setBoolean(node, fc, Boolean.parseBoolean(value));
        }

        if (node.getType().equals(BdaType.FLOAT32)) {
            return this.setFixedFloat(node, fc, Float.parseFloat(value));
        }

        if (node.getType().equals(BdaType.INT8)) {
            return this.setByte(node, fc, Byte.parseByte(value));
        }

        if (node.getType().equals(BdaType.INT32)) {
            return this.setInt(node, fc, Integer.parseInt(value));
        }

        if (node.getType().equals(BdaType.QUALITY)) {
            return this.setQuality(node, fc, QualityType.valueOf(value));
        }

        if (node.getType().equals(BdaType.TIMESTAMP)) {
            return this.setTime(node, fc, this.parseDate(value));
        }

        throw this.nodeTypeNotConfiguredException(node);
    }

    @Override
    public Fc getFunctionalConstraint(final Node node) {
        return FC_BY_NODE.get(node);
    }

}
