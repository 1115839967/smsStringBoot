package com.itheima.sms;

import java.util.Map; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.jms.annotation.JmsListener; 
import org.springframework.stereotype.Component; 
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse; 
import com.aliyuncs.exceptions.ClientException; 
/** 
 * ��Ϣ������ 
 * @author Administrator 
 */ 
@Component 
public class SmsListener { 
 @Autowired 
 private SmsUtil smsUtil; 
 @JmsListener(destination="sms") 
 public void sendSms(Map<String,String> map){   
	 
	 System.out.println(map.get("mobile"));
	 System.out.println(map.get("template_code"));
	 System.out.println(map.get("sign_name"));
	 System.out.println(map.get("code"));
  try { 
   SendSmsResponse response = smsUtil.sendSms( 
     map.get("mobile"),  
     map.get("template_code"), 
     map.get("sign_name"), 
     map.get("code")  );       
       System.out.println("Code=" + response.getCode()); 
          System.out.println("Message=" + response.getMessage()); 
          System.out.println("RequestId=" + response.getRequestId()); 
          System.out.println("BizId=" + response.getBizId());    
  } catch (ClientException e) { 
   e.printStackTrace();    
  }   
 } 
}