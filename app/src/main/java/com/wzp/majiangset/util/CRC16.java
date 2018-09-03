package com.wzp.majiangset.util;

import java.util.Arrays;

/**
 * CRC16
 * 
 * @author Zippen
 * @date 2016/8/30
 */
public class CRC16 {
	
	/**
	 * 获取CRC16校验值
	 * 
	 * @param data 待校验的字节数组
	 * @param len 待校验的数组中元素的个数
	 * @return
	 */
	public static byte[] getCrc16(byte[] data, int len) {
		char crcReg = 0xffff;					// crc寄存器
		final char A001 = 0xA001;				// CRC16对应的二项式A001
		int moveOutBit;							// 右移操作的移出位
		
		for (int i = 0; i < len; i++) {
			// 要先把data[i]转化为int类型（目的是为了类型转换时能得到无符号值）
			crcReg ^= (data[i] & 0xff);
			
			/*
			 * 查表法就是把以下移位操作的结果事先算出并存储起来
			 */
			for (int j = 0; j < 8; j++) {
				// 取出crc寄存器中的最后一位
				moveOutBit = crcReg & 0x01;
				// crc寄存器右移一位
				crcReg >>= 1;
				if (1 == moveOutBit) {
					crcReg ^= A001;
				}
			}
		}

		/*
		 * 将16位crc校验结果拆分为高位字节和低位字节
		 */
		byte highByte = (byte) ((crcReg & 0xFF00) >> 8);
		byte lowByte = (byte) (crcReg & 0x00FF);

		return new byte[] {lowByte, highByte};
	}
	
	/**
	 * 获取CRC16校验值（默认对整个数组进行校验）
	 * 
	 * @param data 待校验的字节数组
	 * @return
	 */
	public static byte[] getCrc16(byte[] data) {
		return getCrc16(data, data.length);
	}
	
	/**
	 * 获取CRC16校验值
	 * 
	 * @param data 待校验的字节数组
	 * @param len 待校验的数组中元素的个数
	 * @param crc 存放crc校验结果
	 */
	public static void getCrc16(final byte[] data, int len, final byte[] crc) {
		if (crc == null || crc.length != 2) {
			throw new IllegalArgumentException("crc数组不能为null，且只能包含两个元素");
		}
		
		char crcReg = 0xffff;					// crc寄存器
		final char A001 = 0xA001;				// CRC16对应的二项式A001
		int moveOutBit;							// 右移操作的移出位
		
		for (int i = 0; i < len; i++) {
			// 要先把data[i]转化为int类型（目的是为了类型转换时能得到无符号值）
			crcReg ^= (data[i] & 0xff);
			
			/*
			 * 查表法就是把以下移位操作的结果事先算出并存储起来
			 */
			for (int j = 0; j < 8; j++) {
				// 取出crc寄存器中的最后一位
				moveOutBit = crcReg & 0x01;
				// crc寄存器右移一位
				crcReg >>= 1;
				if (1 == moveOutBit) {
					crcReg ^= A001;
				}
			}
		}

		/*
		 * 将16位crc校验结果拆分为高位字节和低位字节
		 */
		byte highByte = (byte) ((crcReg & 0xFF00) >> 8);
		byte lowByte = (byte) (crcReg & 0x00FF);
		
		Arrays.fill(crc, (byte) 0);
		crc[0] = lowByte;
		crc[1] = highByte;
	}
	
	/**
	 * 获取CRC16校验值（默认对整个数组进行校验）
	 * 
	 * @param data 待校验的字节数组
	 * @param crc 存放crc校验结果
	 */
	public static void getCrc16(byte[] data, byte[] crc) {
		getCrc16(data, data.length, crc);
	}

	/**
	 * 对字节数组进行CRC校验，并将校验结果放在数组的最后两个元素
	 *
	 * @param data 待校验的数组
	 */
	public static void check(byte[] data) {
		int length = data.length;
		byte[] crc = CRC16.getCrc16(data, length - 2);
		data[length - 2] = crc[0];
		data[length - 1] = crc[1];
	}
}
