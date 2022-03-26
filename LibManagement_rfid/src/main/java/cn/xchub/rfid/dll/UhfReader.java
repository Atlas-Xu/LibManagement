package cn.xchub.rfid.dll;

import java.util.Arrays;

public class UhfReader {
    public UhfReader() {
        System.loadLibrary("UHF_Reader18");
    }

    public static UhfReader init() {
        return new UhfReader();
    }

    private final Reader18 tnt = new Reader18();

    private int[] rec = new int[5000];
    private int[] sendBuff = new int[5000];
    private int result = 0x30;
    private int frmHandle = 0;
    private int fComAddr = 0;

    private void setZero(int[] array) {
        Arrays.fill(array, 0);
    }

    /**
     * 打开设备
     *
     * @param comPort  串口号
     * @param comAddr  读写器地址
     * @param baudRate 波特率
     */
    public int openByCom(int comPort, byte comAddr, byte baudRate) {
        setZero(sendBuff);
        sendBuff[0] = comPort;
        sendBuff[1] = comAddr;
        sendBuff[2] = baudRate;
        rec = tnt.OpenComPort(sendBuff);
        if (rec[0] == 0) {
            result = 0;
            fComAddr = rec[1];
            frmHandle = rec[2];
        } else {
            result = rec[0];
            fComAddr = 255;
            frmHandle = -1;
        }
        return result;
    }

    /**
     * 自动打开串口
     */
    public int autoOpenCom(byte ComAddr, byte baudRate) {
        setZero(sendBuff);
        sendBuff[0] = ComAddr;
        sendBuff[1] = baudRate;
        rec = tnt.AutoOpenComPort(sendBuff);
        if (rec[0] == 0) {
            result = 0;
            fComAddr = rec[2];
            frmHandle = rec[3];
        } else {
            result = rec[0];
            fComAddr = 255;
            frmHandle = -1;
        }
        return result;
    }

    /**
     * 关闭设备,FrmHandle设备句柄;
     */
    public int closeByCom(int FrmHandle) {
        return tnt.CloseSpecComPort(FrmHandle);
    }

    public int openByTcp(String Ipaddr, int Port) {
        setZero(sendBuff);
        rec = tnt.OpenNetPort(255, Port, Ipaddr);
        if (rec[0] == 0) {
            result = 0;
            fComAddr = rec[1];
            frmHandle = rec[2];
        } else {
            result = rec[0];
            fComAddr = 255;
            frmHandle = -1;
        }
        return result;
    }

    public int closeByTcp(int FrmHandle) {
        return tnt.CloseNetPort(FrmHandle);
    }

