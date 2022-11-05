/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.miio.internal;

import static org.openhab.binding.miio.internal.MiIoBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * Mi IO Devices
 *
 * @author Marcel Verpaalen - Initial contribution
 */
@NonNullByDefault
public enum MiIoDevices {
    AUX_AIRCONDITION_V1("aux.aircondition.v1", "AUX Smart Air Conditioner", THING_TYPE_UNSUPPORTED),
    CARELI_FRYER_MAF01("careli.fryer.maf01", "Mi Air Frying Pan", THING_TYPE_BASIC),
    CARELI_FRYER_MAF02("careli.fryer.maf02", "Mi Smart Air Fryer (3.5L)", THING_TYPE_BASIC),
    CARELI_FRYER_MAF03("careli.fryer.maf03", "Mi Air Frying Pan", THING_TYPE_BASIC),
    CGLLC_AIRM_CGDN1("cgllc.airm.cgdn1", "Qingping Air Monitor Lite", THING_TYPE_BASIC),
    CGLLC_AIRMONITOR_B1("cgllc.airmonitor.b1", "Mi Multifunction Air Monitor", THING_TYPE_BASIC),
    CGLLC_AIRMONITOR_S1("cgllc.airmonitor.s1", "Qingping Air Monitor", THING_TYPE_BASIC),
    CHUANGMI_IR_V2("chuangmi.ir.v2", "Mi Universal Remote", THING_TYPE_UNSUPPORTED),
    CHUANGMI_PLUG_212A01("chuangmi.plug.212a01", "Mi Smart Power Plug 2 (Wi-Fi and Bluetooth Gateway)",
            THING_TYPE_BASIC),
    CHUANGMI_PLUG_HMI205("chuangmi.plug.hmi205", "Mi Smart Plug WiFi", THING_TYPE_BASIC),
    CHUANGMI_PLUG_HMI206("chuangmi.plug.hmi206", "Mi Smart Plug (WiFi)", THING_TYPE_BASIC),
    CHUANGMI_PLUG_HMI208("chuangmi.plug.hmi208", "Mi Smart Wi-Fi Plug (Bluetooth Gateway)", THING_TYPE_BASIC),
    CHUANGMI_PLUG_M1("chuangmi.plug.m1", "Mi Plug Mini", THING_TYPE_BASIC),
    CHUANGMI_PLUG_M3("chuangmi.plug.m3", "Mi Smart Plug (Wi-Fi) Basic", THING_TYPE_BASIC),
    CHUANGMI_PLUG_V1("chuangmi.plug.v1", "Mi Smart Power Plug", THING_TYPE_BASIC),
    CHUANGMI_PLUG_V2("chuangmi.plug.v2", "Mi Smart Power Plug v2", THING_TYPE_BASIC),
    CHUANGMI_PLUG_V3("chuangmi.plug.v3", "MIJIA Smart  Plug Enhanced", THING_TYPE_BASIC),
    CHUANGMI_REMOTE_V2("chuangmi.remote.v2", "Mi Remote", THING_TYPE_UNSUPPORTED),
    CHUNMI_COOKER_NORMAL1("chunmi.cooker.normal1", "Mi IH Rice Cooker", THING_TYPE_UNSUPPORTED),
    CHUNMI_COOKER_NORMAL2("chunmi.cooker.normal2", "Mi IH Rice Cooker", THING_TYPE_UNSUPPORTED),
    CHUNMI_COOKER_NORMAL4("chunmi.cooker.normal4", "Mi IH Rice Cooker 4L", THING_TYPE_UNSUPPORTED),
    CHUNMI_COOKER_PRESS1("chunmi.cooker.press1", "Mi IH Pressure Rice Cooker", THING_TYPE_UNSUPPORTED),
    CHUNMI_COOKER_PRESS2("chunmi.cooker.press2", "Mi IH Pressure Rice Cooker", THING_TYPE_UNSUPPORTED),
    CUCO_PLUG_CP1("cuco.plug.cp1", "Gosund Smart Plug", THING_TYPE_BASIC),
    DEERMA_HUMIDIFIER_JSQ("deerma.humidifier.jsq", "Mi Smart Antibacterial Humidifier", THING_TYPE_BASIC),
    DEERMA_HUMIDIFIER_JSQ1("deerma.humidifier.jsq1", "Mi S Smart Humidifer ", THING_TYPE_BASIC),
    DEERMA_HUMIDIFIER_JSQ5("deerma.humidifier.jsq5", "Mi Smart Antibacterial Humidifier", THING_TYPE_BASIC),
    DEERMA_HUMIDIFIER_JSQS("deerma.humidifier.jsqs", "Mi Smart Humidifer S", THING_TYPE_BASIC),
    DEERMA_HUMIDIFIER_MJJSQ("deerma.humidifier.mjjsq", "Mi Smart Humidifier", THING_TYPE_BASIC),
    DMAKER_AIRFRESH_A1("dmaker.airfresh.a1", "Mi Fresh Air Ventilator A1-150", THING_TYPE_BASIC),
    DMAKER_AIRFRESH_T2017("dmaker.airfresh.t2017", "Mi Fresh Air Ventilator", THING_TYPE_BASIC),
    DMAKER_FAN_1C("dmaker.fan.1c", "Mi Smart Standing Fan 2 Lite", THING_TYPE_BASIC),
    DMAKER_FAN_P5("dmaker.fan.p5", "Mi Smart Standing Fan 1X", THING_TYPE_BASIC),
    DMAKER_FAN_P8("dmaker.fan.p8", "Mi Smart Standing Fan 1C", THING_TYPE_BASIC),
    DMAKER_FAN_P9("dmaker.fan.p9", "Mi Smart Tower Fan", THING_TYPE_BASIC),
    DMAKER_FAN_P10("dmaker.fan.p10", "Mi Smart Standing Fan 2", THING_TYPE_BASIC),
    DMAKER_FAN_P15("dmaker.fan.p15", "Mi Smart Standing Fan Pro", THING_TYPE_BASIC),
    DMAKER_FAN_P18("dmaker.fan.p18", "Mi Smart Standing Fan 2", THING_TYPE_BASIC),
    DREAME_VACUUM_MC1808("dreame.vacuum.mc1808", "Mi Robot Vacuum Mop 1C STYTJ01ZHM", THING_TYPE_BASIC),
    DREAME_VACUUM_P2008("dreame.vacuum.p2008", "Dreame Robot Vacuum-Mop F9", THING_TYPE_BASIC),
    DREAME_VACUUM_P2009("dreame.vacuum.p2009", "Dreame Robot Vacuum D9 ", THING_TYPE_BASIC),
    DREAME_VACUUM_P2027("dreame.vacuum.p2027", "Dreame Bot W10", THING_TYPE_BASIC),
    DREAME_VACUUM_P2028("dreame.vacuum.p2028", "Dreame Bot Z10 Pro", THING_TYPE_BASIC),
    DREAME_VACUUM_P2029("dreame.vacuum.p2029", "Dreame Bot L10 Pro", THING_TYPE_BASIC),
    DREAME_VACUUM_P2036("dreame.vacuum.p2036", "Trouver Robot LDS Vacuum-Mop Finder", THING_TYPE_BASIC),
    DREAME_VACUUM_P2041O("dreame.vacuum.p2041o", "Mi Robot Vacuum-Mop 2 Pro+", THING_TYPE_BASIC),
    DREAME_VACUUM_P2156O("dreame.vacuum.p2156o", "MOVA Z500 Robot Vacuum and Mop Cleaner", THING_TYPE_BASIC),
    DREAME_VACUUM_P2157("dreame.vacuum.p2157", "MOVA L600 Robot Vacuum and Mop Cleaner", THING_TYPE_BASIC),
    DREAME_VACUUM_P2259("dreame.vacuum.p2259", "Dreame Bot D9 Max", THING_TYPE_BASIC),
    HUAYI_LIGHT_ARI013("huayi.light.ari013", "HUIZUO ARIES For Bedroom", THING_TYPE_BASIC),
    HUAYI_LIGHT_ARIES("huayi.light.aries", "HUIZUO ARIES For Living Room", THING_TYPE_BASIC),
    HUAYI_LIGHT_FANWY("huayi.light.fanwy", "HUIZUO Fan Light", THING_TYPE_BASIC),
    HUAYI_LIGHT_FANWY2("huayi.light.fanwy2", "HUIZUO Fan Light(2020)", THING_TYPE_BASIC),
    HUAYI_LIGHT_PEG091("huayi.light.peg091", "HUIZUO PEGASUS For Living Room", THING_TYPE_BASIC),
    HUAYI_LIGHT_PEG093("huayi.light.peg093", "HUIZUO PEGASUS For Bedroom", THING_TYPE_BASIC),
    HUAYI_LIGHT_PIS123("huayi.light.pis123", "HUIZUO PISCES For Bedroom", THING_TYPE_BASIC),
    HUAYI_LIGHT_PISCES("huayi.light.pisces", "HUIZUO PISCES For Living Room", THING_TYPE_BASIC),
    HUAYI_LIGHT_TAU023("huayi.light.tau023", "HUIZUO TAURUS For Bedroom", THING_TYPE_BASIC),
    HUAYI_LIGHT_TAURUS("huayi.light.taurus", "HUIZUO TAURUS For Living Room", THING_TYPE_BASIC),
    HUAYI_LIGHT_VIR063("huayi.light.vir063", "HUIZUO VIRGO For Bedroom", THING_TYPE_BASIC),
    HUAYI_LIGHT_VIRGO("huayi.light.virgo", "HUIZUO VIRGO For Living Room", THING_TYPE_BASIC),
    HUAYI_LIGHT_WY("huayi.light.wy", "HUIZUO Ceiling Light", THING_TYPE_BASIC),
    HUAYI_LIGHT_WY200("huayi.light.wy200", "HUIZUO LIANGCHEN(BLE Mesh)", THING_TYPE_BASIC),
    HUAYI_LIGHT_WY201("huayi.light.wy201", "HUIZUO SAG Downlight (BLE Mesh)", THING_TYPE_BASIC),
    HUAYI_LIGHT_WY202("huayi.light.wy202", "HUIZUO Bulb (BLE Mesh)", THING_TYPE_BASIC),
    HUAYI_LIGHT_WY203("huayi.light.wy203", "HUIZUO YONG Downlight (BLE Mesh)", THING_TYPE_BASIC),
    HUAYI_LIGHT_WY204("huayi.light.wy204", "huayi.light.wy204", THING_TYPE_BASIC),
    HUAYI_LIGHT_WYHEAT("huayi.light.wyheat", "HUIZUO Heating Lamp", THING_TYPE_BASIC),
    HUAYI_LIGHT_ZW131("huayi.light.zw131", "HUIZUO ZIWEI Ceiling Lamp", THING_TYPE_BASIC),
    HUNMI_COOKER_NORMAL3("hunmi.cooker.normal3", "MiJia Rice Cooker", THING_TYPE_UNSUPPORTED),
    IDELAN_AIRCONDITION_V1("idelan.aircondition.v1", "Jinxing Smart Air Conditioner", THING_TYPE_UNSUPPORTED),
    IJAI_VACUUM_V19("ijai.vacuum.v19", "Xiaomi Robot Vacuum-Mop 2S", THING_TYPE_BASIC),
    IKEA_LIGHT_LED1545G12("ikea.light.led1545g12", "IKEA E27 white spectrum opal", THING_TYPE_LUMI),
    IKEA_LIGHT_LED1546G12("ikea.light.led1546g12", "IKEA E27 white spectrum clear", THING_TYPE_LUMI),
    IKEA_LIGHT_LED1536G5("ikea.light.led1536g5", "IKEA E14 white spectrum", THING_TYPE_LUMI),
    IKEA_LIGHT_LED1537R6("ikea.light.led1537r6", "IKEA GU10 white spectrum", THING_TYPE_LUMI),
    IKEA_LIGHT_LED1623G12("ikea.light.led1623g12", "IKEA E27 warm white", THING_TYPE_LUMI),
    IKEA_LIGHT_LED1650R5("ikea.light.led1650r5", "IKEA GU10 warm white", THING_TYPE_LUMI),
    IKEA_LIGHT_LED1649C5("ikea.light.led1649c5", "IKEA E14 warm white", THING_TYPE_LUMI),
    LUMI_CTRL_NEUTRAL1_V1("lumi.ctrl_neutral1.v1", "Aqara Wall Switch(No Neutral, Single Rocker)",
            THING_TYPE_UNSUPPORTED),
    LUMI_CTRL_NEUTRAL2_V1("lumi.ctrl_neutral2.v1", "Aqara Wall Switch (No Neutral, Double Rocker)",
            THING_TYPE_UNSUPPORTED),
    LUMI_CURTAIN_HAGL05("lumi.curtain.hagl05", "Xiaomiyoupin Curtain Controller (Wi-Fi)", THING_TYPE_BASIC),
    LUMI_GATEWAY_MGL03("lumi.gateway.mgl03", "Mi Air Purifier virtual", THING_TYPE_GATEWAY),
    LUMI_GATEWAY_MIEU01("lumi.gateway.mieu01", "Mi smart Home Gateway Hub", THING_TYPE_GATEWAY),
    LUMI_GATEWAY_V1("lumi.gateway.v1", "Mi smart Home Gateway Hub v1", THING_TYPE_GATEWAY),
    LUMI_GATEWAY_V2("lumi.gateway.v2", "Mi smart Home GatewayHub v2", THING_TYPE_GATEWAY),
    LUMI_GATEWAY_V3("lumi.gateway.v3", "Mi smart Home Gateway Hub v3", THING_TYPE_GATEWAY),
    LUMI_LIGHT_AQCN02("lumi.light.aqcn02", "Aqara LED Light Bulb (Tunable White)", THING_TYPE_LUMI),
    LUMI_LOCK_V1("lumi.lock.v1", "Door lock", THING_TYPE_LUMI),
    LUMI_LOCK_AQ1("lumi.lock.aq1", "Aqara Door Lock", THING_TYPE_LUMI),
    LUMI_LOCK_ACN02("lumi.lock.acn02", "Aqara Door Lock S2", THING_TYPE_LUMI),
    LUMI_LOCK_ACN03("lumi.lock.acn03", "Aqara Door lock S2 Pro", THING_TYPE_LUMI),
    LUMI_PLUG_MMEU01("lumi.plug.mmeu01", "Mi Smart Plug (Zigbee)", THING_TYPE_LUMI),
    LUMI_SENSOR_MAGNET_V2("lumi.sensor_magnet.v2", "Mi Window and Door Sensor", THING_TYPE_LUMI),
    LUMI_SENSOR_MOTION_AQ2("lumi.sensor_motion.aq2", "Mi Motion Sensor", THING_TYPE_LUMI),
    LUMI_SENSOR_MOTION_V2("lumi.sensor_motion.v2", "Mi Motion Sensor", THING_TYPE_LUMI),
    LUMI_SENSOR_HT_V1("lumi.sensor_ht.v1", "Mi Temperature and Humidity Sensor", THING_TYPE_LUMI),
    LUMI_SENSOR_WLEAK_AQ1("lumi.sensor_wleak.aq1", "Water Leak Sensor", THING_TYPE_LUMI),
    LUMI_WEATHER_V1("lumi.weather.v1", "Aqara Temperature and Humidity Sensor", THING_TYPE_LUMI),
    MIDEA_AIRCONDITION_V1("midea.aircondition.v1", "Midea AC-i Youth", THING_TYPE_UNSUPPORTED),
    MIDEA_AIRCONDITION_V2("midea.aircondition.v2", "Midea Air Conditioner v2", THING_TYPE_UNSUPPORTED),
    MIDEA_AIRCONDITION_XA1("midea.aircondition.xa1", "Midea AC-Cool Golden", THING_TYPE_UNSUPPORTED),
    MIJIA_VACUUM_V2("mijia.vacuum.v2", "Mi Robot Vacuum-Mop Essential", THING_TYPE_BASIC),
    MMGG_PET_WATERER_S1("mmgg.pet_waterer.s1", "Mijia Smart Pet Water Dispenser", THING_TYPE_BASIC),
    MMGG_PET_WATERER_S2("mmgg.pet_waterer.s2", "Mijia Smart Pet Water Dispenser", THING_TYPE_BASIC),
    MMGG_PET_WATERER_S3("mmgg.pet_waterer.s3", "Mijia Smart Pet Water Dispenser", THING_TYPE_BASIC),
    MMGG_PET_WATERER_S4("mmgg.pet_waterer.s4", "XIAOWAN Smart Pet Water Dispenser", THING_TYPE_BASIC),
    MRBOND_AIRER_M1PRO("mrbond.airer.m1pro", "MR.BOND", THING_TYPE_BASIC),
    MRBOND_AIRER_M1S("mrbond.airer.m1s", "MR.BOND", THING_TYPE_BASIC),
    MRBOND_AIRER_M1SUPER("mrbond.airer.m1super", "MR.BOND", THING_TYPE_BASIC),
    NWT_DERH_WDH318EFW1("nwt.derh.wdh318efw1", "WIDETECH WDH318EFW1 Internet Dehumidifier", THING_TYPE_BASIC),
    PHILIPS_LIGHT_BCEILING1("philips.light.bceiling1", "Philips Zhirui Ceiling Lamp Bedroom 40W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_BCEILING2("philips.light.bceiling2", "Philips Zhirui Ceiling Lamp Bedroom 28W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_BULB("philips.light.bulb", "Philips ZhiRui E27 bulb", THING_TYPE_BASIC),
    PHILIPS_LIGHT_CANDLE("philips.light.candle", "Philips ZhiRui E14 Candle Lamp Frosted version", THING_TYPE_BASIC),
    PHILIPS_LIGHT_CANDLE2("philips.light.candle2", "Philips ZhiRui E14 Candle Lamp Crystal version", THING_TYPE_BASIC),
    PHILIPS_LIGHT_CBULB("philips.light.cbulb", "Mijia Philips Color Bulb", THING_TYPE_BASIC),
    PHILIPS_LIGHT_CBULBS("philips.light.cbulbs", "Philips Light", THING_TYPE_BASIC),
    PHILIPS_LIGHT_CEILING("philips.light.ceiling", "Philips Connected Ceiling", THING_TYPE_BASIC),
    PHILIPS_LIGHT_DCOLOR("philips.light.dcolor", "Philips Light", THING_TYPE_BASIC),
    PHILIPS_LIGHT_DLIGHT("philips.light.dlight", "ZhiRui Dimmable Downlight", THING_TYPE_BASIC),
    PHILIPS_LIGHT_DOWNLIGHT("philips.light.downlight", "Philips ZhiRui Downlight", THING_TYPE_BASIC),
    PHILIPS_LIGHT_HBULB("philips.light.hbulb", "Philips Wi-Fi bulb E27 White", THING_TYPE_BASIC),
    PHILIPS_LIGHT_LNBLIGHT1("philips.light.lnblight1", "Philips ZhiYi Ceiling Lamp FL 40W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_LNBLIGHT2("philips.light.lnblight2", "Philips ZhiYi Ceiling Lamp FL 28W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_LNLRLIGHT("philips.light.lnlrlight", "Philips ZhiYi Ceiling Lamp FL 80W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_LRCEILING("philips.light.lrceiling", "Philips Zhirui Ceiling Lamp Living room 80W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_MCEIL("philips.light.mceil", "Zhirui Ceiling Lamp Nordic 80W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_MCEILM("philips.light.mceilm", "Zhirui Ceiling Lamp Nordic 40W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_MCEILS("philips.light.mceils", "Zhirui Ceiling Lamp Nordic 28W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_MONO1("philips.light.mono1", "Philips Smart Lamp", THING_TYPE_BASIC),
    PHILIPS_LIGHT_MOONLIGHT("philips.light.moonlight", "Philips ZhiRui Bedside Lamp", THING_TYPE_BASIC),
    PHILIPS_LIGHT_OBCEIL("philips.light.obceil", "Zhirui Ceiling Lamp Black 80W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_OBCEIM("philips.light.obceim", "Zhirui Ceiling Lamp Black 40W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_OBCEIS("philips.light.obceis", "Zhirui Ceiling Lamp Black 28W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_RWREAD("philips.light.rwread", "Mijia Philips Study Desk Lamp", THING_TYPE_BASIC),
    PHILIPS_LIGHT_SCEIL("philips.light.sceil", "Zhirui Ceiling Lamp Starry 80W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_SCEILM("philips.light.sceilm", "Zhirui Ceiling Lamp Starry 40W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_SCEILS("philips.light.sceils", "Zhirui Ceiling Lamp Starry 28W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_SREAD1("philips.light.sread1", "Philips EyeCare Connected Desk Lamp gen2.", THING_TYPE_BASIC),
    PHILIPS_LIGHT_SREAD2("philips.light.sread2", "Mijia Philips Desk Lamp 2S", THING_TYPE_BASIC),
    PHILIPS_LIGHT_VIRTUAL("philips.light.virtual", "Philips Connected Lights", THING_TYPE_BASIC),
    PHILIPS_LIGHT_XZCEIL("philips.light.xzceil", "Zhirui Ceiling Lamp Gorgeous 80W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_XZCEIM("philips.light.xzceim", "Zhirui Ceiling Lamp Gorgeous 40W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_XZCEIS("philips.light.xzceis", "Zhirui Ceiling Lamp Gorgeous 28W", THING_TYPE_BASIC),
    PHILIPS_LIGHT_ZYCEILING("philips.light.zyceiling", "Philips ZhiYi Ceiling lamp", THING_TYPE_BASIC),
    PHILIPS_LIGHT_ZYSREAD("philips.light.zysread", "Philips ZhiYi Desk Lamp", THING_TYPE_BASIC),
    PHILIPS_LIGHT_ZYSTRIP("philips.light.zystrip", "Philips ZhiYi Strip", THING_TYPE_BASIC),
    QMI_POWERSTRIP_V1("qmi.powerstrip.v1", "CHINGMI Smart Power Strip v1", THING_TYPE_BASIC),
    ROBOROCK_SWEEPER_E2V2("roborock.sweeper.e2v2", "Rockrobo Xiaowa Sweeper v2", THING_TYPE_UNSUPPORTED),
    ROBOROCK_SWEEPER_E2V3("roborock.sweeper.e2v3", "Rockrobo Xiaowa Sweeper v3", THING_TYPE_UNSUPPORTED),
    ROBOROCK_VACUUM_A08("roborock.vacuum.a08", "Roborock S6 Pure", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_A09("roborock.vacuum.a09", "Roborock T7 Pro", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_A10("roborock.vacuum.a10", "Roborock S6 MaxV", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_A11("roborock.vacuum.a11", "Roborock T7", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_A14("roborock.vacuum.a14", "Roborock T7S", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_A15("roborock.vacuum.a15", "Roborock S7", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_A19("roborock.vacuum.a19", "Roborock S4 Max", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_A23("roborock.vacuum.a23", "Roborock T7S Plus", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_C1("roborock.vacuum.c1", "Xiaowa C1", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_E2("roborock.vacuum.e2", "Roborock Xiaowa E Series Vacuum v2", THING_TYPE_UNSUPPORTED),
    ROBOROCK_VACUUM_M1S("roborock.vacuum.m1s", "Mi Robot Vacuum 1S", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_P5("roborock.vacuum.p5", "Roborock P5", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_S4("roborock.vacuum.s4", "Roborock S4", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_S4V2("roborock.vacuum.s4v2", "Roborock Vacuum S4v2", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_S5("roborock.vacuum.s5", "Roborock S5", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_S5E("roborock.vacuum.s5e", "Roborock S5 Max", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_S6("roborock.vacuum.s6", "Roborock S6", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T4("roborock.vacuum.t4", "Roborock T4", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T4V2("roborock.vacuum.t4v2", "Roborock Vacuum T4 v2", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T4V3("roborock.vacuum.t4v3", "Roborock Vacuum T4 v3", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T6("roborock.vacuum.t6", "Roborock T6", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T6V2("roborock.vacuum.t6v2", "Roborock Vacuum T6 v2", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T6V3("roborock.vacuum.t6v3", "Roborock Vacuum T6 v3", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T7("roborock.vacuum.t7", "Roborock Vacuum T7", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T7P("roborock.vacuum.t7p", "Roborock Vacuum T7p", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T7PV2("roborock.vacuum.t7pv2", "Roborock Vacuum T7 v2", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T7PV3("roborock.vacuum.t7pv3", "Roborock Vacuum T7 v3", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T7V2("roborock.vacuum.t7v2", "Roborock Vacuum T7 v2", THING_TYPE_VACUUM),
    ROBOROCK_VACUUM_T7V3("roborock.vacuum.t7v3", "Roborock Vacuum T7 v3", THING_TYPE_VACUUM),
    ROCKROBO_VACUUM_S6("rockrobo.vacuum.s6", "Roborock Vacuum S6", THING_TYPE_VACUUM),
    ROCKROBO_VACUUM_V1("rockrobo.vacuum.v1", "Mi Robot Vacuum", THING_TYPE_VACUUM),
    ROIDMI_VACUUM_V60("roidmi.vacuum.v60", "ROIDMI EVE vacuum", THING_TYPE_BASIC),
    S090615_SWITCH_XSWITCH01("090615.switch.xswitch01", "PTX OneKey Switch (WIFI)", THING_TYPE_BASIC),
    S090615_SWITCH_XSWITCH02("090615.switch.xswitch02", "PTX Twokey switch(wifi)", THING_TYPE_BASIC),
    S090615_SWITCH_XSWITCH03("090615.switch.xswitch03", "PTX ThreeKey Switch (WIFI)", THING_TYPE_BASIC),
    SCISHARE_COFFEE_S1102("scishare.coffee.s1102", "SCISHARE Smart Capsule Coffee Machine", THING_TYPE_BASIC),
    SCISHARE_COFFEE_S1301("scishare.coffee.s1301", "Xiaomi Scishare Smart Capsule Coffee Machine", THING_TYPE_BASIC),
    SOOCARE_TOOTHBRUSH_X3("soocare.toothbrush.x3", "Soocare Electric Toothbrush", THING_TYPE_UNSUPPORTED),
    VIOMI_FRIDGE_V3("viomi.fridge.v3", "Viomi Internet Refrigerator iLive(French style 462L)", THING_TYPE_UNSUPPORTED),
    VIOMI_VACUUM_V6("viomi.vacuum.v6", "Viomi Cleaning Robot V-RVCLM21B", THING_TYPE_BASIC),
    VIOMI_VACUUM_V7("viomi.vacuum.v7", "Mi Robot Vacuum-Mop P", THING_TYPE_BASIC),
    VIOMI_VACUUM_V8("viomi.vacuum.v8", "Mi Robot Vacuum-Mop P", THING_TYPE_BASIC),
    VIOMI_VACUUM_V18("viomi.vacuum.v18", "Viomi S9", THING_TYPE_BASIC),
    VIOMI_WATERHEATER_E1("viomi.waterheater.e1", "VIOMI Internet Electric Water Heater 1A (60L)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MA1("xiaomi.aircondition.ma1", "Mi Inverter Air Conditioner (1.5HP)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MA2("xiaomi.aircondition.ma2",
            "Mi Inverter Air Conditioner (1.5HP, China Energy Label Level 1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MA4("xiaomi.aircondition.ma4", "Mi Vertical Air Conditioner (2HP)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MA5("xiaomi.aircondition.ma5",
            "Mi Smart Vertical Air Conditioner C1 (2HP / Inverter / China Energy Label Level 1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MA6("xiaomi.aircondition.ma6",
            "Mi Smart Air Conditioner C1 (1.5HP / Conventional / China Energy Label Level 3)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MA9("xiaomi.aircondition.ma9",
            "Mi Smart Air Conditioner C1 (1HP / Inverter / China Energy Label Level 1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MC1("xiaomi.aircondition.mc1",
            "Mi Smart Air Conditioner A (1HP / Inverter / China Energy Label Level 1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MC2("xiaomi.aircondition.mc2",
            "Mi Smart Air Conditioner A (1.5HP / Inverter / China Energy Label Level 1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MC4("xiaomi.aircondition.mc4",
            "Mi Smart Air Conditioner A (1HP / Inverter / China Energy Label Level <1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MC5("xiaomi.aircondition.mc5",
            "Mi Smart Air Conditioner A (1.5HP / Inverter / China Energy Label Level <1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MC6("xiaomi.aircondition.mc6",
            "Mi Smart Vertical Air Conditioner A (2HP / Inverter / China Energy Label Level <1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MC7("xiaomi.aircondition.mc7",
            "Mi Smart Vertical Air Conditioner A (3HP / Inverter / China Energy Label Level <1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MC8("xiaomi.aircondition.mc8",
            "Mi Smart Ultra Electricity Saving Air Conditioner(1.5HP/Inverter/New China Energy Label Level 3)",
            THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MC9("xiaomi.aircondition.mc9",
            "Mi Smart Ultra Electricity Saving Vertical Air Conditioner(2HP/Inverter/New China Energy Label Level 3)",
            THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_C10("xiaomi.aircondition.c10",
            "Mi Smart Ultra Electricity Saving Vertical Air Conditioner (2HP/Inverter/New China Energy Label Level 1)",
            THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_C11("xiaomi.aircondition.c11",
            "Mi Smart Ultra Electricity Saving Vertical Air Conditioner (3HP/Inverter/New China Energy Label Level 1)",
            THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MH1("xiaomi.aircondition.mh1",
            "Mi Smart Air Conditioner C (1HP / Inverter / New China Energy Label Level 1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MH2("xiaomi.aircondition.mh2",
            "Mi Smart Air Conditioner C (1.5HP / Inverter / New China Energy Label Level 1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MH3("xiaomi.aircondition.mh3",
            "Mi Smart Ultra Electricity Saving Air Conditioner(1HP/Inverter/New China Energy Label Level 3)",
            THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MT1("xiaomi.aircondition.mt1",
            "Mi Smart Air Conditioner X (1HP / Inverter / New China Energy Label Level 1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MT2("xiaomi.aircondition.mt2",
            "Mi Smart Air Conditioner X (1.5HP / Inverter / New China Energy Label Level 1)", THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MT3("xiaomi.aircondition.mt3",
            "Mi Smart Gentle Breeze Air Conditioner (1HP / Inverter / New China Energy Label Level 1)",
            THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MT4("xiaomi.aircondition.mt4",
            "Mi Smart Gentle Breeze Air Conditioner (1.5HP / Inverter / New China Energy Label Level 1)",
            THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MT5("xiaomi.aircondition.mt5",
            "Mi Smart Gentle Breeze Vertical Air Conditioner (3HP / Inverter / New China Energy Label Level 1)",
            THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MT7("xiaomi.aircondition.mt7",
            "Mi Smart Ultra Electricity Saving Air Conditioner (1HP/Inverter/New China Energy Label Level 1)",
            THING_TYPE_BASIC),
    XIAOMI_AIRCONDITION_MT8("xiaomi.aircondition.mt8",
            "Mi Smart Ultra Electricity Saving Air Conditioner (1.5HP/Inverter/New China Energy Label Level 1)",
            THING_TYPE_BASIC),
    XIAOMI_REPEATER_V2("xiaomi.repeater.v2", "Mi Wi-Fi Repeater 2", THING_TYPE_UNSUPPORTED),
    XIAOMI_WIFISPEAKER_V1("xiaomi.wifispeaker.v1", "Mi Network Speaker", THING_TYPE_UNSUPPORTED),
    XJX_TOILET_PRO("xjx.toilet.pro", "Uclean Smart Toilet Seat", THING_TYPE_BASIC),
    XJX_TOILET_PURE("xjx.toilet.pure", "Uclean Smart Toilet pure", THING_TYPE_BASIC),
    XJX_TOILET_RELAX("xjx.toilet.relax", "Uclean Smart Toilet relax", THING_TYPE_BASIC),
    XJX_TOILET_ZERO("xjx.toilet.zero", "Whale Spout Smart Toilet Zero", THING_TYPE_BASIC),
    YEELIGHT_BHF_LIGHT_V2("yeelight.bhf_light.v2", "Yeelight Smart Bath Heater", THING_TYPE_UNSUPPORTED),
    YEELINK_BHF_LIGHT_V1("yeelink.bhf_light.v1", "Yeelight Smart Bath Heater Pro", THING_TYPE_BASIC),
    YEELINK_BHF_LIGHT_V2("yeelink.bhf_light.v2", "Yeelight Smart Bath Heater", THING_TYPE_BASIC),
    YEELINK_LIGHT_BSLAMP1("yeelink.light.bslamp1", "Mi Bedside Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_BSLAMP2("yeelink.light.bslamp2", "Mi Bedside Lamp 2", THING_TYPE_BASIC),
    YEELINK_LIGHT_BSLAMP3("yeelink.light.bslamp3", "Yeelight Bedside Lamp II", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING1("yeelink.light.ceiling1", "Yeelight Ceiling Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING2("yeelink.light.ceiling2", "Yeelight Ceiling Light SE", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING3("yeelink.light.ceiling3", "Yeelight LED Ceiling Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING4("yeelink.light.ceiling4", "Yeelight LED Ceiling Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING4_AMBI("yeelink.light.ceiling4.ambi", "Yeelight LED Ceiling Ambi Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING5("yeelink.light.ceiling5", "Mi LED Ceiling Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING6("yeelink.light.ceiling6", "Yeelight HaoShi LED Ceiling Lamp Pro", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING7("yeelink.light.ceiling7", "Yeelight Haoshi Ceiling Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING8("yeelink.light.ceiling8", "LED Ceiling Light Crystal Plus", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING9("yeelink.light.ceiling9", "Yeelight HaoShi LED Ceiling Lamp Pro", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING10("yeelink.light.ceiling10", "Yeelight Crystal Pendant Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING10_AMBI("yeelink.light.ceiling10.ambi", "Yeelight LED Ceiling Ambi Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING11("yeelink.light.ceiling11", "Yeelight Ceiling Light 320 1S", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING12("yeelink.light.ceiling12", "Yeelight Stylized Ceiling Light  Pro", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING13("yeelink.light.ceiling13", "Yeelight Ceiling Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING14("yeelink.light.ceiling14", "Yeelight Ceiling Light Mini", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING15("yeelink.light.ceiling15", "Yeelight Ceiling Light 480 1S", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING16("yeelink.light.ceiling16", "Yeelight Xingyu Ceiling Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING17("yeelink.light.ceiling17", "Yeelight ShaoHua Celing Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING18("yeelink.light.ceiling18", "Yeelight Ceiling Light Pro", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING19("yeelink.light.ceiling19", "Yeelight Ceiling Light Pro", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING19_AMBI("yeelink.light.ceiling19.ambi", "Yeelight LED Ceiling Ambi Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING20("yeelink.light.ceiling20", "Yeelight Ceiling Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING20_AMBI("yeelink.light.ceiling20.ambi", "Yeelight LED Ceiling Ambi Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING21("yeelink.light.ceiling21", "Mi Smart LED Living Room Ceiling Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING22("yeelink.light.ceiling22", "Mi Smart LED Ceiling Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEILING23("yeelink.light.ceiling23", "Mi Smart LED Ceiling Light (350mm)", THING_TYPE_BASIC),
    YEELINK_LIGHT_CEIL26("yeelink.light.ceil26", "Yeelight Jade Smart LED Ceiling Light C2001", THING_TYPE_BASIC),
    YEELINK_LIGHT_COLOR1("yeelink.light.color1", "Yeelight Color Bulb", THING_TYPE_BASIC),
    YEELINK_LIGHT_COLOR2("yeelink.light.color2", "Yeelight LED Bulb (Color)", THING_TYPE_BASIC),
    YEELINK_LIGHT_COLOR3("yeelink.light.color3", "Mi LED Smart Bulb (White and Color)", THING_TYPE_BASIC),
    YEELINK_LIGHT_COLOR4("yeelink.light.color4", "Yeelight LED Bulb 1S（Color）", THING_TYPE_BASIC),
    YEELINK_LIGHT_COLOR5("yeelink.light.color5", "Mi Smart LED Bulb Essential (White and Color)", THING_TYPE_BASIC),
    YEELINK_LIGHT_COLORA("yeelink.light.colora", "Yeelight Smart LED Bulb 1SE (color)", THING_TYPE_BASIC),
    YEELINK_LIGHT_CT2("yeelink.light.ct2", "Yeelight LED Bulb (Tunable)", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP1("yeelink.light.lamp1", "Mi LED Desk Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP2("yeelink.light.lamp2", "Mi Smart LED Desk Lamp Pro", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP3("yeelink.light.lamp3", "Yeelight LED Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP4("yeelink.light.lamp4", "Mi LED Desk Lamp 1S", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP5("yeelink.light.lamp5", "Yeelight Smart Desk Lamp Prime", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP6("yeelink.light.lamp6", "Yeelight", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP7("yeelink.light.lamp7", "Yeelight LED Light Sensor Desk Lamp V1", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP8("yeelink.light.lamp8", "Yeelight", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP9("yeelink.light.lamp9", "Yeelight Star LED Table Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP10("yeelink.light.lamp10", "Yeelight Star Floor Lamp", THING_TYPE_BASIC),
    YEELINK_LIGHT_LAMP15("yeelink.light.lamp15", "Yeelight Screen Light Bar", THING_TYPE_BASIC),
    YEELINK_LIGHT_MONO1("yeelink.light.mono1", "Yeelight Bulb", THING_TYPE_BASIC),
    YEELINK_LIGHT_MONO2("yeelink.light.mono2", "Yeelight White Bulb v2", THING_TYPE_BASIC),
    YEELINK_LIGHT_MONO4("yeelink.light.mono4", "Yeelight LED Bulb 1S（Dimmable）", THING_TYPE_BASIC),
    YEELINK_LIGHT_MONO5("yeelink.light.mono5", "Yeelight LED Filament Bulb", THING_TYPE_BASIC),
    YEELINK_LIGHT_MONO6("yeelink.light.mono6", "Mi Smart LED Bulb", THING_TYPE_BASIC),
    YEELINK_LIGHT_MONOA("yeelink.light.monoa", "Yeelight LED smart bulb W3(dimmable)", THING_TYPE_BASIC),
    YEELINK_LIGHT_MONOB("yeelink.light.monob", "Yeelight GU10 Smart Bulb W1(dimmable)", THING_TYPE_BASIC),
    YEELINK_LIGHT_PANEL1("yeelink.light.panel1", "Yeelight Whiteglow Panel Light", THING_TYPE_BASIC),
    YEELINK_LIGHT_STRIP1("yeelink.light.strip1", "Yeelight Lightstrip", THING_TYPE_BASIC),
    YEELINK_LIGHT_STRIP2("yeelink.light.strip2", "Yeelight Lightstrip Plus", THING_TYPE_BASIC),
    YEELINK_LIGHT_STRIP4("yeelink.light.strip4", "Yeelight Willow LED Lightstrip", THING_TYPE_BASIC),
    YEELINK_LIGHT_VIRTUAL("yeelink.light.virtual", "Light Group (Mi & Yeelight)", THING_TYPE_BASIC),
    YEELINK_SWITCH_SW1("yeelink.switch.sw1", "Yeelight Smart Dual Control Module", THING_TYPE_BASIC),
    YEELINK_WIFISPEAKER_V1("yeelink.wifispeaker.v1", "Yeelight Smart Speaker", THING_TYPE_UNSUPPORTED),
    YILAI_LIGHT_CEILING1("yilai.light.ceiling1", "Yilai Ceiling Light Aiyue 480", THING_TYPE_BASIC),
    YILAI_LIGHT_CEILING2("yilai.light.ceiling2", "Yilai Ceiling Lamp Hefeng 430", THING_TYPE_BASIC),
    YILAI_LIGHT_CEILING3("yilai.light.ceiling3", "Yilai Ceiling Lamp Hefeng Pro", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX2("yunmi.waterpuri.lx2", "Mi Water Purifier lx2", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX3("yunmi.waterpuri.lx3", "Mi Water Purifier (Under Counter)", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX4("yunmi.waterpuri.lx4", "Mi Water Purifier lx4", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX5("yunmi.waterpuri.lx5", "Mi Water Purifier 1A/400G Pro", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX6("yunmi.waterpuri.lx6", "Mi Water Purifier (Under Counter)", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX7("yunmi.waterpuri.lx7", "Mi Water Purifier 500G/500G Pro", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX8("yunmi.waterpuri.lx8", "Mi Water Purifier 600G", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX9("yunmi.waterpuri.lx9", "Mi Water Purifier D1", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX10("yunmi.waterpuri.lx10", "Mi Water Purifier lx10", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX11("yunmi.waterpuri.lx11", "Mi Water Purifier C1 (Triple Setting)", THING_TYPE_BASIC),
    YUNMI_WATERPURI_LX12("yunmi.waterpuri.lx12", "Mi Water Purifier S1", THING_TYPE_BASIC),
    YUNMI_WATERPURIFIER_V1("yunmi.waterpurifier.v1", "Mi Water Purifier v1", THING_TYPE_BASIC),
    YUNMI_WATERPURIFIER_V2("yunmi.waterpurifier.v2", "Mi Water Purifier v2", THING_TYPE_BASIC),
    YUNMI_WATERPURIFIER_V3("yunmi.waterpurifier.v3", "Mi Water Purifier (Under sink) v3", THING_TYPE_BASIC),
    YUNMI_WATERPURIFIER_V4("yunmi.waterpurifier.v4", "Mi Water Purifier v4", THING_TYPE_BASIC),
    ZHIMI_AIRFRESH_VA2("zhimi.airfresh.va2", "Smartmi Ventilation System", THING_TYPE_BASIC),
    ZHIMI_AIRFRESH_VA4("zhimi.airfresh.va4", "Smartmi Fresh Air System (Heating)", THING_TYPE_BASIC),
    ZHIMI_AIRFRESH_UA1("zhimi.airfresh.ua1", "Mi Fresh Air Ventilator C1-80", THING_TYPE_BASIC),
    ZHIMI_AIRMONITOR_V1("zhimi.airmonitor.v1", "Mi PM2.5 Air Quality Monitor", THING_TYPE_BASIC),
    ZHIMI_AIRP_MB4A("zhimi.airp.mb4a", "Mi Air Purifier 3C", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_M1("zhimi.airpurifier.m1", "Mi Air Purifier 2 (mini)", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_M2("zhimi.airpurifier.m2", "Mi Air Purifier 2", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_MA1("zhimi.airpurifier.ma1", "Mi Air Purifier 2S", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_MA2("zhimi.airpurifier.ma2", "Mi Air Purifier 2S", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_MA4("zhimi.airpurifier.ma4", "Mi Air Purifier 3", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_MB1("zhimi.airpurifier.mb1", "Mi Air Purifier 2S", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_MB3("zhimi.airpurifier.mb3", "Mi Air Purifier 3/3H", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_MB4("zhimi.airpurifier.mb4", "Mi Air Purifier 3C", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_MC1("zhimi.airpurifier.mc1", "Mi Air Purifier 2S", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_MC2("zhimi.airpurifier.mc2", "Mi Air Purifier 2H", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_SA1("zhimi.airpurifier.sa1", "Mi Air Purifier Super", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_SA2("zhimi.airpurifier.sa2", "Mi Air Purifier MAX / MAX Pro", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_V1("zhimi.airpurifier.v1", "Mi Air Purifier v1", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_V2("zhimi.airpurifier.v2", "Mi Air Purifier v2", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_V3("zhimi.airpurifier.v3", "Mi Air Purifier v3", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_V5("zhimi.airpurifier.v5", "Mi Air Purifier v5", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_V6("zhimi.airpurifier.v6", "Mi Air Purifier Pro v6", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_V7("zhimi.airpurifier.v7", "Mi Air Purifier Pro v7", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_VB2("zhimi.airpurifier.vb2", "Mi Air Purifier Pro H", THING_TYPE_BASIC),
    ZHIMI_AIRPURIFIER_VIRTUAL("zhimi.airpurifier.virtual", "Mi Air Purifier virtual", THING_TYPE_UNSUPPORTED),
    ZHIMI_AIRPURIFIER_VTL_M1("zhimi.airpurifier.vtl_m1", "Mi Air Purifier 2(Virtual)", THING_TYPE_UNSUPPORTED),
    ZHIMI_AIRPURIFIER_ZA1("zhimi.airpurifier.za1", "Smartmi Air Purifier", THING_TYPE_BASIC),
    ZHIMI_FAN_SA1("zhimi.fan.sa1", "Mi Standing Fan", THING_TYPE_BASIC),
    ZHIMI_FAN_V1("zhimi.fan.v1", "Mi Smart Fan", THING_TYPE_BASIC),
    ZHIMI_FAN_V2("zhimi.fan.v2", "Smartmi DC Pedestal Fan", THING_TYPE_BASIC),
    ZHIMI_FAN_V3("zhimi.fan.v3", "Smartmi DC Pedestal Fan", THING_TYPE_BASIC),
    ZHIMI_FAN_ZA1("zhimi.fan.za1", "Smartmi Inverter Pedestal Fan", THING_TYPE_BASIC),
    ZHIMI_FAN_ZA3("zhimi.fan.za3", "Smartmi Standing Fan 2", THING_TYPE_BASIC),
    ZHIMI_FAN_ZA4("zhimi.fan.za4", "Smartmi Standing Fan 2S", THING_TYPE_BASIC),
    ZHIMI_FAN_ZA5("zhimi.fan.za5", "Smartmi Standing Fan 3 ", THING_TYPE_BASIC),
    ZHIMI_HEATER_MA2("zhimi.heater.ma2", "Mi Smart Space Heater S", THING_TYPE_BASIC),
    ZHIMI_HEATER_MA3("zhimi.heater.ma3", "Mi Smart Baseboard Heater E", THING_TYPE_BASIC),
    ZHIMI_HEATER_MC2("zhimi.heater.mc2", "Mi Smart Space Heater S", THING_TYPE_BASIC),
    ZHIMI_HEATER_NA1("zhimi.heater.na1", "Smartmi Smart Fan", THING_TYPE_BASIC),
    ZHIMI_HEATER_NB1("zhimi.heater.nb1", "Smartmi Smart Fan Heater", THING_TYPE_BASIC),
    ZHIMI_HEATER_ZA1("zhimi.heater.za1", "Smartmi Radiant Heater Smart Version", THING_TYPE_BASIC),
    ZHIMI_HEATER_ZA2("zhimi.heater.za2", "Smartmi Smart Convector Heater 1S", THING_TYPE_BASIC),
    ZHIMI_HEATER_ZB1("zhimi.heater.zb1", "Smartmi Smart Convector Heater 1S", THING_TYPE_BASIC),
    ZHIMI_HUMIDIFIER_CA1("zhimi.humidifier.ca1", "Smartmi Evaporative Humidifier", THING_TYPE_BASIC),
    ZHIMI_HUMIDIFIER_CA4("zhimi.humidifier.ca4", "Smartmi Evaporative Humidifer 2", THING_TYPE_BASIC),
    ZHIMI_HUMIDIFIER_CB1("zhimi.humidifier.cb1", "Smartmi Evaporative Humidifier", THING_TYPE_BASIC),
    ZHIMI_HUMIDIFIER_CB2("zhimi.humidifier.cb2", "Smartmi Evaporative Humidifier", THING_TYPE_BASIC),
    ZHIMI_HUMIDIFIER_V1("zhimi.humidifier.v1", "Smartmi Humidifier", THING_TYPE_BASIC),
    ZIMI_CLOCK_MYK01("zimi.clock.myk01", "Mi AI Alarm", THING_TYPE_UNSUPPORTED),
    ZIMI_POWERSTRIP_V2("zimi.powerstrip.v2", "Mi Smart Power Strip", THING_TYPE_BASIC),
    UNKNOWN("unknown", "Unknown Mi IO Device", THING_TYPE_UNSUPPORTED);

    public static MiIoDevices getType(String modelString) {
        for (MiIoDevices mioDev : MiIoDevices.values()) {
            if (mioDev.getModel().equalsIgnoreCase(modelString)) {
                return mioDev;
            }
        }
        return UNKNOWN;
    }

    private final String description;
    private final String model;

    private final ThingTypeUID thingType;

    MiIoDevices(String model, String description, ThingTypeUID thingType) {
        this.model = model;
        this.description = description;
        this.thingType = thingType;
    }

    public String getDescription() {
        return description;
    }

    public String getModel() {
        return model;
    }

    public ThingTypeUID getThingType() {
        return thingType;
    }

    @Override
    public String toString() {
        return description + " (" + model + ")";
    }
}
