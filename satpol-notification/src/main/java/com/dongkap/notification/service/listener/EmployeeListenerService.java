package com.dongkap.notification.service.listener;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongkap.common.stream.CommonStreamListener;
import com.dongkap.common.utils.ParameterStatic;
import com.dongkap.common.utils.StreamKeyStatic;
import com.dongkap.dto.common.CommonStreamMessageDto;
import com.dongkap.dto.notification.MailNotificationDto;
import com.dongkap.notification.service.MailSenderImplService;

import lombok.SneakyThrows;

@Service
public class EmployeeListenerService extends CommonStreamListener<CommonStreamMessageDto> {

	@Autowired
	private MailSenderImplService mailSenderService;

	@Value("${dongkap.locale}")
	private String localeCode;


    public EmployeeListenerService(
    		@Value("${spring.application.name}") String appName,
    		@Value("${spring.application.name}") String groupId) {
		super(appName, groupId, StreamKeyStatic.EMPLOYEE, CommonStreamMessageDto.class);
	}
	
	@Override
    @SneakyThrows
    @Transactional
	public void onMessage(ObjectRecord<String, CommonStreamMessageDto> message) {
		try {
	        String stream = message.getStream();
	        RecordId id = message.getId();
			LOGGER.info("A message was received stream: [{}], id: [{}]", stream, id);
	        CommonStreamMessageDto value = message.getValue();
	        if(value != null) {
	        	for(Object data: value.getDatas()) {
		        	if(data instanceof MailNotificationDto) {
		        		MailNotificationDto request = (MailNotificationDto) data;
		        		if(value.getStatus().equalsIgnoreCase(ParameterStatic.INSERT_DATA)) {
		        			this.sendMail(request);
		        		}
		        	}
		        }
	        }
		} catch (Exception e) {
			LOGGER.warn("Stream On Message : {}", e.getMessage());
		}
	}

	public void sendMail(MailNotificationDto request) {
		try {
			Locale locale = Locale.getDefault();
			if(request.getLocale() != null) {
				locale = Locale.forLanguageTag(request.getLocale());
			} else {
				locale = Locale.forLanguageTag(localeCode);
			}
			this.mailSenderService.sendMessageWithTemplate(request, locale);
		} catch (Exception e) {
			LOGGER.warn("Stream Send Mail : {}", e.getMessage());
		}
	}

}