    public String byteToString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte value : b) {
            String temp = Integer.toHexString(value & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            sb.append(temp);
        }
        return sb.toString().toUpperCase();
    }

    public byte[] stringToByte(String str) {
        byte[] b = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            b[i] = (byte) (0xff & Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
        }
        return b;
    }

    /**
     * 询查标签
     */
    public String[] inventory() {
        int cardNum;
        setZero(sendBuff);
        sendBuff[0] = fComAddr;
        sendBuff[1] = frmHandle;
        setZero(rec);
        rec = tnt.Inventory_G2(sendBuff);
        if ((rec[0] != 0x30) && (rec[1] > 0)) {
            cardNum = rec[1];
            String[] EPC = new String[cardNum];
            int index = 0;
            for (int m = 0; m < cardNum; m++) {
                int len = rec[3 + index];
                byte[] epc_arr = new byte[len];
                for (int n = 0; n < len; n++) {
                    epc_arr[n] = (byte) rec[4 + index + n];
                }
                EPC[m] = byteToString(epc_arr);
                index += len + 1;
            }
            return EPC;
        }
        return null;
    }

    public void getWorkModeParameter() {
        setZero(sendBuff);
        sendBuff[0] = fComAddr;
        sendBuff[1] = frmHandle;
        setZero(rec);
        rec = tnt.GetWorkModeParameter(sendBuff);
        System.out.println(Arrays.toString(rec));
    }

    public void ReadActiveModeData() {
        setZero(sendBuff);
        sendBuff[0] = fComAddr;
        sendBuff[1] = frmHandle;
        setZero(rec);
        rec = tnt.ReadActiveModeData(sendBuff);
        System.out.println(Arrays.toString(rec));
    }


    /**
     * 设置功率
     *
     * @param power 功率
     */
    public int setPower(byte power) {
        setZero(sendBuff);
        sendBuff[0] = fComAddr;
        sendBuff[1] = power;
        sendBuff[2] = frmHandle;
        return tnt.SetPowerDbm(sendBuff);
    }


    /**
     * 设置读写器地址
     */
    public int setAddress(byte address) {
        setZero(sendBuff);
        sendBuff[0] = fComAddr;
        sendBuff[1] = address;
        sendBuff[2] = frmHandle;
        return tnt.WriteComAdr(sendBuff);
    }


    /**
     * 设置读写器工作频率
     */
    public int setRegion(byte maxFre, byte minFre) {
        setZero(sendBuff);
        sendBuff[0] = fComAddr;
        sendBuff[1] = maxFre;
        sendBuff[2] = minFre;
        sendBuff[3] = frmHandle;
        return tnt.Writedfre(sendBuff);
    }


    /**
     * 设置读写器波特率
     */
    public int setBaudRate(byte baudRate) {
        setZero(sendBuff);
        sendBuff[0] = fComAddr;
        sendBuff[1] = baudRate;
        sendBuff[2] = frmHandle;
        return tnt.Writebaud(sendBuff);
    }


    /**
     * 读数据
     *
     * @param epc     EPC号
     * @param wordPtr 读取地址
     * @param num     读取长度
     * @param mem     读取区域
     * @param psd     访问密码
     */
    public String readData(String epc, byte wordPtr, byte num, byte mem, byte[] psd) {
        int len = epc.length() / 2;
        byte[] epc_arr = new byte[len];
        epc_arr = stringToByte(epc);
        setZero(sendBuff);
        setZero(rec);
        sendBuff[0] = fComAddr;
        sendBuff[1] = len;//EPC字节长度
        for (int n = 0; n < len; n++) {
            sendBuff[2 + n] = epc_arr[n];
        }
        sendBuff[2 + len] = mem;
        sendBuff[3 + len] = wordPtr;
        sendBuff[4 + len] = num;
        sendBuff[5 + len] = psd[0];
        sendBuff[6 + len] = psd[1];
        sendBuff[7 + len] = psd[2];
        sendBuff[8 + len] = psd[3];
        sendBuff[9 + len] = 0;//掩码区域，
        sendBuff[10 + len] = 0;
        sendBuff[11 + len] = 0;
        sendBuff[12 + len] = frmHandle;
        rec = tnt.ReadCard_G2(sendBuff);
        if (rec[0] == 0) {
            byte[] data = new byte[num * 2];
            for (int m = 0; m < num * 2; m++) {
                data[m] = (byte) rec[2 + m];
            }
            return byteToString(data);
        } else
            return "";
    }

    /**
     * 写数据
     *
     * @param epc     EPC号
     * @param wordPtr 写入地址
     * @param num     写入长度
     * @param data    写入数据
     * @param mem     写入区域
     * @param psd     访问密码
     */
    public int writeData(String epc, byte wordPtr, byte num, byte[] data, byte mem, byte[] psd) {
        int result;
        int len = epc.length() / 2;
        byte[] epc_arr;
        epc_arr = stringToByte(epc);
        setZero(sendBuff);
        setZero(rec);
        sendBuff[0] = fComAddr;
        // EPC字节长度
        sendBuff[1] = len;
        for (int n = 0; n < len; n++) {
            sendBuff[2 + n] = epc_arr[n];
        }
        sendBuff[2 + len] = mem;
        sendBuff[3 + len] = wordPtr;
        // 写入字节数
        sendBuff[4 + len] = num * 2;
        int wnum = num * 2;
        for (int p = 0; p < wnum; p++) {
            sendBuff[5 + len + p] = data[p];
        }
        sendBuff[5 + len + wnum] = psd[0];
        sendBuff[6 + len + wnum] = psd[1];
        sendBuff[7 + len + wnum] = psd[2];
        sendBuff[8 + len + wnum] = psd[3];
        sendBuff[9 + len + wnum] = 0;//掩码区域
        sendBuff[10 + len + wnum] = 0;
        sendBuff[11 + len + wnum] = 0;
        sendBuff[12 + len + wnum] = frmHandle;
        rec = tnt.WriteCard_G2(sendBuff);
        result = rec[0];
        return result;
    }

    /**
     * 写数据
     *
     * @param epc EPC号
     * @param psd 访问密码
     */
    public int writeEPC(String epc, byte[] psd) {
        int result = 0;
        int len = epc.length() / 2;
        byte[] epc_arr;
        epc_arr = stringToByte(epc);
        setZero(sendBuff);
        setZero(rec);
        sendBuff[0] = fComAddr;
        sendBuff[1] = len / 2;//EPC字长度
        for (int n = 0; n < len; n++) {
            sendBuff[2 + n] = epc_arr[n];
        }
        sendBuff[2 + len] = psd[0];
        sendBuff[3 + len] = psd[1];
        sendBuff[4 + len] = psd[2];
        sendBuff[5 + len] = psd[3];
        sendBuff[6 + len] = frmHandle;
        rec = tnt.WriteEPC_G2(sendBuff);
        result = rec[0];
        return result;
    }
}
