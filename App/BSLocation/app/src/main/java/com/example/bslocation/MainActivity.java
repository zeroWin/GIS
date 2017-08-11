package com.example.bslocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthLte;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GSMCellLocationActivity";

    private TextView textView;
    private int dbm = -1;

    ArrayList<LteInfo> LteArray = new ArrayList<LteInfo>();

    public class LteInfo{
        String eNB_ID;
        String Cell_ID;
        String PCI;
        String dBm;
    }

    private void startLocation(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        EditText editText = (EditText)findViewById(R.id.edit_text);
        textView = (TextView)findViewById(R.id.text);

        // 获取基站信息
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                GetLteInfo(LteArray);
                SortLteArray(LteArray);
                LtePrint();

//                TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);



//                ArrayList<LteInfo> LteArray = new ArrayList<LteInfo>();
//                // 返回值MCC + MNC
//                String operator = mTelephonyManager.getNetworkOperator();
//                int mcc = Integer.parseInt(operator.substring(0, 3));
//                int mnc = Integer.parseInt(operator.substring(3));

//                CellInfoLte cellInfoLte_location = new CellInfoLte() ;
//                StringBuilder sb_lte = new StringBuilder("链接中LTE： "+"\n");
//                sb_lte.append("CID: " + cellInfoLte_location.getCellIdentity().getCi() + "--");
//                sb_lte.append("Pci：" + cellInfoLte_location.getCellIdentity().getPci() + "--");
//                sb_lte.append("RSSI：" + cellInfoLte_location.getCellSignalStrength().getDbm() + "\n\n");
//                CellInfoLte cellInfoLte_1 =(CellInfoLte) CellInfo;
//                cellInfoLte_1.getCellIdentity()
//                CellIdentityLte cellIdentityLte;
//                StringBuilder sb_lte = new StringBuilder("连接中LTE: " + "\n");
//                sb_lte.append("CID: " + cellIdentityLte.getCi());
//                sb_lte.append("Pci: " + cellIdentityLte.getPci());
//                sb_lte.append("RSSI: " + cellIdentityLte.)
//                cellLocation.getClass()
//                cellInfoLte.getCellSignalStrength().getClass().getMethod("getLteSignalStrength");
                // 中国移动和中国联通获取LAC、CID的方式
//                GsmCellLocation locationInfo = (GsmCellLocation) mTelephonyManager.getCellLocation();
//                StringBuilder sb_local = new StringBuilder("链接中："+"\n");
////                sb_local.append("ltestrength:"+ cellInfoLte_1.getCellSignalStrength().getClass().getMethod("getLteSignalStrength"+"\n"));
//                sb_local.append("CID: " + locationInfo.getCid()+"--");
//                sb_local.append("Psc: " + locationInfo.getPsc()+"\n\n");
//                sb_local.append("--------------");


//                List<CellInfo>cellInfoList = mTelephonyManager.getAllCellInfo();
//                StringBuilder stringBuilder = new StringBuilder("总数 : " + cellInfoList.size() + "\n");
//                if (null!=cellInfoList){
//                    for (CellInfo cellInfo:cellInfoList){
//                        if (cellInfo instanceof CellInfoGsm){
//                            CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
//                            stringBuilder.append("GSM --> ");
//                            stringBuilder.append("CID: " + cellInfoGsm.getCellIdentity().getCid()+"--");
//                            stringBuilder.append("RSRP: " + cellInfoGsm.getCellSignalStrength().getDbm()+"\n");
//                        } else if (cellInfo instanceof CellInfoWcdma){
//                            CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
//                            stringBuilder.append("WCDMA --> ");
//                            stringBuilder.append("CID: " + cellInfoWcdma.getCellIdentity().getCid()+"--");
//                            stringBuilder.append("RSRP: " + cellInfoWcdma.getCellSignalStrength().getDbm()+"\n");
//                        } else if (cellInfo instanceof CellInfoLte){
//                            CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
//                            LteInfo mLteInfo = new LteInfo();
////                            mLteInfo.eNB_ID
//                            mLteInfo.Cell_ID = String.valueOf(cellInfoLte.getCellIdentity().getCi());
//                            mLteInfo.PCI = String.valueOf(cellInfoLte.getCellIdentity().getPci());
//                            mLteInfo.dBm = String.valueOf(cellInfoLte.getCellSignalStrength().getDbm());
//                            LteArray.add(mLteInfo);
//
//                            stringBuilder.append("LTE --> ");
//                            stringBuilder.append("CID: " + mLteInfo.Cell_ID+"--");
//                            stringBuilder.append("Pci: " + mLteInfo.PCI+"--");
//                            stringBuilder.append("RSSi: " + mLteInfo.dBm+"\n");
//
//                        }
//                    }
//                }


//                textView.setText(sb_local.toString() + stringBuilder.toString());

                // 中国移动和中国联通获取LAC、CID的方式
//                GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
//                int lac = location.getLac();
//                int cellId = location.getCid();
//
//                String str = " MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId;
////                Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
////                textView.append(str);
////                Log.i(TAG, " MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId);
//
//                // 中国电信获取LAC、CID的方式
                /*CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                lac = location1.getNetworkId();
                cellId = location1.getBaseStationId();
                cellId /= 16;*/

                // 获取邻区基站信息
//                List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
//                StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");
////                StringBuffer sb = new StringBuffer("总数 : " + infos.size());
//
//                for (NeighboringCellInfo info1 : infos) { // 根据邻区总数进行循环
//                    sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
//                    sb.append(" CID : " + info1.getCid()); // 取出当前邻区的CID
//                    sb.append(" BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
//                }

//                textView.setText(String.valueOf(lac_1));
//                textView.setText(str+"\n"+sb.toString());//注释
//                Toast.makeText(MainActivity.this,sb.toString(),Toast.LENGTH_LONG).show();
//                Log.i(TAG, " 获取邻区基站信息:" + sb.toString());

            }
        });



    }

    private void GetLteInfo(ArrayList LteArray){

        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        LteArray.clear();
        CellLocation cel = mTelephonyManager.getCellLocation();
        int nPhoneType = mTelephonyManager.getPhoneType();
        //移动联通 GsmCellLocation
        if (nPhoneType == 1 && cel instanceof GsmCellLocation) {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cel;
            int nGSMCID = gsmCellLocation.getCid();
            int nPsc = gsmCellLocation.getPsc();
            int nmLac = gsmCellLocation.getLac();
            if (nGSMCID > 0) {
                if (nGSMCID != 65535) {
                    System.out.print(nGSMCID);
                }
            }
        }

        List<NeighboringCellInfo> cellneiInfoList = mTelephonyManager.getNeighboringCellInfo();
        StringBuilder stringBuilder1 = new StringBuilder("总数 : " + cellneiInfoList.size() + "\n");

        List<CellInfo>cellInfoList = mTelephonyManager.getAllCellInfo();
        StringBuilder stringBuilder = new StringBuilder("总数 : " + cellInfoList.size() + "\n");
        if (null!=cellInfoList){
            for (CellInfo cellInfo:cellInfoList){
                if (cellInfo instanceof CellInfoGsm){
                    CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                    stringBuilder.append("GSM --> ");
                    stringBuilder.append("CID: " + cellInfoGsm.getCellIdentity().getCid()+"--");
                    stringBuilder.append("RSRP: " + cellInfoGsm.getCellSignalStrength().getDbm()+"\n");
                } else if (cellInfo instanceof CellInfoWcdma){
                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
                    stringBuilder.append("WCDMA --> ");
                    stringBuilder.append("CID: " + cellInfoWcdma.getCellIdentity().getCid()+"--");
                    stringBuilder.append("RSRP: " + cellInfoWcdma.getCellSignalStrength().getDbm()+"\n");
                } else if (cellInfo instanceof CellInfoLte){
                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                    LteInfo mLteInfo = new LteInfo();

//                    int tmp = cellInfoLte.getCellIdentity().getCi();
//                    int tmp_cellid = tmp&0x0FF;
//                    int tmp_enbid = tmp&0x00FFFFF00;
//                    mLteInfo.Cell_ID = String.valueOf(tmp_cellid);
//                    mLteInfo.eNB_ID = String.valueOf(tmp_enbid);

                    mLteInfo.Cell_ID = String.valueOf(Integer.toBinaryString(cellInfoLte.getCellIdentity().getCi()));
                    mLteInfo.PCI = String.valueOf(cellInfoLte.getCellIdentity().getPci());
                    mLteInfo.dBm = String.valueOf(cellInfoLte.getCellSignalStrength().getDbm());
                    LteArray.add(mLteInfo);

                }
            }
        }
    }

    private void LtePrint(){
        StringBuilder lteStringBuilder = new StringBuilder();

        for(int i=0;i<LteArray.size();i++){
            LteInfo mLteInfo = LteArray.get(i);
//            lteStringBuilder.append("LTE-->").append("eNB_ID:").append(mLteInfo.eNB_ID).append("CID: ").append(mLteInfo.Cell_ID).append("--PCI:").append(mLteInfo.PCI).append("--dBm:").append(mLteInfo.dBm).append("\n");
            lteStringBuilder.append("LTE-->").append("CID: ").append(mLteInfo.Cell_ID).append("--PCI:").append(mLteInfo.PCI).append("--dBm:").append(mLteInfo.dBm).append("\n");
        }

        textView.setText(lteStringBuilder);

    }

    private void SortLteArray(ArrayList LteArray){
        Collections.sort(LteArray, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                LteInfo lteInfo1 = (LteInfo)o1;
                LteInfo lteInfo2 = (LteInfo)o2;
                if (Integer.valueOf(lteInfo1.dBm)<Integer.valueOf(lteInfo2.dBm)){
                    return 1;
                } else if (Integer.valueOf(lteInfo1.dBm)==Integer.valueOf(lteInfo2.dBm)) {
                    return 0;
                }else {
                    return -1;
                }
            }
        });
    }
}
