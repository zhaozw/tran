package com.renyu.nj_tran.receiver;

import cn.jpush.android.api.JPushInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class JPushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();		
        System.out.println("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            System.out.println("[MyReceiver] ����Registration Id : " + regId);
            //send the Registration Id to your server...                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	System.out.println("[MyReceiver] ���յ������������Զ�����Ϣ: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
        	System.out.println("[MyReceiver] ���յ�����������֪ͨ");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            System.out.println("[MyReceiver] ���յ�����������֪ͨ��ID: " + notifactionId);        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
        	System.out.println("[MyReceiver] �û��������֪ͨ");            
            JPushInterface.reportNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MSG_ID));
            
        	//���Զ����Activity
        	Intent i=new Intent(context, JPushActivity.class);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	context.startActivity(i);
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
        	System.out.println("[MyReceiver] �û��յ���RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //��������� JPushInterface.EXTRA_EXTRA �����ݴ������룬������µ�Activity�� ��һ����ҳ��..
        	
        } else {
        	System.out.println("[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// ��ӡ���е� intent extra ����
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
}