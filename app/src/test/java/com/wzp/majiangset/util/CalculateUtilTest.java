package com.wzp.majiangset.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wzp on 2018/1/28.
 */
public class CalculateUtilTest {

    @Test
    public void analyseMessage() throws Exception {
        byte[] byteArr = new byte[] {(byte) 0xfe, (byte) 0xa5, (byte) 0x01, 0x01, 0x00, 0x01, 0x00, 0x55, 0x61, 0x00, (byte) 0x8a, (byte) 0x90, (byte) 0x9f, 0x04, 0x0a, 0x14, 0x00, 0x55, 0x61, 0x00, (byte) 0x8a, (byte) 0x90, (byte) 0x9f, 0x04, 0x0a, 0x14, 0x00, 0x55, 0x61, 0x00, (byte) 0x8a, (byte) 0x90, (byte) 0x9f, (byte) 0x9f, (byte) 0x9f, (byte) 0x9f, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

        System.out.println(byteToString(byteArr));
        CalculateUtil.analyseMessage(byteArr);
        System.out.println(byteToString(byteArr));

        String str = null;
        List<String> list = new ArrayList<>();
        str = new String("abc");
        list.add(str);
        str = new String("hahah");
        list.add(str);
        str = new String("welcome");
        list.add(str);

        System.out.println(list);
    }

    @Test
    public void analyseMessage2() throws Exception {
        String str = "fe a4 01 02 01 00 01 00 27 ea 8a 79 9f d1 0a 7d 00 6e 61 bf 8a 79 9f d1 0a 6d 08 03 55 bb 8a 79 9f d5 00 3b 00 3b 00 3b 00 00 00";
        String[] strArr = str.split(" ");
//        strArr[25] = "04";
//        strArr[26] = "08";
//        strArr[27] = "0c";
        byte[] byteArr = new byte[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            byteArr[i] = Integer.valueOf(strArr[i], 16).byteValue();
        }

        System.out.println(byteToString(byteArr));
        CalculateUtil.analyseMessage(byteArr);
        System.out.println(byteToString(byteArr));
    }

    private String byteToString(byte[] byteArr) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < byteArr.length; i++) {
            res.append(String.format("%02x", byteArr[i]));
            res.append(" ");
        }
        return res.toString();
    }

    @Test
    public void testParseDistrictCode() {
        int[] arr = parseDistrictCode("000000");
        System.out.println(Arrays.toString(arr));

        byte[] msgArr = new byte[45];
        int[] districtCodeArr = parseDistrictCode("350203");
        msgArr[0] = (byte) 0xfe;
        msgArr[1] = (byte) 0xa9;
        if (districtCodeArr != null) {
            msgArr[2] = (byte) districtCodeArr[0];
            msgArr[3] = (byte) districtCodeArr[1];
            msgArr[4] = (byte) districtCodeArr[2];
        } else {
            msgArr[2] = (byte) 0xff;
            msgArr[3] = (byte) 0xff;
            msgArr[4] = (byte) 0xff;
        }
//        CalculateUtil.analyseMessage(msgArr);
//        CRC16.check(msgArr);
        System.out.println(Arrays.toString(msgArr));
    }

    private int[] parseDistrictCode(String districtCode) {
        if (districtCode == null) {
            return null;
        }
        int dCode = Integer.parseInt(districtCode);
        int[] resArr = new int[3];
        resArr[0] = dCode / 10000;
        resArr[1] = dCode % 10000 / 100;
        resArr[2] = dCode % 100;
        return resArr;
    }
}