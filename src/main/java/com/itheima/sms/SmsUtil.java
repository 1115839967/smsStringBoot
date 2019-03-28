package com.itheima.sms;

import com.aliyuncs.DefaultAcsClient; 
import com.aliyuncs.IAcsClient; 
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest; 
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse; 
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest; 
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse; 
import com.aliyuncs.exceptions.ClientException; 
import com.aliyuncs.profile.DefaultProfile; 
import com.aliyuncs.profile.IClientProfile; 
import java.text.SimpleDateFormat; 
import java.util.Date; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.core.env.Environment; 
import org.springframework.stereotype.Component; 
/** 
 * ���Ź����� 
 * @author Administrator 
 * 
 */ 
@Component 
public class SmsUtil { 
 
    //��Ʒ����:��ͨ�Ŷ��� API ��Ʒ,�����������滻 
    static final String product = "Dysmsapi"; 
    //��Ʒ����,�����������滻 
    static final String domain = "dysmsapi.aliyuncs.com"; 
     
    @Autowired 
    private Environment env; 
 
    // TODO �˴���Ҫ�滻�ɿ������Լ��� AK(�ڰ����Ʒ��ʿ���̨Ѱ��) 
     
    /** 
     * ���Ͷ��� 
     * @param mobile �ֻ��� 
     * @param template_code ģ��� 
     * @param sign_name ǩ�� 
     * @param param ���� 
     * @return 
     * @throws ClientException 
     */ 
    public SendSmsResponse sendSms(String mobile,String template_code,String 
    									sign_name,String code) throws ClientException { 
 
     String accessKeyId =env.getProperty("accessKeyId"); 
        String accessKeySecret = env.getProperty("accessKeySecret"); 
      
        //������������ʱʱ�� 
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000"); 
        System.setProperty("sun.net.client.defaultReadTimeout", "10000"); 
 
        //��ʼ�� acsClient,�ݲ�֧�� region �� 
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, 
accessKeySecret); 
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain); 
        IAcsClient acsClient = new DefaultAcsClient(profile); 
 
        //��װ�������-��������������̨-�ĵ��������� 
        SendSmsRequest request = new SendSmsRequest();
        //����:�������ֻ��� 
        request.setPhoneNumbers(mobile); 
        //����:����ǩ��-���ڶ��ſ���̨���ҵ� 
        request.setSignName(sign_name); 
        //����:����ģ��-���ڶ��ſ���̨���ҵ� 
        request.setTemplateCode(template_code); 
        //��ѡ:ģ���еı����滻 JSON ��,��ģ������Ϊ"�װ���${name},������֤��Ϊ${code}"ʱ,�˴���ֵΪ 

        request.setTemplateParam("{\"code\":\""+code+"\"}"); 
 
        //ѡ��-���ж�����չ��(�����������û�����Դ��ֶ�) 
        //request.setSmsUpExtendCode("90997"); 
 
        //��ѡ:outId Ϊ�ṩ��ҵ����չ�ֶ�,�����ڶ��Ż�ִ��Ϣ�н���ֵ���ظ������� 
        request.setOutId("yourOutId"); 
 
        //hint �˴����ܻ��׳��쳣��ע�� catch 
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request); 
 
        return sendSmsResponse; 
    } 
 
    public  QuerySendDetailsResponse querySendDetails(String mobile,String bizId) 
throws ClientException { 
     String accessKeyId =env.getProperty("accessKeyId"); 
     String accessKeySecret = env.getProperty("accessKeySecret"); 
     //������������ʱʱ�� 
     System.setProperty("sun.net.client.defaultConnectTimeout", "10000"); 
     System.setProperty("sun.net.client.defaultReadTimeout", "10000"); 
     //��ʼ�� acsClient,�ݲ�֧�� region �� 
     IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, 
accessKeySecret); 
     DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain); 
     IAcsClient acsClient = new DefaultAcsClient(profile); 
     //��װ������� 
     QuerySendDetailsRequest request = new QuerySendDetailsRequest(); 
     //����-���� 
     request.setPhoneNumber(mobile); 
     //��ѡ-��ˮ�� 
     request.setBizId(bizId); 
     //����-�������� ֧�� 30 ���ڼ�¼��ѯ����ʽ yyyyMMdd 
     SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd"); 
     request.setSendDate(ft.format(new Date())); 
     //����-ҳ��С 
     request.setPageSize(10L); 
     //����-��ǰҳ��� 1 ��ʼ���� 
     request.setCurrentPage(1L); 
     //hint �˴����ܻ��׳��쳣��ע�� catch 
     QuerySendDetailsResponse querySendDetailsResponse =  acsClient.getAcsResponse(request); 
    		
     return querySendDetailsResponse; 
 } 
} 